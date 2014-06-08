package net.umc.ludumdare.states;

import java.util.ArrayList;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.network.Card;
import net.umc.ludumdare.network.CardPlayedResponse;
import net.umc.ludumdare.network.GameClient;
import net.umc.ludumdare.network.GameState;
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

public class HistoryScreen extends BasicGameState {

    int stateID = 0;
    private int _offsetX = 0;
    private int _offsetY = 0;
    
    private int _drawBigPile = -1;
    private int _drawBigImage = -1;
    
    private boolean _showChatBox, _showChat;
    private GameState _state;
    private GameClient _client;
    private TextField _chatBox;
    
    public HistoryScreen( int stateID ) 
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
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
    	int count = 0;
    	ArrayList<CardPlayedResponse> gameHistory = _client.getHistory();
    	for (int i = gameHistory.size() - 1; i >= 0; i--) {
    		CardPlayedResponse response = gameHistory.get(i);
    		g.setColor(Color.green);
    		g.fillRect(10, (count * 20) + 45, 200, 16);
    		g.setColor(Color.white);
    		String message = (count + 1) + " - " + response.username  + ": " + response.message;
    		g.drawString(message, 10, (count * 20) + 45);
    	}
    	
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
    	
    	if (GameHUD.update(gc, sbg, mX, mY, 0, 0, leftMouseClicked, leftMouseDown, rightMouseDown, _client, _drawBigImage)) return;
    	
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
    }
}
