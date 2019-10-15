package com.mantledillusion.data.epiphy.mixed.tests;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ContextTest {

    @Test
    public void testFactorizeIndex() {
        PropertyIndex idx = PropertyIndex.of(MixedModelProperties.LISTED_NODE, 0);
        Assertions.assertSame(MixedModelProperties.LISTED_NODE, idx.getProperty());
        Assertions.assertEquals(0, idx.getReference());
        Assertions.assertTrue(idx.toString().contains(MixedModelProperties.LISTED_NODE.toString()));

        PropertyIndex idx2 = PropertyIndex.of(MixedModelProperties.LISTED_NODE, 0);
        Assertions.assertEquals(idx, idx2);
        Assertions.assertEquals(idx.hashCode(), idx2.hashCode());

        PropertyIndex idx3 = PropertyIndex.of(ModelProperty.fromList("listedNode"), 0);
        Assertions.assertNotEquals(idx, idx3);
    }

    @Test
    public void testFactorizeRoute() {
        PropertyRoute route = PropertyRoute.of(MixedModelProperties.NODE.getNodeRetriever(), Context.EMPTY);
        Assertions.assertSame(MixedModelProperties.NODE.getNodeRetriever(), route.getProperty());
        Assertions.assertTrue(Arrays.equals(new Context[] {Context.EMPTY}, route.getReference()));
        Assertions.assertTrue(route.toString().contains(MixedModelProperties.NODE.getNodeRetriever().toString()));

        PropertyRoute route2 = PropertyRoute.of(MixedModelProperties.NODE.getNodeRetriever(), Context.EMPTY);
        Assertions.assertEquals(route, route2);
        Assertions.assertEquals(route.hashCode(), route2.hashCode());

        PropertyRoute route3 = PropertyRoute.of(ModelPropertyNode.from("node", MixedModelProperties.RETRIEVER).getNodeRetriever(), Context.EMPTY);
        Assertions.assertNotEquals(route, route3);
    }

    @Test
    public void testFactorizeEmpty() {
        Context ctx = Context.of();
        Assertions.assertEquals(0, ctx.size());
        Assertions.assertSame(Context.EMPTY, ctx);
    }

    @Test
    public void testFactorizeUsingIndex() {
        PropertyIndex idx = PropertyIndex.of(MixedModelProperties.LISTED_NODE, 0);
        Context ctx = Context.of(idx);

        Assertions.assertEquals(1, ctx.size());
        Assertions.assertTrue(ctx.containsReference(MixedModelProperties.LISTED_NODE));
        Assertions.assertTrue(ctx.containsReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class));
        Assertions.assertFalse(ctx.containsReference(MixedModelProperties.LISTED_NODE, PropertyRoute.class));
        Assertions.assertSame(idx, ctx.getReference(MixedModelProperties.LISTED_NODE));
        Assertions.assertSame(idx, ctx.getReference(MixedModelProperties.LISTED_NODE, PropertyIndex.class));

        Context ctx2 = Context.of(PropertyIndex.of(MixedModelProperties.LISTED_NODE, 0));
        Assertions.assertEquals(ctx, ctx2);
        Assertions.assertEquals(ctx.hashCode(), ctx2.hashCode());
    }

    @Test
    public void testFactorizeUsingRoute() {
        PropertyRoute route = PropertyRoute.of(MixedModelProperties.NODE.getNodeRetriever(), Context.EMPTY);
        Context ctx = Context.of(route);

        Assertions.assertEquals(1, ctx.size());
        Assertions.assertTrue(ctx.containsReference(MixedModelProperties.NODE.getNodeRetriever()));
        Assertions.assertTrue(ctx.containsReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));
        Assertions.assertFalse(ctx.containsReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyIndex.class));
        Assertions.assertSame(route, ctx.getReference(MixedModelProperties.NODE.getNodeRetriever()));
        Assertions.assertSame(route, ctx.getReference(MixedModelProperties.NODE.getNodeRetriever(), PropertyRoute.class));

        Context ctx2 = Context.of(PropertyRoute.of(MixedModelProperties.NODE.getNodeRetriever(), Context.EMPTY));
        Assertions.assertEquals(ctx, ctx2);
        Assertions.assertEquals(ctx.hashCode(), ctx2.hashCode());
    }

    @Test
    public void testStreamContext() {
        Set<PropertyReference<?, ?>> references = new HashSet<>();
        references.add(PropertyIndex.of(MixedModelProperties.LISTED_NODE, 0));
        references.add(PropertyRoute.of(MixedModelProperties.NODE.getNodeRetriever(), Context.EMPTY));

        Context ctx = Context.of(references.toArray(new PropertyReference[2]));
        Set<PropertyReference<?, ?>> references2 = new HashSet<>();
        ctx.stream().forEach(references2::add);

        Assertions.assertEquals(references , references2);
    }

    @Test
    public void testIterateContext() {
        Set<PropertyReference<?, ?>> references = new HashSet<>();
        references.add(PropertyIndex.of(MixedModelProperties.LISTED_NODE, 0));
        references.add(PropertyRoute.of(MixedModelProperties.NODE.getNodeRetriever(), Context.EMPTY));

        Context ctx = Context.of(references.toArray(new PropertyReference[2]));
        Set<PropertyReference<?, ?>> references2 = new HashSet<>();
        Iterator<? extends PropertyReference<?, ?>> iter = ctx.iterator();
        while (iter.hasNext()) {
            references2.add(iter.next());
        }

        Assertions.assertEquals(references , references2);
    }
}
