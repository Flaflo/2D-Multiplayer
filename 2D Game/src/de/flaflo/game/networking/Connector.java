package de.flaflo.game.networking;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import de.flaflo.game.Game;
import de.flaflo.game.entity.Player;
import de.flaflo.game.entity.PlayerMP;
import de.flaflo.game.entity.PlayerSP;
import de.flaflo.game.main.Main;

/**
 * TODO
 * </br>
 * • Implement the Packet System here
 * </br>
 * • Implement Player IDs
 * @author Flaflo
 *
 */
public class Connector implements Runnable {

	private String ip;
	private int port;
	
	private Thread innerThread;
	private Thread posUpdateThread;
	
	private boolean isRunning;
	
	private Socket socket;
	
	public Connector(String ip, int port) {
		this.ip = ip;
		this.port = port;
		
		innerThread = new Thread(this);
		posUpdateThread = new Thread() {
			@Override
			public void run() {
				try {
					while (isRunning) {
						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						
						out.writeUTF("posUpdate");
						out.writeInt(Game.getGame().getPlayer().getX());
						out.writeInt(Game.getGame().getPlayer().getY());
						
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	/**
	 * Sendet ein Positions Packet an den Server
	 * @param x X Positions auf dem Server
	 * @param y Y Position auf dem Server
	 */
	public void playOutPosition(int x, int y) {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
			out.writeUTF("posUpdate");
	
			out.writeInt(x);
			out.writeInt(y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		isRunning = true;
		
		while (isRunning) {
			try {
				DataInputStream in = new DataInputStream(socket.getInputStream());
				@SuppressWarnings("unused")
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());

				String desire = in.readUTF();
				
				switch (desire) {
					case "addPlayer":
						String toAddName = in.readUTF();
						int toAddX = in.readInt();
						int toAddY = in .readInt();
						
						int toAddRed = in.readInt();
						int toAddGreen = in.readInt();
						int toAddBlue = in.readInt();
						
						Game.getGame().spawnPlayer(new PlayerMP(toAddName, new Color(toAddRed, toAddGreen, toAddBlue), toAddX, toAddY, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT));
						
						Main.log(toAddName + " hat das Spiel betreten.");
						break;
					case "removePlayer":
						String toRemoveName = in.readUTF();
						Game.getGame().despawnPlayer(Game.getGame().getPlayerByName(toRemoveName));
						
						Main.log(toRemoveName + " hat das Spiel verlassen.");
						break;
					case "posUpdate":
						String toUpdateName = in.readUTF();
						int toUpdateX = in.readInt();
						int toUpdateY = in.readInt();
						
						PlayerMP playerToUpdate = Game.getGame().getPlayerByName(toUpdateName);
						
						if (playerToUpdate != null) {
							playerToUpdate.interpolateToPosition(toUpdateX, toUpdateY);
						}
						
						break;
					case "teleport": 
						int toX = in.readInt();
						int toY = in.readInt();
						
						Game.getGame().getPlayer().teleport(toX, toY);
						break;
				}
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Verbindung wurde unterbrochen:\n" + e.getMessage());
				System.exit(0);
			}
		}
	}

	public synchronized void connect() throws UnknownHostException, IOException {
		if (isRunning)
			return;
		
		Main.log("Verbinde auf " + ip + ":" + port);

		if (socket == null)
			socket = new Socket(ip, port);
		
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		out.writeUTF(PlayerSP.PLAYER_NAME);
		out.writeInt(Game.getGame().getPlayer().getX());
		out.writeInt(Game.getGame().getPlayer().getY());
		
		Color color = PlayerSP.PLAYER_COLOR;
		
		out.writeInt(color.getRed());
		out.writeInt(color.getGreen());
		out.writeInt(color.getBlue());

		int size = in.readInt();

		for (int i = 0; i < size; i++) {
			String pName = in.readUTF();
			int pX = in.readInt();
			int pY = in.readInt();
			
			int cRed = in.readInt();
			int cGreen = in.readInt();
			int cBlue = in.readInt();
			
			Game.getGame().spawnPlayer(new PlayerMP(pName, new Color(cRed, cGreen, cBlue), pX, pY, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT));
		}
		
		innerThread.start();
		posUpdateThread.start();
		
		Main.log("Erfolgreich verbunden.");
	}
	
	public synchronized void disconnect() {
		if (!isRunning)
			return;
		
		isRunning = false;
	}
	
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
}
