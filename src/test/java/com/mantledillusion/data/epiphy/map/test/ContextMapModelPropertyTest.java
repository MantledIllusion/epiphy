package com.mantledillusion.data.epiphy.map.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.map.AbstractMapModelPropertyTest;
import com.mantledillusion.data.epiphy.map.MapModelProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ContextMapModelPropertyTest extends AbstractMapModelPropertyTest {

    @Test
    public void testEmptyOccurrences() {
        this.model.clear();

        Assertions.assertEquals(0, MapModelProperties.ELEMENTMAP.occurrences(this.model));
    }

    @Test
    public void testOccurrences() {
        Assertions.assertEquals(2, MapModelProperties.ELEMENTMAP.occurrences(this.model));
    }

    @Test
    public void testEmptyContexting() {
        Collection<Context> contexts = MapModelProperties.ELEMENT.contextualize(Collections.emptyMap());
        Assertions.assertEquals(0, contexts.size());
    }

    @Test
    public void testContexting() {
        Map<String, String> map = new HashMap<>();
        map.put("A", ELEMENT_A_ELEMENT_A);
        map.put("B", ELEMENT_A_ELEMENT_B);

        Collection<Context> contexts = MapModelProperties.ELEMENT.contextualize(map);
        Assertions.assertEquals(2, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertEquals(1, context.size());
            Assertions.assertTrue(context.containsReference(MapModelProperties.ELEMENT, PropertyKey.class));
            PropertyKey index = context.getReference(MapModelProperties.ELEMENT, PropertyKey.class);
            Assertions.assertSame(map.get(index.getReference()), MapModelProperties.ELEMENT.get(map, context));
        }
    }

    @Test
    public void testBasedContexting() {
        Map<String, String> map = new HashMap<>();
        map.put("A", ELEMENT_A_ELEMENT_A);
        map.put("B", ELEMENT_A_ELEMENT_B);

        Collection<Context> contexts = MapModelProperties.ELEMENT.contextualize(map,
                Context.of(PropertyKey.ofMap(MapModelProperties.ELEMENT, "B")));
        Assertions.assertEquals(1, contexts.size());
        Context context = contexts.iterator().next();

        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(MapModelProperties.ELEMENT, PropertyKey.class));
        Assertions.assertSame("B", context.getReference(MapModelProperties.ELEMENT, PropertyKey.class).getReference());
        Assertions.assertSame(ELEMENT_A_ELEMENT_B, MapModelProperties.ELEMENT.get(map, context));
    }

    @Test
    public void testValueContexting() {
        Map<String, String> map = new HashMap<>();
        map.put("A", ELEMENT_A_ELEMENT_A);
        map.put("B", ELEMENT_A_ELEMENT_B);

        Collection<Context> contexts = MapModelProperties.ELEMENT.contextualize(map, ELEMENT_A_ELEMENT_B);
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(MapModelProperties.ELEMENT, PropertyKey.class));
        Assertions.assertSame(ELEMENT_A_ELEMENT_B, MapModelProperties.ELEMENT.get(map, context));
    }

    @Test
    public void testValueBasedContexting() {
        Map<String, String> map = new HashMap<>();
        map.put("A", ELEMENT_A_ELEMENT_B);
        map.put("B", ELEMENT_A_ELEMENT_B);

        Collection<Context> contexts = MapModelProperties.ELEMENT.contextualize(map, ELEMENT_A_ELEMENT_B,
                Context.of(PropertyKey.ofMap(MapModelProperties.ELEMENT, "B")));
        Assertions.assertEquals(1, contexts.size());

        Context context = contexts.iterator().next();
        Assertions.assertEquals(1, context.size());
        Assertions.assertTrue(context.containsReference(MapModelProperties.ELEMENT, PropertyKey.class));
        Assertions.assertSame(ELEMENT_A_ELEMENT_B, MapModelProperties.ELEMENT.get(map, context));
    }

    @Test
    public void testStream() {
        Set<String> expected = new HashSet<>(Arrays.asList(ELEMENT_A_ELEMENT_A, ELEMENT_A_ELEMENT_B, ELEMENT_B_ELEMENT_A));
        MapModelProperties.ELEMENTMAP_TO_ELEMENT.stream(this.model).forEachOrdered(element -> Assertions.assertTrue(expected.remove(element)));
        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void testIterate() {
        Set<String> expected = new HashSet<>(Arrays.asList(ELEMENT_A_ELEMENT_A, ELEMENT_A_ELEMENT_B, ELEMENT_B_ELEMENT_A));
        for (String s: MapModelProperties.ELEMENTMAP_TO_ELEMENT.iterate(this.model)) {
            Assertions.assertTrue(expected.remove(s));
        }
        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void testPredecessor() {
        Set<String> expected = new HashSet<>(Arrays.asList(ELEMENT_A_ELEMENT_A, ELEMENT_A_ELEMENT_B, ELEMENT_B_ELEMENT_A));
        Set<String> predecessors = new HashSet<>();
        for (String s: expected) {
            predecessors.add(MapModelProperties.ELEMENTMAP_TO_ELEMENT.predecessor(this.model, s));
        }
        Assertions.assertEquals(3, predecessors.size());

        predecessors.removeAll(expected);
        Assertions.assertEquals(1, predecessors.size());
        Assertions.assertTrue(predecessors.contains(null));
    }

    @Test
    public void testSuccessor() {
        Set<String> expected = new HashSet<>(Arrays.asList(ELEMENT_A_ELEMENT_A, ELEMENT_A_ELEMENT_B, ELEMENT_B_ELEMENT_A));
        Set<String> successors = new HashSet<>();
        for (String s: expected) {
            successors.add(MapModelProperties.ELEMENTMAP_TO_ELEMENT.successor(this.model, s));
        }
        Assertions.assertEquals(3, successors.size());

        successors.removeAll(expected);
        Assertions.assertEquals(1, successors.size());
        Assertions.assertTrue(successors.contains(null));
    }
}
