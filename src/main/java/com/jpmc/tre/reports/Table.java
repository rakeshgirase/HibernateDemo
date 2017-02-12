package com.jpmc.tre.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Table {
	
	private static final String HORIZONTAL_BAR = "-";
	private static final String VERTICLE_BAR = "|";
	private static final String FILLER = " ";
	private Collection<Row> rows = new ArrayList<>();
	private List<Integer> columnWidths;
	private Integer width;
	
	static class Row{
		private Collection<Object> columns = new HashSet<>();
		
		public Row(Collection<Object> columns) {
			this.columns = columns;
		}
		public Collection<Object> getColumns() {
			return columns;
		}
		@Override
		public String toString() {
			return "Row [columns=" + columns + "]";
		}		
	}
	
	public Table(Collection<Row> rows) {
		this.rows = rows;
		this.columnWidths = getColumnWidths();
		this.width = getWidth();
	}
	
	public Collection<Row> getRows() {
		return rows;
	}
		
	public List<Integer> getColumnWidths(){
		List<Integer> columnMaxWidths = new ArrayList<>(); 
		for (Row row : rows) {
			Collection<Object> columns = row.getColumns();
			int index = 0;
			for (Object object : columns) {				
				int columnWidth = object.toString().length();
				if(!columnMaxWidths.isEmpty() && columnMaxWidths.size()==columns.size()){
					Integer currentMaxWidth = columnMaxWidths.get(index);					
					if(null!=object && currentMaxWidth<columnWidth){
						columnMaxWidths.set(index, columnWidth);						
					}
				}else{
					columnMaxWidths.add(columnWidth);					
				}
				index++;
			}
		}
		return columnMaxWidths;		
	}
	
	private Integer getWidth(){
		return columnWidths.stream().reduce(0, Math::addExact) + getColumnWidths().size() + 1;
	}
	
	public void print() {
		printHorizontalLine();
		Iterator<Row> iterator = rows.iterator();
		Row header = iterator.next();
		printRow(header);
		printHorizontalLine();
		while (iterator.hasNext()) {
			printRow(iterator.next());
		}
		printHorizontalLine();
	}

	private void printHorizontalLine() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < width; i++) {
			builder.append(HORIZONTAL_BAR);
		}
		System.out.println(builder);
	}

	private void printRow(Row row) {
		StringBuilder builder = new StringBuilder();
		builder.append(VERTICLE_BAR);
		int index = 0;
		List<Integer> columnWidths = this.getColumnWidths();
		for (Object column: row.getColumns()) {
			builder.append(column);
			Integer columnWidth = columnWidths.get(index);			
			for (int i = column.toString().length(); i < columnWidth; i++) {				
				builder.append(FILLER);
			}
			builder.append(VERTICLE_BAR);			
			index++;
		}
		System.out.println(builder);
	}
	
	

	
}
