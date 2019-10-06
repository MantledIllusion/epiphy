package com.mantledillusion.data.epiphy.mixed.tests;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;
import com.mantledillusion.data.epiphy.mixed.model.MixedModelNode;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

public class ContextMixedModelPropertyTest {

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
            Assertions.assertTrue(context.containsReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));
            PropertyRoute route = context.getReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class);
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
