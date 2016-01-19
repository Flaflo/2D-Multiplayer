package de.flaflo.game.entity;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.flaflo.game.input.IKeyListener;
import de.flaflo.game.input.Keyboard;

public class PlayerSP extends Player implements IKeyListener {

	public static final int MOVEMENT_SPEED = 2;
	
	public PlayerSP(String name, Color color, int x, int y, int width, int height) {
		super(name, color, x, y, width, height);
		
		Keyboard.registerListener(this);
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		tryMove(e.getKeyCode());
	}

	@Override
	public void onKeyReleased(KeyEvent e) {
		tryMove(e.getKeyCode());
	}

	@Override
	public void onKeyTyped(KeyEvent e) {
		tryMove(e.getKeyCode());
		
	}
	
	private void tryMove(int key) {
		switch (key) {
		case KeyEvent.VK_W:
			this.setY(this.getY() - MOVEMENT_SPEED);
			break;
		case KeyEvent.VK_S:
			this.setY(this.getY() + MOVEMENT_SPEED);
			break;
		case KeyEvent.VK_A:
			this.setX(this.getX() - MOVEMENT_SPEED);
			break;
		case KeyEvent.VK_D:
			this.setX(this.getX() + MOVEMENT_SPEED);
			break;
		}
	}

}
