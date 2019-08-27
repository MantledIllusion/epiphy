package com.mantledillusion.data.epiphy.mixed;


import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

import java.util.List;

public interface MixedModelProperties {

	ModelPropertyList<MixedSubType, MixedSubType> SUBLIST = ModelPropertyList.fromObject(s -> s.leaves);
	ModelProperty<List<MixedSubType>, MixedSubType> SUB = ModelProperty.fromList();
	ModelPropertyNode<MixedSubType, MixedSubType> NODE = ModelPropertyNode.from(SUBLIST.append(SUB));
	ModelProperty<MixedSubType, String> ID = NODE.append(ModelProperty.fromObject(s -> s.subId, (s, v) -> s.subId = v));
}
