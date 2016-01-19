package de.flaflo.game.entity;

import java.awt.Color;

import de.flaflo.game.graphics.RenderHelper;

public abstract class Player {

	public static final int PLAYER_WIDTH = 12;
	public static final int PLAYER_HEIGHT = 12;
	
	public static final Color PLAYER_COLOR = Color.GREEN;
	
	private Color color;

	private String name;
	
	private int x, y, width, height;
	
	public Player(String name, Color color, int x, int y, int width, int height) {
		this.name = name;
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void render() {
		RenderHelper.drawRect(x, y, width, height, color);
		RenderHelper.drawOutlineRect(x, y, width - 1, height - 1, color.darker());
		RenderHelper.drawString(this.name, x - (int) (RenderHelper.getStringWidth(this.name) / 2) - 1, y - 4, Color.WHITE);
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
		return width;
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
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
