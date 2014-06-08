package net.umc.ludumdare.states;

import java.util.ArrayList;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.network.Card;
import net.umc.ludumdare.network.GameState;
import net.umc.ludumdare.tools.Button;
import net.umc.ludumdare.utils.CardHelper;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

public class CardSelect {

	private static Button btnPlayer1, btnPlayer2, btnPlayer3, btnPlayer4;
	private static UnicodeFont _fontHUD;
	
	private static final int CARD_WIDTH = 195;
	private static final int CARD_HEIGHT = 280;
	private static int _currentPlayer = 1;
	private static int _offsetX = 0;
	private static int  _x, _y;
	private static boolean _shown = false;
	private static int _selectType = 0;
	private static GameState _state;
	private static ArrayList<Card> _cardsToDraw;
	
    
    public static boolean update(GameContainer gc, StateBasedGame sbg, int mX, int mY, boolean clicked, boolean leftMouseDown, boolean rightMouseDown, GameState state) {
    	boolean clickedOnHUD = false;
    	_state = state;
    	
    	if (state.playerResponse != _shown) {
    		resetCardSelect();
    		_shown = state.playerResponse;
    	}
    	
     	if (!_shown) return false;   
     	
     	if (_selectType == 2 || _selectType == 3) _currentPlayer = _state.playerResponseTarget;
     	
    	switch (_selectType) {
	    	case 0:
	    	case 1:
	    		btnPlayer1.update(mX, mY, clicked);
	    		if (state.players.length > 1) btnPlayer2.update(mX, mY, clicked);
	    		if (state.players.length > 2) btnPlayer3.update(mX, mY, clicked);
	    		if (state.players.length > 3) btnPlayer4.update(mX, mY, clicked);
	    		
	    		if (btnPlayer1.isClicked()) _currentPlayer = 1;
	    		if (btnPlayer2.isClicked()) _currentPlayer = 2;
	    		if (btnPlayer3.isClicked()) _currentPlayer = 3;
	    		if (btnPlayer4.isClicked()) _currentPlayer = 4;
	    		
	    		_cardsToDraw = _selectType == 0 ? state.players[_currentPlayer - 1].hand : state.players[_currentPlayer - 1].discard;
	    		break;
	    	case 2:
	    		_cardsToDraw = state.players[state.playerResponseTarget].hand;
	    		break;
	    	case 3:
	    		_cardsToDraw = state.players[state.playerResponseTarget].discard;
	    		break;
	    	case 4:
	    		_cardsToDraw = new ArrayList<Card>();
	    		for (int i = 0; i < _state.players.length; i++) {
	    			_cardsToDraw.add(state.players[i].character);
	    		}
	    		break;
    	}
    	
    	return clickedOnHUD;
    }
    
    private static void resetCardSelect() {
    	
    }
    
    public static void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
    	if (!_shown || _cardsToDraw == null || _cardsToDraw.size() == 0) return;
    	g.setColor(Color.green);
    	g.fillRect(_x, _y, MainGame.width, 400);
    	g.setColor(Color.white);
    	
    	String title = "";
    	switch (_selectType) {
	    	case 0:
	    		title += String.format("Choose %s card(s) from a players hand. - %s's Hand", _state.playerRespondNumberOfCards, _state.players[_currentPlayer - 1].name);
	    		break;
	    	case 1:
	    		title += String.format("Choose %s card(s) from a players discard. - %s's Hand", _state.playerRespondNumberOfCards, _state.players[_currentPlayer - 1].name);
	    		break;
	    	case 2:
	    		title += String.format("Choose %s card(s) from this players hand. - %s's Hand", _state.playerRespondNumberOfCards, _state.players[_currentPlayer - 1].name);
	    		break;
	    	case 3:
	    		title += String.format("Choose %s card(s) from this players discard. - %s's Hand", _state.playerRespondNumberOfCards, _state.players[_currentPlayer - 1].name);
	    		break;
	    	case 4:
	    		title += "Choose a player.";
	    		break;
    	}
    	_fontHUD.drawString((MainGame.width - _fontHUD.getWidth(title)) / 2, _y, title);
    	
    	g.translate(_offsetX, 0);
    	
    	for (int i = 0; i < _cardsToDraw.size(); i++) {
    		g.drawImage(CardHelper.cardImage(_cardsToDraw.get(i)), (i * (CARD_WIDTH + 30)) + 10, _y + 50);
    		if (_selectType == 4) {
    			_fontHUD.drawString((i * (CARD_WIDTH + 30)) + 10, _y + CARD_HEIGHT + 50, _state.players[i].name);
    		}
    	}
    	
    	g.translate(-_offsetX, 0);
    }

	public static void init(GameState state) {
    	_fontHUD = GameHUD._fontHUD;
    	_x = 0;
    	_y = MainGame.height - 400;
    	_y /= 2;
    	
    	Image image = null;
    	try {
			image = new Image("resources/images/icons/hud_button_blank.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
    	
    	btnPlayer1 = new Button(_x, _y + 20, 96, 20, image, state.players[0].name.length() > 8 ? state.players[0].name.substring(0, 8) : state.players[0].name);
    	if (state.players.length > 1) btnPlayer2 = new Button(_x + 100, _y + 20, 96, 20, image, state.players[1].name.length() > 8 ? state.players[1].name.substring(0, 8) : state.players[1].name);
    	if (state.players.length > 2) btnPlayer3 = new Button(_x + 200, _y + 20, 96, 20, image, state.players[2].name.length() > 8 ? state.players[2].name.substring(0, 8) : state.players[2].name);
    	if (state.players.length > 3) btnPlayer4 = new Button(_x + 300, _y + 20, 96, 20, image, state.players[3].name.length() > 8 ? state.players[3].name.substring(0, 8) : state.players[3].name);
    	
    }
}
