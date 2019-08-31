package com.mantledillusion.data.epiphy.node;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.node.model.NodeModel;

public interface NodeModelProperties {

	ModelProperty<NodeModel, NodeModel> RETRIEVER = ModelProperty.fromObject("retriever", NodeModel::getChild, NodeModel::setChild);
	ModelPropertyNode<NodeModel, NodeModel> NODE = ModelPropertyNode.from("node", RETRIEVER);
}