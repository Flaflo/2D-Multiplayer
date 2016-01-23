package de.flaflo.game.networking.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {

	protected byte id;
	
	/**
	 * @param id
	 */
	public Packet(byte id) {
		this.id = id;
	}
	
	public abstract void send(DataOutputStream out) throws IOException;
	public abstract void receive(DataInputStream in) throws IOException;
	
	/**
	 * @return the id
	 */
	public byte getId() {
		return id;
	}
}
