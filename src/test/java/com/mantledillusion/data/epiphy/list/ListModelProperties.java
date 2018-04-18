package com.mantledillusion.data.epiphy.list;

import java.util.List;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.ReadOnlyModelPropertyList;

public class ListModelProperties {
	
	public static final ReadOnlyModelPropertyList<List<List<String>>, List<String>> MODEL = ModelProperty.rootChildList();
	
	public static final ModelPropertyList<List<List<String>>, String> ELEMENTLIST = MODEL.defineElementAsChildList("elementList");
	
	public static final ModelProperty<List<List<String>>, String> ELEMENT = ELEMENTLIST.defineElementAsChild("element");
}
