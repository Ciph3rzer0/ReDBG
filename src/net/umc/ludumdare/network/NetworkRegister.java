package net.umc.ludumdare.network;

import java.util.ArrayList;
import java.util.Hashtable;

import com.esotericsoftware.kryo.Kryo;

public class NetworkRegister {

	public static void RegisterNetworkObjects(Kryo kryo) {
		kryo.register(String.class);
		kryo.register(int.class);
		kryo.register(ArrayList.class);
		kryo.register(boolean.class);
		kryo.register(Integer.class);
		kryo.register(Hashtable.class);
		
		kryo.register(BuyCardRequest.class);
		kryo.register(Card.class);
		kryo.register(CardPlayedResponse.class);
		kryo.register(ChatRequest.class);
		kryo.register(ChatResponse.class);
		kryo.register(EndTurnRequest.class);
		kryo.register(ExploreRequest.class);
		kryo.register(GameMessageResponse.class);
		kryo.register(GameOverResponse.class);
		kryo.register(GameStartedResponse.class);
		kryo.register(GameState.class);
		kryo.register(JoinRequest.class);
		kryo.register(JoinResponse.class);
		kryo.register(Pile.class);
		kryo.register(Pile[].class);
		kryo.register(PlayCardRequest.class);
		kryo.register(Player.class);
		kryo.register(Player[].class);
		kryo.register(PlayerListResponse.class);
		kryo.register(RoomSizeResponse.class);
		kryo.register(SpectateState.class);
		
		

	}
	
}
