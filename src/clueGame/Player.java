/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 29 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * Player: Stores information pertaining to the player
 */
package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private int row;
	private int col;
	private String name;
	private Color playerColor;
	private Set<Card> playerDeck = new HashSet<>();
	
	/**
	 * Constructor for player, inputs all of the data into the player
	 * @param playerColor
	 * @param name
	 * @param row
	 * @param col
	 */
	public Player(Color playerColor, String name, int row, int col) {
		playerDeck = new HashSet<>();
		this.name = name;
		this.playerColor = playerColor;
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Updates the players cards that they are dealt
	 * @param card
	 */
	public void updateHand(Card card) {
		playerDeck.add(card);
	}
	
	//Used for testing
	public String getName() {
		return this.name;
	}
	
	//Used for testing
	public Set<Card> getCards() {
		return this.playerDeck;
	}

	//Used for testing
	public int getRow() {
		return row;
	}

	//Used for testing
	public int getCol() {
		return col;
	}
}