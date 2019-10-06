package com.mantledillusion.data.epiphy.node.tests;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

public class ContextNodeModelPropertyTest {

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
}
