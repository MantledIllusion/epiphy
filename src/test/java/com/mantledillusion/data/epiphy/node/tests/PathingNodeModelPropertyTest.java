package com.mantledillusion.data.epiphy.node.tests;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.node.model.NodeModel;
import com.mantledillusion.data.epiphy.object.AbstractObjectModelPropertyTest;
import com.mantledillusion.data.epiphy.object.model.ObjectModel;
import com.mantledillusion.data.epiphy.object.model.ObjectSubType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class PathingNodeModelPropertyTest extends AbstractObjectModelPropertyTest {

    @Test
    public void testPrepend() {
        ModelProperty<NodeModel, NodeModel> retriever = ModelProperty.fromObject(NodeModel::getChild);
        ModelPropertyNode<NodeModel, NodeModel> parent = ModelPropertyNode.from(retriever);
        ModelProperty<NodeModel, String> child = ModelProperty.fromObject(NodeModel::getId);
        ModelProperty<NodeModel, String> path = child.prepend(parent);

        Set<Property<?, ?>> hierarchy = path.getHierarchy();
        Assertions.assertEquals(2, hierarchy.size());
        Assertions.assertTrue(hierarchy.contains(retriever));
        Assertions.assertTrue(hierarchy.contains(child));
    }

    @Test
    public void testAppend() {
        ModelProperty<NodeModel, NodeModel> retriever = ModelProperty.fromObject(NodeModel::getChild);
        ModelPropertyNode<NodeModel, NodeModel> parent = ModelPropertyNode.from(retriever);
        ModelProperty<NodeModel, String> child = ModelProperty.fromObject(NodeModel::getId);
        ModelProperty<NodeModel, String> path = parent.append(child);

        Set<Property<?, ?>> hierarchy = path.getHierarchy();
        Assertions.assertEquals(2, hierarchy.size());
        Assertions.assertTrue(hierarchy.contains(retriever));
        Assertions.assertTrue(hierarchy.contains(child));
    }
}
