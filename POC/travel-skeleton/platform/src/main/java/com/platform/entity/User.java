package com.platform.entity;

/**
 * @author Muhil
 * generic user object
 */
public class User implements BaseObject {
	
	private String rootId;
	
	@Override
	public String getObjectId() {
		return this.rootId;
	}
	
}
