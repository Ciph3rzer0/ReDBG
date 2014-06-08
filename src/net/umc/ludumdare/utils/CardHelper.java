package net.umc.ludumdare.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import net.umc.ludumdare.network.Card;
import net.umc.ludumdare.network.GameState;
import net.umc.ludumdare.network.Player;

import org.newdawn.slick.Image;

public class CardHelper {

	private static final Random _random = new Random();
	private static final Hashtable<Integer, String> _names = new Hashtable<Integer, String>();
	private static final Hashtable<String, Integer> _ids = new Hashtable<String, Integer>();
	private static final Hashtable<String, Image> _cardImages = new Hashtable<String, Image>();
	private static final ArrayList<String> _characterCardsUsed = new ArrayList<String>();
	private static final int[] _characterCards = new int[] {1, 2, 9, 14, 15, 32, 33, 34, 44, 49};
	private static final int[] _weaponCards = new int[] {6, 7, 10, 12, 16, 24, 25, 28, 29, 36, 43, 46, 47, 50, 52, 53};
	private static final int[] _actionCards = new int[] {8, 18, 21, 31, 38, 39, 42, 45, 48, 51, 54, 56};
	private static final String EMPTY = "";
	private static final String IMAGE_PATH = "resources/images/cards/%s.png";
	
	public static void init() {
		
		_names.put(0, "undefined");
		_ids.put("undefined", 0);
		
		_names.put(1, "AdaWong");
		_ids.put("AdaWong", 1);

		_names.put(2, "AlbertWesker");
		_ids.put("AlbertWesker", 2);

		_names.put(3, "AMMO10");
		_ids.put("AMMO10", 3);

		_names.put(4, "AMMO20");
		_ids.put("AMMO20", 4);

		_names.put(5, "AMMO30");
		_ids.put("AMMO30", 5);

		_names.put(6, "AssaultMachineGun");
		_ids.put("AssaultMachineGun", 6);

		_names.put(7, "AutomaticShotgun");
		_ids.put("AutomaticShotgun", 7);

		_names.put(8, "BackToBack");
		_ids.put("BackToBack", 8);

		_names.put(9, "BarryBurton");
		_ids.put("BarryBurton", 9);

		_names.put(10, "Bolt-ActionRifle");
		_ids.put("Bolt-ActionRifle", 10);

		_names.put(11, "BuiKichwa");
		_ids.put("BuiKichwa", 11);

		_names.put(12, "Burst-FireHandgun");
		_ids.put("Burst-FireHandgun", 12);

		_names.put(13, "Cerberus");
		_ids.put("Cerberus", 13);

		_names.put(14, "ChrisRedfield");
		_ids.put("ChrisRedfield", 14);

		_names.put(15, "ClaireRedfield");
		_ids.put("ClaireRedfield", 15);

		_names.put(16, "CombatKnife");
		_ids.put("CombatKnife", 16);

		_names.put(17, "ComboBonus");
		_ids.put("ComboBonus", 17);

		_names.put(18, "DeadlyAim");
		_ids.put("DeadlyAim", 18);

		_names.put(19, "DrSalvador");
		_ids.put("DrSalvador", 19);

		_names.put(20, "ElGigante");
		_ids.put("ElGigante", 20);

		_names.put(21, "EscapeFromTheDeadCity");
		_ids.put("EscapeFromTheDeadCity", 21);

		_names.put(22, "Executioner");
		_ids.put("Executioner", 22);

		_names.put(23, "FirstAidSpray");
		_ids.put("FirstAidSpray", 23);

		_names.put(24, "Full-BoreMachineGun");
		_ids.put("Full-BoreMachineGun", 24);

		_names.put(25, "GatlingGun");
		_ids.put("GatlingGun", 25);

		_names.put(26, "GatlingGunManjini");
		_ids.put("GatlingGunManjini", 26);

		_names.put(27, "GreenHerb");
		_ids.put("GreenHerb", 27);

		_names.put(28, "Grenade");
		_ids.put("Grenade", 28);

		_names.put(29, "Handgun");
		_ids.put("Handgun", 29);

		_names.put(30, "Hunter");
		_ids.put("Hunter", 30);

		_names.put(31, "ItemManagement");
		_ids.put("ItemManagement", 31);

		_names.put(32, "JackKrauser");
		_ids.put("JackKrauser", 32);

		_names.put(33, "JillValentine");
		_ids.put("JillValentine", 33);

		_names.put(34, "LeonSKennedy");
		_ids.put("LeonSKennedy", 34);

		_names.put(35, "Licker");
		_ids.put("Licker", 35);

		_names.put(36, "Longbow");
		_ids.put("Longbow", 36);

		_names.put(37, "Majini");
		_ids.put("Majini", 37);

		_names.put(38, "MansionFoyer");
		_ids.put("MansionFoyer", 38);

		_names.put(39, "MasterOfUnlocking");
		_ids.put("MasterOfUnlocking", 39);

		_names.put(40, "MimicryMarcus");
		_ids.put("MimicryMarcus", 40);

		_names.put(41, "Nemesis");
		_ids.put("Nemesis", 41);

		_names.put(42, "OminousBattle");
		_ids.put("OminousBattle", 42);

		_names.put(43, "PumpActionShotgun");
		_ids.put("PumpActionShotgun", 43);

		_names.put(44, "RebeccaChambers");
		_ids.put("RebeccaChambers", 44);

		_names.put(45, "Reload");
		_ids.put("Reload", 45);

		_names.put(46, "RocketLauncher");
		_ids.put("RocketLauncher", 46);

		_names.put(47, "Semi-AutomaticRifle");
		_ids.put("Semi-AutomaticRifle", 47);

		_names.put(48, "ShatteredMemories");
		_ids.put("ShatteredMemories", 48);

		_names.put(49, "ShevaAlomar");
		_ids.put("ShevaAlomar", 49);

		_names.put(50, "SixShooter");
		_ids.put("SixShooter", 50);

		_names.put(51, "StruggleForSurvival");
		_ids.put("StruggleForSurvival", 51);

		_names.put(52, "Submission");
		_ids.put("Submission", 52);

		_names.put(53, "SurvivalKnife");
		_ids.put("SurvivalKnife", 53);

		_names.put(54, "TheMerchant");
		_ids.put("TheMerchant", 54);

		_names.put(55, "TimeBonusPlus1");
		_ids.put("TimeBonusPlus1", 55);

		_names.put(56, "UmbrellaCorporation");
		_ids.put("UmbrellaCorporation", 56);

		_names.put(57, "UroborosAheri");
		_ids.put("UroborosAheri", 57);

		_names.put(58, "YellowHerb");
		_ids.put("YellowHerb", 58);

		_names.put(59, "ZombieButcher");
		_ids.put("ZombieButcher", 59);

		_names.put(60, "ZombieFemale");
		_ids.put("ZombieFemale", 60);

		_names.put(61, "ZombieMale");
		_ids.put("ZombieMale", 61);

	}
		
	public static String cardName(int id) {
		if (_names.containsKey(id)) {
			return _names.get(id);
		}
		System.out.println("Card not found.");
		return EMPTY;
	}
	
	public static int cardID(String cardName) {
		if (_ids.containsKey(cardName)) {
			return _ids.get(cardName);
		}
		return 0;
	}
	
	public static Image cardImage(Card card) {
		return cardImage(card.title);
	}
	
	public static Image cardImage(int id) {
		return cardImage(cardName(id));
	}
	
	public static Image cardImage(String cardName) {
		if (_cardImages.containsKey(cardName)) {
			return _cardImages.get(cardName);
		}
		
		Image image = null;
		try {
			image = new Image(String.format(IMAGE_PATH, cardName), false, Image.FILTER_NEAREST);
			if (image != null) {
				_cardImages.put(cardName, image);
			}
		} catch (Exception e) {
			int id = cardID(cardName);
			String stackMessage = "\n";
			for (StackTraceElement element : e.getStackTrace()) {
				stackMessage += "\t" + element.toString() + "\n";
			}
			Logger.logMessage("Image Load Failed", stackMessage, e.getMessage(), 
					String.format("\n\tCard ID: %s\n\tCard Name: %s\n\tImagePath: %s\n",
							id, cardName, String.format(IMAGE_PATH, cardName)));
		}
		return image;
	}
	
	public static Card createCard(int id, int pile) {
		Card card = new Card();
		card.cardImage = id;
		card.pileID = pile;
		card.title = cardName(id);
		return card;
	}
	
	public static Card createCard(String name, int pile) {
		Card card = new Card();
		card.cardImage = cardID(name);
		card.pileID = pile;
		card.title = name;
		return card;
	}
	
	public static Card createCard(Card card, int pile) {
		Card copy = new Card();
		copy.cardImage = card.cardImage;
		copy.pileID = pile;
		copy.title = card.title;
		return copy;
	}
	
	public static ArrayList<Card> createMansion() {
		ArrayList<Card> mansion = new ArrayList<Card>();
		mansion.add(CardHelper.createCard(11, 0));
		mansion.add(CardHelper.createCard(11, 0));
		mansion.add(CardHelper.createCard(11, 0));

		mansion.add(CardHelper.createCard(13, 0));		
		mansion.add(CardHelper.createCard(13, 0));	
		mansion.add(CardHelper.createCard(13, 0));	
		
		mansion.add(CardHelper.createCard(19, 0));	
		mansion.add(CardHelper.createCard(19, 0));	
		mansion.add(CardHelper.createCard(19, 0));	
		
		mansion.add(CardHelper.createCard(20, 0));	
		
		mansion.add(CardHelper.createCard(22, 0));
		
		mansion.add(CardHelper.createCard(25, 0));
		
		mansion.add(CardHelper.createCard(26, 0));	
		mansion.add(CardHelper.createCard(26, 0));	
		mansion.add(CardHelper.createCard(26, 0));	
		
		mansion.add(CardHelper.createCard(30, 0));	
		mansion.add(CardHelper.createCard(30, 0));	
		
		mansion.add(CardHelper.createCard(35, 0));	
		mansion.add(CardHelper.createCard(35, 0));	
		mansion.add(CardHelper.createCard(35, 0));	
		
		mansion.add(CardHelper.createCard(37, 0));	
		mansion.add(CardHelper.createCard(37, 0));	
		mansion.add(CardHelper.createCard(37, 0));	
		
		mansion.add(CardHelper.createCard(40, 0));	
		mansion.add(CardHelper.createCard(40, 0));	
		
		mansion.add(CardHelper.createCard(41, 0));	
		
		mansion.add(CardHelper.createCard(46, 0));	
		
		mansion.add(CardHelper.createCard(57, 0));	
		
		mansion.add(CardHelper.createCard(58, 0));	
		mansion.add(CardHelper.createCard(58, 0));	
		mansion.add(CardHelper.createCard(58, 0));	
		
		mansion.add(CardHelper.createCard(59, 0));	
		mansion.add(CardHelper.createCard(59, 0));	
		mansion.add(CardHelper.createCard(59, 0));	
		
		mansion.add(CardHelper.createCard(60, 0));	
		mansion.add(CardHelper.createCard(60, 0));	
		
		mansion.add(CardHelper.createCard(61, 0));	
		mansion.add(CardHelper.createCard(61, 0));	
		
		return shuffleDeck(mansion);
	}
	
	public static Card createCard(Card card) {
		return createCard(card, card.pileID);
	}
	
	public static Card randomCharacter() {
		Random random = new Random();
		int card = _characterCards[random.nextInt(_characterCards.length)];
		while (true) {
			if (!_characterCardsUsed.contains(cardName(card))) {
				break;
			}
			card = _characterCards[random.nextInt(_characterCards.length)];
		}
		_characterCardsUsed.add(cardName(card));
		return CardHelper.createCard(card, 0);
	}
	
	public static ArrayList<Card> shuffleDeck(ArrayList<Card> pile) {
		ArrayList<Card> newDeck = new ArrayList<Card>();
		while (pile.size() > 0) {
			newDeck.add(pile.remove(_random.nextInt(pile.size())));
		}
		return newDeck;
	}
	
	public static void discardHand(Player player) {
		for (int i = player.hand.size() - 1; i >= 0; i--) {
			player.discard.add(player.hand.remove(i));
		}
	}
	
	public static void drawNewHand(Player player) {
		discardHand(player);
		drawHand(player);
	}
	
	public static void drawHand(Player player) {
		for (int i = 0; i < 5; i++) drawCard(player);
	}
	
	public static void drawCard(Player player) {
		if (player.inventory.size() > 0) {
			player.hand.add(player.inventory.remove(0));
			player.hand.get(player.hand.size() - 1).played = false;
		}
		else {
			for (int i = player.discard.size() - 1; i >= 0; i--) {
				player.inventory.add(player.discard.remove(i));
			}
			
			player.inventory = shuffleDeck(player.inventory);
			
			if (player.inventory.size() > 0) {
				player.hand.add(player.inventory.remove(0));
				player.hand.get(player.hand.size() - 1).played = false;
			}
		}
	}
	
	public static boolean isActionCard(int card) {
		for (int i: _actionCards) {
			if (i == card) return true;
		}
		return false;
	}
	
	public static void discardRandomFromDiscard(GameState state, int player) {
		if (state.players[player].discard.size() > 0) {
			Random random = new Random();
			int rnd = random.nextInt(state.players[player].discard.size());
			int pileID = state.players[player].discard.get(rnd).pileID;
			if (pileID > -1) {
				state.shopPiles[pileID].cards.add(state.players[player].discard.remove(rnd));
			}
			else {
				state.players[player].discard.remove(rnd);
			}
		}
	}
	
	public static void setInitialInventory(Player player) {
		for (int i = 0; i < 7; i++) {
			player.inventory.add(createCard(3, 0));
		}
		player.inventory.add(createCard(16, 5));
		player.inventory.add(createCard(16, 5));
		player.inventory.add(createCard(29, 4));
		player.inventory = shuffleDeck(player.inventory);
	}
	
	public static void endTurn(GameState state) {
		int num = state.turn - 1;
		resetPlayer(state.players[num]);
		drawNewHand(state.players[num]);
		if (state.players[num].skipActions > 0) state.players[num].skipActions--;
		state.turn = state.turn + 1 > state.numberOfPlayers ? 1 : state.turn + 1;
		startTurn(state.players[state.turn - 1]);
		state.latestGameMessage = state.players[state.turn - 1].name + " ended turn.";
		if (state.players[state.turn - 1].skipTurn > 0) {
			state.players[state.turn - 1].skipTurn--;
			endTurn(state);
		}
	}
	
	public static void trashCardFromHand(GameState state, int player, int cardPos) {
		Card card = state.players[player].hand.remove(cardPos);
		if (card.pileID > -1) {
			state.shopPiles[card.pileID].cards.add(card);
		}
	}
	
	public static boolean didPlayerDie(GameState state, int player) {
		if (state.players[player].currentHealth < 0) {
			state.players[player].maxHealth -= 20;
			if (state.players[player].maxHealth <= 0) {
				state.players[player].dead = true;
			}
			else {
				state.players[player].currentHealth = state.players[player].maxHealth;
			}
			return true;
		}
		return false;
	}
	
	public static void startTurn(Player player) {
		player.actions = 1;
		player.buys = 1;
		player.explores = 1;
	}
	
	public static void resetPlayer(Player player) {
		player.actions = 0;
		player.ammo = 0;
		player.explores = 0;
		player.buys = 0;
		player.damage = 0;
		player.gold = 0;
		player.weaponsPlayed = 0;
		player.actionsPlayed = 0;
		player.bonusDamage = 0;
		player.deadlyAimPlayed = 0;
	}
	
	public static void resetWeaponCards(GameState state, int playerNumber) {
		for (int i = 0; i < state.players[playerNumber].hand.size(); i++) {
			int cardId = state.players[playerNumber].hand.get(0).cardImage;
			for (int j = 0; j < _weaponCards.length; j++) {
				if (cardId == _weaponCards[j]) {
					state.players[playerNumber].hand.get(0).played = false;
					break;
				}
			}
		}
	}
	
	public static void calcBonusDamage(GameState state, int player) {
		int bonusDamage = 0;
		bonusDamage += (state.players[player].deadlyAimPlayed * 10) * state.players[player].weaponsPlayed;
		state.players[player].bonusDamage = bonusDamage;
	}
	
	public static void playCard(GameState state, int player, int cardPos) {
		if (player > state.numberOfPlayers || state.players[player].hand.get(cardPos).played) return;
		if (state.players[player].skipActions > 0 && isActionCard(state.players[player].hand.get(cardPos).cardImage)) {
			state.latestGameMessage = state.players[player].name + " cannot play cards this turn.";
		}
		switch (state.players[player].hand.get(cardPos).cardImage) {
			case 3: // AMMO10
				state.players[player].ammo += 10;
				state.players[player].gold += 10;
				break;
			case 4: // AMMO20
				state.players[player].ammo += 20;
				state.players[player].gold += 20;
				break;
			case 5: // AMMO30
				state.players[player].ammo += 30;
				state.players[player].gold += 30;
				break;
			case 6: // ASSAULTMACHINEGUN
				if (state.players[player].ammo < 40) {
					state.latestGameMessage = "Not enough ammo";
					return;
				}
				state.players[player].ammo -= 40;
				state.players[player].ammoUsed += 40;
				state.players[player].damage += 20;
				state.players[player].weaponsPlayed++;
				break;
			case 7: // Automatic Shotgun
				if (state.players[player].ammo < 80) {
					state.latestGameMessage = "Not enough ammo";
					return;
				}
				state.players[player].ammo -= 80;
				state.players[player].ammoUsed += 80;
				state.players[player].damage += 50;
				state.players[player].weaponsPlayed++;
				break;
			case 10: // Bolt Action Rifle
				if (state.players[player].ammo < 50) {
					state.latestGameMessage = "Not enough ammo";
					return;
				}
				state.players[player].ammo -= 50;
				state.players[player].ammoUsed += 50;
				state.players[player].damage += 20;
				state.players[player].weaponsPlayed++;
				break;
			case 12: // burstfire handgun
				if (state.players[player].ammo < 30) {
					state.latestGameMessage = "Not enough ammo";
					return;
				}
				state.players[player].ammo -= 30;
				state.players[player].ammoUsed += 30;
				state.players[player].damage += 20;
				state.players[player].weaponsPlayed++;
				if (state.players[player].weaponsPlayed > 1) {
					state.players[player].damage += 20;
				}
				break;
			case 16: // Combat Knife
				state.players[player].damage += 5;
				state.players[player].weaponsPlayed++;
				break;
			case 18:
				state.players[player].deadlyAimPlayed++;
				state.players[player].ammo += 20;
				break;
			case 23: // First Aid Spray
				state.players[player].currentHealth = state.players[player].maxHealth;
				trashCardFromHand(state, player, cardPos);
				break;
			case 25:
				state.players[player].ammoUsed += state.players[player].ammo;
				state.players[player].damage += state.players[player].ammo;
				state.players[player].ammo -= state.players[player].ammo;
				state.players[player].weaponsPlayed++;
				break;
			case 27: // greenherb
				state.players[player].currentHealth += 20;
				if (state.players[player].currentHealth > state.players[player].maxHealth) {
					state.players[player].currentHealth = state.players[player].maxHealth;
				}
				trashCardFromHand(state, player, cardPos);
				break;
			case 29: // Handgun
				if (state.players[player].ammo < 20) return;
				state.players[player].ammo -= 20;
				state.players[player].ammoUsed += 20;
				state.players[player].damage += 10;
				state.players[player].weaponsPlayed++;
				break;
			case 36: // Longbow
				state.players[player].damage += 25;
				state.players[player].weaponsPlayed ++;
				break;
			case 38: // Mansion Foyer
				if (state.players[player].actions < 1) {
					state.latestGameMessage = "Not enough actions";
				}
				CardHelper.drawCard(state.players[player]);
				CardHelper.drawCard(state.players[player]);
				state.players[player].actions--;
				break;
			case 43: // Pump shotgun
				if (state.players[player].ammo < 40) {
					state.latestGameMessage = "Not enough ammo";
					return;
				}
				state.players[player].ammo -= 40;
				state.players[player].ammoUsed += 40;
				state.players[player].damage += 25;
				state.players[player].weaponsPlayed++;
				state.players[player].explores++;
				break;
			case 46:
				state.players[player].damage += 90;
				state.players[player].weaponsPlayed++;
				trashCardFromHand(state, player, cardPos);
				break;
			case 47: // semi auto Rifle
				if (state.players[player].ammo < 70) {
					state.latestGameMessage = "Not enough ammo";
					return;
				}
				state.players[player].ammo -= 70;
				state.players[player].ammoUsed += 70;
				state.players[player].damage += 30;
				state.players[player].damage += (10 * state.players[player].actionsPlayed);
				state.players[player].weaponsPlayed++;
				break;
			case 50: // six shooter
				if (state.players[player].ammo < 50) return;
				state.players[player].ammo -= 50;
				state.players[player].ammoUsed += 50;
				state.players[player].damage += 50;
				state.players[player].weaponsPlayed++;
				break;
			case 54: // The Merchant
				if (state.players[player].actions < 1) {
					state.latestGameMessage = "Not enough actions";
				}
				state.players[player].gold += 20;
				CardHelper.drawCard(state.players[player]);
				state.players[player].buys++;
				state.players[player].actions--;
				break;
			default:
				return;
		}
		if (isActionCard(state.players[player].hand.get(cardPos).cardImage)) {
			state.players[player].actionsPlayed++;
		}
		state.players[player].hand.get(cardPos).played = true;
		state.latestGameMessage = state.players[player].name + " played " + 
				CardHelper.cardName(state.players[player].hand.get(cardPos).cardImage);
		calcBonusDamage(state, player);
	}
	
	public static int cardCost(int cardID) {
		switch (cardID) {
			case 3: // AMMO10
			case 16: // Combat Knife
			case 58: // yellow herb
				return 0;
			case 27: // Greenherb
			case 29: // Handgun
			case 48: // shattered memories
			case 52: // submission
				return 20;
			case 4: // AMMO20
			case 6: // Assault Machine Gun
			case 8: // back to back
			case 31: // Item management
			case 38: // Mansion Foyer
			case 39: // Master of unlocking
			case 51: // struggle for survival
				return 30;
			case 28: // Grenade
				return 40;
			case 10: // BOLT ACTION RIFLE
			case 18: // deadly aim
			case 45: // reload
			case 53: // survival knife
			case 54: // The Merchant
			case 56: // umbrell acorp
				return 50;
			case 5: // AMMO30
			case 12: // burst fire handgun
			case 23: // first aid spray
			case 42: // ominous battle
				return 60;
			case 21: // Escape from the dead city
				return 70;
			case 7: // Automatic shotgun
				return 80;
			case 47: // semi automatic rifle
			case 50: // Six shooter
				return 90;
			case 24:  // Full bore machine gun
				return 100;
			case 25: // Gatling gun
			case 36: // Longbow
				return 110;
			case 46: // Rocket launcher
				return 130;
		}
		return 0;
	}
	
	public static void explore(GameState state, int playerNumber) {
		Card card = state.mansion.get(0);
		boolean defeated = false, urboros = false;
		switch (card.cardImage) {
			case 11:	// Bui kichwa
				if (state.players[playerNumber].damage >= 10) {
					defeated = true;
					state.players[playerNumber].decorations++;
				}
				else {
					state.players[playerNumber].currentHealth -= 10;
				}
				break;
			case 13:	// Cerberus
				if (state.players[playerNumber].damage >= 25) {
					defeated = true;
					state.players[playerNumber].decorations+=2;
				}
				else {
					state.players[playerNumber].currentHealth -= 10;
				}
				break;
			case 19:	// DrSalvador
				if (state.players[playerNumber].damage >= 20) {
					defeated = true;
					state.players[playerNumber].decorations+=2;
				}
				else {
					state.players[playerNumber].currentHealth -= 15;
				}
				break;
			case 20:	// El Gigante
				if (state.players[playerNumber].damage >= 40) {
					defeated = true;
					state.players[playerNumber].decorations+=4;
				}
				else {
					state.players[playerNumber].currentHealth -= 40;
				}
				break;
			case 22:	// Executioner
				if (state.players[playerNumber].damage >= 30) {
					defeated = true;
					state.players[playerNumber].decorations+=3;
				}
				else {
					state.players[playerNumber].currentHealth -= 25;
				}
				break;
			case 25:
				defeated = true;
				state.players[playerNumber].discard.add(CardHelper.createCard(25, -1));
				break;
			case 26:	// Gatling Gun Majini
				if (state.players[playerNumber].damage >= 40) {
					defeated = true;
					state.players[playerNumber].decorations+=4;
				}
				else {
					state.players[playerNumber].currentHealth -= 25;
					int extraDamage = state.players[playerNumber].ammoUsed / 10;
					extraDamage*=5;
					state.players[playerNumber].currentHealth -= extraDamage;
				}
				break;
			case 30:	// Hunter
				if (state.players[playerNumber].damage >= 40) {
					defeated = true;
					state.players[playerNumber].decorations+=4;
				}
				else {
					state.players[playerNumber].currentHealth -= 30;
					discardRandomFromDiscard(state, playerNumber);
					discardRandomFromDiscard(state, playerNumber);
				}
				break;
			case 35:	// Licker
				if (state.players[playerNumber].damage >= 40) {
					defeated = true;
					state.players[playerNumber].decorations+=3;
				}
				else {
					state.players[playerNumber].currentHealth -= 30;
				}
				break;
			case 37:	// Majini
				if (state.players[playerNumber].damage >= 15) {
					defeated = true;
					state.players[playerNumber].decorations+=1;
				}
				else {
					state.players[playerNumber].currentHealth -= 10;
				}
				break;
			case 40:	// Mimicry Marcus
				if (state.players[playerNumber].damage >= 30) {
					defeated = true;
					state.players[playerNumber].decorations+=2;
				}
				else {
					state.players[playerNumber].currentHealth -= 20;
				}
				break;
			case 41:	// Nemesis
				if (!state.topMansionRevealed) {
					for (Player p: state.players) {
						p.currentHealth -= 20;
					}
					
					for (Player p: state.players) {
						if (state.turn == p.id) continue;
						// if dead
					}
					// if dead this player
				}
				
				if (state.players[playerNumber].damage >= 60) {
					defeated = true;
					state.players[playerNumber].decorations+=5;
				}
				else {
					state.players[playerNumber].currentHealth -= 40;
				}
				break;
			case 46:
				defeated = true;
				state.players[playerNumber].discard.add(CardHelper.createCard(46, -1));
				break;
			case 57:	// Urboros
				urboros = true;
				if (state.players[playerNumber].damage >= 90) {
					defeated = true;
					state.players[playerNumber].decorations+=8;
				}
				else {
					state.players[playerNumber].currentHealth -= 70;
				}
				break;
			case 58:
				defeated = true;
				state.players[playerNumber].maxHealth += 10;
				state.players[playerNumber].currentHealth += 10;
				break;
			case 59:	// Zombie Butcher
				if (state.players[playerNumber].damage >= 15) {
					defeated = true;
					state.players[playerNumber].decorations+=1;
				}
				else {
					state.players[playerNumber].currentHealth -= 10;
				}
				break;
			case 60:	// Zombie Female
				if (state.players[playerNumber].damage >= 15) {
					defeated = true;
					state.players[playerNumber].decorations+=1;
				}
				else {
					state.players[playerNumber].currentHealth -= 10;
				}
				break;
			case 61:	// Zombie Male
				if (state.players[playerNumber].damage >= 20) {
					defeated = true;
					state.players[playerNumber].decorations+=1;
				}
				else {
					state.players[playerNumber].currentHealth -= 20;
				}
				break;
			default:
				return;
		}
		state.players[playerNumber].explores--;
		resetWeaponCards(state, playerNumber);
		state.players[playerNumber].damage = 0;
		state.players[playerNumber].bonusDamage = 0;
		if (defeated) {
			state.latestGameMessage = state.players[playerNumber].name + " beat " + CardHelper.cardName(card.cardImage);
			state.topMansionRevealed = false;
			state.lastFought = card;
			state.players[playerNumber].attached.add(state.mansion.remove(0));
			if (urboros) {
				state.gameover = true;
			}
			if (didPlayerDie(state, playerNumber)) {
				state.players[playerNumber].skipTurn++;
				endTurn(state);
			}
		}
		else {
			state.latestGameMessage = state.players[playerNumber].name + " lost to " + CardHelper.cardName(card.cardImage);
			state.topMansionRevealed = true && !urboros;
			if (urboros) {
				CardHelper.shuffleDeck(state.mansion);
			}
		}
	}
}
