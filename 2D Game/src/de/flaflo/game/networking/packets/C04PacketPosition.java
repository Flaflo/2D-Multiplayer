package de.flaflo.game.networking.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.flaflo.game.Game;
import de.flaflo.game.entity.PlayerMP;

public class C04PacketPosition extends Packet {

	private PlayerMP player;
	private int x, y;
	
	public C04PacketPosition(int x, int y) {
		super((byte) 4);
	
		this.x = x;
		this.y = y;
	}
	
	public C04PacketPosition() {
		super((byte) 4);
	}
	
	@Override
	public void send(DataOutputStream out) throws IOException {
		out.writeByte(id);
		out.writeInt(x);
		out.writeInt(y);
	}

	@Override
	public void receive(DataInputStream in) throws IOException {
		String toUpdateName = in.readUTF();
		int toUpdateX = in.readInt();
		int toUpdateY = in.readInt();
		
		this.setPlayer(Game.getGame().getPlayerByName(toUpdateName));
		this.setX(toUpdateX);
		this.setY(toUpdateY);
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

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}
