package com.mantledillusion.data.epiphy.node.tests;

import com.mantledillusion.data.epiphy.AbstractModelPropertyTest;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;
import com.mantledillusion.data.epiphy.node.model.NodeParent;
import org.junit.jupiter.api.Test;

public class FactorizeNodeModelPropertyTest extends AbstractModelPropertyTest {

    @Test
    public void testFactorizeFromSelf() {
        validateFactorization(ModelPropertyNode.from(NodeModelProperties.RETRIEVER), true, false, NodeModelProperties.RETRIEVER);
    }

    @Test
    public void testFactorizeFromObjectReadOnly() {
        validateFactorization(ModelPropertyNode.fromObject(NodeParent::getNode, NodeModelProperties.RETRIEVER), false, NodeModelProperties.RETRIEVER);
    }

    @Test
    public void testFactorizeFromObject() {
        validateFactorization(ModelPropertyNode.fromObject(NodeParent::getNode, NodeParent::setNode, NodeModelProperties.RETRIEVER), true, NodeModelProperties.RETRIEVER);
    }

    @Test
    public void testFactorizeFromList() {
        validateFactorization(ModelPropertyNode.fromList(NodeModelProperties.RETRIEVER), true, NodeModelProperties.RETRIEVER);
    }

    @Test
    public void testFactorizeFromMap() {
        validateFactorization(ModelPropertyNode.fromMap(NodeModelProperties.RETRIEVER), true, NodeModelProperties.RETRIEVER);
    }
}
