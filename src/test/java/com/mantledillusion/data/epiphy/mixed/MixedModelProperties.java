package com.mantledillusion.data.epiphy.mixed;

import com.mantledillusion.data.epiphy.DefiniteModelProperty;
import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.mixed.model.MixedModel;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

public class MixedModelProperties {

	public static final DefiniteModelProperty<MixedModel, MixedModel> MODEL = ModelProperty.rootChild("model");
	
	public static final ModelProperty<MixedModel, String> MODELID = MODEL.registerChild("modelId", sub -> sub.modelId, (sub, modelId) -> sub.modelId = modelId);
	
	public static final ModelPropertyList<MixedModel, MixedSubType> SUBLIST = MODEL.registerChildList("subList", model -> model.subList, (model, subList) -> model.subList = subList);
	
	public static final DefiniteModelProperty<MixedModel, MixedSubType> SUB = SUBLIST.defineElementAsChild("sub");
	
	public static final ModelProperty<MixedModel, String> SUBID = SUB.registerChild("subId", sub -> sub.subId, (sub, subId) -> sub.subId = subId);
}
