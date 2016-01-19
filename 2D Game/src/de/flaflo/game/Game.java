package de.flaflo.game;

import java.awt.Dimension;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import de.flaflo.game.entity.Player;
import de.flaflo.game.entity.PlayerMP;
import de.flaflo.game.entity.PlayerSP;
import de.flaflo.game.input.Keyboard;
import de.flaflo.game.input.Mouse;
import de.flaflo.game.networking.Connector;

/**
 * Hauptmanagementklasse des Spiels
 * @author Flaflo
 *
 */
public class Game extends JPanel {

	public static final Random RANDOM = new Random();
	
	public static String PLAYER_NAME = "";
	public static final int PORT = 1338;
	public static final String IP = "localhost";
	
	private boolean isInMultiplayer = false;
	
	/**
	 * Serialisierungs ID
	 */
	private static final long serialVersionUID = 923915829347736711L;

	public static final String TITLE = "2D Game";
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
		
		player = new PlayerSP(PLAYER_NAME, Player.PLAYER_COLOR, WIDTH / 2, HEIGHT / 2, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
		players = new CopyOnWriteArrayList<PlayerMP>();
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.requestFocus();
		
		this.connector = new Connector(IP, PORT);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		
		try {
			this.init();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialisiert das Spiel
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	private void init() throws UnknownHostException, IOException {
		gameLoop = new GameLoop(this);
		gameLoop.start();
		
		this.connector.connect();
	}
	
	/**
	 * Spawnt einen Spieler in die Welt
	 * @param player Spieler, der gespawnt wird
	 */
	public void spawnPlayer(PlayerMP player) {
		this.players.add(player);
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

	public boolean isInMultiplayer() {
		return isInMultiplayer;
	}

	public void setInMultiplayer(boolean isInMultiplayer) {
		this.isInMultiplayer = isInMultiplayer;
	}
}
