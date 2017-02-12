package com.jpmc.tre.exception;

public class ApplicationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private Exception exception;
	

	public ApplicationException(String message, Exception exception) {
		super(message);		
		this.exception = exception;
	}

	public ApplicationException(String format, Object...args) {
		super(String.format(format, args));
	}

	@Override
	public String toString() {
		return "ApplicationException [message=" + getMessage() + ", exception=" + exception + "]";
	}
}
