package de.flaflo.game.input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

/**
 * 
 * @author Flaflo
 * 
 * Basic Mouse Listener
 * 
 */
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static Point mouseLocation;
	
	private static boolean[] keys;
	private static ArrayList<IMouseListener> listeners;
	
	public static final int MOUSE_LEFT = 1;
	public static final int MOUSE_RIGHT = 2;
	public static final int MOUSE_MIDDLE = 3;

	public Mouse() {
		init();
	}

	public void init() {
		mouseLocation = new Point(0, 0);
		
		keys = new boolean[12];
		listeners = new ArrayList<IMouseListener>();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		setButtonDown(arg0.getButton());
		
		for (IMouseListener l : getListeners())
			l.mouseClicked(arg0.getX(), arg0.getY(), arg0.getButton());
		
		releaseButton(arg0.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { }
	
	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		setButtonDown(arg0.getButton());
		
		for (IMouseListener l : getListeners())
			l.mousePressed(arg0.getX(), arg0.getY(), arg0.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		releaseButton(arg0.getButton());
		
		for (IMouseListener l : getListeners())
			l.mouseReleased(arg0.getX(), arg0.getY(), arg0.getButton());
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseLocation = arg0.getPoint();
		
		for (IMouseListener l : getListeners())
			l.mouseDragged(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mouseLocation = arg0.getPoint();
		
		for (IMouseListener l : getListeners())
			l.mouseMoved(arg0.getX(), arg0.getY());
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		for (IMouseListener l : getListeners())
			l.mouseWheelMoved(arg0.getWheelRotation());
	}

	public static Point getMousePosition() {
		return new Point(mouseLocation.x, mouseLocation.y);
	}
	
	public static boolean isButtonDown(int button) {
		return keys[button];
	}

	public static void setButtonDown(int button) {
		keys[button] = true;
	}

	public static void releaseButton(int button) {
		keys[button] = false;
	}
	
	public static void registerListener(IMouseListener listener) {
		listeners.add(listener);
	}
	
	public static void unregisterListener(IMouseListener listener) {
		listeners.remove(listener);
	}

	public static ArrayList<IMouseListener> getListeners() {
		return new ArrayList<IMouseListener>(listeners);
	}

}
