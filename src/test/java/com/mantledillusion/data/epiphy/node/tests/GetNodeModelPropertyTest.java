package com.mantledillusion.data.epiphy.node.tests;

import com.mantledillusion.data.epiphy.context.DefaultContext;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.node.AbstractNodeModelPropertyTest;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetNodeModelPropertyTest extends AbstractNodeModelPropertyTest {

	@Test
	public void testGetNodedProperty() {
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, DefaultContext.EMPTY, DefaultContext.EMPTY));
		assertSame(this.root.getChild().getChild(), NodeModelProperties.NODE.get(this.root, context));
	}

	@Test
	public void testGetNodedPropertyIntermediateNull() {
		this.root.setChild(null);
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, DefaultContext.EMPTY, DefaultContext.EMPTY));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			NodeModelProperties.NODE.get(this.root, context);
		});
	}
}
