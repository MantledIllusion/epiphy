package com.mantledillusion.data.epiphy.object.test;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.object.AbstractObjectModelPropertyTest;
import com.mantledillusion.data.epiphy.object.ObjectModelProperties;
import com.mantledillusion.data.epiphy.object.model.ObjectModel;
import com.mantledillusion.data.epiphy.object.model.ObjectSubType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class PathingObjectModelPropertyTest extends AbstractObjectModelPropertyTest {

    @Test
    public void testObfuscate() {
        ModelProperty<Object, String> modelId = ObjectModelProperties.MODELID.obfuscate(ObjectModel.class);
        Assertions.assertNull(modelId.get(new Object(), true));
        Assertions.assertEquals(this.model.getModelId(), modelId.get(this.model));
    }

    @Test
    public void testPrepend() {
        ModelProperty<ObjectModel, ObjectSubType> parent = ModelProperty.fromObject(ObjectModel::getSub);
        ModelProperty<ObjectSubType, String> child = ModelProperty.fromObject(ObjectSubType::getSubId);
        ModelProperty<ObjectModel, String> path = child.prepend(parent);

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
        ModelProperty<ObjectModel, ObjectSubType> parent = ModelProperty.fromObject(ObjectModel::getSub);
        ModelProperty<ObjectSubType, String> child = ModelProperty.fromObject(ObjectSubType::getSubId);
        ModelProperty<ObjectModel, String> path = parent.append(child);

        Set<Property<?, ?>> hierarchy = path.getHierarchy();
        Assertions.assertEquals(2, hierarchy.size());
        Assertions.assertTrue(hierarchy.contains(parent));
        Assertions.assertTrue(hierarchy.contains(child));

        Assertions.assertNull(child.getParent());
        Assertions.assertSame(parent, path.getParent());
        Assertions.assertNull(parent.getParent());
    }
}
