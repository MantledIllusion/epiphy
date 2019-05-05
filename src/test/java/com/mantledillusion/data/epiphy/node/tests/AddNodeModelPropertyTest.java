package com.mantledillusion.data.epiphy.node.tests;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.context.PropertyRoute;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.node.AbstractNodeModelPropertyTest;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeModelNodeType;

public class AddNodeModelPropertyTest extends AbstractNodeModelPropertyTest {

	@Test
	public void testAddNodedPropertyAtEnd() {
		NodeModelNodeType newNode = new NodeModelNodeType();
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 1, 0));
		NodeModelProperties.NODE.append(this.model, newNode, context);
		assertSame(newNode, this.model.rootNode.leaves.get(1).leaves.get(0).leaves.get(0));
	}

	@Test
	public void testAddNodedPropertyAtIndex() {
		NodeModelNodeType newNode = new NodeModelNodeType();
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 1, 0));
		NodeModelProperties.NODE.addAt(this.model, newNode, context);
		assertSame(newNode, this.model.rootNode.leaves.get(1).leaves.get(0));
	}

	@Test
	public void testAddNodedPropertyIntermediateNull() {
		this.model.rootNode.leaves.add(null);
		NodeModelNodeType newNode = new NodeModelNodeType();
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 2, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			NodeModelProperties.NODE.addAt(this.model, newNode, context);
		});
	}
}
