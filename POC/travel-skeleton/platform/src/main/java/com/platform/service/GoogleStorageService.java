package com.platform.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
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
import com.platform.session.PlatformBaseSession;
import com.platform.util.FileUtil;
import com.platform.util.Log;

/**
 * @author Muhil
 * GCS impl
 */
public class GoogleStorageService implements AbstractStorage {

	private static GoogleStorageService instance;

	private final Credentials credentials;
	private final Storage storage;
	private final String bucketName;

	private GoogleStorageService(File gcpConfigFile, String bucketName) throws IOException {
		// enrypt json file
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

	public static GoogleStorageService getInstance() {
		Asserts.notNull(instance, "Google Instance is not initialized");
		return instance;
	}

	@Override
	public File readFile(Optional<?> optional) throws IOException {
		if (optional.isPresent()) {
			if (optional.get() instanceof BlobId) {
				BlobId bId = (BlobId) optional.get();
				Blob blob = storage.get(bId);
				File file = File.createTempFile(PlatformBaseSession.getTenant().getTenantUniqueName(),
						"." + FilenameUtils.getExtension(blob.getName()));
				blob.downloadTo(file.toPath());
				return file;
			}
		}
		return null;
	}

	@Override
	public String saveFile(File file) throws IOException {
		BlobId blobId = BlobId.of(bucketName,
				PlatformBaseSession.getTenant().getTenantUniqueName() + File.separator + file.getName());
		return uploadFile(blobId, file, false).getMediaLink();
	}

	@Override
	public String saveFile(File file, String dir) throws IOException {
		BlobId blobId = BlobId.of(bucketName, PlatformBaseSession.getTenant().getTenantUniqueName()
				.concat(FileUtil.sanitizeDirPath(dir)).concat(file.getName()));
		return uploadFile(blobId, file, false).getMediaLink();
	}

	@Override
	public BlobId saveFile(File file, boolean isInternalOnly) throws IOException {
		BlobId blobId = BlobId.of(bucketName,
				PlatformBaseSession.getTenant().getTenantUniqueName() + File.separator + file.getName());
		uploadFile(blobId, file, isInternalOnly);
		return blobId;
	}

	@Override
	public BlobId saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		BlobId blobId = BlobId.of(bucketName, PlatformBaseSession.getTenant().getTenantUniqueName()
				.concat(FileUtil.sanitizeDirPath(dir)).concat(file.getName()));
		uploadFile(blobId, file, false);
		return blobId;
	}

	private Blob uploadFile(BlobId blobId, File file, boolean isInternalOnly) throws IOException {
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
		Blob blob = this.storage.create(blobInfo, FileUtils.readFileToByteArray(file),
				BlobTargetOption.predefinedAcl(isInternalOnly ? PredefinedAcl.PRIVATE : PredefinedAcl.PUBLIC_READ));
		Log.platform.info("Uploded file to GCS : " + blob.getMediaLink());
		return blob;
	}

}
