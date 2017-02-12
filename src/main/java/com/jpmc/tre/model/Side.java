package com.jpmc.tre.model;

import com.jpmc.tre.exception.ApplicationException;

public enum Side {
	B("BUY"),S("SELL");
	private String code;
	
	Side(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "SIDE: " + code;
	}
	
	public Side getByCode(String code) throws ApplicationException{
		for (Side side : values()) {
			if(side.code.equalsIgnoreCase(code)){
				return side;
			}
		}
		throw new ApplicationException("Couldnt find side %s", code);
		
	}
}
