import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class WinScene is scene shown when player has won the final boss. Such scene
 * is a background image of traditional window error blue screen covered with 
 * scores highlighted in yellow.
 * @author kevingok
 *
 */
public class WinScene extends Scene {
	/**Declare Color of wordings*/
	private Color color;
	/**Declare Font of wordings*/
	private Font font;

	/**Declare MediaPlayer*/
	private MediaPlayer mediaPlayer;
	/**Declares Animation.*/
	private Animation animation;
	
	/**Declare score*/
	private int score;

	/**Class constructor declares background image, font details of
	 * final score description*/ 
	public WinScene() {
		// TODO Auto-generated constructor stub

		color = Color.RED;
		font = Font.font("Lucida Console", FontWeight.BOLD, 15);
		mediaPlayer = new MediaPlayer(MultimediaHelper.getMusicByName("win.mp3"));
		
		Image spritesheet = MultimediaHelper.getImageByName("bigwin.png");
		ArrayList<Image> frames = new ArrayList<Image>();
		for(int i = 0; i < 25; i++) {
			frames.add(MultimediaHelper.getSubImage(spritesheet, (int)(i * 600), 0, (int)600, (int)368));
		}

		animation = new Animation();
		animation.setFrames(frames);
		animation.setDelay(130);
	}

	/**Get latest score after player win the game.*/
	@Override
	public void init() {
		// TODO Auto-generated method stub
		score = ScoreSystem.getInstance().getLatestScore();
	}

	/**
	 * Update background and play music continuously.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		animation.update();
		mediaPlayer.play();
	}

	/**
	 * Draw background image and show score detail.
	 */
	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage(animation.getImage(), 20, 94);

		gc.setFill(color);
		gc.setFont(font);
		gc.fillText("YOU WIN!! CONGRATULATIONs!!", 40, 40);
		gc.fillText("And your score is " + score, 40, 60);
	}

	/**
	 * Handle key pressed event. By pressing ENTER, user will prompted back
	 * to menu scene.
	 */
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case RIGHT:
		case UP:
		case LEFT:
		case DOWN:
		case F:
		case D:
			break;
		default:
		{
			mediaPlayer.stop();
			SceneManager.getInstance().changeSceneLevel(SceneManager.MENU);
		}
		}
	}

	/**
	 * Handle keyboard released event.
	 */
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}
}