package com.jpmc.tre.service;

import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.jpmc.tre.model.Instruction;
import com.jpmc.tre.reports.Reporter;
@RunWith(MockitoJUnitRunner.class)
public class BusinessFacadeTest {

	@InjectMocks
	private BusinessFacade facade;
	@Mock
	private InstructionReceiver receiver;
	@Mock
	private PersistenceService persistenceService;
	@Mock
	private Reporter reporter;
	@Mock
	private LedgerService ledgerService;
	@Mock
	private Instruction instruction;
	@Mock
	private Collection<Instruction> instructions;
	
	@Test
	public void testSendInstruction() {
		facade.send(instruction);
		verify(receiver).receive(instruction);
	}

	@Test
	public void testDisplay() {
		facade.display();
		verify(reporter).display();
	}

	@Test
	public void testSendCollectionOfInstruction() {
		facade.send(instructions);
		verify(receiver).receive(instructions);
	}

	@Test
	public void testReport() {
		facade.report();
		verify(reporter).incoming();
		verify(reporter).outgoing();
		verify(reporter).ranking();
	}

}
