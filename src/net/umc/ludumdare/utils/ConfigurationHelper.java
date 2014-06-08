package net.umc.ludumdare.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class ConfigurationHelper {

	public static Hashtable<String, String> loadConfiguration(String filepath) {
		Hashtable<String, String> config = new Hashtable<String, String>();
		Scanner scan;
		try {
			scan = new Scanner(new File(filepath));
		} catch (FileNotFoundException e) {
			String stackMessage = "\n";
			for (StackTraceElement element : e.getStackTrace()) {
				stackMessage += "\t" + element.toString() + "\n";
			}
			Logger.logMessage("Failed to Load Configuration", stackMessage, e.getMessage(), 
					String.format("\n\tPath: %s", filepath));
			return config;
		}
		while(scan.hasNext()) {
			String line = scan.nextLine();
			if (line.charAt(0) == '[') {
				String key = line.split("\\[")[1].split("\\]")[0];
				if (!scan.hasNext()) {
					return config;
				}
				String value = scan.nextLine();
				config.put(key, value);
			}
		}
		return config;
	}
	
}
