package net.umc.ludumdare.utils;

public class CharacterHelper {

	public static int getCharacterHealth(String card) {
		
		if (card.equals("AdaWong")) {
			return 70;
		}
		else if (card.equals("AlbertWesker")) {
			return 100;
		}
		else if (card.equals("BarryBurton")) {
			return 90;
		}
		else if (card.equals("ChrisRedfield")) {
			return 120;
		}
		else if (card.equals("ClaireRedfield")) {
			return 70;
		}
		else if (card.equals("JackKrauser")) {
			return 80;
		}
		else if (card.equals("JillValentine")) {
			return 80;
		}
		else if (card.equals("LeonSKennedy")) {
			return 80;
		}
		else if (card.equals("RebeccaChambers")) {
			return 70;
		}
		else if (card.equals("ShevaAlomar")) {
			return 80;
		}
		return 0;
	}
	
	public static int getCharacterAbilityOne(String card) {
		
		if (card.equals("AdaWong")) {
			return 4;
		}
		else if (card.equals("AlbertWesker")) {
			return 2;
		}
		else if (card.equals("BarryBurton")) {
			return 2;
		}
		else if (card.equals("ChrisRedfield")) {
			return 0;
		}
		else if (card.equals("ClaireRedfield")) {
			return 2;
		}
		else if (card.equals("JackKrauser")) {
			return 1;
		}
		else if (card.equals("JillValentine")) {
			return 5;
		}
		else if (card.equals("LeonSKennedy")) {
			return 2;
		}
		else if (card.equals("RebeccaChambers")) {
			return 2;
		}
		else if (card.equals("ShevaAlomar")) {
			return 2;
		}
		return 0;
	}
	
	public static int getCharacterAbilityTwo(String card) {
		
		if (card.equals("AdaWong")) {
			return 8;
		}
		else if (card.equals("AlbertWesker")) {
			return 9;
		}
		else if (card.equals("BarryBurton")) {
			return 7;
		}
		else if (card.equals("ChrisRedfield")) {
			return 4;
		}
		else if (card.equals("ClaireRedfield")) {
			return 6;
		}
		else if (card.equals("JackKrauser")) {
			return 7;
		}
		else if (card.equals("JillValentine")) {
			return 8;
		}
		else if (card.equals("LeonSKennedy")) {
			return 6;
		}
		else if (card.equals("RebeccaChambers")) {
			return 4;
		}
		else if (card.equals("ShevaAlomar")) {
			return 4;
		}
		return 0;
	}
}
