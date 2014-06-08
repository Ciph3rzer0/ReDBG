package net.umc.ludumdare.network;

import java.util.ArrayList;

public class GameState {

    public int numberOfPlayers;
    public Player[] players;
    public Pile[] shopPiles;
    public int turn;
    public int turnLength;
    public int turnTimeElapsed;
    public long turnStartTime;
    public String latestGameMessage;
    public ArrayList<Card> mansion;
	public Card lastFought;
	public boolean topMansionRevealed;
	public boolean gameover;
	
	public boolean playerResponse;
	public int playerToRespond;
	public int playerRespondType;
	public int playerRespondNumberOfCards;
	public int playerRespondTime;
	public int playerRespondTimeRemaining;
	public int playerResponseTarget;
	public long playerRespondStartTime;
}
