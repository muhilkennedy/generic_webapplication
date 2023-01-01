package com.base.service;

import java.io.File;
import java.io.IOException;

import com.google.cloud.storage.BlobId;

/**
 * @author Muhil
 *
 */
public interface StorageService {
	
	default public StorageService getStorageService() {
		return null;
	}

	default public File readFile(BlobId blobId) throws IOException {
		return null;
	}

	default public File readFile(String filePath) throws IOException {
		return null;
	}

	String saveFile(File file) throws IOException;
	
	String saveFile(File file, String dir) throws IOException;

	String saveFile(File file, boolean isInternalOnly) throws IOException;
	
	String saveFile(File file, String dir, boolean isInternalOnly) throws IOException;

}
