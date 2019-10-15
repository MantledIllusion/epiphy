package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.AbstractModelPropertyTest;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.list.model.ListModel;
import org.junit.jupiter.api.Test;

public class FactorizeListModelPropertyTest extends AbstractModelPropertyTest {

    @Test
    public void testFactorizeFromObjectReadOnly() {
        validateFactorization(ModelPropertyList.fromObject(ListModel::getObjects), false);
    }

    @Test
    public void testFactorizeFromObject() {
        validateFactorization(ModelPropertyList.fromObject(ListModel::getObjects, ListModel::setObjects), true);
    }

    @Test
    public void testFactorizeFromList() {
        validateFactorization(ModelPropertyList.fromList(), true);
    }
}
