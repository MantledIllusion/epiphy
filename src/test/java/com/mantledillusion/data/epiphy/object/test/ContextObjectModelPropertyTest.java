package com.mantledillusion.data.epiphy.object.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.object.ObjectModelProperties;
import com.mantledillusion.data.epiphy.object.model.ObjectModel;
import com.mantledillusion.data.epiphy.object.model.ObjectSubType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

public class ContextObjectModelPropertyTest {

    @Test
    public void testNullOccurrences() {
        ObjectModel model = new ObjectModel();
        model.setSub(null);

        Assertions.assertEquals(0, ObjectModelProperties.MODELSUB.occurrences(model));
    }

    @Test
    public void testOccurrences() {
        ObjectModel model = new ObjectModel();
        model.setSub(new ObjectSubType());

        Assertions.assertEquals(1, ObjectModelProperties.MODELSUB.occurrences(model));
    }

    @Test
    public void testNullContexting() {
        ObjectModel model = new ObjectModel();
        model.setSub(null);

        Collection<Context> contexts = ObjectModelProperties.MODELSUB.contextualize(model);
        Assertions.assertEquals(0, contexts.size());
    }

    @Test
    public void testContexting() {
        ObjectModel model = new ObjectModel();
        model.setSub(new ObjectSubType());

        Collection<Context> contexts = ObjectModelProperties.MODELSUB.contextualize(model);
        Assertions.assertEquals(1, contexts.size());
        Iterator<Context> iter = contexts.iterator();
        while (iter.hasNext()) {
            Context context = iter.next();
            Assertions.assertFalse(context.containsReference(ObjectModelProperties.MODELSUB, PropertyReference.class));
            Assertions.assertSame(model.getSub(), ObjectModelProperties.MODELSUB.get(model, context));
        }
    }
}
