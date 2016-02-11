package de.flaflo.game.networking.packets;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class C01PacketLogin extends Packet {
	
	private String name;
	private Color color;
	
	private int x, y;
	
	private int mass;
	
	public C01PacketLogin(String name, Color color, int mass, int x, int y) {
		super((byte) 1);
		
		this.name = name;
		this.color = color;
		this.x = x;
		this.y = y;
	}

	@Override
	public void send(DataOutputStream out) throws IOException {
		out.writeUTF(name);
		out.writeInt(x);
		out.writeInt(y);
		
		out.writeInt(color.getRed());
		out.writeInt(color.getGreen());
		out.writeInt(color.getBlue());
		
		out.writeInt(mass);
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

	/**
	 * @return the mass
	 */
	public int getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(int mass) {
		this.mass = mass;
	}
}
