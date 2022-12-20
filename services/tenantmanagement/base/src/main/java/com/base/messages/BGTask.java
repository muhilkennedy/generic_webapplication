package com.base.messages;

public class BGTask {

	private String realmRootId;
	private boolean allRealms;
	private Object bgWork;

	public String getRealmRootId() {
		return realmRootId;
	}

	public void setRealmRootId(String realmRootId) {
		this.realmRootId = realmRootId;
	}

	public boolean isAllRealms() {
		return allRealms;
	}

	public void setAllRealms(boolean allRealms) {
		this.allRealms = allRealms;
	}

	public Object getBgWork() {
		return bgWork;
	}

	public void setBgWork(Object bgWork) {
		this.bgWork = bgWork;
	}

}
