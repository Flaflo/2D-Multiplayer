package de.flaflo.game.entity;

import java.awt.Color;

public class PlayerMP extends Player {

	public static final int EXPECTED_MOVEMENT_SPEED = PlayerSP.MOVEMENT_SPEED;

	private int lastPosX, lastPosY;
	private int polPosX, polPosY;

	private boolean isMoving;

	public PlayerMP(String name, Color color, int x, int y, int width, int height) {
		super(name, color, x, y, width, height);
	}

	public void interpolateToPosition(int x, int y) {
		isMoving = true;
		
		polPosX = x;
		polPosY = y;
	}

	@Override
	public void render() {
		super.render();

		if (isMoving) {
			if (this.getX() == polPosX && this.getY() == polPosY) {
				isMoving = false;
		
				return;
			}
			
			if (this.getX() < polPosX)
				this.setX(this.getX() + EXPECTED_MOVEMENT_SPEED);
			else if (this.getX() > polPosX)
				this.setX(this.getX() - EXPECTED_MOVEMENT_SPEED);
			if (this.getY() < polPosY)
				this.setY(this.getY() + EXPECTED_MOVEMENT_SPEED);
			else if (this.getY() > polPosY)
				this.setY(this.getY() - EXPECTED_MOVEMENT_SPEED);
		}
	}

	/**
	 * @return the polPosX
	 */
	public int getPolPosX() {
		return polPosX;
	}

	/**
	 * @param polPosX
	 *            the polPosX to set
	 */
	public void setPolPosX(int polPosX) {
		this.polPosX = polPosX;
	}

	/**
	 * @return the polPosY
	 */
	public int getPolPosY() {
		return polPosY;
	}

	/**
	 * @param polPosY
	 *            the polPosY to set
	 */
	public void setPolPosY(int polPosY) {
		this.polPosY = polPosY;
	}

	/**
	 * @return the lastPosX
	 */
	public int getLastPosX() {
		return lastPosX;
	}

	/**
	 * @param lastPosX
	 *            the lastPosX to set
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
	 * @param lastPosY
	 *            the lastPosY to set
	 */
	public void setLastPosY(int lastPosY) {
		this.lastPosY = lastPosY;
	}
}
