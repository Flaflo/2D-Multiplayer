package de.flaflo.game;

import java.awt.Color;

import de.flaflo.game.entity.Player;
import de.flaflo.game.graphics.RenderHelper;

public class GameLoop implements Runnable {
	
	private Game game;
	private boolean isRunning;
	
	private Thread innnerThread;
	
	public GameLoop(Game game) {
		this.game = game;
		this.innnerThread = new Thread(this);
	}
	
	public void start() {
		if (isRunning)
			return;
		
		this.innnerThread.start();
	}
	
	private void runTick() {
		
	}
	
	private void runRender() {
		doRender();
		
		this.game.getGraphics().drawImage(RenderHelper.getImageBuffer(), 0, 0, Game.WIDTH, Game.HEIGHT, null);
	}
	
	private void doRender() {
		RenderHelper.clearColor(new Color(0x5A219B));
		
		//Spieler
		this.game.getPlayer().render();
		//Andere Spieler
		for (Player p : this.game.getPlayers())
			p.render();
	}
	
	@Override
	public void run() {
		this.isRunning = true;
		
		while (isRunning) {
			
			runTick();
			runRender();
			
			try {
				Thread.sleep(15L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public Game getGame() {
		return game;
	}
}
