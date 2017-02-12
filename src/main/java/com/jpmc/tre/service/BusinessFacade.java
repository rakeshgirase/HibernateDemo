package com.jpmc.tre.service;

import java.util.Collection;

import com.jpmc.tre.model.Instruction;
import com.jpmc.tre.reports.ConsoleReporter;
import com.jpmc.tre.reports.Reporter;

public class BusinessFacade implements Facade{

	private InstructionReceiver receiver;
	private PersistenceService persistenceService;
	private Reporter reporter;
	private LedgerService ledgerService;

	private BusinessFacade() {
		persistenceService = new CachePersistenceService();
		receiver = new InstructionReceiver(persistenceService);
		ledgerService = new LedgerService(persistenceService);
		reporter = new ConsoleReporter(ledgerService);		
	}
	
	@Override
	public void send(Instruction instruction) {
		receiver.receive(instruction);
	}

	@Override
	public void display() {
		reporter.display();	
	}
	
	public static Facade getInstance() {		
		return new BusinessFacade();
	}

	@Override
	public void send(Collection<Instruction> instructions) {
		receiver.receive(instructions);		
	}

	@Override
	public void report() {
		reporter.incoming();
		reporter.outgoing();
		reporter.ranking();
	}

}
