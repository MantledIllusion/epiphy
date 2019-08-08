package com.mantledillusion.data.epiphy.node.tests;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.context.PropertyRoute;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.node.AbstractNodeModelPropertyTest;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeModelNodeType;

public class SetNodeModelPropertyTest extends AbstractNodeModelPropertyTest {

	@Test
	public void testSetNodedProperty() {
		NodeModelNodeType newNode = new NodeModelNodeType();
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 1, 0));
		NodeModelProperties.NODE.set(this.model, newNode, context);
		assertSame(newNode, this.model.rootNode.leaves.get(1).leaves.get(0));
	}

	@Test
	public void testSetNodedPropertyIntermediateNull() {
		this.model.rootNode.leaves.set(1, null);
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 1, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			NodeModelProperties.NODE.set(this.model, new NodeModelNodeType(), context);
		});
	}

	@Test
	public void testSetNodedPropertyOutOfBounds() {
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 1, 1));
		assertThrows(OutboundPropertyPathException.class, () -> {
			NodeModelProperties.NODE.set(this.model, new NodeModelNodeType(), context);
		});
	}
}
