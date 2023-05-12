package com.mken.base.storage.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mken.base.util.PropertiesUtil;
import com.platform.exception.CryptoException;
import com.platform.messages.StoreType;
import com.platform.service.GoogleStorageService;
import com.platform.service.NFSStorageService;
import com.platform.service.StorageService;
import com.platform.util.FileCryptoUtil;
import com.platform.util.Log;

/**
 * @author Muhil
 * Initialize google storage objects.
 */
@Configuration
public class StorageConfiguration {
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Value("${app.nfs.path}")
	private String nfsPath;
	
	@Value("${app.gcs.bucket.default}")
	private String defaultGcsbucket;

	@PostConstruct
	private void setUpStorageServices() throws IOException, CryptoException {
		//ResourceUtils.getFile("classpath:gcs/config.json")
		InputStream is = getClass().getResourceAsStream("/gcs/config.json");
		File tempFile = File.createTempFile("config", ".json");
	    FileUtils.copyInputStreamToFile(is, tempFile);
		File decryptedFile = FileCryptoUtil.decrypt(PropertiesUtil.getFileSecret(), tempFile);
		GoogleStorageService.init(decryptedFile, defaultGcsbucket);
		NFSStorageService.init(nfsPath, appName);
		StorageService.getStorage().initDefaultStore(StoreType.GCP);
		Log.platform.info("Storage Service intialized");
	}

}
