import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class HelpScene shows help instruction for player.
 * @author kevingok
 *
 */
public class HelpScene extends Scene {
	/**Declare background instance*/
	private Background background;
	/**Declare Font color of the title*/
	private Color titleColor;
	/**Declare Font of the title*/
	private Font titleFont;
	/**Declare Font of other wordings*/
	private Font font;

	/**
	 * Class constructor defines all variables for scene initialization. 
	 */
	public HelpScene() {
		background = new Background("menubg.gif", 1);
		background.setVector(-0.1, 0);
		
		titleColor = Color.RED;
		titleFont = Font.font("Centry Gothic", FontWeight.NORMAL, 56);
		font = Font.font("Arial", FontWeight.NORMAL, 24);
	}
	
	/**
	 * No special intentional use.
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	/**
	 * Update background.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		background.update();
	}

	/**
	 * Draw the background image and wordings that will needed on scene
	 */
	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		background.render(gc);
		
		gc.setFill(titleColor);
		gc.setFont(titleFont);
		gc.fillText("Help Me!", 140, 140);
		
		gc.setFill(Color.BLUEVIOLET);
		gc.setFont(font);
		gc.fillText("Up Arrow Key - Jump", 140, 200);
		gc.fillText("Left Arrow Key - Move Left", 140, 225);
		gc.fillText("Right Arrow Key - Move Right", 140, 250);
		gc.fillText("Space Bar - Slide (while in air)", 140, 275);
		gc.fillText("'D' - Scratch Attack", 140, 300);
		gc.fillText("'F' - Fire Ball", 140, 325);
		
		gc.setFill(Color.RED);
		gc.fillText("Back", 290, 390);
	}
	
	/**
	 * Handle keyboard pressed event
	 */
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		default:
			SceneManager.getInstance().changeSceneLevel(SceneManager.MENU);
		}
	}

	/**
	 * Handle keyboard released event
	 */
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
	}
}