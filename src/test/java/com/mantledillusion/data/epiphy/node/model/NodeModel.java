package com.mantledillusion.data.epiphy.node.model;

public class NodeModel {

	private String id;
	private NodeModel child;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public NodeModel getChild() {
		return child;
	}

	public void setChild(NodeModel child) {
		this.child = child;
	}
}
