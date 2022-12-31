package com.base.serviceimpl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.base.service.BaseSession;
import com.base.service.StorageService;
import com.base.util.FileUtil;
import com.base.util.PropertiesUtil;
import com.google.api.client.util.Value;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("NFS")
public class NFSServiceImpl implements StorageService {
	
	@Autowired
	private BaseSession baseSession;

	@Value("${app.gcs.bucket.default}")
	private String defaultBucketName;
	
	@Override
	public File readFile(String filePath) throws IOException  {
		return new File(filePath);
	}

	@Override
	public String saveFile(File file) throws IOException {
		File newFile = new File(PropertiesUtil.getDefaultDirectory()
				+ (StringUtils.isNotBlank(baseSession.getTenantId()) ? baseSession.getTenantId() : "admin")
				+ File.separator + file.getName());
		FileUtils.copyFile(file, newFile);
		return newFile.getPath();
	}

	@Override
	public String saveFile(File file, boolean isInternalOnly) throws IOException {
		//TODO: encrypt file in disk - decrypt while reading.
		return saveFile(file);
	}

	@Override
	public String saveFile(File file, String dir) throws IOException {
		File newFile = new File(PropertiesUtil.getDefaultDirectory()
				+ (StringUtils.isNotBlank(baseSession.getTenantId()) ? baseSession.getTenantId() : "admin")
						.concat(FileUtil.sanitizeDirPath(dir)).concat(file.getName()));
		FileUtils.copyFile(file, newFile);
		return newFile.getPath();
	}

	@Override
	public String saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		//TODO: encrypt file in disk - decrypt while reading.
		return saveFile(file, dir);
	}


}
