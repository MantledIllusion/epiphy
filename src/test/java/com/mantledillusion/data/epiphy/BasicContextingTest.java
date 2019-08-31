package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;
import com.mantledillusion.data.epiphy.mixed.model.MixedModelNode;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeModel;
import com.mantledillusion.data.epiphy.object.ObjectModelProperties;
import com.mantledillusion.data.epiphy.object.model.ObjectModel;
import com.mantledillusion.data.epiphy.object.model.ObjectSubType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class BasicContextingTest {

    @Test
    public void testNullObjectIndexing() {
        ObjectModel model = new ObjectModel();
        model.sub = null;

        Collection<Context> contexts = ObjectModelProperties.MODELSUB.contextualize(model);
        Assertions.assertEquals(0, contexts.size());
    }

    @Test
    public void testObjectIndexing() {
        ObjectModel model = new ObjectModel();
        model.sub = new ObjectSubType();

        Collection<Context> contexts = ObjectModelProperties.MODELSUB.contextualize(model);
        Assertions.assertEquals(1, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertFalse(context.containsReference(ObjectModelProperties.MODELSUB, PropertyReference.class));
            Assertions.assertSame(model.sub, ObjectModelProperties.MODELSUB.get(model, context));
        }
    }

    @Test
    public void testEmptyListIndexing() {
        Collection<Context> contexts = ListModelProperties.ELEMENT.contextualize(Collections.emptyList());
        Assertions.assertEquals(0, contexts.size());
    }

    @Test
    public void testListIndexing() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");

        Collection<Context> contexts = ListModelProperties.ELEMENT.contextualize(list);
        Assertions.assertEquals(2, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertTrue(context.containsReference(ListModelProperties.ELEMENT, PropertyIndex.class));
            PropertyIndex index = context.getReference(ListModelProperties.ELEMENT, PropertyIndex.class);
            Assertions.assertSame(list.get(index.getReference()), ListModelProperties.ELEMENT.get(list, context));
        }
    }

    @Test
    public void testNodeIndexing() {
        NodeModel root = new NodeModel();
        NodeModel sub1 = new NodeModel();
        root.setChild(sub1);
        NodeModel sub2 = new NodeModel();
        sub1.setChild(sub2);

        Collection<Context> contexts = NodeModelProperties.NODE.contextualize(root);
        Assertions.assertEquals(3, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertTrue(context.containsReference(NodeModelProperties.NODE, PropertyRoute.class));
            PropertyRoute route = context.getReference(NodeModelProperties.NODE, PropertyRoute.class);
            NodeModel current = root;
            for (Context routeContext: route.getReference()) {
                Assertions.assertFalse(routeContext.containsReference(NodeModelProperties.RETRIEVER, PropertyReference.class));
                current = current.getChild();
            }
            Assertions.assertSame(current, NodeModelProperties.NODE.get(root, context));
        }
    }

    @Test
    public void testEmptyPathIndexing() {
        MixedModelNode root = new MixedModelNode();
        MixedSubType sub1 = new MixedSubType();
        root.setSub(sub1);

        Collection<Context> contexts = MixedModelProperties.NODE.contextualize(root);
        Assertions.assertEquals(1, contexts.size());
        Assertions.assertSame(root, MixedModelProperties.NODE.get(root, contexts.iterator().next()));
    }

    @Test
    public void testPathIndexing() {
        MixedModelNode root = new MixedModelNode();
        MixedSubType sub1 = new MixedSubType();
        root.setSub(sub1);
        MixedModelNode child1a = new MixedModelNode();
        sub1.getSubNodes().add(child1a);
        MixedModelNode child1b = new MixedModelNode();
        sub1.getSubNodes().add(child1b);

        Collection<Context> contexts = MixedModelProperties.NODE.contextualize(root);
        Assertions.assertEquals(3, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertTrue(context.containsReference(MixedModelProperties.NODE, PropertyRoute.class));
            PropertyRoute route = context.getReference(MixedModelProperties.NODE, PropertyRoute.class);
            MixedModelNode currentNode = root;
            for (Context routeContext: route.getReference()) {
                Assertions.assertTrue(routeContext.containsReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class));
                PropertyIndex index = routeContext.getReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class);
                currentNode = currentNode.getSub().getSubNodes().get(index.getReference());
            }
            Assertions.assertSame(currentNode, MixedModelProperties.NODE.get(root, context));
        }
    }
}
