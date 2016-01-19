package de.flaflo.game.input;

/**
 * 
 * @author Flaflo
 *
 */
public interface IMouseListener {

	public void mouseMoved(int x, int y);
	
	public void mouseDragged(int x, int y);
	
	public void mouseClicked(int x, int y, int button);

	public void mousePressed(int x, int y, int button);
	
	public void mouseReleased(int x, int y, int button);
	
	public void mouseWheelMoved(int rotation);
	
}
