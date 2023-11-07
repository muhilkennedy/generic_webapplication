package com.platform.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.google.cloud.storage.BlobId;
import com.platform.messages.StoreType;

/**
 * @author Muhil
 *
 */
public class StorageService implements AbstractStorage {

	private static StorageService service = new StorageService();
	private StoreType defaultType;
	private AbstractStorage storageService;

	private StorageService() {
		// Singleton instance.
	}

	public void initDefaultStore(StoreType deafultType) {
		this.defaultType = deafultType;
		switch (defaultType) {
		case GCP:
			this.storageService = GoogleStorageService.getInstance();
			break;
		case NFS:
			this.storageService = NFSStorageService.getInstance();
			break;
		}
	}

	/**
	 * @return default storage instance
	 */
	public static StorageService getStorage() {
		return service;
	}

	public AbstractStorage getStorage(StoreType type) {
		switch (type) {
		case GCP:
			return GoogleStorageService.getInstance();
		case NFS:
			return NFSStorageService.getInstance();
		}
		return null;
	}

	@Override
	public File readFile(Optional<?> blobId) throws IOException {
		return storageService.readFile(blobId);
	}

	@Override
	public String saveFile(File file) throws IOException {
		return storageService.saveFile(file);
	}

	@Override
	public String saveFile(File file, String dir) throws IOException {
		return storageService.saveFile(file, dir);
	}

	@Override
	public Object saveFile(File file, boolean isInternalOnly) throws IOException {
		return storageService.saveFile(file, isInternalOnly);
	}

	@Override
	public Object saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		return storageService.saveFile(file, isInternalOnly);
	}

}
