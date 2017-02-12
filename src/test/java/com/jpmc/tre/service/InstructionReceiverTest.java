package com.jpmc.tre.service;

import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.jpmc.tre.model.Instruction;
@RunWith(MockitoJUnitRunner.class)
public class InstructionReceiverTest {

	@InjectMocks
	private InstructionReceiver unitToTest;
	@Mock
	private PersistenceService persistenceService;
	@Mock
	private Instruction instruction;
	@Mock
	private Collection<Instruction> instructions;
	
	@Test
	public void testReceive() {
		unitToTest.receive(instruction);
		verify(persistenceService).persist(instruction);
	}
	
	@Test
	public void testReceiveCollection() {
		unitToTest.receive(instructions);
		verify(persistenceService).persist(instructions);
	}

}
