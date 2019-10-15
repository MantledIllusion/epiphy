package com.mantledillusion.data.epiphy;

import org.junit.jupiter.api.Assertions;

import java.util.Set;

public abstract class AbstractModelPropertyTest {

    protected <O, V> void validateFactorization(Property<O, V> property, boolean writable, Property<?, ?>... expectedHierarchy) {
        validateFactorization(property, writable, true, expectedHierarchy);
    }

    protected <O, V> void validateFactorization(Property<O, V> property, boolean writable, boolean selfHierarchy, Property<?, ?>... expectedHierarchy) {
        Assertions.assertNotNull(property);
        Assertions.assertNotNull(property.getId());
        Assertions.assertEquals(writable, property.isWritable());

        Set<Property<?, ?>> hierarchy = property.getHierarchy();
        Assertions.assertEquals((selfHierarchy ? 1 : 0)+expectedHierarchy.length, hierarchy.size());
        Assertions.assertEquals(selfHierarchy, hierarchy.contains(property));
        for (Property hierarchyProperty: expectedHierarchy) {
            Assertions.assertTrue(hierarchy.contains(hierarchyProperty));
        }
    }
}
