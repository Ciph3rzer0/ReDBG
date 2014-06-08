package net.umc.ludumdare.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {

	private static final String LOG_PATH = "resources/data/log.txt";
	private static final String EMPTY = "";
	private static FileWriter _fileWriter;
	
	public static void logMessage(String name, String stackTrace, String message, String detail) {	
		
		name = isNullOrEmpty(name) ? "Undefined" : name;
		stackTrace = isNullOrEmpty(stackTrace) ? EMPTY : stackTrace;
		message = isNullOrEmpty(message) ? EMPTY : message;
		detail = isNullOrEmpty(detail) ? EMPTY : detail;
		try {
			_fileWriter = new FileWriter(LOG_PATH, true);
			_fileWriter.write("Name: " + name + "\n");
			_fileWriter.write("Date: " + dateTimeNow() + "\n");
			_fileWriter.write("Message: " + message + "\n");
			_fileWriter.write("Detail: " + detail + "\n");
			_fileWriter.write("Stack Trace: " + stackTrace + "\n");
			_fileWriter.write("-----------------------------------------------------\n\n");
			_fileWriter.close();
			_fileWriter = null;
		} 		
		catch(IOException ioe) {
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	
	private static boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
	
	@SuppressWarnings("deprecation")
	private static String dateTimeNow() {
		Date date = new Date();
		return date.toLocaleString();
	}
}
