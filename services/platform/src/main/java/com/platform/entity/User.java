package com.platform.entity;

public class User implements BaseObject {
	
	private String rootId;
	
	@Override
	public String getObjectId() {
		return this.rootId;
	}
	
}
