package com.mantledillusion.data.epiphy.tree;

import org.junit.Before;

import com.mantledillusion.data.epiphy.tree.model.TreeModel;
import com.mantledillusion.data.epiphy.tree.model.TreeSubSubType;
import com.mantledillusion.data.epiphy.tree.model.TreeSubType;

public abstract class AbstractTreeModelPropertyTest {
	
	protected TreeModel model;
	
	@Before
	public void before() {
		model = new TreeModel();
		model.modelId = "model";
		
		TreeSubType sub = new TreeSubType();
		sub.subId = "sub";
		model.sub = sub;
		
		TreeSubSubType subSub = new TreeSubSubType();
		subSub.subSubId = "subSub";
		sub.subSub = subSub;
	}
}
