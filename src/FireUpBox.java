import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/**
 * Class FireUpBox is for storing fire ball
 * @author kevingok
 *
 */
public class FireUpBox extends GameObject {

	/**Define number of fire ball.*/
	private int numOfFireBall;
	/**Define whether fire ball used up.*/
	private boolean used;
	
	/**
	 * Class constructor initiates width and height of the fire ball and set default
	 * of not used up.
	 * @param map				Map of the level
	 * @param numOfFireBall		Number of Fire Ball stored
	 */
	public FireUpBox(Map map, int numOfFireBall) {
		super(map);
		// TODO Auto-generated constructor stub
		width = height = 60;
		
		this.numOfFireBall = numOfFireBall;
		used = false;
	}
	
	/**
	 * Retrieve number of fire ball.
	 * @return		Number of fire ball.
	 */
	public int getNumOfBall() {
		return numOfFireBall;
	}

	/** No intention to use.*/
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/** No intention to use.*/
	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

	/** No intention to use.*/
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}

	/** No intention to use.*/
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}

}
