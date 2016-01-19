package de.flaflo.game.main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.flaflo.game.Game;

/**
 * Main-Start
 * @author Flaflo
 *
 */
public class Main {

	public static void main(String[] args) {
		initWindow();
	}
	
	/**
	 * Initialisiert das JFrame
	 */
	private static void initWindow() {
		Game.PLAYER_NAME = JOptionPane.showInputDialog("Wähle einen Spielernamen: ", "Randy" + Math.abs(Game.RANDOM.nextInt()));
		
		JFrame mainFrame = new JFrame(Game.TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setContentPane(new Game());
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	/**
	 * Gibt einen Text im Logformat an die Konsole
	 * @param text Text der ausgegeben wird
	 */
	public static void log(String text) {
		System.out.println("[Game]: " + text);
	}

}
