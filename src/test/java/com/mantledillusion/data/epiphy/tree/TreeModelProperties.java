package com.mantledillusion.data.epiphy.tree;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ReadOnlyModelProperty;
import com.mantledillusion.data.epiphy.tree.model.TreeModel;
import com.mantledillusion.data.epiphy.tree.model.TreeSubSubType;
import com.mantledillusion.data.epiphy.tree.model.TreeSubType;

public class TreeModelProperties {

	public static final ReadOnlyModelProperty<TreeModel, TreeModel> MODEL = ModelProperty.rootChild("model");

	public static final ModelProperty<TreeModel, String> MODELID = MODEL.registerChild("modelId", model -> model.modelId, (model, value) -> model.modelId = value);
	
	public static final ModelProperty<TreeModel, TreeSubType> SUB = MODEL.registerChild("sub", model -> model.sub, (model, sub) -> model.sub = sub);

	public static final ModelProperty<TreeModel, String> SUBID = SUB.registerChild("subId", sub -> sub.subId, (sub, value) -> sub.subId = value);
	
	public static final ModelProperty<TreeModel, TreeSubSubType> SUBSUB = SUB.registerChild("subSub", sub -> sub.subSub, (sub, subSub) -> sub.subSub = subSub);

	public static final ModelProperty<TreeModel, String> SUBSUBID = SUBSUB.registerChild("subSubId", sub -> sub.subSubId, (sub, value) -> sub.subSubId = value);
}
