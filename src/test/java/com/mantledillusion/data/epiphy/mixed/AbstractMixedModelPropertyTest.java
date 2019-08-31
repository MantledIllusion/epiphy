package com.mantledillusion.data.epiphy.mixed;

import com.mantledillusion.data.epiphy.mixed.model.MixedModelNode;
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;
import org.junit.jupiter.api.BeforeEach;

public class AbstractMixedModelPropertyTest {

	protected static final String NODE_ROOT = "root";
	protected static final String SUB_ROOT = "sub_root";
	protected static final String NODE_CHILD1A = "child1a";
	protected static final String NODE_CHILD1B = "child1b";
	protected static final String SUB_CHILD1A = "sub_child1a";
	protected static final String SUB_CHILD1B = "sub_child1b";
	protected static final String NODE_CHILD2A = "child2a";

	protected MixedModelNode root;
	
	@BeforeEach
	public void before() {
		this.root = new MixedModelNode();
		this.root.setNodeId(NODE_ROOT);
		
		MixedSubType sub1 = new MixedSubType();
		sub1.setSubId(SUB_ROOT);
		this.root.setSub(sub1);

		MixedModelNode child1a = new MixedModelNode();
		child1a.setNodeId(NODE_CHILD1A);
		sub1.getSubNodes().add(child1a);

		MixedModelNode child1b = new MixedModelNode();
		child1b.setNodeId(NODE_CHILD1B);
		sub1.getSubNodes().add(child1b);

		MixedSubType sub_child1a = new MixedSubType();
		sub_child1a.setSubId(SUB_CHILD1A);
		child1a.setSub(sub_child1a);

		MixedSubType sub_child1b = new MixedSubType();
		sub_child1b.setSubId(SUB_CHILD1B);
		child1b.setSub(sub_child1b);

		MixedModelNode child2a = new MixedModelNode();
		child2a.setNodeId(NODE_CHILD2A);
		sub_child1a.getSubNodes().add(child2a);
	}
}
