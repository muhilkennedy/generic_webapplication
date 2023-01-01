package com.base.api;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.annotation.Loggable;
import com.base.annotation.ValidateUserToken;
import com.base.security.Permissions;
import com.base.service.CacheService;
import com.base.service.FileBlobService;
import com.base.util.FileUtil;
import com.base.util.Log;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("base")
public class BaseController {
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private FileBlobService fileBlobService;

	@ValidateUserToken(permissions = { Permissions.SUPER_USER })
	@Loggable(message = "Clear Cache", perf = true)
	@RequestMapping(value = "/cache", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	public Response clearCache(HttpServletRequest request) {
		Log.base.info("Evicting cache objects");
		cacheService.evictAll();
		return Response.ok().build();
	}

	/**
	 * @param request
	 * @param imageId
	 * @return load image from file system if present
	 * @throws IOException
	 */
	@RequestMapping(value = "/file/{imageId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> getFile(HttpServletRequest request, @PathVariable long imageId) throws IOException {
		File file = new File(fileBlobService.findURLByImageId(imageId));
		byte[] byteArray = FileUtils.readFileToByteArray(file);
		return ResponseEntity.status(HttpStatus.OK).contentType(
				org.springframework.http.MediaType.valueOf(FileUtil.findContentTypeFromFileName(file.getName())))
				.body(byteArray);
	}
	
}
