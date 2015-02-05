import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * Class Bullet is to store the status and behavior of a bullet in the game. 
 * @author kevingok
 *
 */
public abstract class Bullet extends GameObject {
	/**Define the status of a bullet flying as 0.*/
	protected final int FLYING = 0;
	/**Define the status of a bullet hitting the other object as 1.*/
	protected final int HITING = 1;

	/**Declare an array list to store the image of sprites.*/
	protected ArrayList<ArrayList<Image>> sprites;
	/**Declare an indicator on whether the bullet hit an object.*/
	protected boolean isHit;
	/**Declare an indicator on whether the bullet should be removed.*/
	protected boolean shouldBeRemoved;
	/**Declare the damage of a bullet.*/
	protected int damage;
	
	/**
	 * Class constructor to create a bullet on the map.
	 * @param map		Map instance of level
	 */
	public Bullet(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Handle bullet hit event
	 */
	public void isHit() {
		if(!isHit) {
			dx = 0;
			isHit = true;
			animation.setFrames(sprites.get(HITING));
			animation.setDelay(50);
		}
	}
	
	/**
	 * Retrieve the damage of a bullet.
	 * @return		Damage of a bullet.
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Retrieve condition whether a bullet should be removed.
	 * @return		True/False on condition whether a bullet should be removed.
	 */
	public boolean shoudBeRemove() {
		return shouldBeRemoved;
	}

	/**
	 * Update bullet animation and mark bullet to be removed.
	 */
	@Override
	public void update() {
		int currentRow = (int) (yPosition / tileSize);
		
		double nextX = xPosition + dx;
		
		if(dx > 0) {
			if(map.getTileType(currentRow, (int)((xPosition + 10) / tileSize)) == Tile.BLOCKTILE) 
			{
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}
		else if(dx < 0) {
			if(map.getTileType(currentRow, (int)((xPosition - 10) / tileSize)) == Tile.BLOCKTILE) {
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}
		
		xPosition = nextX;
		
		if(dx == 0 && !isHit) {
			isHit();
		}
		
		animation.update();
		
		if(isHit && animation.playedOnce()) {
			shouldBeRemoved = true;
		}
	}

	/**
	 * Draw the bullet whether facing right or left.
	 */
	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if(right) {
			gc.drawImage(animation.getImage(), xPosition + map.getX() - width / 2, yPosition - height / 2);
		}
		else {
			gc.drawImage(animation.getImage(), xPosition + map.getX() + width / 2 , yPosition - height / 2, -width, height);
		}
	}
	
	/*No intention to use.*/
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}

	/*No intention to use.*/
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}
}