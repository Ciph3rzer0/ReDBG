package net.umc.ludumdare.states;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.network.GameClient;
import net.umc.ludumdare.network.GameState;
import net.umc.ludumdare.utils.CardHelper;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MansionScreen extends BasicGameState {

    int stateID = 0;
    
    private boolean _showChatBox, _showChat, _bigRevealed, _bigLastFought;
    private GameState _state;
    private GameClient _client;
    private TextField _chatBox;
    private Image _imgMansionBack;
    private int drawBigImage = -1;
    
    public MansionScreen( int stateID ) 
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
    	_chatBox = new TextField(gc, gc.getGraphics().getFont(), 0, MainGame.height - 20, MainGame.width, 20);
    	_state = _client.gameState();
    	_imgMansionBack = new Image("resources/images/icons/explore_mansion.png");
    	_bigRevealed = false;
    	_bigLastFought = false;
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	int midPoint = MainGame.width / 2;
    	g.drawString("Current / Expore", midPoint + ((midPoint - 228) / 2), 50);
    	if (_state.topMansionRevealed) {
    		Image image = CardHelper.cardImage(_state.mansion.get(0));
    		g.drawImage(image, midPoint + ((midPoint - 228) / 2), 70, midPoint + ((midPoint - 228) / 2) + 228, 70 + 328,
    				0, 0, image.getWidth(), image.getHeight());
    	}
    	else {
			g.drawImage(_imgMansionBack, midPoint + ((midPoint - 228) / 2), 70, midPoint + ((midPoint - 228) / 2) + 228, 70 + 328,
					0, 0, _imgMansionBack.getWidth(), _imgMansionBack.getHeight());
    	}
    	
    	g.drawString("Last Killed", ((midPoint - 228) / 2), 50);
    	if (_state.lastFought != null) {
    		Image prevCard = CardHelper.cardImage(_state.lastFought);
    		g.drawImage(prevCard, ((midPoint - 228) / 2), 70, ((midPoint - 228) / 2) + 228, 70 + 328, 
    				0, 0, prevCard.getWidth(), prevCard.getHeight());
    	}
    	
    	GameHUD.drawHUD(gc, sbg, g, _showChat || _showChatBox, _state);
    	if (_showChatBox) _chatBox.render(gc, g);
    	
    	if (_bigRevealed && _state.topMansionRevealed) {
    		drawBigImage = _state.mansion.get(0).cardImage;
			Image image = CardHelper.cardImage(_state.mansion.get(0));
			g.drawImage(image, 
					262, 34, 262 + 500, 34 + 700,
					0, 0, image.getWidth(), image.getHeight());
    	}
    	else if (_bigLastFought) {
    		drawBigImage = _state.lastFought.cardImage;
    		Image image = CardHelper.cardImage(_state.lastFought);
			g.drawImage(image, 
					262, 34, 262 + 500, 34 + 700,
					0, 0, image.getWidth(), image.getHeight());
    	}
    	else {
    		drawBigImage = -1;
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
    	boolean leftMouseClicked = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
    	boolean leftMouseDown = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
    	
    	if (GameHUD.update(gc, sbg, mX, mY, 0, 0, leftMouseClicked, leftMouseDown, rightMouseDown, _client, drawBigImage)) return;
    	
    	int midPoint = MainGame.width / 2;
    	if (leftMouseClicked && mX >= midPoint + ((midPoint - 228) / 2) && mY >= 70 && mX <= midPoint + ((midPoint - 228) / 2) + 228 && mY <= 328 + 70) {
    		if (_state.turn == _client.playerNumber() && 
    				_state.players[_client.playerNumber() - 1].explores > 0) {
    			_client.explore();
    		}
    	}
    	
    	if (rightMouseDown) {
    		if (mX >= midPoint + ((midPoint - 228) / 2) && mY >= 70 && mX <= midPoint + ((midPoint - 228) / 2) + 228 && mY <= 328 + 70) {
    			_bigRevealed = true;
    			_bigLastFought = false;
    		}
    		else if (_state.lastFought != null && mX >= ((midPoint - 228) / 2) && mY >= 70 && mX <= ((midPoint - 228) / 2) + 228 && mY <= 328 + 70) {
    			_bigRevealed = false;
    			_bigLastFought = true;
    		}
    	}
    	else {
    		_bigRevealed = false;
			_bigLastFought = false;
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
