package com.mantledillusion.data.epiphy.node.tests;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.mantledillusion.data.epiphy.context.PropertyRoute;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.node.AbstractNodeModelPropertyTest;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;

public class GetNodeModelPropertyTest extends AbstractNodeModelPropertyTest {

	@Test
	public void testGetNodedProperty() {
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 1, 0));
		assertSame(this.model.rootNode.leaves.get(1).leaves.get(0), NodeModelProperties.NODE.get(this.model, context));
	}

	@Test(expected = InterruptedPropertyPathException.class)
	public void testGetNodedPropertyIntermediateNull() {
		this.model.rootNode.leaves.add(null);
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 2, 0));
		NodeModelProperties.NODE.get(this.model, context);
	}

	@Test(expected = OutboundPropertyPathException.class)
	public void testGetNodedPropertyOutOfBounds() {
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 2, 0));
		NodeModelProperties.NODE.get(this.model, context);
	}
}
