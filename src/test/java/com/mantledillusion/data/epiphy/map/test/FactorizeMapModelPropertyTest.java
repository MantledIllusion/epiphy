package com.mantledillusion.data.epiphy.map.test;

import com.mantledillusion.data.epiphy.AbstractModelPropertyTest;
import com.mantledillusion.data.epiphy.ModelPropertyMap;
import com.mantledillusion.data.epiphy.map.model.MapModel;
import org.junit.jupiter.api.Test;

public class FactorizeMapModelPropertyTest extends AbstractModelPropertyTest {

    @Test
    public void testFactorizeFromObjectReadOnly() {
        validateFactorization(ModelPropertyMap.fromObject(MapModel::getObjects), false);
    }

    @Test
    public void testFactorizeFromObject() {
        validateFactorization(ModelPropertyMap.fromObject(MapModel::getObjects, MapModel::setObjects), true);
    }

    @Test
    public void testFactorizeFromList() {
        validateFactorization(ModelPropertyMap.fromList(), true);
    }

    @Test
    public void testFactorizeFromMap() {
        validateFactorization(ModelPropertyMap.fromMap(), true);
    }
}
