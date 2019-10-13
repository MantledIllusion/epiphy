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
		model.setModelId("model");
		
		ObjectSubType sub = new ObjectSubType();
		sub.setSubId("sub");
		model.setSub(sub);
		
		ObjectSubSubType subSub = new ObjectSubSubType();
		subSub.setSubSubId("subSub");
		sub.setSubSub(subSub);
	}
}
