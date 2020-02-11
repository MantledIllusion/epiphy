package com.mantledillusion.data.epiphy.map;

import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

public class AbstractMapModelPropertyTest {

    protected static final String ELEMENT_A_ELEMENT_A = "eA_eA";
    protected static final String ELEMENT_A_ELEMENT_B = "eA_eB";
    protected static final String ELEMENT_B_ELEMENT_A = "eB_eA";

    protected static final String NEW_ELEMENT = "newElem";

    protected Map<String, Map<String, String>> model;

    @BeforeEach
    public void before() {
        this.model = new HashMap<>();

        Map<String, String> elementList1 = new HashMap<>();
        this.model.put("A", elementList1);

        elementList1.put("A", ELEMENT_A_ELEMENT_A);
        elementList1.put("B", ELEMENT_A_ELEMENT_B);

        Map<String, String> elementList2 = new HashMap<>();
        this.model.put("B", elementList2);

        elementList2.put("A", ELEMENT_B_ELEMENT_A);
    }
}
