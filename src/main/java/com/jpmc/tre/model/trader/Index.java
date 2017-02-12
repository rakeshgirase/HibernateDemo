package com.jpmc.tre.model.trader;

public enum Index {
	ENTITY(0), SIDE(1), AGREED_FX(2), CURRENCY(3), INSTRUCTION_DATE(4), SETTLEMENT_DATE(5), UNITS(6), PRICE(7);
	int position;

	Index(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}
}