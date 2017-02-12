package com.jpmc.tre.model;

import java.time.LocalDate;

public class Instruction {

	private Entity entity;
	private Side side;
	private double agreedFx;
	private Currency currency;
	private LocalDate instructionDate;
	private LocalDate settlementDate;
	private int units;
	private double price;

	private Instruction(InstructionBuilder builder) {
		this.entity = builder.entity;
		this.side = builder.side;
		this.agreedFx = builder.agreedFx;
		this.currency = builder.currency;
		this.instructionDate = builder.instructionDate;
		this.settlementDate = builder.settlementDate;
		this.units = builder.units;
		this.price = builder.price;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public double getAgreedFx() {
		return agreedFx;
	}

	public void setAgreedFx(double agreedFx) {
		this.agreedFx = agreedFx;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public void setInstructionDate(LocalDate instructionDate) {
		this.instructionDate = instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTradeAmountInUsd() {
		return price * units * agreedFx;
	}
	
	public LocalDate findWorkingSettlementDate(){
		LocalDate workingSettlementDate = this.settlementDate;
		while (currency.isHoliday(workingSettlementDate)) {
			workingSettlementDate = workingSettlementDate.plusDays(1L);			
		}
		return workingSettlementDate;		
	}

	@Override
	public String toString() {
		return "Instruction [entity=" + entity + ", side=" + side + ", agreedFx=" + agreedFx + ", currency=" + currency
				+ ", instructionDate=" + instructionDate + ", settlementDate=" + settlementDate + ", units=" + units
				+ ", price=" + price + "]";
	}

	public static class InstructionBuilder {
		private Entity entity;
		private Side side;
		private double agreedFx;
		private Currency currency;
		private LocalDate instructionDate;
		private LocalDate settlementDate;
		private int units;
		private double price;

		public InstructionBuilder() {

		}

		public InstructionBuilder entity(Entity entity) {
			this.entity = entity;
			return this;
		}

		public InstructionBuilder side(Side side) {
			this.side = side;
			return this;
		}

		public InstructionBuilder agreedFx(double agreedFx) {
			this.agreedFx = agreedFx;
			return this;
		}

		public InstructionBuilder currency(Currency currency) {
			this.currency = currency;
			return this;
		}

		public InstructionBuilder instructionDate(LocalDate localDate) {
			this.instructionDate = localDate;
			return this;
		}

		public InstructionBuilder settlementDate(LocalDate settlementDate) {
			this.settlementDate = settlementDate;
			return this;
		}

		public InstructionBuilder units(int units) {
			this.units = units;
			return this;
		}

		public InstructionBuilder price(double price) {
			this.price = price;
			return this;
		}

		public Instruction build() {
			return new Instruction(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(agreedFx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((instructionDate == null) ? 0 : instructionDate.hashCode());
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((settlementDate == null) ? 0 : settlementDate.hashCode());
		result = prime * result + ((side == null) ? 0 : side.hashCode());
		result = prime * result + units;
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
		Instruction other = (Instruction) obj;
		if (Double.doubleToLongBits(agreedFx) != Double.doubleToLongBits(other.agreedFx))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (instructionDate == null) {
			if (other.instructionDate != null)
				return false;
		} else if (!instructionDate.equals(other.instructionDate))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (settlementDate == null) {
			if (other.settlementDate != null)
				return false;
		} else if (!settlementDate.equals(other.settlementDate))
			return false;
		if (side != other.side)
			return false;
		if (units != other.units)
			return false;
		return true;
	}
	
	
}
