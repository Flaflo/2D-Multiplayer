package de.flaflo.game.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import de.flaflo.game.Game;

public class RenderHelper {

	private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB;
	
	private static final BufferedImage IMAGE_BUFFER;
	private static Graphics IMAGE_GRAPHICS;
	
	static {
		IMAGE_BUFFER = new BufferedImage(Game.WIDTH, Game.HEIGHT, IMAGE_TYPE);
		IMAGE_GRAPHICS = IMAGE_BUFFER.getGraphics();
	}
	
	public static void drawRect(int x, int y, int width, int height, Color color) {
		IMAGE_GRAPHICS.setColor(color);
		IMAGE_GRAPHICS.fillRect(x, y, width, height);
	}
	
	public static void drawOutlineRect(int x, int y, int width, int height, Color color) {
		IMAGE_GRAPHICS.setColor(color);
		IMAGE_GRAPHICS.drawRect(x, y, width, height);
	}
	
	public static void clearColor(Color color) {
		drawRect(0, 0, Game.WIDTH, Game.HEIGHT, color);
	}
	
	public static void drawString(String text, int x, int y, Color color) {
		IMAGE_GRAPHICS.setColor(color);
		IMAGE_GRAPHICS.drawString(text, x, y);
	}
	
	public static float getStringWidth(String str) {
		return SwingUtilities.computeStringWidth(IMAGE_GRAPHICS.getFontMetrics(), str);
	}
	
	public static final BufferedImage getImageBuffer() {
		return IMAGE_BUFFER;
	}
}
