package de.flaflo.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * 
 * @author Flaflo
 *
 *         Keyboard Input Management
 */
public class Keyboard implements KeyListener {

	private static boolean[] keys;
	private static ArrayList<IKeyListener> listeners;

	public Keyboard() {
		init();
	}

	public void init() {
		keys = new boolean[10000];
		listeners = new ArrayList<IKeyListener>();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		setKeyPressed(e.getKeyCode());

		for (IKeyListener l : getListeners())
			l.onKeyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		releaseKey(e.getKeyCode());

		for (IKeyListener l : getListeners())
			l.onKeyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		setKeyPressed(e.getKeyCode());

		for (IKeyListener l : getListeners())
			l.onKeyTyped(e);
	}

	public static void releaseKey(int key) {
		keys[key] = false;
	}

	public static void setKeyPressed(int key) {
		keys[key] = true;
	}

	public static boolean isKeyPressed(int key) {
		return keys[key];
	}

	public static void registerListener(IKeyListener listener) {
		listeners.add(listener);
	}

	public static void unregisterListener(IKeyListener listener) {
		listeners.remove(listener);
	}

	public ArrayList<IKeyListener> getListeners() {
		return new ArrayList<IKeyListener>(listeners);
	}

}
