package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.object.AbstractObjectModelPropertyTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PathingListModelPropertyTest extends AbstractObjectModelPropertyTest {

    @Test
    public void testPrepend() {
        ModelPropertyList<Optional<List<String>>, String> parent = ModelPropertyList.fromObject(Optional::get);
        ModelProperty<List<String>, String> child = ModelProperty.fromList();
        ModelProperty<Optional<List<String>>, String> path = child.prepend(parent);

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
        ModelPropertyList<Optional<List<String>>, String> parent = ModelPropertyList.fromObject(Optional::get);
        ModelProperty<List<String>, String> child = ModelProperty.fromList();
        ModelProperty<Optional<List<String>>, String> path = child.prepend(parent);

        Set<Property<?, ?>> hierarchy = path.getHierarchy();
        Assertions.assertEquals(2, hierarchy.size());
        Assertions.assertTrue(hierarchy.contains(parent));
        Assertions.assertTrue(hierarchy.contains(child));

        Assertions.assertNull(child.getParent());
        Assertions.assertSame(parent, path.getParent());
        Assertions.assertNull(parent.getParent());
    }
}
