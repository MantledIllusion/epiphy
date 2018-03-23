package com.mantledillusion.data.epiphy.mixed;

import com.mantledillusion.data.epiphy.DefiniteModelProperty;
import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.mixed.model.MixedModel;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

public class MixedModelProperties {

	public static final String ID_MODEL = "model";
	public static final DefiniteModelProperty<MixedModel, MixedModel> MODEL = ModelProperty.rootChild(ID_MODEL);

	public static final String ID_MODELID = "modelId";
	public static final ModelProperty<MixedModel, String> MODELID = MODEL.registerChild(ID_MODELID, sub -> sub.modelId, (sub, modelId) -> sub.modelId = modelId);

	public static final String ID_SUBLIST = "subList";
	public static final ModelPropertyList<MixedModel, MixedSubType> SUBLIST = MODEL.registerChildList(ID_SUBLIST, model -> model.subList, (model, subList) -> model.subList = subList);

	public static final String ID_SUB = "sub";
	public static final DefiniteModelProperty<MixedModel, MixedSubType> SUB = SUBLIST.defineElementAsChild(ID_SUB);

	public static final String ID_SUBID = "subId";
	public static final ModelProperty<MixedModel, String> SUBID = SUB.registerChild(ID_SUBID, sub -> sub.subId, (sub, subId) -> sub.subId = subId);
}
