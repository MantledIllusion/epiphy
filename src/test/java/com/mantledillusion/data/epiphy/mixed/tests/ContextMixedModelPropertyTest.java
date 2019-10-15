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
    public void testEmptyPathIndexing() {
        this.model.getRoot().getNode().getSub().getSubNodes().clear();

        Collection<Context> contexts = MixedModelProperties.NODE.contextualize(this.model);
        Assertions.assertEquals(1, contexts.size());
        Assertions.assertSame(this.model.getRoot().getNode(), MixedModelProperties.NODE.get(this.model, contexts.iterator().next()));
    }

    @Test
    public void testPathIndexing() {
        Collection<Context> contexts = MixedModelProperties.NODE.contextualize(this.model);
        Assertions.assertEquals(4, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertTrue(context.containsReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));
            PropertyRoute route = context.getReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class);
            MixedModelNode currentNode = this.model.getRoot().getNode();
            for (Context routeContext: route.getReference()) {
                Assertions.assertTrue(routeContext.containsReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class));
                PropertyIndex index = routeContext.getReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class);
                currentNode = currentNode.getSub().getSubNodes().get(index.getReference());
            }
            Assertions.assertSame(currentNode, MixedModelProperties.NODE.get(this.model, context));
        }
    }
}
