package com.mantledillusion.data.epiphy.set.test;

import com.mantledillusion.data.epiphy.AbstractModelPropertyTest;
import com.mantledillusion.data.epiphy.ModelPropertySet;
import com.mantledillusion.data.epiphy.set.model.SetModel;
import org.junit.jupiter.api.Test;

public class FactorizeSetModelPropertyTest extends AbstractModelPropertyTest {

    @Test
    public void testFactorizeFromObjectReadOnly() {
        validateFactorization(ModelPropertySet.fromObject(SetModel::getObjects), false);
    }

    @Test
    public void testFactorizeFromObject() {
        validateFactorization(ModelPropertySet.fromObject(SetModel::getObjects, SetModel::setObjects), true);
    }

    @Test
    public void testFactorizeFromList() {
        validateFactorization(ModelPropertySet.fromList(), true);
    }

    @Test
    public void testFactorizeFromSet() {
        validateFactorization(ModelPropertySet.fromSet(), true);
    }

    @Test
    public void testFactorizeFromMap() {
        validateFactorization(ModelPropertySet.fromMap(), true);
    }
}
