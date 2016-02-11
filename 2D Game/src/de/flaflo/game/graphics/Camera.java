package de.flaflo.game.graphics;

import java.awt.Graphics2D;

import de.flaflo.game.util.Scheduler;
import de.flaflo.game.util.Scheduler.Task;

/**
 * This class can be used as a camera for games.
 * In order to apply the translation the method "apply" needs to be called before rendering. It also needs to get updated every tick.
 * After rendering the method "revert" should be called to undo the translation.
 * @author Sogomn
 *
 */
public final class Camera {
	
	private double x, y;
	private double targetX, targetY;
	private float smoothness;
	
	private double minX, minY;
	private double maxX, maxY;
	
	private double rotation;
	private double pivotX, pivotY;
	
	private double scale;
	private double centerX, centerY;
	
	private double xOffset, yOffset;
	private double rotationOffset;
	private Scheduler shakeScheduler;
	private Shaker shaker;
	
	/**
	 * If passed to the method "setSmoothness" the camera position will automatically be the target position.
	 */
	public static final float NO_SMOOTHNESS = 0;
	
	/**
	 * Represents a minimum value of no fixed size.
	 */
	public static final int NO_MINIMUM = Integer.MIN_VALUE;
	
	/**
	 * Represents a maximum value of no fixed size.
	 */
	public static final int NO_MAXIMUM = Integer.MAX_VALUE;
	
	/**
	 * Constructs a new Camera object with the default smoothness of 0 and no minimum or maximum values.
	 */
	public Camera() {
		minX = minY = NO_MINIMUM;
		maxX = maxY = NO_MAXIMUM;
		shakeScheduler = new Scheduler();
		shaker = new Shaker();
	}
	
	private void move(final double delta) {
		if (smoothness == NO_SMOOTHNESS) {
			x = targetX;
			y = targetY;
		} else {
			final double distX = targetX - x;
			final double distY = targetY - y;
			
			x += (distX / smoothness) * delta;
			y += (distY / smoothness) * delta;
		}
	}
	
	private void clampPosition() {
		x = Math.max(Math.min(x, maxX), minX);
		y = Math.max(Math.min(y, maxY), minY);
		
		final double actualX = getX();
		final double actualY = getY();
		
		if (actualX < minX) {
			xOffset = x - minX;
		} else if (actualX > maxX) {
			xOffset = x - maxX;
		}
		
		if (actualY < minY) {
			yOffset = y - minY;
		} else if (actualY > maxY) {
			yOffset = y - maxY;
		}
	}
	
	private void clampTarget() {
		targetX = Math.max(Math.min(targetX, maxX), minX);
		targetY = Math.max(Math.min(targetY, maxY), minY);
	}
	
	/**
	 * Updates the camera.
	 */
	public void update(final double delta) {
		move(delta);
		
		shakeScheduler.update(delta);
		shaker.update(delta);
		
		clampPosition();
	}
	
	/**
	 * Applies the camera transform to the given Graphics2D object.
	 * @param g The Graphics2D object
	 */
	public void apply(final Graphics2D g) {
		final double actualX = getX();
		final double actualY = getY();
		final double actualRotation = getRotation();
		
		if (actualRotation != 0) {
			g.rotate(actualRotation, pivotX, pivotY);
		}
		
		g.translate(-actualX, -actualY);
		
		if (scale != 0) {
			g.translate(centerX, centerY);
			g.scale(scale, scale);
			g.translate(-centerX, -centerY);
		}
	}
	
	/**
	 * Reverts the camera tramsform of the given Graphics2D object.
	 * This should only be called after the method "apply" has been called.
	 * @param g The Graphics2D object
	 */
	public void revert(final Graphics2D g) {
		final double actualX = getX();
		final double actualY = getY();
		final double actualRotation = getRotation();
		
		if (scale != 0) {
			final double reverseScale = 1 / scale;
			
			g.translate(centerX, centerY);
			g.scale(reverseScale, reverseScale);
			g.translate(-centerX, -centerY);
		}
		
		g.translate(actualX, actualY);
		
		if (actualRotation != 0) {
			g.rotate(-actualRotation, pivotX, pivotY);
		}
	}
	
	/**
	 * Resets the camera position and target.
	 * Also stops camera shake.
	 */
	public void reset() {
		x = y = 0;
		targetX = targetY = 0;
		
		resetShake();
		resetRotation();
		resetScaling();
	}
	
	/**
	 * Stops and resets the camera shake.
	 */
	public void resetShake() {
		xOffset = yOffset = 0;
		rotationOffset = 0;
		
		shakeScheduler.clearTasks();
		shaker.stop();
	}
	
	/**
	 * Resets the rotation and the rotation pivot.
	 */
	public void resetRotation() {
		rotation = 0;
		pivotX = pivotY = 0;
	}
	
	/**
	 * Resets the scaling and the scale center point.
	 */
	public void resetScaling() {
		scale = 0;
		centerX = centerY = 0;
	}
	
	/**
	 * Applies camera shake for the given duration with the given intensity.
	 * @param xIntensity The intensity on the x-axis
	 * @param yIntensity The intensity on the y-axis
	 * @param rotationIntensity The rotation shake intensity
	 * @param duration The duration in seconds
	 */
	public void shake(final double xIntensity, final double yIntensity, final double rotationIntensity, final float duration) {
		final Task task = new Task(() -> {
			resetShake();
		}, duration);
		
		resetShake();
		shakeScheduler.addTask(task);
		shaker.shake(xIntensity, yIntensity, Math.toRadians(rotationIntensity), duration);
	}
	
	/**
	 * Offsets the target position of the camera by the given one.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void moveBy(final double x, final double y) {
		targetX += x;
		targetY += y;
		
		clampTarget();
	}
	
	/**
	 * Sets the target position of the camera to the given one.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void moveTo(final double x, final double y) {
		targetX = x;
		targetY = y;
		
		clampTarget();
	}
	
	/**
	 * Sets the camera and the target position to the given one.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void set(final double x, final double y) {
		this.x = targetX = x;
		this.y = targetY = y;
		
		clampTarget();
		clampPosition();
	}
	
	/**
	 * Sets the minimum position the camera can have.
	 * @param minX The minimum x value
	 * @param minY The minimum y value
	 */
	public void setMinimum(final double minX, final double minY) {
		this.minX = minX;
		this.minY = minY;
	}
	
	/**
	 * Sets the maximum position the camera can have.
	 * @param maxX The maximum x value
	 * @param maxY The maximum y value
	 */
	public void setMaximum(final double maxX, final double maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	/**
	 * Sets the minimum and maximum values the camera can have.
	 * @param minX The minimum x value
	 * @param minY The minimum y value
	 * @param maxX The maximum x value
	 * @param maxY The maximum y value
	 */
	public void setBounds(final double minX, final double minY, final double maxX, final double maxY) {
		setMinimum(minX, minY);
		setMaximum(maxX, maxY);
	}
	
	/**
	 * Sets the rotation of the camera.
	 * @param degrees The rotation in degrees
	 */
	public void setRotation(final double degrees) {
		rotation = Math.toRadians(degrees);
	}
	
	/**
	 * Sets the pivot for the camera rotation.
	 * The default is (0|0)
	 * @param pivotX The x coordinate
	 * @param pivotY The y coordinate
	 */
	public void setRotationPivot(final double pivotX, final double pivotY) {
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}
	
	/**
	 * Sets the scale.
	 * One is default.
	 * @param scale The scale
	 */
	public void setScale(final double scale) {
		this.scale = scale;
	}
	
	/**
	 * Sets the scaling center point.
	 * @param centerX The x coordinate
	 * @param centerY The y coordinate
	 */
	public void setScaleCenterPoint(final double centerX, final double centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	/**
	 * Sets the smoothness for the camera.
	 * Good values are between 0 an 1.
	 * @param smoothness The smoothness
	 */
	public void setSmoothness(final float smoothness) {
		this.smoothness = smoothness;
	}
	
	/**
	 * Returns the x coordinate of the camera.
	 * Ignores scale.
	 * @return The coordinate
	 */
	public double getX() {
		return x + xOffset;
	}
	
	/**
	 * Returns the y coordinate of the camera.
	 * Ignores scale.
	 * @return The coordinate
	 */
	public double getY() {
		return y + yOffset;
	}
	
	/**
	 * Returns the target x coordinate of the camera.
	 * @return The coordinate
	 */
	public double getTargetX() {
		return targetX;
	}
	
	/**
	 * Returns the target y coordinate of the camera.
	 * @return The coordinate
	 */
	public double getTargetY() {
		return targetY;
	}
	
	/**
	 * Returns the scrolling smoothness.
	 * @return The smoothness
	 */
	public float getSmoothness() {
		return smoothness;
	}
	
	/**
	 * Returns the minimum x value.
	 * @return The value
	 */
	public double getMinimumX() {
		return minX;
	}
	
	/**
	 * Returns the minimum y value.
	 * @return The value
	 */
	public double getMinimumY() {
		return minY;
	}
	
	/**
	 * Returns the maximum x value.
	 * @return The value
	 */
	public double getMaximumX() {
		return maxX;
	}
	
	/**
	 * Returns the maximum y value.
	 * @return The value
	 */
	public double getMaximumY() {
		return maxY;
	}
	
	/**
	 * Returns the camera rotation.
	 * Includes the rotation shake.
	 * @return The rotation
	 */
	public double getRotation() {
		return rotation + rotationOffset;
	}
	
	/**
	 * Returns the scale.
	 * @return The scale
	 */
	public double getScale() {
		return scale;
	}
	
	/**
	 * Returns whether the camera is shaking or not.
	 * @return The state
	 */
	public boolean isShaking() {
		return shaker.shaking;
	}
	
	private final class Shaker {
		
		private boolean shaking;
		private double xIntensity, yIntensity;
		private double rotationIntensity;
		
		private double initialXIntensity, initialYIntensity;
		private double initialRotationIntensity;
		private float duration;
		
		public Shaker() {
			//...
		}
		
		public void update(final double delta) {
			if (!shaking) {
				return;
			}
			
			xOffset = Math.random() * xIntensity * 2 - xIntensity;
			yOffset = Math.random() * yIntensity * 2 - yIntensity;
			rotationOffset = Math.random() * rotationIntensity * 2 - rotationIntensity;
			
			if (duration > 0) {
				xIntensity -= initialXIntensity / duration * delta;
				yIntensity -= initialYIntensity / duration * delta;
				rotationIntensity -= initialRotationIntensity / duration * delta;
			}
		}
		
		public void shake(final double xIntensity, final double yIntensity, final double rotationIntensity, final float duration) {
			this.xIntensity = initialXIntensity = xIntensity;
			this.yIntensity = initialYIntensity = yIntensity;
			this.rotationIntensity = initialRotationIntensity = rotationIntensity;
			this.duration = duration;
			
			shaking = true;
		}
		
		public void stop() {
			shaking = false;
		}
		
	}
	
}
