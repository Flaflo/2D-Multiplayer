package de.flaflo.game.entity;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import de.flaflo.game.graphics.RenderHelper;

public abstract class Player {

	public static final int START_MASS = 5;
	
	public static final int PLAYER_WIDTH = 12;
	public static final int PLAYER_HEIGHT = 12;
	
	public static Color PLAYER_COLOR = Color.GREEN;
	
	private Color color;

	private String name;
	
	private float x, y, width, height;
	
	private Rectangle2D boundingBox;
	
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

		updateBoundingBox();
		
		this.mass = START_MASS;
	}

	/**
	 * Rendert den Spieler auf den Screen
	 */
	public void render() {
		RenderHelper.drawRect(new Rectangle2D.Float(getX(), getY(), getWidth(), getHeight()), color);
		RenderHelper.drawOutlineRect(new Rectangle2D.Float(getX(), getY(), getWidth() - 1, getHeight() - 1), color.darker());
		RenderHelper.drawString(this.name, getX() + getWidth() / 2 - (int) (RenderHelper.getStringWidth(this.name) / 2) - 1, getY() - 4, Color.WHITE);
	}
	
	public void update(double delta) { 
		updateBoundingBox();
	}
	
	/**
	 * Gibt zurück, ob der Spieler mit einem anderen Spieler kollidiert
	 * @param player Spieler zum testen
	 * @return Kollidiert Spieler a mit Spieler b
	 */
	public boolean isCollidingWith(Player player) {
		return player.getBoundingBox().intersects(boundingBox);
	}
	
	private void updateBoundingBox() {
		boundingBox = new Rectangle2D.Float(x, y, width, height);
	}
	
	/**
	 * @return the boundingBox
	 */
	public Rectangle2D getBoundingBox() {
		return boundingBox;
	}

	/**
	 * @param boundingBox the boundingBox to set
	 */
	public void setBoundingBox(Rectangle2D boundingBox) {
		this.boundingBox = boundingBox;
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
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width + mass;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height + mass;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}
}
