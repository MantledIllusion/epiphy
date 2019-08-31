package com.mantledillusion.data.epiphy.mixed.model;

import java.util.ArrayList;
import java.util.List;

public class MixedSubType {

	private String subId;
	private final List<MixedModelNode> subNodes = new ArrayList<>();

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public List<MixedModelNode> getSubNodes() {
		return subNodes;
	}
}
