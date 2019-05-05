package com.mantledillusion.data.epiphy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.mixed.model.MixedModel;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicModelPropertyCreationTest {

    private ReadOnlyModelProperty<MixedModel, MixedModel> root;
    private ModelPropertyList<MixedModel, MixedSubType> subList;

    @BeforeEach
    public void before() {
        this.root = ModelProperty.root();
        this.subList = this.root.registerChildList(model -> model.subList, (model, value) -> model.subList = value);
    }

    @Test
    public void testDefineChildWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> this.root.registerChild("model.Id", root -> root.modelId, (root, modelId) -> root.modelId = modelId));
    }

    @Test
    public void testCreateChildWithoutGetter() {
        assertThrows(IllegalArgumentException.class, () -> {
            ModelProperty<MixedModel, String> modelId = this.root.registerChild(null, (model, value) -> model.modelId = value);
        });
    }

    @Test
    public void testCreateChildWithoutSetter() {
        assertThrows(IllegalArgumentException.class, () -> {
            ModelProperty<MixedModel, String> modelId = this.root.registerChild(model -> model.modelId, null);
        });
    }

    @Test
    public void testCreateChildrenWithoutGetter() {
        assertThrows(IllegalArgumentException.class, () -> {
            ModelPropertyList<MixedModel, MixedSubType> subList = this.root.registerChildList(null, (model, value) -> model.subList = value);
        });
    }

    @Test
    public void testCreateChildrenWithoutSetter() {
        assertThrows(IllegalArgumentException.class, () -> {
            ModelPropertyList<MixedModel, MixedSubType> subList = this.root.registerChildList(model -> model.subList, null);
        });
    }

    @Test
    public void testDefineElementTwice() {
        this.subList.defineElementAsChild();
        assertThrows(IllegalStateException.class, () -> {
            this.subList.defineElementAsChildList();
        });
    }

    @Test
    public void testDefineIdTwice() {
        this.root.registerChild("modelId", root -> root.modelId, (root, modelId) -> root.modelId = modelId);
        assertThrows(IllegalStateException.class, () -> {
            this.root.registerChild("modelId", root -> root.modelId, (root, modelId) -> root.modelId = modelId);
        });
    }
}
