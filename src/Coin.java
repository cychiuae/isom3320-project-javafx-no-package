import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * Class Coin represents coin objects during gameplay. Its functions is to be grabbed by player
 * character for increasing score of the game.
 * @author kevingok
 *
 */
public class Coin extends GameObject {

	/**Declare statues of coin being gotten.*/
	private boolean isGot;
	
	/**Declare collision width*/
	private double collisionWidth;
	/**Declare collision height*/
	private double collisionHegiht;

	/**
	 * Class constructor initializes width, height, image, animation for a mushroom 
	 * with a map instance to reveal specified location on the map.
	 * @param map		Map instance of the level.
	 */
	public Coin(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
		width = height = 60;
		collisionWidth = collisionHegiht = 30;
		isGot = false;
		
		
		Image spritesheet = MultimediaHelper.getImageByName("coin.png");
		ArrayList<Image> sprite = new ArrayList<Image>();
		for(int i = 0; i < 6; i++) {
			if(i == 4)
				continue;
			sprite.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), 0, (int) width, (int) height));
		}
		
		animation = new Animation();
		animation.setFrames(sprite);
		animation.setDelay(100);
	}
	
	/**
	 * Change statue of coin to be gotten.
	 */
	public void gotIt() {
		isGot = true;
	}
	
	/**
	 * Retrieve statues of coin.
	 * @return	True/False of whether coin is gotten.
	 */
	public boolean isGot() {
		return isGot;
	}
	
	/**
	 * Retrieve width of collision size.
	 */
	public double getCollisionWidth() {
		return collisionWidth;
	}

	/**
	 * Retrieve height of collision size.
	 */
	public double getCollisionHegiht() {
		return collisionHegiht;
	}

	/**
	 * Update animation of coin.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		animation.update();
	}

	/**
	 * Draw coins on the display.
	 */
	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage(animation.getImage(), xPosition + map.getX() - width / 2, yPosition - height / 2, width, height);
	}

	/**
	 * No intention to use.
	 */
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}

	/**
	 * No intention to use.
	 */
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}

}
