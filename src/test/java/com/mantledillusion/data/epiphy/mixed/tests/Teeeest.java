package com.mantledillusion.data.epiphy.mixed.tests;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.DefaultContext;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Teeeest {

    @Test
    public void test() {
        MixedSubType r = new MixedSubType();
        r.subId = "s0";

        MixedSubType s0 = new MixedSubType();
        s0.subId = "s0";
        r.leaves.add(s0);

        MixedSubType s1 = new MixedSubType();
        s1.subId = "s1";
        r.leaves.add(s1);

        MixedSubType s1a = new MixedSubType();
        s1a.subId = "s1a";
        s1.leaves.add(s1a);

        Context rContext = DefaultContext.of(PropertyIndex.of(MixedModelProperties.SUB, 1));
        Context s1Context = DefaultContext.of(PropertyIndex.of(MixedModelProperties.SUB, 0));
        Context context = DefaultContext.of(PropertyRoute.of(MixedModelProperties.NODE, rContext, s1Context));
        Assertions.assertEquals(s1a.subId, MixedModelProperties.ID.get(r, context));
    }
}
