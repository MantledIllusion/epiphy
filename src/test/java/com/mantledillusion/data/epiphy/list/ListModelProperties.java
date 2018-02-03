package com.mantledillusion.data.epiphy.list;

import java.util.List;

import com.mantledillusion.data.epiphy.DefiniteModelProperty;
import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;

public class ListModelProperties {
	
	public static final ModelPropertyList<List<List<String>>, List<String>> MODEL = ModelProperty.rootChildList();
	
	public static final ModelPropertyList<List<List<String>>, String> ELEMENTLIST = MODEL.defineElementAsChildList("elementList");
	
	public static final DefiniteModelProperty<List<List<String>>, String> ELEMENT = ELEMENTLIST.defineElementAsChild("element");
}
