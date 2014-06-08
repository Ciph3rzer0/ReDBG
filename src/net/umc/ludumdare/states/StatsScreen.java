package net.umc.ludumdare.states;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.network.Card;
import net.umc.ludumdare.network.GameClient;
import net.umc.ludumdare.network.GameState;
import net.umc.ludumdare.network.Player;
import net.umc.ludumdare.utils.CardHelper;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StatsScreen extends BasicGameState {

    int stateID = 0;
    private int _offsetX = 0;
    private int _offsetY = 0;
    
    private int _prevMouseX = 0;
    private int _prevMouseY = 0;
    private int _drawBigPile = -1;
    private int _drawBigImage = -1;
    
    private boolean _isMouseDown = false;
    private boolean _showChatBox, _showChat;
    private GameState _state;
    private GameClient _client;
    private TextField _chatBox;
    
    public StatsScreen( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    public int getID() {
        return stateID;
    }
 
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	_client = MainGame.client;
    	_chatBox = new TextField(gc, gc.getGraphics().getFont(), 0, 548, 1024, 20);
    	_state = _client.gameState();
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	g.translate(_offsetX, _offsetY);
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
    	
    	g.translate(-_offsetX, -_offsetY);
    	
    	GameHUD.drawHUD(gc, sbg, g, _showChat || _showChatBox, _state);
    	if (_showChatBox) _chatBox.render(gc, g);
    	
    	if (_drawBigPile > -1 && _drawBigPile <= _state.numberOfPlayers) {
    		Card character = _state.players[_drawBigPile - 1].character;
    		_drawBigImage = character.cardImage;
			Image image = CardHelper.cardImage(character);
			g.drawImage(image, 
					262, 34, 262 + 500, 34 + 700,
					0, 0, image.getWidth(), image.getHeight());
    	}
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	_state = _client.gameState();
    	
    	Input input = gc.getInput();
    	boolean enterPressed = input.isKeyPressed(Input.KEY_ENTER);
    	boolean tPressed = input.isKeyPressed(Input.KEY_T);
    	
    	int mX = input.getMouseX();
    	int mY = input.getMouseY();  	
    	boolean rightMouseDown = input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
    	boolean leftMouseDown = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
    	boolean leftMouseClicked = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
    	
    	if (!GameHUD._myTurn && GameHUD._spectating) {
    		_offsetX = _client.getSpectate().screenOffsetX;
    		_offsetY = _client.getSpectate().screenOffsetY;
    	}
    	
    	if (GameHUD.update(gc, sbg, mX, mY, _offsetX, _offsetY, leftMouseClicked, leftMouseDown, rightMouseDown, _client, _drawBigImage)) return;
    	
    	if (rightMouseDown) {
    		if (mX - _offsetX >= 259 && mX - _offsetX <= 487 && mY - _offsetY >= 50 && mY - _offsetY  <= 378) _drawBigPile = 1;
    		if (mX - _offsetX >= 529 && mX - _offsetX <= 757 && mY - _offsetY  >= 50 && mY - _offsetY  <= 378) _drawBigPile = 2;
    		if (mX - _offsetX >= 259 && mX - _offsetX <= 487 && mY - _offsetY  >= 478 && mY - _offsetY  <= 756) _drawBigPile = 3;
    		if (mX - _offsetX >= 529 && mX - _offsetX <= 757 && mY - _offsetY  >= 478 && mY - _offsetY  <= 756) _drawBigPile = 4;
    	}
    	else {
    		_drawBigPile = -1;
    		_drawBigImage = -1;
    	}
    	
    	if (leftMouseDown) {
	    	if (_isMouseDown) {
	    		int offX = mX - _prevMouseX;
	    		int offY = mY - _prevMouseY;
	    		_offsetX += offX;
	    		_offsetY += offY;
	    		if (_offsetY <= -720) _offsetY = -720;
	    		if (_offsetY >= 0) _offsetY = 0;
	    		if (_offsetX <= -420) _offsetX = -420;
	    		if (_offsetX >= 0) _offsetX = 0;
	    		_prevMouseX = mX;
	    		_prevMouseY = mY;
	    	}
	    	else {
	    		_isMouseDown = true;
	    		_prevMouseX = mX;
	    		_prevMouseY = mY;
	    	}
    	}
    	else {
    		_isMouseDown = false;
    	}
	    	
    	if (!_showChatBox && enterPressed) {
    		_showChatBox = true;
    		_showChat = true;
    		_chatBox.setFocus(true);
    	}
    	else if (_showChatBox && enterPressed) {
    		_client.sendChat(_chatBox.getText());
    		_chatBox.setText("");
    		_showChatBox = false;
    		_showChat = true;
    	}
    	
    	if (!_showChat && !_showChatBox && tPressed) {
    		_showChat = true;
    		_chatBox.setText("");
    	}
    	else if (_showChat && tPressed) {
    		_showChat = false;
    		_chatBox.setText("");
    	}
    	
    	if (input.isKeyDown(Input.KEY_DOWN)) {
    		if (_offsetY <= -720) _offsetY = -720;
    		else _offsetY -= 5;
    	}
    	else if (input.isKeyDown(Input.KEY_UP)) {
    		if (_offsetY >= 0) _offsetY = 0;
    		else _offsetY += 5;
    	}
    	
    	if (input.isKeyDown(Input.KEY_LEFT)) {
    		if (_offsetX >= 0) _offsetX = 0;
    		else _offsetX += 5;
    	}
    	else if (input.isKeyDown(Input.KEY_RIGHT)) {
    		if (_offsetX <= -420) {
    			_offsetX = -420;
    		}
    		else {
    			_offsetX -= 5;
    		}
    	}
    }
}
