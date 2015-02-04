import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * Class Animation is for the animation of game objects. It handles
 * image shown in different frames. Mainly Animation is provided for
 * movable object in game, namely player character and enemies.
 * @author kevingok
 *
 */
public class Animation {
	 /**Declare ArrayList for the images of the frame.*/
	private ArrayList<Image> frames;
	/**Declares the current frame.*/
	private int currentFrame;
	
	/**Declare the start time of an animation.*/
	private long startTime;
	/**Declare the time of delay for an animation.*/ 
	private long delay;
	/**Declare the indicator for whether the animation has been played*/ 
	private boolean playedOnce;
	
	 /**
	  * Class constructor of an animation. On default sets animation
	  * has not been played yet.
	  * */
	public Animation() {
		playedOnce = false;
	}
	
	/**
	 * Set the frame of the animation and provide a start time for the 
	 * animation.
	 * @param frames		Frames of animations
	 */
	public void setFrames(ArrayList<Image> frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	/**
	 * Set time of delay.
	 * @param delay		Set time of delay
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	/**
	 * Update frame of the animation.
	 */
	public void update() {
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.size()) {
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	/**
	 * Get image of current frame.
	 * @return			Image of the current frame.
	 */
	public Image getImage() {
		return frames.get(currentFrame);
	}
	
	 /**
	  * Get the value of playOnce.
	  * @return    		True/False whether animation played once.
	  * */
	public boolean playedOnce() {
		return playedOnce;
	}
}
