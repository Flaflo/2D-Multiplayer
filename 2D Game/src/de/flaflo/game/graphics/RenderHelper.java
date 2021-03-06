package de.flaflo.game.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import de.flaflo.game.Game;

public class RenderHelper {

	private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB;
	
	private static final BufferedImage IMAGE_BUFFER;
	public static Graphics2D IMAGE_GRAPHICS;
	
	static {
		IMAGE_BUFFER = new BufferedImage(Game.WIDTH, Game.HEIGHT, IMAGE_TYPE);
		IMAGE_GRAPHICS = (Graphics2D) IMAGE_BUFFER.getGraphics();
	}
	
	public static void drawRect(int x, int y, int width, int height, Color color) {
		IMAGE_GRAPHICS.setColor(color);
		IMAGE_GRAPHICS.fillRect(x, y, width, height);
	}
	
	public static void drawRect(Rectangle2D rect, Color color) {
		IMAGE_GRAPHICS.setColor(color);
		IMAGE_GRAPHICS.fill(rect);
	}
	
	public static void drawOutlineRect(int x, int y, int width, int height, Color color) {
		IMAGE_GRAPHICS.setColor(color);
		IMAGE_GRAPHICS.drawRect(x, y, width, height);
	}
	
	public static void drawOutlineRect(Rectangle2D rect, Color color) {
		IMAGE_GRAPHICS.setColor(color);
		IMAGE_GRAPHICS.draw(rect);
	}
	
	public static void clearColor(Color color) {
		drawRect(0, 0, Game.WIDTH, Game.HEIGHT, color);
	}
	
	public static void drawString(String text, int x, int y, Color color) {
		IMAGE_GRAPHICS.setColor(color);
		IMAGE_GRAPHICS.drawString(text, x, y);
	}
	
	public static void drawString(String text, float x, float y, Color color) {
		IMAGE_GRAPHICS.setColor(color);
		IMAGE_GRAPHICS.drawString(text, (int) x, (int) y);
	}
	
	public static float getStringWidth(String str) {
		return IMAGE_GRAPHICS.getFontMetrics().stringWidth(str);
	}
	
	public static float getStringHeight() {
		return IMAGE_GRAPHICS.getFontMetrics().getHeight();
	}
	
	public static final BufferedImage getImageBuffer() {
		return IMAGE_BUFFER;
	}
}
