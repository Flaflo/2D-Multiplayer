package de.flaflo.game;

import java.awt.Color;

import de.flaflo.game.entity.Player;
import de.flaflo.game.graphics.RenderHelper;

public class GameLoop implements Runnable {
	
	public static final double FRAME_CAPTION = 1e9 / 60.0D;
	
	private Game game;
	private boolean isRunning;
	
	private Thread innnerThread;
	
	private int fps, tps;
	
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
		
		//Andere Spieler
		for (Player p : this.game.getPlayers())
			p.render();
		
		//Spieler
		this.game.getPlayer().render();
		
		RenderHelper.drawString("FPS: " + fps, 4, (int) RenderHelper.getStringHeight(), Color.WHITE);
	}
	
	@Override
	public void run() {
		this.isRunning = true;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTime = System.nanoTime();
		long time = System.currentTimeMillis();
		
		double unprocessed = 0.0D;
		
		boolean shouldRender = false;
		
		while (isRunning) {
			long now = System.nanoTime();
			
			unprocessed += (now - lastTime) / FRAME_CAPTION;
			lastTime = now;
			
			if (unprocessed >= 1) {
				runTick();
				
				unprocessed--;
				ticks++;
				
				shouldRender = true;
			} else
				shouldRender = false;
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (shouldRender) {
				runRender();
				frames++;
			}
			
			if (System.currentTimeMillis() - 1000 > time) {
				time += 1000;
				
				fps = frames;
				tps = ticks;
				
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public Game getGame() {
		return game;
	}

	/**
	 * @return the fps
	 */
	public int getFps() {
		return fps;
	}

	/**
	 * @return the tps
	 */
	public int getTps() {
		return tps;
	}
}
