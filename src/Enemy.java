import javafx.scene.input.KeyCode;

/**Abstract class Enemy regulates general behavior of any types of enemies, namely
 * Mushroom, eat people flower and the boss
 * 
 * @author kevingok
 */
public abstract class Enemy extends Character {

	/**Declare damage to player due to collision*/
	protected int damage;
	
	/**
	 * Class constructor initializes all useful information for an enemy with a map instance
	 *  to reveal specified location on the map.
	 * @param map		Map instance of the level.
	 */
	public Enemy(Map map) {
		super(map);
	}
	
	/**
	 * Retrieve damage of enemy made to player if collision happens.
	 * @return		Value of damage made on each collision to player's HP. 
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Handle Keyboard pressed event.
	 */
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
	}

	/**
	 * Handle Keyboard released event.
	 */
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub	
	}

	/**
	 * Handle enemy action if enemy fired.
	 */
	public abstract void startFiring();
	
	/**
	 * Handle enemy action if enemy jumped.
	 */
	public abstract void startJumping();

	/**
	 * Validation to check whether collision happens between players and
	 * enemies, further action proceeds like reduce HP of player.
	 * @param player		Player object of the game.
	 */
	public abstract void checkHit(Player player);
}
