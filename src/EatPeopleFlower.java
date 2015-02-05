import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * Class EatPeopleFlower defines properties and regulates behaviors of eating people flower.
 * @author kevingok
 *
 */
public class EatPeopleFlower extends GameObject {
	
	
	/**Declare width of collision*/
	double collisionWidth; 
	/**Declare height of collision*/
	double collisionHeight;
	
	/**Declare damage made to player if collision happens.*/
	int damage;
	
	/**
	 * Class constructor initializes width, height, damage, image, animation for a flower 
	 * with a map instance to reveal specified location on the map.
	 * @param map		Map instance of the level.
	 */
	public EatPeopleFlower(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
		width = height = 60;
		collisionWidth = 28;
		collisionHeight = 30;
		
		damage = 1;
		
		Image spritesheet = MultimediaHelper.getImageByName("flower.png");
		ArrayList<Image> sprite = new ArrayList<Image>();
		for(int i = 0; i < 2; i++) {
			sprite.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), 0, (int) width, (int) height));
		}
		
		animation = new Animation();
		animation.setFrames(sprite);
		animation.setDelay(new Random().nextInt(300) + 200);
	}

	/**
	 * Retrieve width of collision size of the flower.
	 * @return		Width of collision size of the flower
	 */
	public double getCollisionWidth() {
		return collisionWidth;
	}

	/**
	 * Retrieve height of collision size of the flower.
	 * @return		Height of collision size of the flower
	 */
	public double getCollisionHeight() {
		return collisionHeight;
	}
	
	/**
	 * Retrieve damage a flower made to player.
	 * @return		Damage value caused by collision of flower and player.
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Update animation of enemy flower.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		animation.update();
	}

	/**
	 * Draw out the enemy flower.
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
