package com.mantledillusion.data.epiphy.node.tests;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.node.AbstractNodeModelPropertyTest;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ContextNodeModelPropertyTest extends AbstractNodeModelPropertyTest {

    @Test
    public void testNoChildOccurrences() {
        this.root.setChild(null);

        Assertions.assertEquals(1, NodeModelProperties.NODE.occurrences(this.root));
    }

    @Test
    public void testOccurrences() {
        Assertions.assertEquals(3, NodeModelProperties.NODE.occurrences(this.root));
    }

    @Test
    public void testNoChildContexting() {
        this.root.setChild(null);

        Collection<Context> contexts = NodeModelProperties.NODE.contextualize(this.root);
        Assertions.assertEquals(1, contexts.size());
        Context context = contexts.iterator().next();

        Assertions.assertTrue(context.containsReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));
        Assertions.assertEquals(0, context.getReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class).getReference().length);
    }

    @Test
    public void testContexting() {
        Collection<Context> contexts = NodeModelProperties.NODE.contextualize(this.root);
        Assertions.assertEquals(3, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertEquals(1, context.size());
            Assertions.assertTrue(context.containsReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));
            PropertyRoute route = context.getReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class);
            NodeModel current = this.root;
            for (Context routeContext: route.getReference()) {
                Assertions.assertFalse(routeContext.containsReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyReference.class));
                current = current.getChild();
            }
            Assertions.assertSame(current, NodeModelProperties.NODE.get(this.root, context));
        }
    }

    @Test
    public void testValueContexting() {
        Collection<Context> contexts = NodeModelProperties.NODE.contextualize(this.root, this.root.getChild());
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));

        PropertyRoute route = context.getReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyRoute.class);
        Assertions.assertEquals(1, route.getReference().length);
        Assertions.assertFalse(route.getReference()[0].containsReference(NodeModelProperties.NODE.getNodeRetriever(), PropertyReference.class));
        Assertions.assertSame(this.root.getChild(), NodeModelProperties.NODE.get(this.root, context));
    }

    @Test
    public void testStream() {
        Queue<NodeModel> expected = new ArrayDeque<>(Arrays.asList(this.root, this.root.getChild(), this.root.getChild().getChild()));
        NodeModelProperties.NODE.stream(this.root).forEachOrdered(node -> Assertions.assertSame(expected.poll(), node));
    }

    @Test
    public void testIterate() {
        Queue<NodeModel> expected = new ArrayDeque<>(Arrays.asList(this.root, this.root.getChild(), this.root.getChild().getChild()));
        for (NodeModel node: NodeModelProperties.NODE.iterate(this.root)) {
            Assertions.assertSame(expected.poll(), node);
        }
    }

    @Test
    public void testPredecessor() {
        Assertions.assertSame(null, NodeModelProperties.NODE.predecessor(this.root, this.root));
        Assertions.assertSame(this.root, NodeModelProperties.NODE.predecessor(this.root, this.root.getChild()));
        Assertions.assertSame(this.root.getChild(), NodeModelProperties.NODE.predecessor(this.root, this.root.getChild().getChild()));
    }

    @Test
    public void testSuccessor() {
        Assertions.assertSame(this.root.getChild(), NodeModelProperties.NODE.successor(this.root, this.root));
        Assertions.assertSame(this.root.getChild().getChild(), NodeModelProperties.NODE.successor(this.root, this.root.getChild()));
        Assertions.assertSame(null, NodeModelProperties.NODE.successor(this.root, this.root.getChild().getChild()));
    }
}
