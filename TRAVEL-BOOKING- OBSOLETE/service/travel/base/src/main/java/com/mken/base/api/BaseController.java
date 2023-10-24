package com.mken.base.api;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mken.base.service.FileStoreService;
import com.platform.util.FileUtil;

import jakarta.servlet.http.HttpServletRequest;


/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("base")
public class BaseController {
	
	@Autowired
	private FileStoreService fsService;
	
	@GetMapping(value = "/file/{fileId}", produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> getFile(HttpServletRequest request, @PathVariable long fileId) throws IOException {
		File file = fsService.getFileById(fileId);
		try {
			byte[] byteArray = FileUtils.readFileToByteArray(file);
			return ResponseEntity.status(HttpStatus.OK).contentType(
					org.springframework.http.MediaType.valueOf(FileUtil.findContentTypeFromFileName(file.getName())))
					.body(byteArray);
		} finally {
			FileUtil.deleteDirectoryOrFile(file);
		}
	}
}
