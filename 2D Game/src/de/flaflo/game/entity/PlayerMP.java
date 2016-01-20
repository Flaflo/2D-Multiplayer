package de.flaflo.game.entity;

import java.awt.Color;

public class PlayerMP extends Player {

	private int lastPosX, lastPosY;
	
	public PlayerMP(String name, Color color, int x, int y, int width, int height) {
		super(name, color, x, y, width, height);
	}

	/**
	 * @return the lastPosX
	 */
	public int getLastPosX() {
		return lastPosX;
	}

	/**
	 * @param lastPosX the lastPosX to set
	 */
	public void setLastPosX(int lastPosX) {
		this.lastPosX = lastPosX;
	}

	/**
	 * @return the lastPosY
	 */
	public int getLastPosY() {
		return lastPosY;
	}

	/**
	 * @param lastPosY the lastPosY to set
	 */
	public void setLastPosY(int lastPosY) {
		this.lastPosY = lastPosY;
	}
}
