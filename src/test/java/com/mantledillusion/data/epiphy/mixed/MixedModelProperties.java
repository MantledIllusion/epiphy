package com.mantledillusion.data.epiphy.mixed;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.mixed.model.MixedModel;
import com.mantledillusion.data.epiphy.mixed.model.MixedModelNode;
import com.mantledillusion.data.epiphy.mixed.model.MixedModelNodeRoot;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

import java.util.List;

public interface MixedModelProperties {

	ModelProperty<MixedModelNode, MixedSubType> NODE_SUB = ModelProperty.fromObject("nodeSub", MixedModelNode::getSub, MixedModelNode::setSub);
	ModelPropertyList<MixedSubType, MixedModelNode> SUB_NODE_LIST = ModelPropertyList.fromObject("nodeList", MixedSubType::getSubNodes);
	ModelProperty<List<MixedModelNode>, MixedModelNode> LISTED_NODE = ModelProperty.fromList("listedNode");

	ModelProperty<MixedModel, MixedModelNodeRoot> MODEL_NODE_ROOT = ModelProperty.fromObject("nodeRoot", MixedModel::getRoot, MixedModel::setRoot);
	ModelProperty<MixedModelNode, MixedModelNode> RETRIEVER = NODE_SUB.append(SUB_NODE_LIST).append(LISTED_NODE);
	ModelPropertyNode<MixedModel, MixedModelNode> NODE = MODEL_NODE_ROOT.append(ModelPropertyNode.fromObject("node",  MixedModelNodeRoot::getNode, RETRIEVER));
	ModelProperty<MixedModel, String> NODE_ID = NODE.append(ModelProperty.fromObject("nodeId", MixedModelNode::getNodeId, MixedModelNode::setNodeId));
}
