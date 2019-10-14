package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ContextListModelPropertyTest {

    @Test
    public void testEmptyOccurrences() {
        Assertions.assertEquals(0, ListModelProperties.ELEMENT.occurrences(Collections.emptyList()));
    }

    @Test
    public void testOccurrences() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");

        Assertions.assertEquals(2, ListModelProperties.ELEMENT.occurrences(list));
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
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        Queue<String> expected = new ArrayDeque<>(Arrays.asList(list.get(0), list.get(1), list.get(2), list.get(3)));
        ListModelProperties.ELEMENT.stream(list).forEachOrdered(element -> Assertions.assertSame(expected.poll(), element));
    }

    @Test
    public void testIterate() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        Queue<String> expected = new ArrayDeque<>(Arrays.asList(list.get(0), list.get(1), list.get(2), list.get(3)));
        for (String s: ListModelProperties.ELEMENT.iterate(list)) {
            Assertions.assertSame(expected.poll(), s);
        }
    }
}
