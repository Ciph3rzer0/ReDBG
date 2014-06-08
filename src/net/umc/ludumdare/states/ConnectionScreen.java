package net.umc.ludumdare.states;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.network.GameClient;
import net.umc.ludumdare.tools.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ConnectionScreen extends BasicGameState {

    int stateID = 0;
    
    private UnicodeFont font;
    private GameClient _client;
    private Button _button;
    
    public ConnectionScreen( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    public int getID() {
        return stateID;
    }
 
    @SuppressWarnings("unchecked")
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	font = new UnicodeFont("resources/fonts/Designosaur-Regular.ttf", 16, false, false);
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect(java.awt.Color.black));  // Create a default white color effect
		font.loadGlyphs();

		_client = MainGame.client;
		_button = new Button((MainGame.width - 256) / 2,
				(MainGame.height - 64) / 2, 256, 64, new Image("resources/images/icons/connect_button.png"));
	}
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	_button.render(gc, sbg, g);
    	if (_client.isConnecting()) {
    		g.setColor(Color.white);
    		g.drawString("Connecting.", 384, 350);
    	}
    	else if (_client.failedToConnect()) {
    		g.setColor(Color.red);
    		g.drawString("Failed to connect.", 384, 350);
    		g.drawString(_client.connectMessage(), 384, 370);
    	}
    	else if (_client.isConnected()) {
    		g.setColor(Color.white);
    		g.drawString("Connected", 384, 350);
    	}
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	if (_client.isConnected()) {
    		sbg.enterState(MainGame.LOBBYSTATE);
    	}
    	
    	Input input = gc.getInput();
    	float mx = input.getMouseX();
    	float my = input.getMouseY(); 
    	boolean leftMouseClick = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);//input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
    	_button.update(mx, my, leftMouseClick);
    	if (_button.isClicked()) {
    		_client.connect();
    	}
    }
	
}
