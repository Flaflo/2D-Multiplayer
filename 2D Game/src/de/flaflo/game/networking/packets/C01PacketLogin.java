package de.flaflo.game.networking.packets;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.flaflo.game.Game;

public class C01PacketLogin extends Packet {
	
	private String name;
	private Color color;
	
	public C01PacketLogin(String name, Color color) {
		super((byte) 1);
		
		this.name = name;
		this.color = color;
	}

	@Override
	public void send(DataOutputStream out) throws IOException {
		out.writeUTF(name);
		out.writeInt(Game.getGame().getPlayer().getX());
		out.writeInt(Game.getGame().getPlayer().getY());
		
		out.writeInt(color.getRed());
		out.writeInt(color.getGreen());
		out.writeInt(color.getBlue());
	}

	@Override
	public void receive(DataInputStream in) { }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
