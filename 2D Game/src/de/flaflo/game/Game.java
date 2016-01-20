package de.flaflo.game;

import java.awt.Dimension;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.flaflo.game.entity.Player;
import de.flaflo.game.entity.PlayerMP;
import de.flaflo.game.entity.PlayerSP;
import de.flaflo.game.input.Keyboard;
import de.flaflo.game.input.Mouse;
import de.flaflo.game.networking.Connector;

/**
 * Hauptmanagementklasse des Spiels
 * 
 * @author Flaflo
 *
 */
public class Game extends JPanel {

	public static final Random RANDOM = new Random();

	public static final int PORT = 1338;
	public static final String IP = "localhost";

	/**
	 * Serialisierungs ID
	 */
	private static final long serialVersionUID = 923915829347736711L;

	public static final String TITLE = "2D Multiplayer";
	public static final int WIDTH = 800, HEIGHT = 600;

	private static Game instance;

	private GameLoop gameLoop;

	private Connector connector;

	private PlayerSP player;

	private CopyOnWriteArrayList<PlayerMP> players;

	public Game() {
		instance = this;

		Mouse mouse = new Mouse();

		this.addKeyListener(new Keyboard());
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);

		player = new PlayerSP(PlayerSP.PLAYER_NAME, PlayerSP.PLAYER_COLOR, WIDTH / 2, HEIGHT / 2, Player.PLAYER_WIDTH,
				Player.PLAYER_HEIGHT);
		players = new CopyOnWriteArrayList<PlayerMP>();

		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.requestFocus();

		this.connector = new Connector(IP, PORT);
	}

	@Override
	public void addNotify() {
		super.addNotify();

		this.init();
	}

	/**
	 * Initialisiert das Spiel
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	private void init() {
		gameLoop = new GameLoop(this);
		gameLoop.start();

		try {
			this.connector.connect();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Konnte Host nicht auflösen.");
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Der Host hat die Verbindung verweigert.");
			System.exit(1);
		}
	}

	/**
	 * Spawnt einen Spieler in die Welt
	 * 
	 * @param player
	 *            Spieler, der gespawnt wird
	 */
	public void spawnPlayer(PlayerMP player) {
		this.players.add(player);
	}

	/**
	 * Entfernt einen Spieler aus der Welt
	 * 
	 * @param player
	 *            Spieler der entfernt wird
	 */
	public void despawnPlayer(PlayerMP player) {
		this.players.remove(player);
	}

	/**
	 * Gibt einen Spieler mithilfe seines Namens zurück
	 * 
	 * @param name
	 *            Name des zu suchendem Spielers
	 * @return Spieler der gefunden wurde oder null
	 */
	public PlayerMP getPlayerByName(String name) {
		for (PlayerMP p : Game.getGame().getPlayers()) {
			if (p.getName().equals(name)) {
				return p;
			}
		}

		return null;
	}

	public static Game getGame() {
		return instance;
	}

	public CopyOnWriteArrayList<PlayerMP> getPlayers() {
		return players;
	}

	public Player getPlayer() {
		return player;
	}
}
