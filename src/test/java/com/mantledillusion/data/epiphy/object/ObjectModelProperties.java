package com.mantledillusion.data.epiphy.object;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.object.model.ObjectModel;
import com.mantledillusion.data.epiphy.object.model.ObjectSubSubType;
import com.mantledillusion.data.epiphy.object.model.ObjectSubType;

public interface ObjectModelProperties {

    ModelProperty<ObjectModel, String> MODELID = ModelProperty.fromObject(ObjectModel::getModelId, ObjectModel::setModelId);
    ModelProperty<ObjectModel, ObjectSubType> MODELSUB = ModelProperty.fromObject(ObjectModel::getSub, ObjectModel::setSub);
    ModelProperty<ObjectSubType, String> SUBID = ModelProperty.fromObject(ObjectSubType::getSubId, ObjectSubType::setSubId);
    ModelProperty<ObjectSubType, ObjectSubSubType> SUBSUB = ModelProperty.fromObject(ObjectSubType::getSubSub, ObjectSubType::setSubSub);
    ModelProperty<ObjectSubSubType, String> SUBSUBID = ModelProperty.fromObject(ObjectSubSubType::getSubSubId, ObjectSubSubType::setSubSubId);

    ModelProperty<ObjectModel, String> MODEL_TO_SUBID = MODELSUB.append(SUBID);
    ModelProperty<ObjectModel, ObjectSubSubType> MODEL_TO_SUBSUB = MODELSUB.append(SUBSUB);
    ModelProperty<ObjectModel, String> MODEL_TO_SUBSUBID = MODEL_TO_SUBSUB.append(SUBSUBID);
}
