package com.mantledillusion.data.epiphy.tree;

import com.mantledillusion.data.epiphy.DefiniteModelProperty;
import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.tree.model.TreeModel;
import com.mantledillusion.data.epiphy.tree.model.TreeSubSubType;
import com.mantledillusion.data.epiphy.tree.model.TreeSubType;

public class TreeModelProperties {

	public static final DefiniteModelProperty<TreeModel, TreeModel> MODEL = ModelProperty.rootChild("model");

	public static final DefiniteModelProperty<TreeModel, String> MODELID = MODEL.registerChild("modelId", model -> model.modelId, (model, value) -> model.modelId = value);
	
	public static final DefiniteModelProperty<TreeModel, TreeSubType> SUB = MODEL.registerChild("sub", model -> model.sub, (model, sub) -> model.sub = sub);

	public static final DefiniteModelProperty<TreeModel, String> SUBID = SUB.registerChild("subId", sub -> sub.subId, (sub, value) -> sub.subId = value);
	
	public static final DefiniteModelProperty<TreeModel, TreeSubSubType> SUBSUB = SUB.registerChild("subSub", sub -> sub.subSub, (sub, subSub) -> sub.subSub = subSub);

	public static final DefiniteModelProperty<TreeModel, String> SUBSUBID = SUBSUB.registerChild("subSubId", sub -> sub.subSubId, (sub, value) -> sub.subSubId = value);
}
