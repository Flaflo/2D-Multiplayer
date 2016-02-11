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
import de.flaflo.game.graphics.Camera;
import de.flaflo.game.input.Keyboard;
import de.flaflo.game.input.Mouse;
import de.flaflo.game.networking.Client;

/**
 * Hauptmanagementklasse des Spiels
 * 
 * @author Flaflo
 *
 */
public class Game extends JPanel {

	public static final Random RANDOM = new Random();

	public static int PORT = 1338;
	public static String IP = "localhost";

	/** Serialisierungs ID */
	private static final long serialVersionUID = 923915829347736711L;

	public static final String TITLE = "2D Multiplayer";
	public static final int WIDTH = 800, HEIGHT = 600;

	private static Game instance;

	private GameLoop gameLoop;

	private Camera camera;
	
	private Client client;

	private PlayerSP player;

	private CopyOnWriteArrayList<PlayerMP> players;

	public Game() {
		Mouse mouse = new Mouse();

		this.addKeyListener(new Keyboard());
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);

		camera = new Camera();
		camera.setBounds(0, 0, this.getWidth() - Game.WIDTH - 1, this.getHeight() - Game.HEIGHT);
		camera.setSmoothness(0.18F);
		player = new PlayerSP(PlayerSP.PLAYER_NAME, PlayerSP.PLAYER_COLOR, RANDOM.nextInt(WIDTH) - Player.PLAYER_WIDTH, RANDOM.nextInt(HEIGHT) - Player.PLAYER_HEIGHT, Player.PLAYER_WIDTH,
				Player.PLAYER_HEIGHT);
		players = new CopyOnWriteArrayList<PlayerMP>();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.requestFocus();

		this.client = new Client(IP, PORT);
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
			this.client.connect();
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
		for (PlayerMP p : players) {
			if (p.getName().equals(name)) {
				return p;
			}
		}

		return null;
	}

	public PlayerMP getPlayerByID(int id) {
		for (PlayerMP p : players)
			if (p.getId() == id)
				return p;
		
		return null;
	}
	
	public Client getClient() {
		return client;
	}
	
	public static Game getGame() {
		return instance;
	}
	
	public static Game setInstance(Game game) {
		instance = game;
		
		return game;
	}

	public CopyOnWriteArrayList<PlayerMP> getPlayers() {
		return players;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * @param camera the camera to set
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
