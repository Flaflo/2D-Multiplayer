package de.flaflo.game.networking.packets;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.flaflo.game.entity.Player;
import de.flaflo.game.entity.PlayerMP;

public class C02PacketPlayerList extends Packet {

	private PlayerMP[] players; 
	
	public C02PacketPlayerList() {
		super((byte) 2);
	}

	@Override
	public void send(DataOutputStream out) throws IOException { }

	@Override
	public void receive(DataInputStream in) throws IOException {
		int size = in.readInt();
		
		players = new PlayerMP[size];

		for (int i = 0; i < size; i++) {
			String pName = in.readUTF();
			int id = in.readInt();
			int pX = in.readInt();
			int pY = in.readInt();
			
			int cRed = in.readInt();
			int cGreen = in.readInt();
			int cBlue = in.readInt();
			
			players[i] = new PlayerMP(id, pName, new Color(cRed, cGreen, cBlue), pX, pY, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
		}
	}

	/**
	 * @return the players
	 */
	public PlayerMP[] getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(PlayerMP[] players) {
		this.players = players;
	}

}
