package com.jpmc.tre.reports;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.jpmc.tre.model.Entity;
import com.jpmc.tre.model.Instruction;
import com.jpmc.tre.util.MultivalueMap;

public class ReportDataHolder {

	private Collection<Instruction> instructions;
	private MultivalueMap<Entity,Instruction> instructionsByEntity = new MultivalueMap<>();
	private MultivalueMap<LocalDate,Instruction> instructionsByDate = new MultivalueMap<>();
	
	public ReportDataHolder(Collection<Instruction> instructions) {
		this.instructions = instructions;
		build();
	}
	
	private void build(){
		for (Instruction instruction : instructions) {
			instructionsByEntity.put(instruction.getEntity(), instruction);
			instructionsByDate.put(instruction.findWorkingSettlementDate(), instruction);
		}
	}
	
	public double totalRevenue(){
		double totalRevenue = 0;
		return totalRevenue;		
	}
	
	public Map<LocalDate, Double> getTotalRevenueByDate(){
		Map<LocalDate, Double> revenueByDate = new HashMap<>();
		Set<Entry<LocalDate, Collection<Instruction>>> entries = instructionsByDate.entrySet();
		for (Entry<LocalDate, Collection<Instruction>> entry : entries) {
			Double total = entry.getValue().stream().map(instr->instr.getTradeAmountInUsd()).reduce(0.0, this::add);
			revenueByDate.put(entry.getKey(), total);
		}
		return revenueByDate;
	}
	
	public Map<Double, Entity> rankingByAmount(){
		Map<Double, Entity> entityByRevenue = new TreeMap<>(Collections.reverseOrder());
		Set<Entry<Entity, Collection<Instruction>>> entries = instructionsByEntity.entrySet();
		for (Entry<Entity, Collection<Instruction>> entry : entries) {
			Double total = entry.getValue().stream().map(instr->instr.getTradeAmountInUsd()).reduce(0.0, this::add);
			entityByRevenue.put(total, entry.getKey());
		}		
		return entityByRevenue;
	}
	
	public double add(double d1, double d2){
		return d1+d2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instructions == null) ? 0 : instructions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportDataHolder other = (ReportDataHolder) obj;
		if (instructions == null) {
			if (other.instructions != null)
				return false;
		} else if (!instructions.equals(other.instructions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportDataHolder [instructions=" + instructions + "]";
	}	
}