package com.base.serviceimpl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.base.service.StorageService;
import com.google.cloud.storage.BlobId;

/**
 * @author Muhil
 * Default store is NFS. Diff stores can be enabled accordingly.
 */
@Service
@Primary
@Qualifier("FileStore")
public class StorageServiceImpl implements StorageService {

	@Value("${app.gcs.enabled}")
	private boolean useGCS;

	@Autowired
	private GoogleStorageServiceImpl gcpService;

	@Autowired
	private NFSServiceImpl nfsService;
	
	public StorageService getStorageService() {
		if (useGCS) {
			return gcpService;
		} else {
			return nfsService;
		}
	}

	@Override
	public String saveFile(File file) throws IOException {
		if (useGCS) {
			return gcpService.saveFile(file);
		} else {
			return nfsService.saveFile(file);
		}
	}

	@Override
	public String saveFile(File file, boolean isInternalOnly) throws IOException {
		if (useGCS) {
			return gcpService.saveFile(file, isInternalOnly);
		} else {
			return nfsService.saveFile(file, isInternalOnly);
		}
	}

	@Override
	public File readFile(BlobId blobId) throws IOException {
		if (useGCS) {
			return gcpService.readFile(blobId);
		} else {
			return nfsService.readFile(blobId);
		}
	}

	@Override
	public File readFile(String filePath) throws IOException {
		if (useGCS) {
			return gcpService.readFile(filePath);
		} else {
			return nfsService.readFile(filePath);
		}
	}

	@Override
	public String saveFile(File file, String dir) throws IOException {
		if (useGCS) {
			return gcpService.saveFile(file, dir);
		} else {
			return nfsService.saveFile(file, dir);
		}
	}

	@Override
	public String saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		if (useGCS) {
			return gcpService.saveFile(file, dir, isInternalOnly);
		} else {
			return nfsService.saveFile(file, dir, isInternalOnly);
		}
	}

}
