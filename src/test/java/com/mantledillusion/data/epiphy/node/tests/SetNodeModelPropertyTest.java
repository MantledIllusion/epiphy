package com.mantledillusion.data.epiphy.node.tests;

import com.mantledillusion.data.epiphy.context.DefaultContext;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.node.AbstractNodeModelPropertyTest;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetNodeModelPropertyTest extends AbstractNodeModelPropertyTest {

	@Test
	public void testSetNodedProperty() {
		NodeModel newNode = new NodeModel();
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, DefaultContext.EMPTY));
		NodeModelProperties.NODE.set(this.root, newNode, context);
		assertSame(newNode, this.root.getChild().getChild());
	}

	@Test
	public void testSetNodedPropertyIntermediateNull() {
		this.root.setChild(null);
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, DefaultContext.EMPTY));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			NodeModelProperties.NODE.set(this.root, new NodeModel(), context);
		});
	}
}
