package com.mantledillusion.data.epiphy.set.test;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertySet;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.set.AbstractSetModelPropertyTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

public class PathingSetModelPropertyTest extends AbstractSetModelPropertyTest {

    @Test
    public void testPrepend() {
        ModelPropertySet<Optional<Set<String>>, String> parent = ModelPropertySet.fromObject(Optional::get);
        ModelProperty<Set<String>, String> child = ModelProperty.fromSet();
        ModelProperty<Optional<Set<String>>, String> path = child.prepend(parent);

        Set<Property<?, ?>> hierarchy = path.getHierarchy();
        Assertions.assertEquals(2, hierarchy.size());
        Assertions.assertTrue(hierarchy.contains(parent));
        Assertions.assertTrue(hierarchy.contains(child));

        Assertions.assertNull(child.getParent());
        Assertions.assertSame(parent, path.getParent());
        Assertions.assertNull(parent.getParent());
    }

    @Test
    public void testAppend() {
        ModelPropertySet<Optional<Set<String>>, String> parent = ModelPropertySet.fromObject(Optional::get);
        ModelProperty<Set<String>, String> child = ModelProperty.fromSet();
        ModelProperty<Optional<Set<String>>, String> path = child.prepend(parent);

        Set<Property<?, ?>> hierarchy = path.getHierarchy();
        Assertions.assertEquals(2, hierarchy.size());
        Assertions.assertTrue(hierarchy.contains(parent));
        Assertions.assertTrue(hierarchy.contains(child));

        Assertions.assertNull(child.getParent());
        Assertions.assertSame(parent, path.getParent());
        Assertions.assertNull(parent.getParent());
    }
}
