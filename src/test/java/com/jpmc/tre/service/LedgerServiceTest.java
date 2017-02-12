package com.jpmc.tre.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.jpmc.tre.model.Currency;
import com.jpmc.tre.model.Entity;
import com.jpmc.tre.model.Instruction;
import com.jpmc.tre.model.Instruction.InstructionBuilder;
import com.jpmc.tre.model.Side;
import com.jpmc.tre.reports.ReportDataHolder;

@RunWith(MockitoJUnitRunner.class)
public class LedgerServiceTest {

	@InjectMocks
	private LedgerService ledgerService;
	@Mock
	private PersistenceService persistenceService;
	private Instruction buyInstruction1;
	private Instruction buyInstruction2;
	private Instruction sellInstruction1;
	private Instruction sellInstruction2;
	@Mock
	private Currency currency;

	@Before
	public void setup() {
		buyInstruction1 = new InstructionBuilder().entity(new Entity("TEST1")).side(Side.B).currency(currency)
				.settlementDate(LocalDate.parse("2017-02-13")).build();
		buyInstruction2 = new InstructionBuilder().entity(new Entity("TEST2")).side(Side.B).currency(currency)
				.settlementDate(LocalDate.parse("2017-02-13")).build();
		sellInstruction1 = new InstructionBuilder().entity(new Entity("TEST3")).side(Side.S).currency(currency)
				.settlementDate(LocalDate.parse("2017-02-13")).build();
		sellInstruction2 = new InstructionBuilder().entity(new Entity("TEST4")).side(Side.S).currency(currency)
				.settlementDate(LocalDate.parse("2017-02-13")).build();
		Collection<Instruction> instructions = Arrays.asList(buyInstruction1, buyInstruction2, sellInstruction1,
				sellInstruction2);
		when(persistenceService.getInstructions()).thenReturn(instructions);
		when(currency.isHoliday(any(LocalDate.class))).thenReturn(false);
	}

	@Test
	public void testFindBuyInstructions() {
		Collection<Instruction> buyInstructions = ledgerService.findBuyInstructions();
		assertTrue(Arrays.asList(buyInstruction1, buyInstruction2).containsAll(buyInstructions));
		assertTrue(buyInstructions.containsAll(Arrays.asList(buyInstruction1, buyInstruction2)));
	}

	@Test
	public void testFindSellInstructions() {
		Collection<Instruction> sellInstructions = ledgerService.findSellInstructions();
		assertTrue(Arrays.asList(sellInstruction1, sellInstruction2).containsAll(sellInstructions));
		assertTrue(sellInstructions.containsAll(Arrays.asList(sellInstruction1, sellInstruction2)));
	}

	@Test
	public void testBuildIncomingReportData() {
		ReportDataHolder reportData = ledgerService.buildIncomingReportData();
		assertTrue(Objects.equals(new ReportDataHolder(new HashSet<>(Arrays.asList(buyInstruction1, buyInstruction2))),
				reportData));
	}

	@Test
	public void testBuildOutgoingReportData() {
		ReportDataHolder reportData = ledgerService.buildOutgoingReportData();
		assertTrue(Objects.equals(
				new ReportDataHolder(new HashSet<>(Arrays.asList(sellInstruction1, sellInstruction2))), reportData));
	}

}
