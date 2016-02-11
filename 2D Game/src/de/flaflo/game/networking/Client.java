package de.flaflo.game.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import de.flaflo.game.Game;
import de.flaflo.game.entity.PlayerMP;
import de.flaflo.game.entity.PlayerSP;
import de.flaflo.game.main.Main;
import de.flaflo.game.networking.packets.C01PacketLogin;
import de.flaflo.game.networking.packets.C02PacketPlayerList;
import de.flaflo.game.networking.packets.C03PacketAddPlayer;
import de.flaflo.game.networking.packets.C04PacketPosition;
import de.flaflo.game.networking.packets.C05PacketLeave;
import de.flaflo.game.networking.packets.Packet;

/**
 * TODO Packet Positions conversition to floats
 * @author Flaflo
 *
 */
public class Client implements Runnable {

	public static final long POSITION_UPDATE_DELAY = 100L;
	
	private String ip;
	private int port;
	
	private Thread innerThread;
	private Thread posUpdateThread;
	
	private boolean isRunning;
	
	private Socket socket;
	
	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		
		innerThread = new Thread(this);
		posUpdateThread = new Thread() {
			@Override
			public void run() {
				while (isRunning) {
					sendPacket(new C04PacketPosition((int) Game.getGame().getPlayer().getX(), (int) Game.getGame().getPlayer().getY()));
					
					try {
						Thread.sleep(POSITION_UPDATE_DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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

				byte id = in.readByte();
				
				switch (id) {
					case 2:
						C02PacketPlayerList listPacket = new C02PacketPlayerList();
						listPacket.receive(in);
						
						for (PlayerMP player : listPacket.getPlayers())
							Game.getGame().spawnPlayer(player);
						break;
					case 3:
						C03PacketAddPlayer addPacket = new C03PacketAddPlayer();
						addPacket.receive(in);
						
						Game.getGame().spawnPlayer(addPacket.getPlayer());
						break;
					case 4:
						C04PacketPosition inPos = new C04PacketPosition();
						inPos.receive(in);
						
						if (inPos.getPlayer() != null)
							inPos.getPlayer().interpolateToPosition(inPos.getX(), inPos.getY());
						
						break;
					case 5:
						C05PacketLeave leave = new C05PacketLeave();
						leave.receive(in);
						
						Game.getGame().despawnPlayer(leave.getPlayer());
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
		
		Main.log("Connecting to " + ip + ":" + port);

		if (socket == null)
			socket = new Socket(ip, port);
		
		this.sendPacket(new C01PacketLogin(PlayerSP.PLAYER_NAME, PlayerSP.PLAYER_COLOR, (int) Game.getGame().getPlayer().getX(), (int) Game.getGame().getPlayer().getY()));
		
		C02PacketPlayerList listPacket = new C02PacketPlayerList();
		listPacket.receive(new DataInputStream(socket.getInputStream()));
		
		for (PlayerMP player : listPacket.getPlayers())
			Game.getGame().spawnPlayer(player);
		
		innerThread.start();
		posUpdateThread.start();
		
		Main.log("Successfully connected.");
	}
	
	public synchronized void disconnect() {
		if (!isRunning)
			return;
		
		isRunning = false;
	}
	
	public void sendPacket(Packet packet) {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			packet.send(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
