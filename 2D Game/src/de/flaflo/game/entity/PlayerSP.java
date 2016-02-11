package de.flaflo.game.entity;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.flaflo.game.Game;
import de.flaflo.game.input.Keyboard;

public class PlayerSP extends Player {

	public static String PLAYER_NAME = "Randy" + Math.abs(Game.RANDOM.nextInt());

	public static final int MOVEMENT_SPEED = 2;

	public PlayerSP(String name, Color color, int x, int y, int width, int height) {
		super(name, color, x, y, width, height);
	}

	private void tryMove() {
		if (Keyboard.isKeyPressed(KeyEvent.VK_W)) {
			this.setY(this.getY() - MOVEMENT_SPEED);
			Game.getGame().getCamera().moveBy(0, -1);
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_S)) {
			this.setY(this.getY() + MOVEMENT_SPEED);
			Game.getGame().getCamera().moveBy(0, 1);
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_A)) {
			this.setX(this.getX() - MOVEMENT_SPEED);
			Game.getGame().getCamera().moveBy(-1, 0);
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_D)) {
			this.setX(this.getX() + MOVEMENT_SPEED);
			Game.getGame().getCamera().moveBy(1, 0);
		}
	}

	@Override
	public void update(double delta) {
		super.update(delta);

		tryMove();
	}
}
