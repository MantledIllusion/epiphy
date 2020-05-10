package com.mantledillusion.data.epiphy.node.tests;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.node.AbstractNodeModelPropertyTest;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeModel;
import com.mantledillusion.data.epiphy.node.model.NodeParent;
import com.mantledillusion.data.epiphy.object.AbstractObjectModelPropertyTest;
import com.mantledillusion.data.epiphy.object.ObjectModelProperties;
import com.mantledillusion.data.epiphy.object.model.ObjectModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class PathingNodeModelPropertyTest extends AbstractNodeModelPropertyTest {

    @Test
    public void testObfuscate() {
        ModelPropertyNode<Object, NodeModel> modelNode = NodeModelProperties.NODE.obfuscate(NodeModel.class);
        Context context = Context.of(PropertyRoute.of(modelNode.getNodeRetriever(), Context.EMPTY));
        Assertions.assertNull(modelNode.get(new Object(), context, true));
        Assertions.assertEquals(this.root.getChild().getId(), modelNode.get(this.root, context).getId());
    }

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

        Assertions.assertNull(child.getParent());
        Assertions.assertSame(parent, path.getParent());
        Assertions.assertNull(parent.getParent());
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

        Assertions.assertNull(child.getParent());
        Assertions.assertSame(parent, path.getParent());
        Assertions.assertNull(parent.getParent());
    }
}
