package com.mantledillusion.data.epiphy.mixed;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.mixed.model.MixedModelNode;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

import java.util.List;

public interface MixedModelProperties {

	ModelProperty<MixedModelNode, MixedSubType> NODE_SUB = ModelProperty.fromObject("nodeSub", MixedModelNode::getSub, MixedModelNode::setSub);
	ModelPropertyList<MixedSubType, MixedModelNode> SUB_NODE_LIST = ModelPropertyList.fromObject("nodeList", MixedSubType::getSubNodes);
	ModelProperty<List<MixedModelNode>, MixedModelNode> LISTED_NODE = ModelProperty.fromList("listedNode");

	ModelProperty<MixedModelNode, MixedModelNode> RETRIEVER = NODE_SUB.append(SUB_NODE_LIST).append(LISTED_NODE);
	ModelPropertyNode<MixedModelNode, MixedModelNode> NODE = ModelPropertyNode.from("node", RETRIEVER);
}
