package com.mantledillusion.data.epiphy.mixed.tests;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.mixed.AbstractMixedModelPropertyTest;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;
import com.mantledillusion.data.epiphy.mixed.model.MixedModelNode;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

public class ContextMixedModelPropertyTest extends AbstractMixedModelPropertyTest {

    @Test
    public void testEmptyPathContexting() {
        this.model.getRoot().getNode().getSub().getSubNodes().clear();

        Collection<Context> contexts = MixedModelProperties.NODE_ID.contextualize(this.model);
        Assertions.assertEquals(1, contexts.size());
        Assertions.assertSame(NODE_ROOT, MixedModelProperties.NODE_ID.get(this.model, contexts.iterator().next()));
    }

    @Test
    public void testPathContexting() {
        Collection<Context> contexts = MixedModelProperties.NODE.contextualize(this.model);
        Assertions.assertEquals(4, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertEquals(1, context.size());
            Assertions.assertTrue(context.containsReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));
            PropertyRoute route = context.getReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class);
            MixedModelNode currentNode = this.model.getRoot().getNode();
            for (Context routeContext: route.getReference()) {
                Assertions.assertTrue(routeContext.containsReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class));
                PropertyIndex index = routeContext.getReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class);
                currentNode = currentNode.getSub().getSubNodes().get(index.getReference());
            }
            Assertions.assertSame(currentNode.getNodeId(), MixedModelProperties.NODE_ID.get(this.model, context));
        }
    }

    @Test
    public void testBasedPathContexting() {
        Context baseContext = Context.of(PropertyRoute.of(MixedModelProperties.NODE.getNodeRetriever(),
                Context.of(PropertyIndex.of(MixedModelProperties.LISTED_NODE, 1))));
        Collection<Context> contexts = MixedModelProperties.NODE_ID.contextualize(this.model, baseContext);
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));

        PropertyRoute route = context.getReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class);
        Assertions.assertEquals(1, route.getReference().length);
        Assertions.assertTrue(route.getReference()[0].containsReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class));
        Assertions.assertSame(NODE_CHILD1B, MixedModelProperties.NODE_ID.get(this.model, context));
    }

    @Test
    public void testValuePathContexting() {
        Collection<Context> contexts = MixedModelProperties.NODE_ID.contextualize(this.model, NODE_CHILD1B);
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));

        PropertyRoute route = context.getReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class);
        Assertions.assertEquals(1, route.getReference().length);
        Assertions.assertTrue(route.getReference()[0].containsReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class));
        Assertions.assertSame(NODE_CHILD1B, MixedModelProperties.NODE_ID.get(this.model, context));
    }

    @Test
    public void testBasedValuePathContexting() {
        this.model.getRoot().getNode().getSub().getSubNodes().get(0).setNodeId(NODE_CHILD1B);

        Context baseContext = Context.of(PropertyRoute.of(MixedModelProperties.NODE.getNodeRetriever(),
                Context.of(PropertyIndex.of(MixedModelProperties.LISTED_NODE, 1))));
        Collection<Context> contexts = MixedModelProperties.NODE_ID.contextualize(this.model, NODE_CHILD1B, baseContext);
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));

        PropertyRoute route = context.getReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class);
        Assertions.assertEquals(1, route.getReference().length);
        Assertions.assertTrue(route.getReference()[0].containsReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class));
        Assertions.assertSame(NODE_CHILD1B, MixedModelProperties.NODE_ID.get(this.model, context));
    }
}
