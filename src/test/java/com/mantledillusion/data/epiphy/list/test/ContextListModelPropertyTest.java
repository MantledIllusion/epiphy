package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ContextListModelPropertyTest {

    @Test
    public void testEmptyListIndexing() {
        Collection<Context> contexts = ListModelProperties.ELEMENT.contextualize(Collections.emptyList());
        Assertions.assertEquals(0, contexts.size());
    }

    @Test
    public void testListIndexing() {
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
}
