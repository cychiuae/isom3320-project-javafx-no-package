/**
 * Abstract class Character is to regulates possible statues and 
 * behaviors of a any character in the game.
 * 
 * @author kevingok
 */
public abstract class Character extends GameObject {

	/**Declare the health point of a character.*/
	protected int hp;
	/**Declare the maximum health point of a character.*/
	protected int maxHp;
	
	/**Declare an indicator on whether the character is dead.*/
	protected boolean isDead;
	/**Declare an indicator on whether the character is facing right.*/
	protected boolean facingRight;
	/**Declare an indicator on whether the character is hit.*/
	protected boolean isHit;
	/**Declare the time when a character is hit.*/
	protected long hitTimer;
	
	/**
	 * Class constructor to create a character on the map of the game.
	 * @param map		Map instance of a level
	 */
	public Character(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
	}

	 /**
	  * Retrieve the current health point of a character.
	  * @return 	Current health point 
	  */
	public int getHp() {
		return hp;
	}
	
	/**
	 * Retrieve the maximum health point of a character.
	 * @return 		Maximum health point 
	 */
	public int getMaxHP() {
		return maxHp;
	}
	
	/**
	 * Retrieve condition whether a character is dead. 
	 * @return		Condition whether a character is dead
	 */
	public boolean isDead() {
		return isDead;
	}
	
	/**
	 * Retrieve whether a character is facing right or left.
	 * @return		True = Right, False = Left
	 */
	public boolean facingRight() {
		return facingRight;
	}
	
	/**
	 * To trigger the deduction of health point when the character is hit.
	 * @param damage		Damage dealt by the character
	 */
	public void hit(int damage) {
		if(isDead || isHit) {
			return;
		}
		
		hp -= damage;
		if(hp < 0) {
			hp = 0;
		}
		if(hp == 0) {
			isDead = true;
		}
		
		isHit = true;
		hitTimer = System.nanoTime();
	}
}
