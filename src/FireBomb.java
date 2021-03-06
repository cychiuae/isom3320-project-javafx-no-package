import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * Class FireBomb manages the fire bomb ejected from the boss.
 * @author kevingok
 *
 */
public class FireBomb extends Bullet {
	/**
	 * Class constructor declare all detail information(width, height, collision width, collision height, 
	 * damage value, direction, image, animation of the fire bomb.
	 * @param map			Map of the level.
	 * @param right			Determines direction of fire bomb. 
	 */
	public FireBomb(Map map, boolean right) {
		super(map);
		// TODO Auto-generated constructor stub
		this.right = right;
		dx = right ? 3.0 : -3.0;
		damage = 5;
		
		width = height = 60;
		collisionHeight = collisionWidth = 40;
		
		sprites = new ArrayList<ArrayList<Image>>();
		Image spritesheet = MultimediaHelper.getImageByName("firebomb.gif");
		ArrayList<Image> ballframes = new ArrayList<Image>();
		for(int i = 0; i < 4; i++) {
			ballframes.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), 0, (int) width, (int) height));
		}
		for(int i = 0; i < 2; i++) {
			ballframes.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), (int) height, (int) width, (int) height));
		}
		sprites.add(ballframes);
		
		ArrayList<Image> explosionframes = new ArrayList<Image>();
		for(int i = 0; i < 4; i++) {
			explosionframes.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), (int) (2 * height), (int) width, (int) height));
		}
		sprites.add(explosionframes);
		
		animation = new Animation();
		animation.setFrames(sprites.get(FLYING));
		animation.setDelay(100);
	}
}