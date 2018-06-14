package com.mantledillusion.data.epiphy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

public class BasicIndexingTest {

	private DefaultContext a;
	
	@Before
	public void before() {
		this.a = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0));
	}
	
	@Test
	public void testCreateIndex() {
		PropertyIndex i = PropertyIndex.of(ListModelProperties.MODEL, 5);
		assertTrue(i.getProperty() == ListModelProperties.MODEL);
		assertEquals(i.getKey(), Integer.valueOf(5));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateIndexWithoutProperty() {
		PropertyIndex.of(null, 0);
	}
	
	@Test
	public void testCreateEmptyIndexContext() {
		DefaultContext.of();
	}
	
	@Test
	public void testIndexEqualsHashCode() {
		PropertyIndex a = PropertyIndex.of(ListModelProperties.MODEL, 0);
		PropertyIndex b = PropertyIndex.of(ListModelProperties.MODEL, 0);

		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a, b);
		assertEquals(a.toString(), b.toString());
	}
	
	@Test
	public void testContextEqualsHashCode() {
		DefaultContext newA = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0));
		DefaultContext b = DefaultContext.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		
		assertEquals(this.a.hashCode(), newA.hashCode());
		assertTrue(this.a.equals(newA));
		
		assertNotEquals(this.a.hashCode(), b.hashCode());
		assertFalse(this.a.equals(b));

		assertEquals(this.a.toString(), newA.toString());
	}
	
	@Test
	public void testGetIndex() {
		assertEquals((Integer) 0, this.a.getKey(ListModelProperties.MODEL));
		assertEquals((Integer) null, this.a.getKey(ListModelProperties.ELEMENTLIST));
	}
	
	@Test
	public void testContainsIndex() {
		assertTrue(this.a.containsKey(ListModelProperties.MODEL));
		assertFalse(this.a.containsKey(ListModelProperties.ELEMENTLIST));
	}
}
