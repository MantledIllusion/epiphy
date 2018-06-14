package com.mantledillusion.data.epiphy.node;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.ReadOnlyModelProperty;
import com.mantledillusion.data.epiphy.node.model.NodeModel;
import com.mantledillusion.data.epiphy.node.model.NodeModelNodeType;

public interface NodeModelProperties {

	public static final ReadOnlyModelProperty<NodeModel, NodeModel> MODEL = ModelProperty.rootChild("model");

	public static final ModelPropertyNode<NodeModel, NodeModelNodeType> NODE = MODEL
			.registerChildNode(model -> model.rootNode, (model, node) -> model.rootNode = node, node -> node.leaves);

	public static final ReadOnlyModelProperty<NodeModel, String> NODE_ID = NODE.registerChild("nodeId",
			node -> node.id);
}
