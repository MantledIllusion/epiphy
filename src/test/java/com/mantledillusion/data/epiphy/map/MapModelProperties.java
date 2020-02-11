package com.mantledillusion.data.epiphy.map;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyMap;

import java.util.Map;

public interface MapModelProperties {

    ModelPropertyMap<Map<String, Map<String, String>>, String, String> ELEMENTMAP = ModelPropertyMap.fromMap();

    ModelProperty<Map<String, String>, String> ELEMENT = ModelProperty.fromMap();

    ModelProperty<Map<String, Map<String, String>>, String> ELEMENTMAP_TO_ELEMENT = ELEMENTMAP.append(ELEMENT);
}
