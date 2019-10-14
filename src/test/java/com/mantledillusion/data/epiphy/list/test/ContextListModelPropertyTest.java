package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ContextListModelPropertyTest extends AbstractListModelPropertyTest {

    @Test
    public void testEmptyOccurrences() {
        this.model.clear();

        Assertions.assertEquals(0, ListModelProperties.ELEMENTLIST.occurrences(this.model));
    }

    @Test
    public void testOccurrences() {
        Assertions.assertEquals(2, ListModelProperties.ELEMENTLIST.occurrences(this.model));
    }

    @Test
    public void testEmptyContexting() {
        Collection<Context> contexts = ListModelProperties.ELEMENT.contextualize(Collections.emptyList());
        Assertions.assertEquals(0, contexts.size());
    }

    @Test
    public void testContexting() {
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
    public void testStream() {
        Queue<String> expected = new ArrayDeque<>(Arrays.asList(ELEMENT_0_ELEMENT_0, ELEMENT_0_ELEMENT_1, ELEMENT_1_ELEMENT_0));
        ListModelProperties.ELEMENTLIST_TO_ELEMENT.stream(this.model).forEachOrdered(element -> Assertions.assertSame(expected.poll(), element));
    }

    @Test
    public void testIterate() {
        Queue<String> expected = new ArrayDeque<>(Arrays.asList(ELEMENT_0_ELEMENT_0, ELEMENT_0_ELEMENT_1, ELEMENT_1_ELEMENT_0));
        for (String s: ListModelProperties.ELEMENTLIST_TO_ELEMENT.iterate(this.model)) {
            Assertions.assertSame(expected.poll(), s);
        }
    }

    @Test
    public void testPredecessor() {
        Assertions.assertSame(null, ListModelProperties.ELEMENTLIST_TO_ELEMENT.predecessor(this.model, this.model.get(0).get(0)));
        Assertions.assertSame(this.model.get(0).get(0), ListModelProperties.ELEMENTLIST_TO_ELEMENT.predecessor(this.model, this.model.get(0).get(1)));
        Assertions.assertSame(this.model.get(0).get(1), ListModelProperties.ELEMENTLIST_TO_ELEMENT.predecessor(this.model, this.model.get(1).get(0)));
    }

    @Test
    public void testSuccessor() {
        Assertions.assertSame(this.model.get(0).get(1), ListModelProperties.ELEMENTLIST_TO_ELEMENT.successor(this.model, this.model.get(0).get(0)));
        Assertions.assertSame(this.model.get(1).get(0), ListModelProperties.ELEMENTLIST_TO_ELEMENT.successor(this.model, this.model.get(0).get(1)));
        Assertions.assertSame(null, ListModelProperties.ELEMENTLIST_TO_ELEMENT.successor(this.model, this.model.get(1).get(0)));
    }
}
