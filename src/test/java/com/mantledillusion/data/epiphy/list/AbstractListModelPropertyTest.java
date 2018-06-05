package com.mantledillusion.data.epiphy.list;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

public abstract class AbstractListModelPropertyTest {

	protected static final String ELEMENT_1A = "e1a";
	protected static final String ELEMENT_1B = "e1b";
	
	protected List<List<String>> model;
	
	@Before
	public void before() {
		this.model = new ArrayList<>();
		
		List<String> elementList1 = new ArrayList<>();
		this.model.add(elementList1);
		
		elementList1.add(ELEMENT_1A);
		elementList1.add(ELEMENT_1B);
		
		List<String> elementList2 = new ArrayList<>();
		this.model.add(elementList2);
		
		elementList2.add("e2a");
	}
}
