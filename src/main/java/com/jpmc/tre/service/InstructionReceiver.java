package com.jpmc.tre.service;

import java.util.Collection;

import com.jpmc.tre.model.Instruction;

public class InstructionReceiver {

	private PersistenceService persistenceService;

	public InstructionReceiver(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public void receive(Instruction instruction) {
		persistenceService.persist(instruction);
	}

	public void receive(Collection<Instruction> instructions) {
		persistenceService.persist(instructions);		
	}
}
