package com.jpmc.tre.service;

import java.util.Collection;

import com.jpmc.tre.model.Instruction;

public interface PersistenceService {

	public Collection<Instruction> getInstructions();

	public void persist(Instruction instruction);

	public void persist(Collection<Instruction> instructions);

	public void display();

}
