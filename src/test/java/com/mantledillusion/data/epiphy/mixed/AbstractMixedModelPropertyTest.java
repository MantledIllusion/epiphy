package com.mantledillusion.data.epiphy.mixed;

import java.util.ArrayList;

import org.junit.Before;

import com.mantledillusion.data.epiphy.mixed.model.MixedModel;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

public class AbstractMixedModelPropertyTest {

	protected MixedModel model;
	
	@Before
	public void before() {
		this.model = new MixedModel();
		this.model.subList = new ArrayList<>();
		
		MixedSubType sub1 = new MixedSubType();
		sub1.subId = "1";
		this.model.subList.add(sub1);
		
		MixedSubType sub2 = new MixedSubType();
		sub2.subId = "2";
		this.model.subList.add(sub2);
	}
}
