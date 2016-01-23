package de.flaflo.game.networking.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.flaflo.game.Game;
import de.flaflo.game.entity.PlayerMP;

public class C05PacketLeave extends Packet {

	private PlayerMP player;
	
	public C05PacketLeave() {
		super((byte) 5);
	}

	@Override
	public void send(DataOutputStream out) throws IOException {
		out.writeByte(id);
	}

	@Override
	public void receive(DataInputStream in) throws IOException { 
		int id = in.readInt();
		
		this.setPlayer(Game.getGame().getPlayerByID(id));
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
