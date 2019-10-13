package com.mantledillusion.data.epiphy.object.model;

public final class ObjectSubType {
	
	private String subId;
	private ObjectSubSubType subSub;

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public ObjectSubSubType getSubSub() {
		return subSub;
	}

	public void setSubSub(ObjectSubSubType subSub) {
		this.subSub = subSub;
	}
}