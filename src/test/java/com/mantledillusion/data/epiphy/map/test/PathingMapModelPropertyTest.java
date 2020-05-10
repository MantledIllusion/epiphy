package com.mantledillusion.data.epiphy.map.test;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyMap;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.map.AbstractMapModelPropertyTest;
import com.mantledillusion.data.epiphy.map.MapModelProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class PathingMapModelPropertyTest extends AbstractMapModelPropertyTest {

    @Test
    public void testObfuscate() {
        ModelProperty<Object, String> elementSet = MapModelProperties.ELEMENT.obfuscate((Class) Map.class);
        Context context = Context.of(PropertyKey.ofMap(elementSet, "A"));
        Assertions.assertNull(elementSet.get(new Object(), context, true));
        Assertions.assertEquals(ELEMENT_A_ELEMENT_A, elementSet.get(this.model.get("A"), context));
    }

    @Test
    public void testPrepend() {
        ModelPropertyMap<Map<String, Map<String, String>>, String, String> parent = ModelPropertyMap.fromMap();
        ModelProperty<Map<String, String>, String> child = ModelProperty.fromMap();
        ModelProperty<Map<String, Map<String, String>>, String> path = child.prepend(parent);

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
        ModelPropertyMap<Map<String, Map<String, String>>, String, String> parent = ModelPropertyMap.fromMap();
        ModelProperty<Map<String, String>, String> child = ModelProperty.fromMap();
        ModelProperty<Map<String, Map<String, String>>, String> path = parent.append(child);

        Set<Property<?, ?>> hierarchy = path.getHierarchy();
        Assertions.assertEquals(2, hierarchy.size());
        Assertions.assertTrue(hierarchy.contains(parent));
        Assertions.assertTrue(hierarchy.contains(child));

        Assertions.assertNull(child.getParent());
        Assertions.assertSame(parent, path.getParent());
        Assertions.assertNull(parent.getParent());
    }
}
