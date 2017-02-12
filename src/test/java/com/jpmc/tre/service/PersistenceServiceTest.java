package com.jpmc.tre.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.jpmc.tre.model.Instruction;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceServiceTest {

	@InjectMocks
	private CachePersistenceService unitToTest;
	@Mock
	private Instruction instruction1;
	@Mock
	private Instruction instruction2;
	@Mock
	private Instruction instruction3;
	
	private Collection<Instruction> instructions;
	
	@Before
	public void setup(){
		instructions = Arrays.asList(instruction1,instruction2,instruction3);
	}
	
	@Test
	public void testPersist() {
		unitToTest.persist(instruction1);
		assertEquals(1, unitToTest.getInstructions().size());
		assertEquals(instruction1, unitToTest.getInstructions().iterator().next());
	}
	
	@Test
	public void testPersistCollection() {
		unitToTest.persist(instructions);
		assertTrue(instructions.containsAll(unitToTest.getInstructions()));
		assertTrue(unitToTest.getInstructions().containsAll(instructions));
				
	}

}
