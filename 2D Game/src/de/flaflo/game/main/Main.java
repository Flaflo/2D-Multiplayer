package de.flaflo.game.main;

import java.net.Inet4Address;
import java.time.Instant;
import java.util.Date;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import de.flaflo.game.Game;
import de.flaflo.game.entity.PlayerSP;
import sun.util.calendar.CalendarUtils;

/**
 * Main-Start
 * 
 * @author Flaflo
 *
 */
@SuppressWarnings("restriction")
public class Main {

	public static void main(String[] args) {
		initWindow();
	}

	/**
	 * Initialisiert das JFrame
	 */
	private static void initWindow() {
		String[] ipResult = JOptionPane.showInputDialog("IP zum Verbinden (IP:Port):",
				"ncp.poltergeistclient.de:1338").split(":");
		
		try {
			Game.IP = ipResult[0];
			Game.PORT = Integer.parseInt(ipResult[1]);
			
			if (!Inet4Address.getByName(Game.IP).isReachable(5000)) {
				JOptionPane.showMessageDialog(null, "Konnte nicht zum Host verbinden.");
				
				initWindow();
				return;
			}
		
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Bitte gebe eine korrekte IP an.");
			initWindow();
			return;
		}
		
		PlayerSP.PLAYER_NAME = JOptionPane.showInputDialog("Wähle einen Spielernamen:",
				"Randy" + Math.abs(Game.RANDOM.nextInt()));

		if (PlayerSP.PLAYER_NAME == null)
			System.exit(0);
		
		if (PlayerSP.PLAYER_NAME.length() > 24) {
			initWindow();
			return;
		}
		
		showColorChooser();
		
		if (PlayerSP.PLAYER_COLOR == null)
			System.exit(0);

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
	@SuppressWarnings("deprecation")
	public static void log(String text) {
	    Date date = Date.from(Instant.now());
		
		StringBuilder sb = new StringBuilder();
		
		CalendarUtils.sprintf0d(sb, date.getHours(), 2).append(':');
	    CalendarUtils.sprintf0d(sb, date.getMinutes(), 2).append(':');
	    CalendarUtils.sprintf0d(sb, date.getSeconds(), 2);
	    
		System.out.println("{[" + sb.toString() + "] Game}: " + text);
	}

	public static void showColorChooser() {
		JColorChooser cc = new JColorChooser();
		AbstractColorChooserPanel[] panels = cc.getChooserPanels();
		for (AbstractColorChooserPanel accp : panels) {
			if (accp.getDisplayName().equals("HSL")) {
				JOptionPane.showMessageDialog(null, accp);
				PlayerSP.PLAYER_COLOR = accp.getColorSelectionModel().getSelectedColor();
			}
		}
	}
}
