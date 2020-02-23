package com.mantledillusion.data.epiphy.set;

import com.mantledillusion.data.epiphy.set.model.SetModel;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;

public abstract class AbstractSetModelPropertyTest {

    protected static final String ELEMENT_A = "A";
    protected static final String ELEMENT_B = "B";
    protected static final String NEW_ELEMENT = "C";

    protected SetModel model;

    @BeforeEach
    public void before() {
        this.model = new SetModel();

        this.model.setObjects(new HashSet<>());

        this.model.getObjects().add(ELEMENT_A);
        this.model.getObjects().add(ELEMENT_B);
    }
}
