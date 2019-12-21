package com.mantledillusion.data.epiphy.object.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.object.AbstractObjectModelPropertyTest;
import com.mantledillusion.data.epiphy.object.ObjectModelProperties;
import com.mantledillusion.data.epiphy.object.model.ObjectSubType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ContextObjectModelPropertyTest extends AbstractObjectModelPropertyTest {

    @Test
    public void testNullOccurrences() {
        this.model.setSub(null);

        Assertions.assertEquals(0, ObjectModelProperties.MODELSUB.occurrences(this.model));
    }

    @Test
    public void testOccurrences() {
        Assertions.assertEquals(1, ObjectModelProperties.MODELSUB.occurrences(this.model));
    }

    @Test
    public void testNullContexting() {
        this.model.setSub(null);

        Collection<Context> contexts = ObjectModelProperties.MODELSUB.contextualize(this.model);
        Assertions.assertEquals(0, contexts.size());
    }

    @Test
    public void testContexting() {
        Collection<Context> contexts = ObjectModelProperties.MODELSUB.contextualize(this.model);
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(0, context.size());
        Assertions.assertFalse(context.containsReference(ObjectModelProperties.MODELSUB, PropertyReference.class));
        Assertions.assertSame(this.model.getSub(), ObjectModelProperties.MODELSUB.get(this.model, context));
    }

    @Test
    public void testValueContexting() {
        Collection<Context> contexts = ObjectModelProperties.MODELSUB.contextualize(this.model, this.model.getSub());
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(0, context.size());
        Assertions.assertFalse(context.containsReference(ObjectModelProperties.MODELSUB, PropertyReference.class));
        Assertions.assertSame(this.model.getSub(), ObjectModelProperties.MODELSUB.get(this.model, context));
    }

    @Test
    public void testStream() {
        Queue<ObjectSubType> expected = new ArrayDeque<>(Arrays.asList(this.model.getSub()));
        ObjectModelProperties.MODELSUB.stream(this.model).forEachOrdered(sub -> Assertions.assertSame(expected.poll(), sub));
    }

    @Test
    public void testIterate() {
        Queue<ObjectSubType> expected = new ArrayDeque<>(Arrays.asList(this.model.getSub()));
        for (ObjectSubType sub: ObjectModelProperties.MODELSUB.iterate(this.model)) {
            Assertions.assertSame(expected.poll(), sub);
        }
    }

    @Test
    public void testPredecessor() {
        Assertions.assertNull(ObjectModelProperties.MODELSUB.predecessor(this.model, this.model.getSub()));
    }

    @Test
    public void testSuccessor() {
        Assertions.assertNull(ObjectModelProperties.MODELSUB.successor(this.model, this.model.getSub()));
    }
}
