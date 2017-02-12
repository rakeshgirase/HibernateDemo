package com.jpmc.tre.util;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class MultivalueMapTest {

	private MultivalueMap<String, String> mvm = new MultivalueMap<>();

	@Before
	public void setup() {
		mvm.put("KEY1", "VALUE1");
		mvm.put("KEY1", "VALUE2");
		mvm.put("KEY1", "VALUE3");
		mvm.put("KEY2", "VALUE4");
		mvm.put("KEY2", "VALUE5");
	}

	@Test
	public void testGet() {
		Collection<String> values1 = mvm.get("KEY1");
		assertTrue(values1.size() == 3);
		values1.containsAll(Arrays.asList("VALUE1", "VALUE2", "VALUE3"));
		Collection<String> values2 = mvm.get("KEY2");
		assertTrue(values2.size() == 2);
		values2.containsAll(Arrays.asList("VALUE4", "VALUE5"));
	}

	@Test
	public void testKeyset() {
		Set<String> keys = mvm.keySet();
		assertTrue(keys.size() == 2);
		assertTrue(keys.containsAll(Arrays.asList("KEY1", "KEY2")));
	}

}
