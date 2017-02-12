package com.jpmc.tre.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.jpmc.tre.model.Instruction;

public class CachePersistenceService implements PersistenceService {
	private Collection<Instruction> instructions = new HashSet<>();

	public void persist(Instruction instruction) {
		instructions.add(instruction);
	}

	public void persist(Collection<Instruction> instructions) {
		this.instructions.addAll(instructions);
	}

	@Override
	public Collection<Instruction> getInstructions() {
		return Collections.unmodifiableCollection(instructions);
	}

	@Override
	public void display() {
		instructions.forEach(System.out::println);
	}
}
