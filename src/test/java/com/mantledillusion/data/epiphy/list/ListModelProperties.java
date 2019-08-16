package com.mantledillusion.data.epiphy.list;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;

import java.util.List;

public interface ListModelProperties {

    ModelPropertyList<List<List<String>>, String> ELEMENTLIST = ModelPropertyList.fromList();

    ModelProperty<List<String>, String> ELEMENT = ModelProperty.fromList();

    ModelProperty<List<List<String>>, String> ELEMENTLIST_TO_ELEMENT = ELEMENTLIST.append(ELEMENT);
}
