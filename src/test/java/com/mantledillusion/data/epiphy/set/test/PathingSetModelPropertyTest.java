package com.mantledillusion.data.epiphy.set.test;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertySet;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.set.AbstractSetModelPropertyTest;
import com.mantledillusion.data.epiphy.set.SetModelProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

public class PathingSetModelPropertyTest extends AbstractSetModelPropertyTest {

    @Test
    public void testObfuscate() {
        ModelProperty<Object, String> elementSet = SetModelProperties.ELEMENT.obfuscate((Class) Set.class);
        Context context = Context.of(PropertyKey.ofSet(elementSet, ELEMENT_A));
        Assertions.assertNull(elementSet.get(new Object(), context, true));
        Assertions.assertEquals(ELEMENT_A, elementSet.get(this.model.getObjects(), context));
    }

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
