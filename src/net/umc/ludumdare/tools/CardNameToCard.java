package net.umc.ludumdare.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CardNameToCard {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("resources/data/cardlist.txt"));
		String string = "_names.put(%s, \"%s\");\n"  +
				"_ids.put(\"%s\", %s);";
		int count = 1;
		while (scan.hasNext()) {
			String line = scan.nextLine().trim();
			System.out.println(String.format(string, count, line, line, count));
			System.out.println();
			count++;
		}
	}

}
