package com.jpmc.tre.reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.jpmc.tre.model.Entity;
import com.jpmc.tre.service.LedgerService;
import static com.jpmc.tre.reports.Table.Row;

public class ConsoleReporter implements Reporter{

	private LedgerService ledgerService;

	public ConsoleReporter(LedgerService ledgerService) {
		this.ledgerService = ledgerService;
	}

	@Override
	public void incoming() {
		ReportDataHolder incoming = ledgerService.buildIncomingReportData();
		showRevenueByDate(incoming, "Incoming Revenue By Settlement Date: ");		
	}

	@Override
	public void outgoing() {
		ReportDataHolder outgoing = ledgerService.buildOutgoingReportData();
		showRevenueByDate(outgoing, "Outgoing Revenue By Settlement Date: ");		
	}

	private void showRevenueByDate(ReportDataHolder reportDataHolder, String title) {
		Map<LocalDate, Double> totalRevenueByDate = reportDataHolder.getTotalRevenueByDate();
		System.err.println(title);
		Table revenueByDateTable = toRevenueByDateTable(totalRevenueByDate);
		revenueByDateTable.print();
	}
	
	private Table toRevenueByDateTable(Map<LocalDate, Double> totalRevenueByDate) {
		Collection<Object> headerColumns = Arrays.asList("Settlement Date", "Amount(In USD)");
		Collection<Row> rows = new ArrayList<>();
		Row header = new Row(headerColumns);
		rows.add(header);
		for (Entry<LocalDate, Double> entry : totalRevenueByDate.entrySet()) {
			Collection<Object> data = Arrays.asList(entry.getKey(), entry.getValue());
			rows.add(new Row(data));
		}
		return new Table(rows);
	}

	private void showEntitiesByRevenue(ReportDataHolder reportDataHolder, String title) {
		Map<Double, Entity> entitiesByRevenue = reportDataHolder.rankingByAmount();
		Table entitiesByRevenueTable = toEntitiesByRevenueTable(entitiesByRevenue);
		System.err.println(title);
		entitiesByRevenueTable.print();
	}
	
	private Table toEntitiesByRevenueTable(Map<Double, Entity> entitiesByRevenue) {		
		Collection<Object> headerColumns = Arrays.asList("Entity", "Amount(In USD)");
		Collection<Row> rows = new ArrayList<>();
		Row header = new Row(headerColumns);
		rows.add(header);
		for (Entry<Double, Entity> entry : entitiesByRevenue.entrySet()) {
			Collection<Object> data = Arrays.asList(entry.getValue(), entry.getKey());
			rows.add(new Row(data));
		}
		return new Table(rows);
	}

	@Override
	public void ranking() {
		ReportDataHolder incoming = ledgerService.buildIncomingReportData();
		ReportDataHolder outgoing = ledgerService.buildOutgoingReportData();
		this.showEntitiesByRevenue(incoming, "Entities By Incoming Revenue: ");
		this.showEntitiesByRevenue(outgoing, "Entities By Outgoing Revenue: ");
	}

	@Override
	public void display() {
		ledgerService.getAllInstructions().forEach(System.out::println);
	}

}
