package com.base.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.docx4j.com.microsoft.schemas.office.drawing.x201611.diagram.STSTorageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.base.entity.ConfigType;
import com.base.service.ConfigurationService;
import com.base.util.Log;
import com.base.util.PropertiesUtil;
import com.platform.exception.CryptoException;
import com.platform.messages.ConfigurationType;
import com.platform.messages.StoreType;
import com.platform.service.GoogleStorageService;
import com.platform.service.NFSStorageService;
import com.platform.service.StorageService;
import com.platform.util.FileCryptoUtil;

import jakarta.annotation.PostConstruct;

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
	
	@Autowired
	private ConfigurationService configService;

	@PostConstruct
	private void setUpStorageServices() throws IOException, CryptoException {
		//ResourceUtils.getFile("classpath:gcs/config.json")
		if(PropertiesUtil.isProdDeployment()) {
			List<ConfigType> storageConfigs = configService.findByConfigNameAndType(StoreType.constants.GCPCONFIG.name(), ConfigurationType.STORAGE);
			List<ConfigType> bucketConfigs = configService.findByConfigNameAndType(StoreType.constants.GCPBUCKET.name(), ConfigurationType.STORAGE);
			Map<Long, String> storageMap = storageConfigs.stream().collect(
                    Collectors.toMap(ConfigType::getTenantid, ConfigType::getVal));
			Map<Long, String> bucketMap = bucketConfigs.stream().collect(
                    Collectors.toMap(ConfigType::getTenantid, ConfigType::getVal));
			GoogleStorageService.initForTenants(storageMap, bucketMap);
			NFSStorageService.init(nfsPath, appName);
			StorageService.getStorage().initDefaultStore(StoreType.GCP);
			Log.base.info("----- PROD Storage Service intialized -----");
		}
		else {
			InputStream is = getClass().getResourceAsStream("/gcs/config.json");
			File tempFile = File.createTempFile("config", ".json");
			FileUtils.copyInputStreamToFile(is, tempFile);
			File decryptedFile = FileCryptoUtil.decrypt(
					new String(Base64.getDecoder().decode(PropertiesUtil.getFileSecret()), StandardCharsets.UTF_8),
					tempFile);
			GoogleStorageService.init(decryptedFile, defaultGcsbucket);
			NFSStorageService.init(nfsPath, appName);
			StorageService.getStorage().initDefaultStore(StoreType.GCP); 
			Log.base.info("----- DEV Storage Service intialized -----");
		}
		
	}

}
