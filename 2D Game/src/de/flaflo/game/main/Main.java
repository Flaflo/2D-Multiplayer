package de.flaflo.game.main;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import de.flaflo.game.Game;
import de.flaflo.game.entity.PlayerSP;

/**
 * Main-Start
 * 
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
		PlayerSP.PLAYER_NAME = JOptionPane.showInputDialog("Wähle einen Spielernamen: ",
				"Randy" + Math.abs(Game.RANDOM.nextInt()));

		if (PlayerSP.PLAYER_NAME == null) {
			initWindow();
			return;
		}

		JColorChooser cc = new JColorChooser();
		AbstractColorChooserPanel[] panels = cc.getChooserPanels();
		for (AbstractColorChooserPanel accp : panels) {
			if (accp.getDisplayName().equals("HSL")) {
				JOptionPane.showMessageDialog(null, accp);
				PlayerSP.PLAYER_COLOR = accp.getColorSelectionModel().getSelectedColor();
			}
		}

		// PlayerSP.PLAYER_COLOR = JColorChooser.showDialog(null, "Wähle deine
		// Spieler Farbe", Color.GREEN);

		if (PlayerSP.PLAYER_COLOR == null) {
			initWindow();
			return;
		}

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
	 * 
	 * @param text
	 *            Text der ausgegeben wird
	 */
	public static void log(String text) {
		System.out.println("[Game]: " + text);
	}

}
