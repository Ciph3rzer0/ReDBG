package net.umc.ludumdare.states;

import java.util.ArrayList;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.network.Card;
import net.umc.ludumdare.network.GameClient;
import net.umc.ludumdare.network.GameState;
import net.umc.ludumdare.network.Player;
import net.umc.ludumdare.network.SpectateState;
import net.umc.ludumdare.tools.Button;
import net.umc.ludumdare.utils.CardHelper;
import net.umc.ludumdare.utils.Logger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

public class GameHUD {

	public static int currentPlayer = 1;
	public static int numberOfPlayers = 0;	
	public static TextField _chatBox;
	public static Button btnShop, btnPlayer1, btnPlayer2, btnPlayer3, btnPlayer4, btnMansion, btnStats, btnEndTurn, btnSpectateOn, btnSpectateOff, btnHistory;
	public static String latestGameMessage, turnMessage;
	public static UnicodeFont _fontHUD;
	public static Image CARD_BACK, MANSION_BACK, DISCARD_PILE, HAND_PILE, ATTACHMENT_PILE, CURSOR;
	public static final Color BACKCOLOR = new Color(12, 12, 12);
	
	public static boolean _spectating = true, _myTurn = false;
	private static SpectateState _spectate;
	private static int _displayType = 0;
	private static final int CARD_WIDTH = 195;
	private static final int CARD_HEIGHT = 280;
	private static int _offsetX = 0;
	private static int _prevMouseX = 0;
	private static int _drawBigPile = -1;
	private static boolean _isMouseDown = false;
	//private static int _offsetY = 0;
	private static ArrayList<String> _chat = new ArrayList<String>();
	private static int _spectateMouseX = 0;
	private static int _spectateMouseY = 0;
	private static int _drawBig = -1;
	
    public static void drawChatBox(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	g.setColor(Color.cyan);
    	g.fillRect(0, MainGame.height - 300, MainGame.width, 280);
    	g.setColor(Color.blue);
    	g.fillRect(5, MainGame.height - 295, MainGame.width - 10, 270);
    	g.setColor(Color.white);
    	int num = _chat.size() > 13 ? _chat.size() - 13 : 0;
    	int count = 0;
    	for (int i = num; i < _chat.size(); i++) {
    		g.drawString(_chat.get(i), 10, MainGame.height - 285 + (20 * count));
    		count++;
    	}
    }

    public static void drawHUD(GameContainer gc, StateBasedGame sbg, Graphics g, boolean showChat, GameState state) throws SlickException {
		g.setColor(Color.red);
		g.fillRect(0, MainGame.height - 320, MainGame.width, 20);
    	if (showChat) {
    		drawChatBox(gc, sbg, g);
    		g.setColor(Color.black);
    		g.fillRect(0, MainGame.height - 20, MainGame.width, 20);
    	}
    	else {
    		g.setColor(Color.red);
    		g.fillRect(0, MainGame.height - 340, MainGame.width, 20);
    		drawPlayer(gc, sbg, g, state);
    		g.setColor(Color.white);
    		String strHandType = state.players[currentPlayer - 1].name + "'s - ";
    		switch (_displayType) {
	    		case 0:
	    			strHandType += "Hand";
	    			break;
	    		case 1:
	    			strHandType += "Discard";
	    			break;
	    		case 2:
	    			strHandType += "Attachments";
	    			break;
    		}
    		int drawMessageX = (MainGame.width - g.getFont().getWidth(strHandType)) / 2;
    		g.drawString(strHandType, drawMessageX, MainGame.height - 340);
    	}
    	
    	g.setColor(Color.white);
    	if (numberOfPlayers > 0) btnPlayer1.render(gc, sbg, g);
    	if (numberOfPlayers > 1) btnPlayer2.render(gc, sbg, g);
    	if (numberOfPlayers > 2) btnPlayer3.render(gc, sbg, g);
    	if (numberOfPlayers > 3) btnPlayer4.render(gc, sbg, g);
    	
    	g.setColor(Color.blue);
    	g.fillRect(0, 0, MainGame.width, 40);
    	g.setColor(Color.white);
    	
    	btnShop.render(gc, sbg, g);

    	btnMansion.render(gc, sbg, g);
    	btnStats.render(gc, sbg, g);
    	btnHistory.render(gc, sbg, g);
    	btnEndTurn.render(gc, sbg, g);
    	
    	if (_spectating) {
    		btnSpectateOff.render(gc, sbg, g);
    	}
    	else {
    		btnSpectateOn.render(gc, sbg, g);
    	}
    	
    	if (latestGameMessage != null) {
    		//g.drawString(latestGameMessage, 10, 20);
    		_fontHUD.drawString(10, 24, latestGameMessage);
    	}
    	
    	if (turnMessage != null) {
    		//g.drawString(turnMessage, 275, 20);
    		_fontHUD.drawString(350, 24, turnMessage);
    	}
    	
    	if (_drawBigPile > -1) {
			ArrayList<Card> cards = null;
			int player = currentPlayer - 1;
			switch (_displayType) {
				case 0:
					cards = state.players[player].hand;
					break;
				case 1:
					cards = state.players[player].discard;
					break;
				case 2:
					cards = state.players[player].attached;
					break;
			}	
			Image image = CardHelper.cardImage(cards.get(_drawBigPile));
			int drawX = MainGame.width - image.getWidth();
			drawX /= 2;
			int drawY = MainGame.height - image.getHeight();
			drawY /= 2;
			g.drawImage(image, drawX, drawY);
    	}
    	
    	if (!_myTurn && _spectating) {
    		g.drawImage(CURSOR, _spectateMouseX, _spectateMouseY);
    	}
    	
    	if (_drawBig > -1) {
			Image image = CardHelper.cardImage(_drawBig);
			g.drawImage(image, 
					(MainGame.width - 500) / 2, (MainGame.height - 700) / 2, ((MainGame.width - 500) / 2) + 500, ((MainGame.height - 700) / 2) + 700,
					0, 0, image.getWidth(), image.getHeight());
    	}
    }
    
    public static void addChatMessage(String message) {
    	_chat.add(message);
    }
    
    public static boolean update(GameContainer gc, StateBasedGame sbg, int mX, int mY, int screenOffsetX, int screenOffsetY, boolean clicked, boolean leftMouseDown, boolean rightMouseDown, GameClient client, int drawBig) {
    	boolean clickedOnHUD = false;
    	GameState state = client.getGameState();
    	int player = currentPlayer - 1;
    	
    	btnShop.update(mX, mY, clicked);
    	if (numberOfPlayers > 0) btnPlayer1.update(mX, mY, clicked);
    	if (numberOfPlayers > 1) btnPlayer2.update(mX, mY, clicked);
    	if (numberOfPlayers > 2) btnPlayer3.update(mX, mY, clicked);
    	if (numberOfPlayers > 3) btnPlayer4.update(mX, mY, clicked);
    	btnMansion.update(mX, mY, clicked);
    	btnStats.update(mX, mY, clicked);
    	btnHistory.update(mX, mY, clicked);
    	btnEndTurn.update(mX, mY, clicked);
    	btnSpectateOn.update(mX, mY, clicked);
    	btnSpectateOff.update(mX, mY, clicked);
    	
    	ArrayList<Card> cards = null;
    	switch (_displayType) {
	    	case 0:
	    		cards = state.players[player].hand;
	    		break;
	    	case 1:
	    		cards = state.players[player].discard;
	    		break;
	    	case 2:
	    		cards = state.players[player].attached;
	    		break;
    	}
    	
    	if (clicked && mX >= 10 + _offsetX && mY >= MainGame.height - 290 && 
    			mX <= 205 + _offsetX && mY <= MainGame.height - 290 + 280) {
    		_displayType = _displayType + 1 > 2 ? 0 : _displayType + 1;
    	}
    	
    	int maxOffsetX = MainGame.width - ((cards.size() + 2) * (CARD_WIDTH + 10)) + 10;
    	if (mX >= 0 && mX <= MainGame.width && mY <= MainGame.height && mY >= MainGame.height - 300) {
    		clickedOnHUD = true;
    		
        	if (_displayType == 0 && clicked && client.playerNumber() == currentPlayer && state.turn == client.playerNumber()) {
        		for (int i = 0; i < cards.size(); i++) {
    				int posX = (i * 205) + 420 + _offsetX;
    				if (mY >= MainGame.height - 300 && mY <= MainGame.height &&
    						posX <= mX && posX + 195 >= mX) {
    					if (!cards.get(i).played) {
    						client.playCard(currentPlayer, i);
    					}
    					break;
    				}
    			}
        	}
    		
        	if (rightMouseDown) {
				for (int i = 0; i < cards.size(); i++) {
    				int posX = (i * 205) + 420 + _offsetX;
    				if (mY >= MainGame.height - 300 && mY <= MainGame.height &&
    						posX <= mX && posX + 195 >= mX) {
						_drawBigPile = i;
						break;
					}
				}
        	}
        	else {
        		_drawBigPile = -1;
        	}
        	
        	if (leftMouseDown) {
    	    	if (_isMouseDown) {
    	    		int offX = mX - _prevMouseX;
    	    		_offsetX += offX;
    	    		if (_offsetX <= maxOffsetX) _offsetX = maxOffsetX;
    	    		if (_offsetX >= 0) _offsetX = 0;
    	    		_prevMouseX = mX;
    	    	}
    	    	else {
    	    		_isMouseDown = true;
    	    		_prevMouseX = mX;
    	    	}
        	}
        	else {
        		_isMouseDown = false;
        	}
    	}
    	
    	if (btnShop.isClicked()) {
    		sbg.enterState(MainGame.SHOPSCREEN);
    		clickedOnHUD = true;
    	}
    	else if (btnPlayer1.isClicked()) {
    		currentPlayer = 1;
    		_displayType = 0;
    		_offsetX = 0;
    		clickedOnHUD = true;
    	}
    	else if (btnPlayer2.isClicked()) {
    		currentPlayer = 2;
    		_displayType = 0;
    		_offsetX = 0;
    		clickedOnHUD = true;
    	}
    	else if (btnPlayer3.isClicked()) {
    		currentPlayer = 1;
    		_displayType = 0;
    		_offsetX = 0;
    		clickedOnHUD = true;
    	}
    	else if (btnPlayer4.isClicked()) {
    		currentPlayer = 1;
    		_displayType = 0;
    		_offsetX = 0;
    		clickedOnHUD = true;
    	}
    	else if (btnStats.isClicked()) {
    		sbg.enterState(MainGame.STATSSCREEN);
    		clickedOnHUD = true;
    	}
    	else if (btnHistory.isClicked()) {
    		sbg.enterState(MainGame.HISTORYSCREEN);
    		clickedOnHUD = true;
    	}
    	else if (btnEndTurn.isClicked()) {
    		client.endTurn();
    		clickedOnHUD = true;
    	}
    	else if (btnMansion.isClicked()) {
    		sbg.enterState(MainGame.MANSIONSCREEN);
    		clickedOnHUD = true;
    	}
    	else if (_spectating && btnSpectateOff.isClicked()) {
    		_spectating = false;
    	}
    	else if (!_spectating && btnSpectateOn.isClicked()) {
    		_spectating = true;
    	}
    	
    	if (client.getGameState() != null && client.getGameState().gameover) {
    		sbg.enterState(MainGame.GAMEOVERSCREEN);
    	}
    	
    	if (client.getGameState() != null) {
    		latestGameMessage = client.getGameState().latestGameMessage;
    		turnMessage = createTurnMessage(client);
    	}
    	
    	_drawBig = drawBig;
    	if (_spectating && client.gameState() != null && client.gameState().turn != client.playerNumber() && client.getSpectate() != null) {
    		_spectate = client.getSpectate();
    		_offsetX = _spectate.handOffsetX;
    		if (sbg.getCurrentStateID() != _spectate.screen) {
    			sbg.enterState(_spectate.screen);
    		}
    		_spectateMouseX = _spectate.mouseX;
    		_spectateMouseY = _spectate.mouseY;
    		_displayType = _spectate.handState;
    		_drawBig = _spectate.drawBig;
    		//_offsetY = _spectate.handOffsetY;
    	}
    	
    	_myTurn = client.gameState().turn == client.playerNumber();
    	if (_myTurn) {
    		SpectateState spectate = new SpectateState();
    		spectate.handOffsetX = _offsetX;
    		spectate.handOffsetY = 0;
    		spectate.mouseX = mX;
    		spectate.mouseY = mY;
    		spectate.screen = sbg.getCurrentStateID();
    		spectate.screenHeight = MainGame.height;
    		spectate.screenOffsetX = screenOffsetX;
    		spectate.screenOffsetY = screenOffsetY;
    		spectate.screenWidth = MainGame.width;
    		spectate.handState = _displayType;
    		spectate.drawBig = drawBig;
    		client.sendSpectateRequest(spectate);
    	}
    	
    	return clickedOnHUD;
    }
    
    @SuppressWarnings("unchecked")
	public static void init(GameState state) {
    	Image image = null;
    	try {
			image = new Image("resources/images/icons/hud_button_blank.png");
			CARD_BACK = new Image("resources/images/icons/DECK_BACK.png");
			MANSION_BACK = new Image("resources/images/icons/MANSION_BACK.png");
			DISCARD_PILE = new Image("resources/images/icons/discard_pile.png");
			ATTACHMENT_PILE = new Image("resources/images/icons/attachment_pile.png");
			HAND_PILE = new Image("resources/images/icons/hand_pile.png");
			CURSOR = new Image("resources/images/icons/cursor.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
    	btnShop = new Button(0, 1, 96, 20, image, "Shop");
    	int numPlayers = state.numberOfPlayers;
    	int windowY = MainGame.height - 320;
    	
    	String username = "";
    	if (numPlayers > 0) username = state.players[0].name;
    	username = username.length() >= 9 ? username.substring(0, 8) : username;
    	btnPlayer1 = new Button(100, windowY, 96, 20, image, username);
    	
    	username = "";
    	if (numPlayers > 1) username = state.players[1].name;
    	username = username.length() >= 9 ? username.substring(0, 8) : username;
    	btnPlayer2 = new Button(200, windowY, 96, 20, image, username);
    	
    	username = "";
    	if (numPlayers > 2) username = state.players[2].name;
    	username = username.length() >= 9 ? username.substring(0, 8) : username;
    	btnPlayer3 = new Button(300, windowY, 96, 20, image, username);
    	
    	username = "";
    	if (numPlayers > 3) username = state.players[3].name;
    	username = username.length() >= 9 ? username.substring(0, 8) : username;
    	btnPlayer4 = new Button(400, windowY, 96, 20, image, username);
    	
    	btnMansion = new Button(100, 1, 96, 20, image, "Mansion");
    	btnStats = new Button(200, 1, 96, 20, image, "Stats");
    	btnHistory = new Button(300, 1, 96, 20, image, "History");
    	btnEndTurn = new Button(400, 1, 96, 20, image, "End Turn");
    	
    	btnSpectateOn = new Button(MainGame.width - 100, MainGame.height - 320, 96, 20, image, "Watch On");
    	btnSpectateOff = new Button(MainGame.width - 100, MainGame.height - 320, 96, 20, image, "Watch Off");
    	
		try {
			//_fontHUD = new UnicodeFont("resources/fonts/bloodcrow.ttf", 14, true, false);
			_fontHUD = new UnicodeFont("resources/fonts/Designosaur-Regular.ttf", 14, true, false);
			_fontHUD.addAsciiGlyphs();
			_fontHUD.getEffects().add(new ColorEffect(java.awt.Color.white));  // Create a default white color effect
			_fontHUD.loadGlyphs();
		} catch (SlickException e) {
			Logger.logMessage("HUD Font Failed", "", e.getMessage(), "Font failed to load.");
		}
    }
    
    private static String createTurnMessage(GameClient client) {
    	if (client.getGameState() == null) {
    		return "";
    	}
    	
    	GameState state = client.getGameState();
    	StringBuilder builder = new StringBuilder();
    	Player player = state.players[state.turn-1];
    	
    	if (client.playerNumber() != state.turn) {
    		builder.append("It's ");
    		builder.append(player.name);
    		builder.append("'s turn. ");
    	}
    	else {
    		builder.append("It's your turn. ");
    	}
    	
    	builder.append("Actions: ");
    	builder.append(player.actions);
    	builder.append("; Buys: ");
    	builder.append(player.buys);
    	builder.append("; Explores: ");
    	builder.append(player.explores);
    	builder.append("; Ammo: ");
    	builder.append(player.ammo);
    	builder.append("; Gold: ");
    	builder.append(player.gold);
    	builder.append("; Damage: ");
    	builder.append(player.damage);
    	
    	return builder.toString();
    }
    
    private static void drawPlayer(GameContainer gc, StateBasedGame sbg, Graphics g, GameState state) {	
		int xStart = 10;
    	g.setColor(BACKCOLOR);
		g.fillRect(0, MainGame.height - 300, MainGame.width, 300);
		
		g.translate(_offsetX, 0);
		
		g.setColor(Color.white);
		
		switch (_displayType) {
			case 0:
				g.drawImage(DISCARD_PILE, 10, MainGame.height - 290, 205, MainGame.height - 290 + 280, 
						0, 0, DISCARD_PILE.getWidth(), DISCARD_PILE.getHeight());
				break;
			case 1:
				g.drawImage(ATTACHMENT_PILE, 10, MainGame.height - 290, 205, MainGame.height - 290 + 280, 
						0, 0, ATTACHMENT_PILE.getWidth(), ATTACHMENT_PILE.getHeight());
				break;
			case 2:
				g.drawImage(HAND_PILE, 10, MainGame.height - 290, 205, MainGame.height - 290 + 280, 
						0, 0, HAND_PILE.getWidth(), HAND_PILE.getHeight());
				break;
		}
		
		g.drawImage(CARD_BACK, 215, MainGame.height - 290, 410, MainGame.height - 290 + 280, 
				0, 0, CARD_BACK.getWidth(), CARD_BACK.getHeight());
		
		int player = currentPlayer - 1;
		ArrayList<Card> cards = null;
		
		switch (_displayType) {
			case 0:
				cards = state.players[player].hand;
				break;
			case 1:
				cards = state.players[player].discard;
				break;
			case 2:
				cards = state.players[player].attached;
				break;
		}		
				
		xStart = 420;
		int yStart = MainGame.height - 290;
		for (int i = 0; i < cards.size(); i++) { 
			Image image = CardHelper.cardImage(cards.get(i));
			if (_displayType == 0) {
				Color color = cards.get(i).played ? Color.red : Color.white;
				g.drawImage(image, xStart + ((CARD_WIDTH + 10) * i), yStart, xStart + ((CARD_WIDTH + 10) * i) + CARD_WIDTH, yStart + CARD_HEIGHT,
						0, 0, image.getWidth(), image.getHeight(), color);
			}
			else {
				g.drawImage(image, xStart + ((CARD_WIDTH + 10) * i), yStart, xStart + ((CARD_WIDTH + 10) * i) + CARD_WIDTH, yStart + CARD_HEIGHT,
						0, 0, image.getWidth(), image.getHeight());
			}
		}
		
		g.translate(-_offsetX, 0);
    }
}
