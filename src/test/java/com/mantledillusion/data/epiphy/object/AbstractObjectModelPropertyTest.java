package com.mantledillusion.data.epiphy.object;

import com.mantledillusion.data.epiphy.object.model.ObjectModel;
import com.mantledillusion.data.epiphy.object.model.ObjectSubSubType;
import com.mantledillusion.data.epiphy.object.model.ObjectSubType;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractObjectModelPropertyTest {
	
	protected ObjectModel model;
	
	@BeforeEach
	public void before() {
		model = new ObjectModel();
		model.modelId = "model";
		
		ObjectSubType sub = new ObjectSubType();
		sub.subId = "sub";
		model.sub = sub;
		
		ObjectSubSubType subSub = new ObjectSubSubType();
		subSub.subSubId = "subSub";
		sub.subSub = subSub;
	}
}
