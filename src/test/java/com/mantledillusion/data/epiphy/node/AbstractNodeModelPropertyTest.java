package com.mantledillusion.data.epiphy.node;

import org.junit.Before;

import com.mantledillusion.data.epiphy.node.model.NodeModel;
import com.mantledillusion.data.epiphy.node.model.NodeModelNodeType;

public abstract class AbstractNodeModelPropertyTest {

	protected static final String ROOT = "root";
	protected static final String NODE_0 = "n0";
	protected static final String NODE_1 = "n1";
	protected static final String NODE_1_NODE_0 = "n1_n0";

	protected NodeModel model;
	
	@Before
	public void before() {
		this.model = new NodeModel();
		
		NodeModelNodeType rootNode = new NodeModelNodeType();
		rootNode.id = ROOT;
		this.model.rootNode = rootNode;

		NodeModelNodeType nodeSub0 = new NodeModelNodeType();
		nodeSub0.id = NODE_0;
		rootNode.leaves.add(nodeSub0);

		NodeModelNodeType nodeSub1 = new NodeModelNodeType();
		nodeSub1.id = NODE_1;
		rootNode.leaves.add(nodeSub1);

		NodeModelNodeType nodeSub1Sub0 = new NodeModelNodeType();
		nodeSub1Sub0.id = NODE_1_NODE_0;
		nodeSub1.leaves.add(nodeSub1Sub0);
	}
}
