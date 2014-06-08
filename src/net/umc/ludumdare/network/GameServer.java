package net.umc.ludumdare.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import net.umc.ludumdare.utils.CardHelper;
import net.umc.ludumdare.utils.ConfigurationHelper;
import net.umc.ludumdare.utils.Logger;
import net.umc.ludumdare.utils.ServerHelper;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServer implements Runnable {

	private boolean _isRunning, _gameover;
	private Server _server;
	private int _roomSize;
	private int _port, _udpPort;
	private GameState _state;
	private Hashtable<String, String> _config;
	
	public static void main(String[] args) {
		new GameServer();
	}

	public GameServer() {
		_server = new Server(16384, 4096){
            protected Connection newConnection () {
                return new GameConnection();
            }
		};
		
		Thread gameThread = new Thread(this);
		init();
		_server.start();
		
		NetworkRegister.RegisterNetworkObjects(_server.getKryo());
		
		try {
			_server.bind(_port, _udpPort);
		} catch (IOException e) {
			String stackMessage = "\n";
			for (StackTraceElement element : e.getStackTrace()) {
				stackMessage += "\t" + element.toString() + "\n";
			}
			Logger.logMessage("Server Start Failed", stackMessage, e.getMessage(), null);
		}
		
	    _server.addListener(new Listener() {
	        public void received (Connection con, Object object) {
	        	GameConnection connection = (GameConnection)con;
	           if (object instanceof ChatRequest) {
	              ChatRequest request = (ChatRequest)object;
	              System.out.println(request.message);

	              ChatResponse response = new ChatResponse();
	              response.message = connection.username + ": " + request.message;
	              _server.sendToAllTCP(response);
	           }
	           else if (object instanceof SpectateState) {
	        	   _server.sendToAllUDP(object);
	           }
	           
	           if (_state != null && _state.playerResponse) {
	        	   //if (object instanceof )
	        	   
	        	   return;
	           }
	           
	           if (object instanceof PlayCardRequest) {
	        	   PlayCardRequest request = (PlayCardRequest)object;
	        	   
	        	   if (request.player == _state.turn) {
	        		   CardHelper.playCard(_state, request.player - 1, request.handPos);
	        	   }
	           }
	           else if (object instanceof BuyCardRequest) {
	        	   BuyCardRequest request = (BuyCardRequest)object;
	        	   if (request.player != _state.turn) return;
	        	   if (_state.shopPiles[request.shopPile].cards.size() <= 0) return;
	        	   if (_state.players[request.player - 1].buys <= 0) return;
	        	   Card card = _state.shopPiles[request.shopPile].cards.get(0);
	        	   if (CardHelper.cardCost(card.cardImage) > _state.players[request.player - 1].gold) return;
	        	   _state.players[request.player - 1].discard.add(_state.shopPiles[request.shopPile].cards.remove(0));
	        	   _state.latestGameMessage = _state.players[request.player - 1].name + " bought " + CardHelper.cardName(card.cardImage);
	        	   _state.players[request.player - 1].buys --;
	        	   _state.players[request.player - 1].gold -= CardHelper.cardCost(card.cardImage);
	           }
	           else if (object instanceof EndTurnRequest) {
	        	   EndTurnRequest request = (EndTurnRequest)object;
	        	   if (request.player != _state.turn) return;
	        	   if (request.endTurn) CardHelper.endTurn(_state);
	           }
	           else if (object instanceof ExploreRequest) {
	        	   ExploreRequest request = (ExploreRequest)object;
	        	   if (request.player != _state.turn) return;
	        	   if (_state.players[_state.turn - 1].explores > 0) {
	        		   CardHelper.explore(_state, request.player - 1);
	        	   }
	           }
	           else if (object instanceof JoinRequest) {
	        	   JoinResponse response = new JoinResponse();
	        	   String username = ((JoinRequest)object).username;
	        	   if (_server.getConnections().length > _roomSize) {
	        		   response.message = "Room full";
	        		   response.success = false;
	        		   connection.sendTCP(response);
	        		   connection.close();
	        		   System.out.println(username + " tried to join the game, but room was full.");
	        		   return;
	        	   }
	        	   else if (!uniqueName(username, connection)) {
	        		   response.message = "Username \"" + username + "\" is in use, failed to connect";
	        		   response.success = false;
	        		   connection.sendTCP(response);
	        		   connection.close();
	        		   System.out.println(username + " tried to join the game, but a user with that name was alredy in game.");
	        		   return;
	        	   }
	        	   connection.username = username;
	        	   response.message = "Connected.";
	        	   response.success = true;
	        	   System.out.println(username + " has connected.");
	        	   connection.sendTCP(response);
	        	   RoomSizeResponse roomsize = new RoomSizeResponse();
	        	   roomsize.roomSize = _roomSize;
	        	   connection.sendTCP(roomsize);
    	   		   PlayerListResponse playerList = new PlayerListResponse();
    	   		   playerList.players = getPlayersList();
	        	   _server.sendToAllTCP(playerList);
	        	   
	        	   if (roomsize.roomSize == playerList.players.size()) {
	        		   _state = ServerHelper.createGame(getPlayersList(), _config);
	        		   _server.sendToAllTCP(_state);
	        		   _isRunning = true;
	        		   GameStartedResponse gameStartedResponse = new GameStartedResponse();
	        		   gameStartedResponse.gameStarted = true;
	        		   _server.sendToAllTCP(gameStartedResponse);
	        	   }
	           }
	        }
	     });
	    System.out.println("Server Started");
	    
	    gameThread.start();
	    
	    while (true) {
	    	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	}

	private boolean uniqueName(String username, GameConnection current) {
		for (Connection con : _server.getConnections()) {
			GameConnection connection = (GameConnection)con;
			if (current == connection) continue;
			if (connection.username.equals(username)) return false;
		}
		return true;
	}
	
	private void init() {
		CardHelper.init();
		_config = ConfigurationHelper.loadConfiguration("resources/data/server.config");
		_roomSize = _config.containsKey("RoomSize") ? Integer.parseInt(_config.get("RoomSize")) : 0;
		_port = _config.containsKey("Port") ? Integer.parseInt(_config.get("Port")) : 3000;
		_udpPort = _config.containsKey("UDPPort") ? Integer.parseInt(_config.get("UDPPort")) : 31337;
		_isRunning = false;
		_gameover = false;
	}
	
	private ArrayList<String> getPlayersList() {
		ArrayList<String> players = new ArrayList<String>();
		for (Connection con : _server.getConnections()) {
			GameConnection connection = (GameConnection)con;
			players.add(connection.username);
		}
		return players;
	}
	
	public void sendGameMessage(String message) {
		GameMessageResponse response = new GameMessageResponse(); 
		response.message = message; 
		_server.sendToAllTCP(response); 
	}
	
	
	static class GameConnection extends Connection {
		public String username;
	}

	@Override
	public void run() {
		while (!_gameover) {
			while (_isRunning) {
				_server.sendToAllTCP(_state);
				
				if (_state.gameover) {
					GameOverResponse response = new GameOverResponse();
					response.gameover = true;
					_server.sendToAllTCP(response);
					_gameover = true;
					break;
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
