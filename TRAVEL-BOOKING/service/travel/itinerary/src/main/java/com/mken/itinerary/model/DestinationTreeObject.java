package com.mken.itinerary.model;

import java.util.List;

/**
 * @author Muhil
 *
 */
public class DestinationTreeObject {
	
	private String name;
	private Long rootId;
	private List<DestinationTreeObject> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public List<DestinationTreeObject> getChildren() {
		return children;
	}

	public void setChildren(List<DestinationTreeObject> children) {
		this.children = children;
	}
}
