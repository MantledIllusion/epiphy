package com.mantledillusion.data.epiphy.set.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.set.AbstractSetModelPropertyTest;
import com.mantledillusion.data.epiphy.set.SetModelProperties;
import com.mantledillusion.data.epiphy.set.model.SetModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ContextSetModelPropertyTest extends AbstractSetModelPropertyTest {

    @Test
    public void testEmptyOccurrences() {
        this.model.getObjects().clear();

        Assertions.assertEquals(0, SetModelProperties.ELEMENTSET_TO_ELEMENT.occurrences(this.model));
    }

    @Test
    public void testOccurrences() {
        Assertions.assertEquals(2, SetModelProperties.ELEMENTSET_TO_ELEMENT.occurrences(this.model));
    }

    @Test
    public void testEmptyContexting() {
        Collection<Context> contexts = SetModelProperties.ELEMENTSET_TO_ELEMENT.contextualize(new SetModel());
        Assertions.assertEquals(0, contexts.size());
    }

    @Test
    public void testContexting() {
        Collection<Context> contexts = SetModelProperties.ELEMENTSET_TO_ELEMENT.contextualize(this.model);
        Assertions.assertEquals(2, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertEquals(1, context.size());
            Assertions.assertTrue(context.containsReference(SetModelProperties.ELEMENT, PropertyKey.class));
            PropertyKey index = context.getReference(SetModelProperties.ELEMENT, PropertyKey.class);
            Assertions.assertSame(index.getReference(), SetModelProperties.ELEMENTSET_TO_ELEMENT.get(this.model, context));
        }
    }

    @Test
    public void testBasedContexting() {
        Collection<Context> contexts = SetModelProperties.ELEMENTSET_TO_ELEMENT.contextualize(this.model,
                Context.of(PropertyKey.ofSet(SetModelProperties.ELEMENT, ELEMENT_B)));
        Assertions.assertEquals(1, contexts.size());
        Context context = contexts.iterator().next();

        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(SetModelProperties.ELEMENT, PropertyKey.class));
        Assertions.assertSame(ELEMENT_B, context.getReference(SetModelProperties.ELEMENT, PropertyKey.class).getReference());
        Assertions.assertSame(ELEMENT_B, SetModelProperties.ELEMENTSET_TO_ELEMENT.get(this.model, context));
    }

    @Test
    public void testValueContexting() {
        Set<Object> set = new HashSet<>();
        set.add(ELEMENT_A);
        set.add(ELEMENT_B);

        Collection<Context> contexts = SetModelProperties.ELEMENT.contextualize(set, ELEMENT_B);
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(SetModelProperties.ELEMENT, PropertyKey.class));
        Assertions.assertSame(ELEMENT_B, SetModelProperties.ELEMENT.get(set, context));
    }

    @Test
    public void testValueBasedContexting() {
        Object object = new Object();
        Set<Object> list = Collections.newSetFromMap(new IdentityHashMap<>());
        list.add(new Object());
        list.add(object);

        Collection<Context> contexts = SetModelProperties.ELEMENT.contextualize(list, object,
                Context.of(PropertyKey.ofSet(SetModelProperties.ELEMENT, object)));
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(SetModelProperties.ELEMENT, PropertyKey.class));
        Assertions.assertSame(object, SetModelProperties.ELEMENT.get(list, context));
    }

    @Test
    public void testStream() {
        Set<String> expected = new HashSet<>(Arrays.asList(ELEMENT_A, ELEMENT_B));
        SetModelProperties.ELEMENTSET_TO_ELEMENT.stream(this.model).forEach(o -> Assertions.assertTrue(expected.contains(o)));
    }

    @Test
    public void testIterate() {
        Set<String> expected = new HashSet<>(Arrays.asList(ELEMENT_A, ELEMENT_B));
        for (Object o: SetModelProperties.ELEMENTSET_TO_ELEMENT.iterate(this.model)) {
            Assertions.assertTrue(expected.contains(o));
        }
    }

    @Test
    public void testPredecessor() {
        Set<Object> copy = new HashSet<>(this.model.getObjects());
        this.model.getObjects().forEach(o -> copy.remove(SetModelProperties.ELEMENTSET_TO_ELEMENT.successor(this.model, o)));

        Assertions.assertEquals(1, copy.size());
        Object first = copy.iterator().next();

        Set<Object> others = new HashSet<>(this.model.getObjects());
        others.remove(first);
        Assertions.assertEquals(1, copy.size());
        Object last = others.iterator().next();

        Assertions.assertSame(first, SetModelProperties.ELEMENTSET_TO_ELEMENT.predecessor(this.model, last));
    }

    @Test
    public void testSuccessor() {
        Set<Object> copy = new HashSet<>(this.model.getObjects());
        this.model.getObjects().forEach(o -> copy.remove(SetModelProperties.ELEMENTSET_TO_ELEMENT.predecessor(this.model, o)));

        Assertions.assertEquals(1, copy.size());
        Object last = copy.iterator().next();

        Set<Object> others = new HashSet<>(this.model.getObjects());
        others.remove(last);
        Assertions.assertEquals(1, copy.size());
        Object first = others.iterator().next();

        Assertions.assertSame(last, SetModelProperties.ELEMENTSET_TO_ELEMENT.successor(this.model, first));
    }
}
