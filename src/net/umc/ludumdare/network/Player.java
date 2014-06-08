package net.umc.ludumdare.network;

import java.util.ArrayList;

public class Player {

    public int id;
    public String name;
    public boolean human;
    public boolean dead;
    public Card character;
    public int currentHealth;
    public int maxHealth;
    public int decorations;
    public int firstAbilityCost;
    public int secondAbilityCost;
    public ArrayList<Card> hand;
    public ArrayList<Card> discard;
    public ArrayList<Card> inventory;
    public ArrayList<Card> attached;
    public int actions;
    public int buys;
    public int explores;
    public int ammo;
    public int ammoUsed;
    public int gold;
	public int damage;
	public int bonusDamage;
	public int weaponsPlayed;
	public int actionsPlayed;
	public int skipActions;
	public int skipTurn;
	public int deadlyAimPlayed;
	
    public Player() {}
    
}
