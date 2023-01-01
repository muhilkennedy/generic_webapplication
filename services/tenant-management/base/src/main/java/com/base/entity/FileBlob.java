package com.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.BlobId;
import com.google.gson.Gson;

/**
 * @author Muhil Kennedy
 * Store FileBlob details and
 */
@Entity
@Table(name = "FILEBLOB")
@ClassMetaProperty(code = "FB")
public class FileBlob extends DefaultBaseEntity {

	@Column(name = "BLOBINFO")
	private String blobInfo;

	@Column(name = "MEDIAURL")
	private String url;

	@Column(name = "STORETYPE")
	private String storeType;

	public String getBlobInfo() {
		return blobInfo;
	}

	public void setBlobInfo(String blobInfo) {
		this.blobInfo = blobInfo;
	}
	
	public void setBlobInfo(BlobId blobId) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		this.setBlobInfo(mapper.writeValueAsString(blobId));
	}

	public BlobId getBlobInfoId() {
		Gson gson = new Gson();
		return gson.fromJson(this.getBlobInfo(), BlobId.class);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

}
