package net.umc.ludumdare.utils;

import java.util.ArrayList;
import java.util.Hashtable;

import net.umc.ludumdare.network.Card;
import net.umc.ludumdare.network.GameState;
import net.umc.ludumdare.network.Pile;
import net.umc.ludumdare.network.Player;

public class ServerHelper {

	public static GameState createGame(ArrayList<String> users, Hashtable<String, String> config) {
		GameState state = new GameState();
		
		state.numberOfPlayers = users.size();
		state.players = new Player[state.numberOfPlayers];
		state.turn = 1;
		state.turnLength = config.containsKey("TurnLength") ? Integer.parseInt(config.get("TurnLength")) : 0;
		state.turnTimeElapsed = 0;
		state.shopPiles = parseStorePiles(config.containsKey("StorePiles") ? config.get("StorePiles") : "");
		for (int i = 0; i < state.numberOfPlayers; i++) {
			state.players[i] = new Player();
			state.players[i].character = CardHelper.randomCharacter();
			state.players[i].decorations = 0;
			state.players[i].discard = new ArrayList<Card>();
			state.players[i].firstAbilityCost = CharacterHelper.getCharacterAbilityOne(state.players[i].character.title);
			state.players[i].hand = new ArrayList<Card>();
			state.players[i].attached = new ArrayList<Card>();
			state.players[i].human = true;
			state.players[i].id = i + 1;
			state.players[i].inventory = new ArrayList<Card>();
			state.players[i].maxHealth = CharacterHelper.getCharacterHealth(state.players[i].character.title);
			state.players[i].currentHealth = state.players[i].maxHealth;
			state.players[i].name = users.get(i);
			state.players[i].secondAbilityCost = CharacterHelper.getCharacterAbilityTwo(state.players[i].character.title);
			CardHelper.setInitialInventory(state.players[i]);
			CardHelper.drawHand(state.players[i]);
			for (Card card : state.players[i].hand) System.out.println(card.title);
			CardHelper.resetPlayer(state.players[i]);
		}
		state.lastFought = null;
		state.mansion = CardHelper.createMansion();
		state.topMansionRevealed = false;
		CardHelper.startTurn(state.players[0]);
		state.latestGameMessage = "";
		return state;
	}
	
	// format: 2x1;4x17,5x2;
	private static Pile[] parseStorePiles(String parse) {
		Pile[] piles = new Pile[15];
		
		int count = 0;
		for (String string : parse.split(";")) {
			Pile pile = new Pile();
			pile.cards = new ArrayList<Card>();
			pile.pileID = count;
			for (String cards : string.split(",")) {
				String[] card = cards.split("x");
				int numOfCard = Integer.parseInt(card[0]);
				int cardNum = Integer.parseInt(card[1]);
				for (int i = 0; i < numOfCard; i++) {
					pile.cards.add(CardHelper.createCard(cardNum, pile.pileID));
				}
			}
			pile.cards = CardHelper.shuffleDeck(pile.cards);
			piles[count] = pile;
			count++;
			if (count >= 15) break;
		}
		
		return piles;
	}
	
}
