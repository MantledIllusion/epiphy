package com.mantledillusion.data.epiphy.object.test;

import com.mantledillusion.data.epiphy.AbstractModelPropertyTest;
import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.object.model.ObjectModel;
import org.junit.jupiter.api.Test;

public class FactorizeObjectModelPropertyTest extends AbstractModelPropertyTest {

    @Test
    public void testFactorizeFromObjectReadOnly() {
        validateFactorization(ModelProperty.fromObject(ObjectModel::getModelId), false);
    }

    @Test
    public void testFactorizeFromObject() {
        validateFactorization(ModelProperty.fromObject(ObjectModel::getModelId, ObjectModel::setModelId), true);
    }

    @Test
    public void testFactorizeFromList() {
        validateFactorization(ModelProperty.fromList(), true);
    }

    @Test
    public void testFactorizeFromSet() {
        validateFactorization(ModelProperty.fromSet(), true);
    }

    @Test
    public void testFactorizeFromMap() {
        validateFactorization(ModelProperty.fromMap(), true);
    }
}
