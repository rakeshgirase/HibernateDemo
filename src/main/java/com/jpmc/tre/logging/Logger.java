package com.jpmc.tre.logging;

public class Logger {

	private Logger() {

	}

	public static void error(Object message) {
		System.err.println("ERROR: " + message);
	}

	public static void warn(Object message) {
		System.out.println("WARNING: " + message);
	}

	public static void warn(Object message, Exception exception) {
		System.out.println("WARNING: " + message);
		System.out.println("WARNING: Exception: " + exception.getMessage());

	}

	public static void info(String message, Object...args) {
		System.out.println("INFO: " + String.format(message, args));
	}

	public static void debug(Object message) {
		System.out.println("DEBUG: " + message);
	}

}
