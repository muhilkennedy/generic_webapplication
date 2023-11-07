package com.platform.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.util.Asserts;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import com.google.cloud.storage.StorageOptions;
import com.platform.messages.StoreType;
import com.platform.session.PlatformBaseSession;
import com.platform.util.FileUtil;
import com.platform.util.Log;

/**
 * @author Muhil GCS impl
 */
public class GoogleStorageService implements AbstractStorage {

	private static GoogleStorageService instance;

	private Credentials credentials;
	private Storage storage;
	private String bucketName;

	private Map<Long, StorageConfig> tenantStorages = new HashMap<Long, GoogleStorageService.StorageConfig>();

	private GoogleStorageService(Map<Long, StorageConfig> storages) {
		this.tenantStorages = storages;
	}

	private GoogleStorageService(File gcpConfigFile, String bucketName) throws IOException {
		this.credentials = GoogleCredentials.fromStream(new FileInputStream(gcpConfigFile));
		this.storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("master").build()
				.getService();
		this.bucketName = bucketName;
	}

	public static void init(File gcpConfigFile, String bucketName) throws IOException {
		if (instance == null) {
			synchronized (GoogleStorageService.class) {
				instance = new GoogleStorageService(gcpConfigFile, bucketName);
			}
		}
	}

	public static void initForTenants(Map<Long, String> gcpConfig, Map<Long, String> gcpBucket) throws IOException {
		if (instance == null) {
			synchronized (GoogleStorageService.class) {
				Map<Long, StorageConfig> gcpStores = new HashMap<Long, StorageConfig>();
				for (Map.Entry<Long, String> entry : gcpConfig.entrySet()) {
					gcpStores.put(entry.getKey(),
							createStorageConfig(entry.getKey(), entry.getValue(), gcpBucket.get(entry.getKey())));
				}
				instance = new GoogleStorageService(gcpStores);
			}
		}
	}
	
	public void updateTenantConfig(Long tenantId, String gcpConfig, String gcpBucket) throws IOException {
		synchronized (GoogleStorageService.class) {
			this.tenantStorages.put(tenantId, createStorageConfig(tenantId, gcpConfig, gcpBucket));
		}
	}
	
	private static StorageConfig createStorageConfig(Long tenantId, String gcpConfig, String gcpBucket) throws IOException {
		byte[] bytes = Base64.getDecoder().decode(gcpConfig);
		Credentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(bytes));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
				.setProjectId(tenantId.toString()).build().getService();
		StorageConfig storageConfig = new StorageConfig();
		storageConfig.setStorage(storage);
		storageConfig.setDefaultBucket(gcpBucket);
		return storageConfig;
	}

	public static GoogleStorageService getInstance() {
		Asserts.notNull(instance, "Google Instance is not initialized");
		return instance;
	}
	
	private Storage storage() {
		if (!tenantStorages.isEmpty()) {
			return tenantStorages.get(PlatformBaseSession.getTenantId()).getStorage();
		}
		return storage;
	}

	private String bucket() {
		if (!tenantStorages.isEmpty()) {
			return tenantStorages.get(PlatformBaseSession.getTenantId()).getDefaultBucket();
		}
		return bucketName;
	}

	@Override
	public File readFile(Optional<?> blobId) throws IOException {
		if (blobId.isPresent()) {
			if (blobId.get() instanceof BlobId) {
				BlobId bId = (BlobId) blobId.get();
				Blob blob = storage().get(bId);
				File file = File.createTempFile(PlatformBaseSession.getTenantUniqueName(),
						"." + FilenameUtils.getExtension(blob.getName()));
				blob.downloadTo(file.toPath());
				return file;
			}
		}
		return null;
	}

	@Override
	public String saveFile(File file) throws IOException {
		BlobId blobId = BlobId.of(bucket(),
				PlatformBaseSession.getTenantUniqueName() + File.separator + file.getName());
		return uploadFile(blobId, file, false).getMediaLink();
	}

	@Override
	public String saveFile(File file, String dir) throws IOException {
		BlobId blobId = BlobId.of(bucket(),
				PlatformBaseSession.getTenantUniqueName().concat(FileUtil.sanitizeDirPath(dir)).concat(file.getName()));
		return uploadFile(blobId, file, false).getMediaLink();
	}

	@Override
	public BlobId saveFile(File file, boolean isInternalOnly) throws IOException {
		BlobId blobId = BlobId.of(bucket(),
				PlatformBaseSession.getTenantUniqueName() + File.separator + file.getName());
		uploadFile(blobId, file, isInternalOnly);
		return blobId;
	}

	@Override
	public BlobId saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		BlobId blobId = BlobId.of(bucket(),
				PlatformBaseSession.getTenantUniqueName().concat(FileUtil.sanitizeDirPath(dir)).concat(file.getName()));
		uploadFile(blobId, file, false);
		return blobId;
	}

	private Blob uploadFile(BlobId blobId, File file, boolean isInternalOnly) throws IOException {
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
		Blob blob = this.storage().create(blobInfo, FileUtils.readFileToByteArray(file),
				BlobTargetOption.predefinedAcl(isInternalOnly ? PredefinedAcl.PRIVATE : PredefinedAcl.PUBLIC_READ));
		Log.platform.info("Uploded file to GCS : " + blob.getMediaLink());
		return blob;
	}

	public static class StorageConfig {

		private Storage storage;
		private String defaultBucket;
		
		public StorageConfig() {
			
		}

		public Storage getStorage() {
			return storage;
		}

		public void setStorage(Storage storage) {
			this.storage = storage;
		}

		public String getDefaultBucket() {
			return defaultBucket;
		}

		public void setDefaultBucket(String defaultBucket) {
			this.defaultBucket = defaultBucket;
		}

	}

	@Override
	public String getConfigKey() {
		return StoreType.constants.GCPCONFIG.name();
	}

	@Override
	public String getDefaultBucketKey() {
		return StoreType.constants.GCPBUCKET.name();
	}

}
