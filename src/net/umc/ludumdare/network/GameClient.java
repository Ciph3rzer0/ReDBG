package net.umc.ludumdare.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import net.umc.ludumdare.MainGame;
import net.umc.ludumdare.states.GameHUD;
import net.umc.ludumdare.utils.Logger;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameClient {

	private GameState _state;
	private Client _client;
	private ArrayList<String> _players;
	private String _username;
	private String _connectMessage;
	private String _latestGameMessage;
	private boolean _connected, _connecting, _failedToConnect;
	private int _roomSize = 0;
	private boolean _gameStarted;
	private int _playerNumber = -1;
	private SpectateState _spectate;
	private ArrayList<CardPlayedResponse> _gameHistory;
	private int _maxHistory;
	
	private static final Random _random = new Random();
	
	public GameClient() {
		_client = new Client(16384, 4096);
	}
	
	public void connect() {
		if (_connected || _connecting) {
			return;
		}
		_client.start();
		_connecting = true;
		
		Hashtable<String, String> config = MainGame.configuration;
		int port = config.containsKey("Port") ? Integer.parseInt(config.get("Port")) : 3000;
		int udpPort = config.containsKey("UDPPort") ? Integer.parseInt(config.get("UDPPort")) : 31337;
		String ip = config.containsKey("IP") ? config.get("IP") : "192.168.2.4";
		_username = config.containsKey("Username") ? config.get("Username") : "Guest" + _random.nextInt(1000);
		_connected = false;
		_maxHistory = (MainGame.height - 360) / 20;
		_gameHistory = new ArrayList<CardPlayedResponse>();
		
		NetworkRegister.RegisterNetworkObjects(_client.getKryo());
		
		try {
			_client.connect(5000, ip, port, udpPort);
		} catch (IOException e) {
			String stackMessage = "\n";
			for (StackTraceElement element : e.getStackTrace()) {
				stackMessage += "\t" + element.toString() + "\n";
			}
			Logger.logMessage("Client Failed to Connect", stackMessage, e.getMessage(), 
					String.format("\n\tIP Address: %s\n\tPort: %s", ip, port));
		}
		
	    _client.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	        	
	           if (object instanceof GameState) {
	        	   _state = (GameState)object;
	           }
	           else if (object instanceof SpectateState) {
	        	   _spectate = (SpectateState)object;       	   
	           }
	           else if (object instanceof CardPlayedResponse) {
	        	   CardPlayedResponse response = (CardPlayedResponse)object;
	        	   if (_gameHistory.size() >= _maxHistory) {
	        		   _gameHistory.remove(0);
	        	   }
	        	   _gameHistory.add(response);
	           }
	           else if (object instanceof ChatResponse) {
	              ChatResponse response = (ChatResponse)object;
	              GameHUD.addChatMessage(response.message);
	           }
	           else if (object instanceof PlayerListResponse) {
	        	   PlayerListResponse response = (PlayerListResponse)object;
	        	   _players = response.players;
	           }
	           else if (object instanceof JoinResponse) {
	        	   _connecting = false;
	        	   JoinResponse response = (JoinResponse)object;
	        	   _connected = response.success;
	        	   _failedToConnect = !response.success;
	        	   _connectMessage = response.message;
	           }
	           else if (object instanceof RoomSizeResponse) {
	        	   _roomSize = ((RoomSizeResponse)object).roomSize;
	        	   GameHUD.numberOfPlayers = _roomSize;
	           }
	           else if (object instanceof GameOverResponse) {
	        	   _state.gameover = ((GameOverResponse)object).gameover;
	           }
	           else if (object instanceof GameStartedResponse) {
	        	   _gameStarted = ((GameStartedResponse)object).gameStarted;
	           }
	        }
	     });
	    
	    JoinRequest request = new JoinRequest();
	    request.username = _username;
	    _client.sendTCP(request);
	}
	
	public boolean isConnected() {return _connected;}
	public boolean isConnecting() {return _connecting;}
	public boolean failedToConnect() {return _failedToConnect;}
	public String connectMessage() {return _connectMessage == null ? "" : _connectMessage;}
	public ArrayList<String> playerList() {return _players == null ? (_players = new ArrayList<String>()) : _players;}
	public int roomSize() {return _roomSize;};
	public GameState gameState() {return _state;}
	public boolean gameStarted() {return _gameStarted;}
	public int playerNumber() { return _playerNumber = (_playerNumber == -1 ? findPlayerNumber() : _playerNumber); }
	public String getGameMessage() { return _latestGameMessage; }
	public GameState getGameState() { return _state; }
	public String getUsername() { return _username;}
	public SpectateState getSpectate() { return _spectate; }
	public ArrayList<CardPlayedResponse> getHistory() { return _gameHistory; }
	
	public void playCard(int player, int handPos) { PlayCardRequest request = new PlayCardRequest(); request.player = player; request.handPos = handPos; _client.sendTCP(request); }
	public void endTurn() { EndTurnRequest request = new EndTurnRequest(); request.endTurn = true; request.player = playerNumber(); _client.sendTCP(request);}
	public void sendChat(String message) { ChatRequest request = new ChatRequest(); request.message = message; _client.sendTCP(request); }
	public void buyCard(int pile, int playerNumber) { BuyCardRequest request = new BuyCardRequest(); request.player = playerNumber; request.shopPile = pile; _client.sendTCP(request); }
	public void explore() { ExploreRequest request = new ExploreRequest(); request.player = playerNumber(); _client.sendTCP(request); }
	public void sendSpectateRequest(SpectateState state) { _client.sendUDP(state); }
	
	private int findPlayerNumber() { for (Player player : _state.players) { if (player.name.equals(_username)) return player.id; } return -1; }
}
