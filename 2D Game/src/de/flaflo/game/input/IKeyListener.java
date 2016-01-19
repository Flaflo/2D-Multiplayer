package de.flaflo.game.input;

import java.awt.event.KeyEvent;

/**
 * 
 * @author Flaflo
 *
 */
public interface IKeyListener {
	
	public void onKeyPressed(KeyEvent e);

	public void onKeyReleased(KeyEvent e);

	public void onKeyTyped(KeyEvent e);
	
}
