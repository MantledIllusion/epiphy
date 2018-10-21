package com.mantledillusion.data.epiphy.node.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.mantledillusion.data.epiphy.context.PropertyRoute;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.node.AbstractNodeModelPropertyTest;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeModelNodeType;

public class RemoveNodeModelPropertyTest extends AbstractNodeModelPropertyTest {

	@Test
	public void testRemoveNodedPropertyAtEnd() {
		DefaultContext context = DefaultContext.of();
		
		assertSame(NODE_1, NodeModelProperties.NODE.strip(this.model, context).getValue().id);
		assertEquals(1, this.model.rootNode.leaves.size());
		assertSame(NODE_0, this.model.rootNode.leaves.get(0).id);
		
		assertSame(NODE_0, NodeModelProperties.NODE.strip(this.model, context).getValue().id);
		assertEquals(0, this.model.rootNode.leaves.size());
		
		assertEquals(null, NodeModelProperties.NODE.strip(this.model, context));
	}

	@Test
	public void testRemoveNodedPropertyAtIndex() {
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 0));
		assertSame(NODE_0, NodeModelProperties.NODE.removeAt(this.model, context).id);
		assertEquals(1, this.model.rootNode.leaves.size());
		assertSame(NODE_1, this.model.rootNode.leaves.get(0).id);
		
		assertSame(NODE_1, NodeModelProperties.NODE.removeAt(this.model, context).id);
		assertEquals(0, this.model.rootNode.leaves.size());
	}

	@Test
	public void testRemoveNodedPropertyByIdentity() {
		NodeModelNodeType n0 = this.model.rootNode.leaves.get(0);
		assertArrayEquals(new int[] {0, 0}, NodeModelProperties.NODE.remove(this.model, n0));
		assertSame(NODE_1, this.model.rootNode.leaves.get(0).id);
		assertEquals(null, NodeModelProperties.NODE.remove(this.model, n0));
	}

	@Test(expected = InterruptedPropertyPathException.class)
	public void testRemoveNodedPropertyIntermediateNull() {
		this.model.rootNode.leaves.add(null);
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 2, 0));
		NodeModelProperties.NODE.removeAt(this.model, context);
	}
}
