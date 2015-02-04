import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class Level1 is the first stage of the game, organising all location of different objects like enemies, coins and player.
 * Inside Level1, player and enemies will be checked death and damage continuously, and direct to game over or win scene. Also, class
 * named Level1 is for first level of game. Further development of levelx is feasible. 
 * @author kevingok
 *
 */
public class Level1 extends Scene {

	/**Declare Map instance.*/
	private Map map;
	/**Declare background instance.*/
	private Background background;

	/**Declare score of the new game play.*/
	private int score;
	/**Declare font of the normal words during game play.*/
	private Font font;

	/**Declare current option chosen in pause menu.*/
	private int currentOption;
	/**Declare options can be chosen in pause menu.*/
	private String[] options;
	
	/**Declare indicator whether game is stop*/
	private boolean stop;

	/**Declare player instance*/
	private Player player;
	/**Declare a bunch of enemies*/
	private ArrayList<Enemy> enemies;
	/**Declare a bunch of coins*/
	private ArrayList<Coin> coins;
	/**Declare a bunch of flowers*/
	private ArrayList<EatPeopleFlower> flowers;

	/**
	 * Class constructor defines start of the level and run initialization.
	 */
	public Level1() {
		stop = false;
		init();
	}

	/**
	 * Initiate all variables need in game play of Level 1. 
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		map = new Map("Resources/level1-1.txt", 60);
		map.setPosition(0);

		background = new Background("background.png", 0.1);

		score = 0;
		font = Font.font("Arial", FontWeight.NORMAL, 24);

		player = new Player(map);
		player.setPosition(100, 100);

		enemies = new ArrayList<Enemy>();
		createEnemies();

		coins = new ArrayList<Coin>();		
		createCoins();
		
		flowers = new ArrayList<EatPeopleFlower>();
		createFlowers();
		
		currentOption = 0;
		options = new String[] {
				"Resume",
				"Quit"
		};
	}

	/**
	 * Create enemies(flower) in dedicated coordinates on the map.
	 */
	private void createFlowers() {
		for(int i = 0; i < 10; i++) {
			EatPeopleFlower f = new EatPeopleFlower(map);
			f.setPosition(995 + i * 30, 400);
			flowers.add(f);
		}
		
		for(int i = 0; i < 12; i++) {
			EatPeopleFlower f = new EatPeopleFlower(map);
			f.setPosition(2310 + i * 30, 400);
			flowers.add(f);
		}
	}

	/**
	 * Create enemies(Mushroom) in dedicated coordinates on the map.
	 */
	private void createEnemies() {
		Enemy boss = new Boss(map);
		boss.setPosition(6300, 350);
		enemies.add(boss);
		
		int[][] enemiesCoord = new int[][] {
				{300, 200},
				{1530, 390},
				{1955, 390},
				{2930, 390},
				{3290, 390},
				{3570, 390},
				{4400, 390},
				{4600, 390},
				{4800, 390}
		};
		
		for(int i = 0; i < enemiesCoord.length; i++) {
			Enemy mushroom = new Mushroom(map);
			mushroom.setPosition(enemiesCoord[i][0], enemiesCoord[i][1]);
			enemies.add(mushroom);
		}
	}

	/**
	 * Create coins in dedicated coordinates on the map.
	 */
	private void createCoins() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Resources/coin.txt"));
			
			String line = null;
			while((line = br.readLine()) != null) {
				String[] coords = line.split(",");
				int x = Integer.parseInt(coords[0].trim());
				int y = Integer.parseInt(coords[1].trim());
				
				Coin c = new Coin(map);
				c.setPosition(x, y);
				coins.add(c);
			}
			
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Update status of all sources of moving game object. Such method is the most important one for handling all events happened during gamrplay.
	 * For example, if player is dead, update() will validate player's HP and direct scene to GameOverScene. Other handling event, namely coin detection,
	 * enemy damage detection, Boss behavior, etc.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(stop) {
			return;
		}

		map.setPosition(Game.WIDTH / 2 - player.getXPosition());

		player.update();
		if(player.isDead()) {
			Score finalScore = new Score("Player", score);
			ScoreSystem.getInstance().addScoreRecord(finalScore);
			SceneManager.getInstance().changeSceneLevel(SceneManager.GAMEOVERSCENE);
		}

		for(int i = 0; i < coins.size(); i++) {
			Coin c = coins.get(i);
			c.update();

			if(player.gotCoin(c)) {
				c.gotIt();
			}

			if(c.isGot()) {
				score += 10;
				coins.remove(i);
				i--;
			}
		}

		Enemy boss = enemies.get(0);
		
		for(int i = 1; i < enemies.size(); i++) {
			Enemy mushroom = enemies.get(i);

			mushroom.update();

			if(player.intersects(mushroom)) {
				player.hit(mushroom.getDamage());
			}

			player.checkHit(mushroom);

			if(mushroom.isDead()) {
				score += 5;
				enemies.remove(i);
				i--;
			}
		}

		for(EatPeopleFlower f : flowers) {
			f.update();
			player.checkAteByFlower(f);
		}
		
		if(player.getXPosition() > 5850) {
			boss.update();
			boss.checkHit(player);
			if(player.intersects(boss)) {
				player.hit(boss.getDamage());
			}
			player.checkHit(boss);
			if(boss.isDead()) {
				enemies.remove(0);
				score += 100;
				Score finalScore = new Score("Player", score);
				ScoreSystem.getInstance().addScoreRecord(finalScore);
				SceneManager.getInstance().changeSceneLevel(SceneManager.WINSCENE);
			}

			int[][] mapData = map.getMap();
			for(int i = 5; i < 7; i++) {
				mapData[i][94] = 3;
				mapData[i][97] = 4;

				mapData[i][95] = 22;
				mapData[i][96] = 22;
			}

			if((player.facingRight() && (player.getXPosition() < boss.getXPosition()) && !boss.facingRight()) ||
					(!player.facingRight() && (player.getXPosition() > boss.getXPosition()) && boss.facingRight())) {
				boss.startFiring();
			}

			if(player.getYPosition() < 300) {
				boss.startJumping();
			}
		}
	}

	/**
	 * Draw out the level 1 game scene including background, map, player, enemies, coins and HP display gauge.
	 */
	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		background.render(gc);
		map.render(gc);
		player.render(gc);

		for(Enemy e : enemies) {
			e.render(gc);
		}

		for(Coin c : coins) {
			c.render(gc);
		}
		
		for(EatPeopleFlower f : flowers) {
			f.render(gc);
		}

		gc.setFont(font);
		gc.setFill(Color.WHITE);
		gc.fillText("Score: " + score, 500, 20);

		if(stop) {
			gc.setFill(Color.rgb(200, 200, 200, 0.5));
			gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			drawOption(gc);
		}
	}

	/**
	 * Draw out the option of pause game menu scene.
	 * @param gc		GraphicsContext of pause option scene.
	 */
	private void drawOption(GraphicsContext gc) {
		gc.setFont(font);

		for(int i = 0; i < options.length; i++) {
			if(i == currentOption) {
				gc.setFill(Color.RED);
			}
			else {
				gc.setFill(Color.BLACK);
			}

			gc.fillText(options[i], 290, 240 + i * 30);
		}
	}

	/**
	 * Handle keyboard pressed event
	 */
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		if(!stop) {
			player.keyPressed(keyCode);
		}

		if(stop) {
			if(keyCode == KeyCode.UP) {
				currentOption --;
				if(currentOption < 0) {
					currentOption = options.length - 1;
				}
			}

			if(keyCode == KeyCode.DOWN) {
				currentOption++;
				if(currentOption == options.length) {
					currentOption = 0;
				}
			}

			if(keyCode == KeyCode.ENTER) {
				stop = !stop;
				if(currentOption == options.length - 1) {
					SceneManager.getInstance().changeSceneLevel(SceneManager.MENU);
				}
			}
		}

		if(keyCode == KeyCode.ESCAPE) {
			stop = !stop;
		}
	}
	
	/**
	 * Handle keyboard released event
	 */
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
		player.keyReleased(keyCode);
	}

}
