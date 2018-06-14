package com.mantledillusion.data.epiphy.list;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

public abstract class AbstractListModelPropertyTest {

	protected static final String ELEMENT_0_ELEMENT_0 = "e0_e0";
	protected static final String ELEMENT_0_ELEMENT_1 = "e0_e1";
	protected static final String ELEMENT_1_ELEMENT_0 = "e1_e0";
	
	protected List<List<String>> model;
	
	@Before
	public void before() {
		this.model = new ArrayList<>();
		
		List<String> elementList1 = new ArrayList<>();
		this.model.add(elementList1);
		
		elementList1.add(ELEMENT_0_ELEMENT_0);
		elementList1.add(ELEMENT_0_ELEMENT_1);
		
		List<String> elementList2 = new ArrayList<>();
		this.model.add(elementList2);
		
		elementList2.add(ELEMENT_1_ELEMENT_0);
	}
}
