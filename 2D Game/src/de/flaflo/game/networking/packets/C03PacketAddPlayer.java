package de.flaflo.game.networking.packets;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.flaflo.game.entity.Player;
import de.flaflo.game.entity.PlayerMP;

public class C03PacketAddPlayer extends Packet {

	private PlayerMP player;
	
	public C03PacketAddPlayer() {
		super((byte) 3);
	}

	@Override
	public void send(DataOutputStream out) throws IOException { }

	@Override
	public void receive(DataInputStream in) throws IOException {
		String toAddName = in.readUTF();
		int id = in.readInt();
		int toAddX = in.readInt();
		int toAddY = in .readInt();
		
		int toAddRed = in.readInt();
		int toAddGreen = in.readInt();
		int toAddBlue = in.readInt();
		
		this.setPlayer(new PlayerMP(id, toAddName, new Color(toAddRed, toAddGreen, toAddBlue), toAddX, toAddY, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT));
	}

	/**
	 * @return the player
	 */
	public PlayerMP getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(PlayerMP player) {
		this.player = player;
	}

}
