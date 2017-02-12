package com.jpmc.tre.service;

import java.util.Collection;

import com.jpmc.tre.model.Instruction;

public interface Facade {

	void display();

	void send(Instruction instruction);
	
	void send(Collection<Instruction> instructions);
	
	void report();

}
