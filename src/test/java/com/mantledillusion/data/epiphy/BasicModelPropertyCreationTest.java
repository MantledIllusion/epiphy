package com.mantledillusion.data.epiphy;

import org.junit.Before;
import org.junit.Test;

import com.mantledillusion.data.epiphy.mixed.model.MixedModel;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

public class BasicModelPropertyCreationTest {
	
	private DefiniteModelProperty<MixedModel, MixedModel> root;
	private ModelPropertyList<MixedModel, MixedSubType> subList;
	
	@Before
	public void before() {
		this.root = ModelProperty.rootChild();
		this.subList = this.root.registerChildList(model -> model.subList, (model, value) -> model.subList = value);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateChildWithoutGetter() {
		@SuppressWarnings("unused")
		DefiniteModelProperty<MixedModel, String> modelId = this.root.registerChild(null, (model, value) -> model.modelId = value);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateChildWithoutSetter() {
		@SuppressWarnings("unused")
		DefiniteModelProperty<MixedModel, String> modelId = this.root.registerChild(model -> model.modelId, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateChildrenWithoutGetter() {
		@SuppressWarnings("unused")
		ModelPropertyList<MixedModel, MixedSubType> subList = this.root.registerChildList(null, (model, value) -> model.subList = value);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateChildrenWithoutSetter() {
		@SuppressWarnings("unused")
		ModelPropertyList<MixedModel, MixedSubType> subList = this.root.registerChildList(model -> model.subList, null);
	}

	@Test(expected=IllegalStateException.class)
	public void testDefineElementTwice() {
		@SuppressWarnings("unused")
		DefiniteModelProperty<MixedModel, MixedSubType> subList = this.subList.defineElementAsChild();
		this.subList.defineElementAsChildList();
	}
}
