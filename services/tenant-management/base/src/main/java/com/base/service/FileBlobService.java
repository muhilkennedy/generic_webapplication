package com.base.service;

import com.base.entity.FileBlob;

/**
 * @author Muhil
 *
 */
public interface FileBlobService extends BaseService {

	String findURLByImageId(long id);
	
}
