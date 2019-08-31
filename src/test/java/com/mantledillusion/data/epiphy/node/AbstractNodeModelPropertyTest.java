package com.mantledillusion.data.epiphy.node;

import com.mantledillusion.data.epiphy.node.model.NodeModel;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractNodeModelPropertyTest {

	protected static final String ROOT = "root";
	protected static final String NODE_0 = "n0";
	protected static final String NODE_1 = "n1";

	protected NodeModel root;
	
	@BeforeEach
	public void before() {
		this.root = new NodeModel();
		this.root.setId(ROOT);

		NodeModel nodeSub0 = new NodeModel();
		nodeSub0.setId(NODE_0);
		this.root.setChild(nodeSub0);

		NodeModel nodeSub1 = new NodeModel();
		nodeSub1.setId(NODE_1);
		nodeSub0.setChild(nodeSub1);
	}
}
