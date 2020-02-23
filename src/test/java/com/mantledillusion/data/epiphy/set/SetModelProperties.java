package com.mantledillusion.data.epiphy.set;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertySet;
import com.mantledillusion.data.epiphy.set.model.SetModel;

import java.util.Set;

public interface SetModelProperties {

    ModelPropertySet<SetModel, Object> ELEMENTSET = ModelPropertySet.fromObject(SetModel::getObjects, SetModel::setObjects);

    ModelProperty<Set<Object>, Object> ELEMENT = ModelProperty.fromSet();

    ModelProperty<SetModel, Object> ELEMENTSET_TO_ELEMENT = ELEMENTSET.append(ELEMENT);
}
