package com.platform.entity;
/**
 * @author Muhil
 * Parent interface for all entities.
 */
public interface BaseObject {
	default String getObjectId() {
		return null;
	}
}
