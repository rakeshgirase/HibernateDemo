package com.jpmc.tre.exception;

public class FatalException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public FatalException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

}
