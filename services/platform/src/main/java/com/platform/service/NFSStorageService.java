package com.platform.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.http.util.Asserts;
import com.platform.util.FileUtil;

public class NFSStorageService implements AbstractStorage {

	private static NFSStorageService instance;

	private NFSStorageService(String nfsMountPath, String serviceName) throws IOException {
		Files.createDirectories(Paths.get(FileUtil.sanitizeDirPath(nfsMountPath) + serviceName));
	}
	
	public static void init(String nfsMountPath, String serviceName) throws IOException {
		if (instance == null) {
			synchronized (NFSStorageService.class) {
				instance = new NFSStorageService(nfsMountPath, serviceName);
			}
		}
	}
	
	public static NFSStorageService getInstance() {
		Asserts.notNull(instance, "NFS Instance is not initialised");
		return instance;
	}

	@Override
	public File readFile(Optional<?> blobId) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String saveFile(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String saveFile(File file, String dir) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String saveFile(File file, boolean isInternalOnly) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
