package com.mantledillusion.data.epiphy.mixed;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;

import com.mantledillusion.data.epiphy.mixed.model.MixedModel;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

public class AbstractMixedModelPropertyTest {
	
	protected static final String ELEMENT_0 = "e0";
	protected static final String ELEMENT_1 = "e1";
	protected static final String ELEMENT_1_NODE_0 = "e1_n0";
	protected static final String ELEMENT_1_NODE_1 = "e1_n1";
	protected static final String ELEMENT_1_NODE_1_NODE_0 = "e1_n1_n0";

	protected MixedModel model;
	
	@BeforeEach
	public void before() {
		this.model = new MixedModel();
		this.model.subList = new ArrayList<>();
		
		MixedSubType e1 = new MixedSubType();
		e1.subId = ELEMENT_0;
		this.model.subList.add(e1);
		
		MixedSubType e2 = new MixedSubType();
		e2.subId = ELEMENT_1;
		this.model.subList.add(e2);
		
		MixedSubType e2_n1 = new MixedSubType();
		e2_n1.subId = ELEMENT_1_NODE_0;
		e2.leaves.add(e2_n1);
		
		MixedSubType e2_n2 = new MixedSubType();
		e2_n2.subId = ELEMENT_1_NODE_1;
		e2.leaves.add(e2_n2);
		
		MixedSubType e2_n2_n1 = new MixedSubType();
		e2_n2_n1.subId = ELEMENT_1_NODE_1_NODE_0;
		e2_n2.leaves.add(e2_n2_n1);
	}
}
