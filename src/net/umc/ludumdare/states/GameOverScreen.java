package net.umc.ludumdare.states;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.network.GameClient;
import net.umc.ludumdare.network.GameState;
import net.umc.ludumdare.network.Player;
import net.umc.ludumdare.utils.CardHelper;
import net.umc.ludumdare.utils.Logger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverScreen extends BasicGameState {

    int stateID = 0;

    private String _message;
    private UnicodeFont _font;
    private GameState _state;
    private GameClient _client;
    
    public GameOverScreen( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    public int getID() {
        return stateID;
    }
 
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	int max = 0;
    	int score = 0;
    	for (Player player: _state.players) {
    		if (player.decorations > score) {
    			max = player.id;
    			score = player.decorations;
    		}
    	}
    	_message = _state.players[max - 1].name + " Won the game with " + _state.players[max - 1].decorations + " points";
    
    }
    
    @SuppressWarnings("unchecked")
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	_client = MainGame.client;
    	_state = _client.gameState();
    	
		try {
			_font = new UnicodeFont("resources/fonts/Designosaur-Regular.ttf", 72, true, false);
			_font.addAsciiGlyphs();
			_font.getEffects().add(new ColorEffect(java.awt.Color.yellow));  // Create a default white color effect
			_font.loadGlyphs();
		} catch (SlickException e) {
			Logger.logMessage("HUD Font Failed", "", e.getMessage(), "Font failed to load.");
		}
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    
    	int count = 0;
    	for(Player player : _state.players) {
    		Image image = CardHelper.cardImage(player.character);
    		switch (count) {
    		case 0:
    			g.drawImage(image, 259, 50, 487, 378, 0, 0, image.getWidth(), image.getHeight());
    			break;
    		case 1:
    			g.drawImage(image, 529, 50, 757, 378, 0, 0, image.getWidth(), image.getHeight());
    			break;
    		case 2:
    			g.drawImage(image, 259, 478, 487, 756, 0, 0, image.getWidth(), image.getHeight());
    			break;
    		case 3:
    			g.drawImage(image, 529, 478, 757, 756, 0, 0, image.getWidth(), image.getHeight());
    			break;
    		}
    		count++;
    	}
    	
    	int numPlayers = _state.players.length;
    	if (numPlayers > 0) {
    		g.setColor(_state.turn == 1 ? Color.orange : Color.white);
    		Player player1 = _state.players[0];
    		g.drawString(player1.name, 50, 50);
    		g.drawString(String.format("Actions: %s", player1.actions), 50, 70);
    		g.drawString(String.format("Buys: %s", player1.buys), 50, 90);
    		g.drawString(String.format("Explores: %s", player1.explores), 50, 110);
    		g.drawString(String.format("Ammo: %s", player1.ammo), 50, 130);
    		g.drawString(String.format("Gold: %s", player1.gold), 50, 150);
    		g.drawString(String.format("Max Health: %s", player1.maxHealth), 50, 170);
    		g.drawString(String.format("Health: %s", player1.currentHealth), 50, 190);
    		g.drawString(String.format("Damage: %s", player1.damage), 50, 210);
    	}
    	
    	if (numPlayers > 1) {
    		g.setColor(_state.turn == 2 ? Color.orange : Color.white);
    		Player player1 = _state.players[1];
    		g.drawString(player1.name, 874, 50);
    		g.drawString(String.format("Actions: %s", player1.actions), 874, 70);
    		g.drawString(String.format("Buys: %s", player1.buys), 874, 90);
    		g.drawString(String.format("Explores: %s", player1.explores), 874, 110);
    		g.drawString(String.format("Ammo: %s", player1.ammo), 874, 130);
    		g.drawString(String.format("Gold: %s", player1.gold), 874, 150);
    		g.drawString(String.format("Max Health: %s", player1.maxHealth), 874, 170);
    		g.drawString(String.format("Health: %s", player1.currentHealth), 874, 190);
    		g.drawString(String.format("Damage: %s", player1.damage), 874, 210);
    	}
    	
    	if (numPlayers > 2) {
    		g.setColor(_state.turn == 3 ? Color.orange : Color.white);
    		Player player1 = _state.players[2];
    		g.drawString(player1.name, 50, 478);
    		g.drawString(String.format("Actions: %s", player1.actions), 50, 498);
    		g.drawString(String.format("Buys: %s", player1.buys), 50, 518);
    		g.drawString(String.format("Explores: %s", player1.explores), 50, 538);
    		g.drawString(String.format("Ammo: %s", player1.ammo), 50, 558);
    		g.drawString(String.format("Gold: %s", player1.gold), 50, 578);
    		g.drawString(String.format("Max Health: %s", player1.maxHealth), 50, 598);
    		g.drawString(String.format("Health: %s", player1.currentHealth), 50, 618);
    		g.drawString(String.format("Damage: %s", player1.damage), 50, 638);
    	}
    	
    	if (numPlayers > 3) {
    		g.setColor(_state.turn == 4 ? Color.orange : Color.white);
    		Player player1 = _state.players[3];
    		g.drawString(player1.name, 874, 478);
    		g.drawString(String.format("Actions: %s", player1.actions), 874, 498);
    		g.drawString(String.format("Buys: %s", player1.buys), 874, 518);
    		g.drawString(String.format("Explores: %s", player1.explores), 874, 538);
    		g.drawString(String.format("Ammo: %s", player1.ammo), 874, 558);
    		g.drawString(String.format("Gold: %s", player1.gold), 874, 578);
    		g.drawString(String.format("Max Health: %s", player1.maxHealth), 874, 598);
    		g.drawString(String.format("Health: %s", player1.currentHealth), 874, 618);
    		g.drawString(String.format("Damage: %s", player1.damage), 874, 638);
    	}

    	g.setColor(Color.yellow);
    	int x = _font.getWidth("GAME OVER");
    	x = (1024 - x) / 2;
    	_font.drawString(x, 300, "GAME OVER");
    	_font.drawString(x, 400, _message);
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	_state = _client.gameState();
    }
}
