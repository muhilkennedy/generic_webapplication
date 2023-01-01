package com.base.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.base.service.BaseSession;
import com.base.service.StorageService;
import com.base.util.FileUtil;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import com.google.cloud.storage.StorageOptions;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("GCS")
public class GoogleStorageServiceImpl implements StorageService {

	private final Credentials credentials;
	private final Storage storage;

	@Autowired
	private BaseSession baseSession;

	@Value("${app.gcs.bucket.default}")
	private String defaultBucketName;

	public GoogleStorageServiceImpl() throws FileNotFoundException, IOException {
		this.credentials = GoogleCredentials
				.fromStream(new FileInputStream(ResourceUtils.getFile("classpath:gcs/config.json")));
		this.storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("master").build()
				.getService();
	}

	public Storage getStorage() {
		return storage;
	}
	
	private String uploadFile(BlobId blobId, File file, boolean isInternalOnly) throws IOException {
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
		Blob blob = this.getStorage().create(blobInfo, FileUtils.readFileToByteArray(file),
				BlobTargetOption.predefinedAcl(isInternalOnly ? PredefinedAcl.PRIVATE : PredefinedAcl.PUBLIC_READ));
		return blob.getMediaLink();
	}

	@Override
	public String saveFile(File file) throws IOException {
		return saveFile(file, false);
	}

	@Override
	public String saveFile(File file, boolean isInternalOnly) throws IOException {
		BlobId blobId = BlobId.of(defaultBucketName, baseSession.getTenantId() + File.separator + file.getName());
		return uploadFile(blobId, file, isInternalOnly);
	}

	@Override
	public File readFile(BlobId blobId) throws IOException {
		Blob bb = storage.get(blobId);
		File file = File.createTempFile(baseSession.getTenantId(), "." + FilenameUtils.getExtension(bb.getName()));
		bb.downloadTo(file.toPath());
		return file;
	}

	@Override
	public String saveFile(File file, String dir) throws IOException {
		BlobId blobId = BlobId.of(defaultBucketName,
				baseSession.getTenantId().concat(FileUtil.sanitizeDirPath(dir)).concat(file.getName()));
		return uploadFile(blobId, file, false);
	}

	@Override
	public String saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		BlobId blobId = BlobId.of(defaultBucketName,
				baseSession.getTenantId().concat(FileUtil.sanitizeDirPath(dir)).concat(file.getName()));
		return uploadFile(blobId, file, isInternalOnly);
	}

}




/*Storage.BlobTargetOption precondition;
if (storage.get(bucketName, objectName) == null) {
  // For a target object that does not yet exist, set the DoesNotExist precondition.
  // This will cause the request to fail if the object is created before the request runs.
  precondition = Storage.BlobTargetOption.doesNotExist();
} else {
  // If the destination already exists in your bucket, instead set a generation-match
  // precondition. This will cause the request to fail if the existing object's generation
  // changes before the request runs.
  precondition =
      Storage.BlobTargetOption.generationMatch(
          storage.get(bucketName, objectName).getGeneration());
}*/

//BlobTargetOption[] option = new BlobTargetOption[]{BlobTargetOption.predefinedAcl(PredefinedAcl.PUBLIC_READ)};
//.setContentDisposition(String.format("inline; filename=\"%s\"", file.getName()))
