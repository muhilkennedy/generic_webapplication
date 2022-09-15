package com.base.service;

import com.base.entity.BaseObject;

/**
 * @author Muhil
 *
 */
public interface BaseService {

	public Object save(BaseObject obj);

	public Object saveAndFlush(BaseObject obj);

	public Object findById(Object rootId);
	
	public void delete(BaseObject obj);

}
