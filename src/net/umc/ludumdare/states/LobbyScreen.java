package net.umc.ludumdare.states;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.network.GameClient;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LobbyScreen extends BasicGameState {

    int stateID = 0;
    
    private GameClient _client;
    private TextField _chatBox;
    private boolean _showChatBox, _showChat;
    
    public LobbyScreen( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    public int getID() {
        return stateID;
    }
 
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	_client = MainGame.client;
    	_chatBox = new TextField(gc, gc.getGraphics().getFont(), 0, 548, 1024, 20);
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	g.setColor(Color.white);
    	g.drawString("Waiting for a full room...", 400, 290);
    	g.drawString(String.format("%s/%s", _client.playerList().size(), _client.roomSize()), 492, 310);
    	for (int i = 0; i < _client.playerList().size(); i++) {
    		g.drawString(_client.playerList().get(i), 462, 350 + (i * 20));
    	}
    	
    	if (_showChat || _showChatBox) GameHUD.drawChatBox(gc, sbg, g);
    	if (_showChatBox) _chatBox.render(gc, g);
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	if (_client.gameStarted()) { 
    		GameHUD.init(_client.gameState());
    		sbg.enterState(MainGame.SHOPSCREEN);
    	}
    	Input input = gc.getInput();
    	
    	boolean enterPressed = input.isKeyPressed(Input.KEY_ENTER);
    	boolean tPressed = input.isKeyPressed(Input.KEY_T);
    	if (!_showChatBox && enterPressed) {
    		_showChatBox = true;
    		_showChat = true;
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
