
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class Player defines all properties and all regulates behaviors of user-control player
 * character.
 * @author kevingok
 *
 */
public class Player extends Character {
	
	/**Define idle states of player*/
	private static final int IDLE = 0;
	/**Define walking states of player*/
	private static final int WALKING = 1;
	/**Define jumping states of player*/
	private static final int JUMPING = 2;
	/**Define falling states of player*/
	private static final int FALLING = 3;
	/**Define flying states of player*/
	private static final int FLYING = 4;
	/**Define firing states of player*/
	private static final int FIRING = 5;
	/**Define attacking(short-range) states of player*/
	private static final int ATTACKING = 6;

	/**Define sound effect of player jumped*/
	private static Media JUMPINGSOUND = MultimediaHelper.getMusicByName("jump.wav");
	/**Define sound effect of player gotten coin*/
	private static Media COINSOUND = MultimediaHelper.getMusicByName("coin.wav");
	/**Define sound effect of player being bombed*/
	private static Media BOMBSOUND = MultimediaHelper.getMusicByName("bomb.wav");
	/**Define sound effect of player fired*/
	private static Media FIRESOUND = MultimediaHelper.getMusicByName("fire.wav");
	/**Define sound effect of player being hit*/
	private static Media HITSOUND = MultimediaHelper.getMusicByName("mario_ooh.wav");

	/**Declare array for storing fire ball instance*/
	private ArrayList<FireBall> balls;
	/**Declare number of fire ball instance*/
	private int numOfFire;
	/**Declare maximum number of fire ball*/
	private int maxFire;
	/**Declare action of firing*/
	private boolean firing;
	/**Declare action of short-range attacking*/
	private boolean attack;
	/**Declare damage of an attack*/
	private int attackDamage;
	/**Declare range of an attack*/
	private int attackRange;

	/**Declare action of flying*/
	private boolean flying;

	/**Declare array for storing sprites instance*/
	private ArrayList<ArrayList<Image>> sprites;

	/**Declare font type for illustrating HP, fireball amount*/
	private Font font;
	
	/**
	 * Class constructor initializes width, height, hp, fireball amount, attack damage, 
	 * image, animation with a map instance to reveal specified location on the map.
	 * @param map		Map instance of the level.
	 */
	public Player(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
		font = Font.font("Arial", 24);
		width = 60;
		height = 60;
		collisionHeight = 60;
		collisionWidth = 50;

		dx = 1.6;
		facingRight = true;

		hp = maxHp = 20;

		balls = new ArrayList<FireBall>();
		numOfFire = maxFire = 10;

		attackDamage = 5;
		attackRange = 80;

		sprites = new ArrayList<ArrayList<Image>>();

		Image spritesheet = MultimediaHelper.getImageByName("playersprites2.gif");
		int[] numFrames = new int[] {1, 2, 1, 1, 4, 2, 5};
		for(int i = 0; i < 7; i++) {
			ArrayList<Image> frames = new ArrayList<Image>();

			for(int j = 0; j < numFrames[i]; j++) {
				if(i != ATTACKING) {
					frames.add(MultimediaHelper.getSubImage(spritesheet, (int)(j * width), (int)(i * height), (int)width, (int)height));
				}
				else {
					frames.add(MultimediaHelper.getSubImage(spritesheet, (int)(j * width * 2), (int)(i * height), (int)width * 2, (int)height));
				}
			}
			sprites.add(frames);
		}

		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}

	/**
	 * Check whether fireball intersect with enemy instance and trigger enemy's hp deduction
	 * and produce corresponding sound effect.
	 * @param e			Enemy instance for checking whether enemy is being hit.
	 */
	public void checkHit(Enemy e) {
		for(int i = 0; i < balls.size(); i++) {
			Bullet fb = balls.get(i);

			if(fb.intersects(e)) {
				e.hit(fb.getDamage());
				new MediaPlayer(BOMBSOUND).play();
				fb.isHit();
			}
		}

		if(attack) {
			if(facingRight) {
				if(e.getXPosition() > xPosition && e.getXPosition() < xPosition + attackRange && e.getYPosition() > yPosition - height / 2 && e.getYPosition() < yPosition + height / 2) {
					e.hit(attackDamage);
				}
			}
			else {
				if(e.getXPosition() < xPosition && e.getXPosition() > xPosition - attackRange && e.getYPosition() > yPosition - height / 2 && e.getYPosition() < yPosition + height / 2) {
					e.hit(attackDamage);
				}
			}

		}
	}

	/**
	 * Collision detection with any enemy.
	 * @param o		Enemy instance
	 * @return		True/False if collision detected.
	 */
	public boolean intersects(Enemy o) {
		if(xPosition + 20 < o.getXPosition() + o.getCollisionWidth() &&
				xPosition + collisionWidth > o.getXPosition() &&
				yPosition < o.getYPosition() + tileSize &&
				yPosition + collisionHeight > o.getYPosition() + (tileSize - o.getCollisionHeight())) 
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Deduction of hp if player is hit.
	 */
	@Override
	public void hit(int damage) {
		if(isDead || isHit) {
			return;
		}
		
		hp -= damage;
		new MediaPlayer(HITSOUND).play();
		if(hp < 0) {
			hp = 0;
		}
		if(hp == 0) {
			isDead = true;
		}
		
		isHit = true;
		hitTimer = System.nanoTime();
	}

	/**
	 * Handle coin detection and play sound effect.  
	 * @param c		Coin instance
	 * @return		True/False if coin collides.
	 */
	public boolean gotCoin(Coin c) {
		if(xPosition + 20 < c.getXPosition() + c.getCollisionWidth() &&
				xPosition + collisionWidth > c.getXPosition() &&
				yPosition < c.getYPosition() + tileSize &&
				yPosition + collisionHeight > c.getYPosition() + (tileSize - c.getCollisionHegiht())) 
		{
			MediaPlayer p = new MediaPlayer(COINSOUND);
			p.play();
			return true;
		}
		return false;
	}

	/**
	 * Check whether player eaten by people eating flower.
	 * @param f		Flwoer instance.
	 */
	public void checkAteByFlower(EatPeopleFlower f) {
		// TODO Auto-generated method stub
		if(xPosition + 20 < f.getXPosition() + f.getCollisionWidth() &&
				xPosition + collisionWidth > f.getXPosition() &&
				yPosition < f.getYPosition() + tileSize &&
				yPosition + collisionHeight > f.getYPosition() + (tileSize - f.getCollisionHeight())) 
		{

			hit(f.getDamage());
		}
	}

	/**
	 * Update action of player. Method will handle subsequent action if player is facing right or left, 
	 * being hit and touching block tiles.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

		if(left || right) {
			facingRight = right;
			dx = right ? 1.6 : -1.6;
		}
		else if(dx > 0 || dx < 0) {	
			dx = 0;
		}

		if((currentAction == ATTACKING || currentAction == FIRING) && !jumping && !falling) {
			dx = 0;
		}

		if(jumping && !falling) {
			dy = -4.8;
			falling = true;
		}

		if(isHit) {
			if((System.nanoTime() - hitTimer) / 1000000 > 1000) {
				isHit = false;
			}
		}

		if(falling) {
			if(dy > 0) {
				jumping = false;

				if(flying) {
					dy += 0.15 * 0.1;
				}
				else {
					dy += 0.15;
				}

				if(dy > 4) {
					dy = 4;
				}
			}
			else if(dy < 0 && !jumping) {
				dy += 0.3;
			}
			else {
				dy += 0.15;
			}
		}

		int currentRow = (int) (yPosition / tileSize);

		double nextX = xPosition + dx;
		double nextY = yPosition + dy;

		if(dx > 0) {
			if(map.getTileType((int) ((yPosition - collisionHeight / 2) / tileSize), (int) ((nextX + 30) / tileSize)) == Tile.BLOCKTILE ||
					map.getTileType((int) ((yPosition + collisionHeight / 2 - 1) / tileSize), (int) ((nextX + 30) / tileSize)) == Tile.BLOCKTILE) 
			{
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}
		else if(dx < 0) {
			if(map.getTileType((int) ((yPosition - collisionHeight / 2) / tileSize), (int)((nextX - 30) / tileSize)) == Tile.BLOCKTILE ||
					map.getTileType((int) ((yPosition + collisionHeight / 2 - 1) / tileSize), (int)((nextX - 30) / tileSize)) == Tile.BLOCKTILE) 
			{
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}

		if(dy > 0) {
			if(map.getTileType((int) ((nextY + collisionHeight / 2 - 1) / tileSize), (int) ((xPosition - collisionWidth / 2) / tileSize)) == Tile.BLOCKTILE || 
					map.getTileType((int) ((nextY + collisionHeight / 2 - 1) / tileSize), (int) ((xPosition + collisionWidth / 2 - 1) / tileSize)) == Tile.BLOCKTILE) 
			{
				dy = 0;
				falling = false;
				nextY = (currentRow + 1) * tileSize - collisionHeight / 2;
			}
			else {
				nextY += dy;
			}
		}
		else if(dy < 0) {
			if(map.getTileType((int) ((nextY - height / 2) / tileSize), (int) ((xPosition - collisionWidth / 2) / tileSize)) == Tile.BLOCKTILE || 
					map.getTileType((int) ((nextY - height / 2) / tileSize), (int) ((xPosition + collisionWidth / 2 - 1) / tileSize)) == Tile.BLOCKTILE) 
			{
				dy = 0;
				nextY = currentRow * tileSize + collisionHeight / 2;
			}
			else {
				nextY += dy;
			}
		}

		if(!falling) {
			if(!(map.getTileType((int) ((nextY + 1 + collisionHeight / 2 - 1) / tileSize), (int) ((xPosition - collisionWidth / 2) / tileSize)) == Tile.BLOCKTILE) && !(map.getTileType((int) ((nextY + 1 + collisionHeight / 2 - 1) / tileSize), (int) ((xPosition + collisionWidth / 2 - 1) / tileSize)) == Tile.BLOCKTILE)) {
				falling = true;
			}
		}

		if(firing && currentAction != FIRING) {
			if(numOfFire > 0) {
				new MediaPlayer(FIRESOUND).play();
				numOfFire--;
				FireBall fb = new FireBall(map, facingRight);
				if(facingRight) {
					fb.setPosition(xPosition + 30, yPosition);
				}
				else {
					fb.setPosition(xPosition, yPosition);
				}
				balls.add(fb);
			}
		}

		for(int i = 0; i < balls.size(); i++) {
			FireBall fb = balls.get(i);

			if(fb.shoudBeRemove()) {
				balls.remove(i);
				i--;
			}
			else {
				fb.update();
			}
		}

		xPosition = nextX;
		yPosition = nextY;

		updateAction();
		animation.update();
	}

	/**
	 * Update special action of player like firing, flying and attack.
	 */
	private void updateAction() {
		if(firing) {
			if(currentAction != FIRING) {
				currentAction = FIRING;
				animation.setFrames(sprites.get(FIRING));
				animation.setDelay(100);
				width = 60;
			}
			if(animation.playedOnce()) {
				firing = false;
			}
		}
		else if(dy > 0) {
			if(flying) {
				if(currentAction != FLYING) {
					currentAction = FLYING;
					animation.setFrames(sprites.get(FLYING));
					animation.setDelay(100);
					width = 60;
				}
			}
			else {
				if(currentAction != FALLING) {
					currentAction = FALLING;
					animation.setFrames(sprites.get(FALLING));
					animation.setDelay(100);
					width = 60;
				}
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				MediaPlayer p = new MediaPlayer(JUMPINGSOUND);
				p.setVolume(0.25);
				p.play();
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(30);
				width = 60;
			}
		}
		else if(attack) {
			if(currentAction != ATTACKING) {
				currentAction = ATTACKING;
				animation.setFrames(sprites.get(ATTACKING));
				animation.setDelay(60);
				width = 120;
			}
			if(animation.playedOnce()) {
				attack = false;
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
				width = 60;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 60;
			}
		}
	}

	/**
	 * Draw player on the game display regarding it is being hit and facing right or left.
	 * @param gc
	 */
	private void drawPlayer(GraphicsContext gc) {
		if(isHit) {
			if((System.nanoTime() - hitTimer) / 100000000 % 2 == 0) {
				return;
			}
		}

		if(facingRight) {
			gc.drawImage(animation.getImage(), xPosition + map.getX() - width / 2, yPosition - height / 2, width, height);
		}
		else {
			gc.drawImage(animation.getImage(), xPosition + map.getX() + width / 2 , yPosition - height / 2, -width, height);
		}
	}

	/**
	 * Draw FireBall and HP text display.
	 */
	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		drawPlayer(gc);

		for(int i = 0; i < balls.size(); i++) {
			balls.get(i).render(gc);
		}

		gc.setFont(font);
		gc.setFill(Color.WHITE);
		gc.fillText("FireBall: " + numOfFire + "/" + maxFire, 10, 20);
		gc.fillText("HP: " + hp + "/" + maxHp, 10, 50);
	}

	/**
	 * Handle keyboard pressed event.
	 */
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		if(keyCode == KeyCode.RIGHT) {
			right = true;
		}
		if(keyCode == KeyCode.LEFT) {
			left = true;
		}
		if(keyCode == KeyCode.UP) {
			jumping = true;
		}
		if(keyCode == KeyCode.SPACE) {
			flying = true;
		}
		if(keyCode == KeyCode.F) {
			firing = true;
		}
		if(keyCode == KeyCode.D) {
			if(dy == 0) {
				attack = true;
			}
		}
	}

	/**
	 * Handle keyboard released event.
	 */
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
		if(keyCode == KeyCode.RIGHT) {
			right = false;
		}
		if(keyCode == KeyCode.LEFT) {
			left = false;
		}
		if(keyCode == KeyCode.SPACE) {
			flying = false;
		}
	}
}
