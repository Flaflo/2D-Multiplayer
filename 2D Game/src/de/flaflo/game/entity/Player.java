package de.flaflo.game.entity;

import java.awt.Color;

import de.flaflo.game.graphics.RenderHelper;

public abstract class Player {

	public static final int START_MASS = 5;
	
	public static final int PLAYER_WIDTH = 12;
	public static final int PLAYER_HEIGHT = 12;
	
	public static Color PLAYER_COLOR = Color.GREEN;
	
	private Color color;

	private String name;
	
	private int x, y, width, height;
	
	private int mass;
	
	/**
	 * @param name Name, der über dem Spieler angezeigt wird
	 * @param color
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Player(String name, Color color, int x, int y, int width, int height) {
		this.name = name;
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.mass = START_MASS;
	}

	/**
	 * Rendert den Spieler auf den Screen
	 */
	public void render() {
		RenderHelper.drawRect(getX(), getY(), getWidth(), getHeight(), color);
		RenderHelper.drawOutlineRect(getX(), getY(), getWidth() - 1, getHeight() - 1, color.darker());
		RenderHelper.drawString(this.name, getX() + getWidth() / 2 - (int) (RenderHelper.getStringWidth(this.name) / 2) - 1, getY() - 4, Color.WHITE);
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
	 * @return the width
	 */
	public int getWidth() {
		return width + mass;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height + mass;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
