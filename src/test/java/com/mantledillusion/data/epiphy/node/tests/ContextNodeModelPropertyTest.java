package com.mantledillusion.data.epiphy.node.tests;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ContextNodeModelPropertyTest {

    @Test
    public void testNoChildOccurrences() {
        NodeModel root = new NodeModel();
        root.setChild(null);

        Assertions.assertEquals(1, NodeModelProperties.NODE.occurrences(root));
    }

    @Test
    public void testOccurrences() {
        NodeModel root = new NodeModel();
        NodeModel sub1 = new NodeModel();
        root.setChild(sub1);
        NodeModel sub2 = new NodeModel();
        sub1.setChild(sub2);

        Assertions.assertEquals(3, NodeModelProperties.NODE.occurrences(root));
    }

    @Test
    public void testNoChildContexting() {
        NodeModel root = new NodeModel();
        root.setChild(null);

        Collection<Context> contexts = NodeModelProperties.NODE.contextualize(root);
        Assertions.assertEquals(1, contexts.size());
        Context context = contexts.iterator().next();

        Assertions.assertTrue(context.containsReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));
        Assertions.assertEquals(0, context.getReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class).getReference().length);
    }

    @Test
    public void testContexting() {
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
            Assertions.assertTrue(context.containsReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));
            PropertyRoute route = context.getReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class);
            NodeModel current = root;
            for (Context routeContext: route.getReference()) {
                Assertions.assertFalse(routeContext.containsReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyReference.class));
                current = current.getChild();
            }
            Assertions.assertSame(current, NodeModelProperties.NODE.get(root, context));
        }
    }

    @Test
    public void testStream() {
        NodeModel root = new NodeModel();
        NodeModel sub1 = new NodeModel();
        root.setChild(sub1);
        NodeModel sub2 = new NodeModel();
        sub1.setChild(sub2);
        NodeModel sub3 = new NodeModel();
        sub2.setChild(sub3);

        Queue<NodeModel> expected = new ArrayDeque<>(Arrays.asList(root, sub1, sub2, sub3));
        NodeModelProperties.NODE.stream(root).forEachOrdered(node -> Assertions.assertSame(expected.poll(), node));
    }

    @Test
    public void testIterate() {
        NodeModel root = new NodeModel();
        NodeModel sub1 = new NodeModel();
        root.setChild(sub1);
        NodeModel sub2 = new NodeModel();
        sub1.setChild(sub2);
        NodeModel sub3 = new NodeModel();
        sub2.setChild(sub3);

        Queue<NodeModel> expected = new ArrayDeque<>(Arrays.asList(root, sub1, sub2, sub3));
        for (NodeModel node: NodeModelProperties.NODE.iterate(root)) {
            Assertions.assertSame(expected.poll(), node);
        }
    }
}
