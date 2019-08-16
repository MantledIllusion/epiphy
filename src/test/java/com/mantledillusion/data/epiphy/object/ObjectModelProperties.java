package com.mantledillusion.data.epiphy.object;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.object.model.ObjectModel;
import com.mantledillusion.data.epiphy.object.model.ObjectSubSubType;
import com.mantledillusion.data.epiphy.object.model.ObjectSubType;

public interface ObjectModelProperties {

    ModelProperty<ObjectModel, String> MODELID = ModelProperty.fromObject(m -> m.modelId, (m, modelId) -> m.modelId = modelId);
    ModelProperty<ObjectModel, ObjectSubType> MODELSUB = ModelProperty.fromObject(m -> m.sub, (m, sub) -> m.sub = sub);
    ModelProperty<ObjectSubType, String> SUBID = ModelProperty.fromObject(s -> s.subId, (s, subId) -> s.subId = subId);
    ModelProperty<ObjectSubType, ObjectSubSubType> SUBSUB = ModelProperty.fromObject(s -> s.subSub, (s, subSub) -> s.subSub = subSub);
    ModelProperty<ObjectSubSubType, String> SUBSUBID = ModelProperty.fromObject(s -> s.subSubId, (s, subSubId) -> s.subSubId = subSubId);

    ModelProperty<ObjectModel, String> MODEL_TO_SUBID = MODELSUB.append(SUBID);
    ModelProperty<ObjectModel, ObjectSubSubType> MODEL_TO_SUBSUB = MODELSUB.append(SUBSUB);
    ModelProperty<ObjectModel, String> MODEL_TO_SUBSUBID = MODEL_TO_SUBSUB.append(SUBSUBID);
}
