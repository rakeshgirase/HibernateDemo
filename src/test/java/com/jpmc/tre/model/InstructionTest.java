package com.jpmc.tre.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class InstructionTest {

	private Instruction instruction ;
	
	private double price = 10;
	private int units = 50;
	private double agreedFx = 0.75;
	private Currency currency;
	private LocalDate workingSettlementDate = LocalDate.parse("2017-02-10");
	private LocalDate saturdaySettlementDate = LocalDate.parse("2017-02-11");
	private LocalDate sundaySettlementDate = LocalDate.parse("2017-02-12");
	private LocalDate mondaySettlementDate = LocalDate.parse("2017-02-13");
	@Before
	public void setUp() throws Exception {
		currency = new Currency("USD");		
	}

	private Instruction buildInstructionWithParams(Currency currency, LocalDate settlementDate, double price, int units, double agreedFx) {
		return new Instruction.InstructionBuilder().currency(currency).settlementDate(settlementDate).price(price).units(units).agreedFx(agreedFx).build();
	}

	@Test
	public void testGetTradeAmountInUsd() {
		instruction = buildInstructionWithParams(currency, workingSettlementDate, price, units, agreedFx);
		assertEquals(price*units*agreedFx, instruction.getTradeAmountInUsd(), 0);
	}

	@Test
	public void testFindWorkingSettlementDateForFriday() {
		instruction = buildInstructionWithParams(currency, workingSettlementDate, price, units, agreedFx);
		assertEquals(workingSettlementDate, instruction.findWorkingSettlementDate());
	}
	
	@Test
	public void testFindWorkingSettlementDateForSaturday() {
		instruction = buildInstructionWithParams(currency, saturdaySettlementDate, price, units, agreedFx);
		assertEquals(mondaySettlementDate, instruction.findWorkingSettlementDate());
	}
	
	@Test
	public void testFindWorkingSettlementDateForSunday() {
		instruction = buildInstructionWithParams(currency, sundaySettlementDate, price, units, agreedFx);
		assertEquals(mondaySettlementDate, instruction.findWorkingSettlementDate());
	}

}
