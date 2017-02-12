package com.jpmc.tre.service;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jpmc.tre.model.Instruction;
import com.jpmc.tre.model.Side;
import com.jpmc.tre.reports.ReportDataHolder;

public class LedgerService {

	private PersistenceService persistenceService;
	
	Function<Side, Predicate<Instruction>> sideFilter = side-> instruction->instruction.getSide().equals(side);

	public LedgerService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public Collection<Instruction> getAllInstructions() {
		return persistenceService.getInstructions();		
	}
	
	public Collection<Instruction> findBuyInstructions(){
		return findInstructions(sideFilter.apply(Side.B));
	}
	
	public Collection<Instruction> findSellInstructions(){
		return findInstructions(sideFilter.apply(Side.S));		
	}
	
	private Collection<Instruction> findInstructions(Predicate<Instruction> filter){
		return getAllInstructions().stream().filter(filter).collect(Collectors.toSet());		
	}
	
	public ReportDataHolder buildIncomingReportData(){
		ReportDataHolder reportData = new ReportDataHolder(findBuyInstructions());
		return reportData;
	}

	public ReportDataHolder buildOutgoingReportData() {
		ReportDataHolder reportData = new ReportDataHolder(findSellInstructions());
		return reportData;
	}
	
}
