package com.mantledillusion.data.epiphy.mixed.model;

public class MixedModelNode {

	private String nodeId;
	private MixedSubType sub;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String modelId) {
		this.nodeId = modelId;
	}

	public MixedSubType getSub() {
		return sub;
	}

	public void setSub(MixedSubType sub) {
		this.sub = sub;
	}
}
