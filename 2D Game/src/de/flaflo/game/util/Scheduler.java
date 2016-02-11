package de.flaflo.game.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Scheduler class can be used to schedule tasks.
 * The method "update" needs to be called regularly in order to work.
 * Does not use a separate thread. All methods are synchronized with the internal list.
 * @author Sogomn
 *
 */
public final class Scheduler {
	
	private ArrayList<Task> tasks;
	
	/**
	 * Constructs a new Scheduler which can execute tasks.
	 */
	public Scheduler() {
		tasks = new ArrayList<Task>();
	}
	
	/**
	 * Updates the scheduler.
	 */
	public synchronized void update(final double delta) {
		final Iterator<Task> iterator = tasks.iterator();
		
		while (iterator.hasNext()) {
			final Task task = iterator.next();
			
			if (task.isDone()) {
				iterator.remove();
				
				task.execute();
			}
			
			task.update(delta);
		}
	}
	
	/**
	 * Removes all tasks from the schedule.
	 */
	public synchronized void clearTasks() {
		tasks.clear();
	}
	
	/**
	 * Adds a task to the schedule.
	 * @param task The task
	 */
	public synchronized void addTask(final Task task) {
		tasks.add(task);
	}
	
	/**
	 * Removes a task from the schedule.
	 * @param task The task to be removed
	 */
	public synchronized void removeTask(final Task task) {
		tasks.remove(task);
	}
	
	/**
	 * Returns whether the scheduler has a task scheduled or not.
	 * @return True if there is a task scheduled; false otherwise.
	 */
	public synchronized boolean hasTask() {
		return !tasks.isEmpty();
	}
	
	/**
	 * Can be scheduled with the help of the Scheduler class
	 * @author Sogomn
	 *
	 */
	public static final class Task {
		
		private Runnable runnable;
		private double timer;
		private float time;
		
		/**
		 * Constructs a new Task object.
		 * @param runnable The method "execute" will be called when the task gets executed
		 * @param time The time the task should be executed after
		 */
		public Task(final Runnable runnable, final float time) {
			this.runnable = runnable;
			this.time = time;
		}
		
		/**
		 * Updates the task.
		 */
		public void update(final double delta) {
			timer += delta;
		}
		
		/**
		 * Returns the time the task should be executed after.
		 * @return The time
		 */
		public float getTime() {
			return time;
		}
		
		/**
		 * Returns whether this Task is done or not.
		 * @return The state
		 */
		public boolean isDone() {
			return timer >= time;
		}
		
		/**
		 * Executes this task. Ignores the timer.
		 */
		public void execute() {
			runnable.run();
		}
		
	}
	
}
