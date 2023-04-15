package com.mken.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "FILEBLOB")
@ClassMetaProperty(code = "FB")
public class FileBlob extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "MEDIAURL")
	private String mediaurl;

	@Column(name = "STORETYPE")
	private String storetype;
	
	@JsonIgnore
	@Column(name = "BLOBINFO")
	private String blobinfo;

	public String getMediaurl() {
		return mediaurl;
	}

	public void setMediaurl(String mediaurl) {
		this.mediaurl = mediaurl;
	}

	public String getStoretype() {
		return storetype;
	}

	public void setStoretype(String storetype) {
		this.storetype = storetype;
	}

	public String getBlobinfo() {
		return blobinfo;
	}

	public void setBlobinfo(String blobinfo) {
		this.blobinfo = blobinfo;
	}

}
