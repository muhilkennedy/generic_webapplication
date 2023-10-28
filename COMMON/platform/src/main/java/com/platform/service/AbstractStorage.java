package com.platform.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Muhil
 *
 */
public interface AbstractStorage {

	File readFile(Optional<?> blobId) throws IOException;

	String saveFile(File file) throws IOException;

	String saveFile(File file, String dir) throws IOException;

	Object saveFile(File file, boolean isInternalOnly) throws IOException;

	Object saveFile(File file, String dir, boolean isInternalOnly) throws IOException;
	
	String getConfigKey();
	
	String getDefaultBucketKey();
	
	default void updateTenantConfig(Long tenantId, String gcpConfig, String gcpBucket) throws IOException{
		//NO-OP
	}
	
}
