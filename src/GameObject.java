import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

/**
 * Abstract Class GameObject handles all the gaming objects, namely player
 * character, enemy, fireBall, coin. Such class handles xy position occurrence,
 * collision width and height and defines various atract method for children
 * class to handle corresponding behavior.
 * @author kevingok
 *
 */
public abstract class GameObject {
	/**Declare Map instance*/
	protected Map map;
	/**Declare size of the tile*/
	protected double tileSize;
	
	/**Declare x-coordinate*/
	protected double xPosition;
	/**Declare y-coordinate*/
	protected double yPosition;
	/**Declare x-Vector*/
	protected double dx;
	/**Declare y-Vector*/
	protected double dy;
	
	/**Declare width of the object*/
	protected double width;
	/**Declare height of the object*/
	protected double height;
	/**Declare collision width of the object*/
	protected double collisionWidth;
	/**Declare collision height of the object*/
	protected double collisionHeight;
	
	/**Declare animation instance*/
	protected Animation animation;
	/**Declare current action*/
	protected int currentAction;
	
	/**Declare left(action)*/
	protected boolean left;
	/**Declare right(action)*/
	protected boolean right;
	/**Declare up(action)*/
	protected boolean up;
	/**Declare down(action)*/
	protected boolean down;
	/**Declare jumping(action)*/
	protected boolean jumping;
	/**Declare falling(action)*/
	protected boolean falling;
	
	/**
	 * Class constructor needs map instance for allocate game object into
	 * suitable coordinate.
	 * @param map		Map of the stage
	 */
	public GameObject(Map map) {
		this.map = map;
		tileSize = map.getTileSize();
	}
	
	/**
	 * Detect for collision.
	 * @param gameObject	game object to be detect for collision.
	 * @return				True/False whether game object intersect each other, that means collision detected.
	 */
	public boolean intersects(GameObject gameObject) {
		Rectangle r = new Rectangle(xPosition, yPosition, collisionWidth, collisionHeight);
		return r.intersects(gameObject.getXPosition(), gameObject.getYPosition(), gameObject.getWidth(), gameObject.getHeight());
	}
	
	/**
	 * Retrieve collision width. 
	 * @return		Collision width of the object.
	 */
	public double getCollisionWidth() {
		return collisionWidth;
	}

	/**
	 * Retrieve collision height. 
	 * @return		Collision height of the object.
	 */
	public double getCollisionHeight() {
		return collisionHeight;
	}

	/**
	 * Retrieve x coordinate of game object.
	 * @return		X coordinate of game object.
	 */
	public double getXPosition() {
		return xPosition;
	}
	
	/**
	 * Retrieve y coordinate of game object.
	 * @return		Y coordinate of game object.
	 */
	public double getYPosition() {
		return yPosition;
	}
	
	/**
	 * Retrieve width of game object.
	 * @return		Width of game object.
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Retrieve height of game object.
	 * @return		Height of game object.
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Set xy coordinate of game object.
	 * @param x			x coordinate.
	 * @param y			y coordinate.
	 */
	public void setPosition(double x, double y) {
		xPosition = x;
		yPosition = y;
	}
	
	/**
	 * Set xy vector of game object.
	 * @param dx		x vector.
	 * @param dy		y vector.
	 */
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Set left to show left corresponding action of game object. 
	 * @param left		True/False
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}

	/**
	 * Set right to show right corresponding action of game object. 
	 * @param right		True/False
	 */
	public void setRight(boolean right) {
		this.right = right;
	}

	/**
	 * Set up to show up corresponding action of game object. 
	 * @param up		True/False
	 */
	public void setUp(boolean up) {
		this.up = up;
	}

	/**
	 * Set down to show down corresponding action of game object. 
	 * @param down		True/False
	 */
	public void setDown(boolean down) {
		this.down = down;
	}

	/**
	 * Set jumping to show jumping corresponding action of game object.
	 * @param jumping		True/False
	 */
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	/**
	 * Set falling to show falling corresponding action of game object.
	 * @param falling		True/False
	 */
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	
	/**
	 * Update game object according to subtype behavior.
	 */
	public abstract void update();
	
	/**
	 * Draw game object according to subtype behavior.
	 * @param gc		Image of game object.
	 */
	public abstract void render(GraphicsContext gc);
	
	/**
	 * Handle keyboard pressed event according to subtype behavior.
	 * @param keyCode		keyboard code
	 */
	public abstract void keyPressed(KeyCode keyCode);
	
	/**
	 * Handle keyboard released event according to subtype behavior.
	 * @param keyCode		keyboard code
	 */
	public abstract void keyReleased(KeyCode keyCode);
}
