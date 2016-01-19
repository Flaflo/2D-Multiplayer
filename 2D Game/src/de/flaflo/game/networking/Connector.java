package de.flaflo.game.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import de.flaflo.game.Game;
import de.flaflo.game.entity.Player;
import de.flaflo.game.entity.PlayerMP;

/**
 * 
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
						String name = in.readUTF();
						int x = in.readInt();
						int y = in .readInt();
						
						Game.getGame().spawnPlayer(new PlayerMP(name, Player.PLAYER_COLOR, x, y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT));
						
						break;
					case "posUpdate":
						
						String recName = in.readUTF();
						int rx = in.readInt();
						int ry = in.readInt();
						
						for (PlayerMP p : Game.getGame().getPlayers()) {
							if (p.getName().equals(recName)) {
								p.setX(rx);
								p.setY(ry);
							}
						}
							
						break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void connect() throws UnknownHostException, IOException {
		if (isRunning)
			return;

		if (socket == null)
			socket = new Socket(ip, port);
		
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		out.writeUTF(Game.PLAYER_NAME);
		out.writeInt(Game.getGame().getPlayer().getX());
		out.writeInt(Game.getGame().getPlayer().getY());

		int size = in.readInt();

		for (int i = 0; i < size; i++) {
			String pName = in.readUTF();
			int pX = in.readInt();
			int pY = in.readInt();
			
			Game.getGame().spawnPlayer(new PlayerMP(pName, Player.PLAYER_COLOR, pX, pY, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT));
		}
		
		innerThread.start();
		posUpdateThread.start();
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
