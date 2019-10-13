package com.mantledillusion.data.epiphy.object.model;

public final class ObjectModel {
	
	private String modelId;
	private ObjectSubType sub;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public ObjectSubType getSub() {
		return sub;
	}

	public void setSub(ObjectSubType sub) {
		this.sub = sub;
	}
}