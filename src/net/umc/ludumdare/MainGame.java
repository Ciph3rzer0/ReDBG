package net.umc.ludumdare;

import java.util.Hashtable;

import net.umc.ludumdare.network.GameClient;
import net.umc.ludumdare.states.ConnectionScreen;
import net.umc.ludumdare.states.GameOverScreen;
import net.umc.ludumdare.states.HistoryScreen;
import net.umc.ludumdare.states.LobbyScreen;
import net.umc.ludumdare.states.MansionScreen;
import net.umc.ludumdare.states.ShopScreen;
import net.umc.ludumdare.states.StatsScreen;
import net.umc.ludumdare.utils.CardHelper;
import net.umc.ludumdare.utils.ConfigurationHelper;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
 
public class MainGame extends StateBasedGame {
 
	public static final int CONNECTIONSCREENSTATE = 0;
    public static final int LOBBYSTATE = 1;
    public static final int SHOPSCREEN = 2;
    public static final int STATSSCREEN = 3;
    public static final int MANSIONSCREEN = 4;
    public static final int GAMEOVERSCREEN = 5;
    public static final int HISTORYSCREEN = 6;
    public static Hashtable<String, String> configuration;
    public static GameClient client;
    public static int width = 1024;
    public static int height = 768;
    public static boolean fullscreen = false;
    public static int FPS = 60;
    
    public MainGame()
    {
        super("Resident Evil - Deck Building Game");
        CardHelper.init();
        
        client = new GameClient();
    }
 
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new MainGame());
         
         configuration = ConfigurationHelper.loadConfiguration("resources/data/client.config");
         
         if (configuration.containsKey("Width")) {
        	 width = Integer.parseInt(configuration.get("Width"));
        	 width = width > 0 ? width : 1024;
         }
         
         if (configuration.containsKey("Height")) {
        	 height = Integer.parseInt(configuration.get("Height"));
        	 height = height > 0 ? height : 768;
         }
         
         if (configuration.containsKey("FPS")) {
        	 FPS = Integer.parseInt(configuration.get("FPS"));
        	 FPS = FPS > 0 ? FPS : 60;
         }
         
         if (configuration.containsKey("Fullscreen")) {
        	 fullscreen = Boolean.parseBoolean(configuration.get("Fullscreen"));
         }
         
         app.setDisplayMode(width, height, fullscreen);
         app.setTargetFrameRate(FPS);
         app.start();
    }
 
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
    	this.addState(new ConnectionScreen(CONNECTIONSCREENSTATE));
    	this.addState(new LobbyScreen(LOBBYSTATE));
    	this.addState(new ShopScreen(SHOPSCREEN));
    	this.addState(new StatsScreen(STATSSCREEN));
    	this.addState(new MansionScreen(MANSIONSCREEN));
    	this.addState(new GameOverScreen(GAMEOVERSCREEN));
    	this.addState(new HistoryScreen(HISTORYSCREEN));
    }
}