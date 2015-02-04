import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/**
 * Abstract class Scene regulate all behavior of its children  
 * @author kevingok
 *
 */
public abstract class Scene {
	
	/**Declare background instance.*/
	protected Background background;
	
	/**Initialization for subtype behavior.*/
	public abstract void init(); 
	
	/** Update background.*/
	public abstract void update();
	
	/**
	 * Draw background image and text.
	 * @param gc		Image of the scene.
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
