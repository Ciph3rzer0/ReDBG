package net.umc.ludumdare.states;

import java.util.ArrayList;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.network.Card;
import net.umc.ludumdare.network.GameClient;
import net.umc.ludumdare.network.GameState;
import net.umc.ludumdare.network.Pile;
import net.umc.ludumdare.utils.CardHelper;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ShopScreen extends BasicGameState {

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
    
    public ShopScreen( int stateID ) 
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
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	g.translate(_offsetX, _offsetY);
    	Pile[] shopPiles = _state.shopPiles;
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 5; j++) {
    			Pile pile = shopPiles[(j*3)+i];
    			ArrayList<Card> cards = pile.cards;
    			if (cards.size() > 0) {
    				Image image = CardHelper.cardImage(cards.get(0));
    				int imgX = 50 + (278 * j);
    				int imgY = 50 + (i * 378);
    				g.drawImage(image, imgX, imgY, imgX + 228, imgY + 328,
    						0, 0, image.getWidth(), image.getHeight());
    			}
    		}
    	}
    	g.translate(-_offsetX,  -_offsetY);
    	
    	GameHUD.drawHUD(gc, sbg, g, _showChat || _showChatBox, _state);
    	if (_showChatBox) _chatBox.render(gc, g);
    	
    	if (_drawBigPile > -1) {
    		Pile pile = shopPiles[_drawBigPile];
    		if (pile.cards.size() > 0) {
    			_drawBigImage = pile.cards.get(0).cardImage;
    			Image image = CardHelper.cardImage(pile.cards.get(0));
    			g.drawImage(image, 
    					262, 34, 262 + 500, 34 + 700,
						0, 0, image.getWidth(), image.getHeight());
    		}
    	}
    	else {
    		_drawBigImage = -1;
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
    	
    	if (GameHUD.update(gc, sbg, mX, mY, _offsetX, _offsetY, leftMouseClicked, leftMouseDown, rightMouseDown, _client, _drawBigImage)) return;
    	
    	if (!GameHUD._myTurn && GameHUD._spectating && _client.getSpectate() != null) {
    		_offsetX = _client.getSpectate().screenOffsetX;
    		_offsetY = _client.getSpectate().screenOffsetY;
    	}
    	
    	if (leftMouseClicked) {
    		if (_state.players[_client.playerNumber() - 1].buys >= 0) {
	    		int pileRow = inPileRow(mY - _offsetY);
	    		int pileCol = inPileColumn(mX - _offsetX);
	    		if (pileRow > -1 && pileCol > -1) {
	    			int pile = (3 * pileCol) + pileRow;
	    			if (_state.shopPiles[pile].cards.size() > 0) {
	    				Card card = _state.shopPiles[pile].cards.get(0);
	    				if (CardHelper.cardCost(card.cardImage) <= _state.players[_client.playerNumber() - 1].gold) {
	    					_client.buyCard(pile, _client.playerNumber());
	    				}
	    			}
	    		}
    		}
    	}
    	
    	if (rightMouseDown) {
    		int pileRow = inPileRow(mY - _offsetY);
    		int pileCol = inPileColumn(mX - _offsetX);
    		if (pileRow > -1 && pileCol > -1) {
    			_drawBigPile = (3 * pileCol) + pileRow;
    		}
    	}
    	else {
    		_drawBigPile = -1;
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
	
    private int inPileRow(int y) {
    	for (int i = 0; i < 3; i++) {
    		int cY = (i * 378) + 50;
    		if (y >= cY && y <= cY + 328) return i;
    	}
    	return -1;
    }
    
    private int inPileColumn(int x) {
    	for (int i = 0; i < 5; i++) {
    		int cX = (i * 278) + 50;
    		if (x >= cX && x <= cX + 228) return i;
    	}
    	return -1;
    }
}
