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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import java.util.Collections;

public abstract class Player {
	protected int row;
	protected int col;
	protected String name;
	protected Color playerColor;
	protected List<Card> playerDeck = new ArrayList<>();
	protected Set<Card> seenCards = new HashSet<>();
	protected boolean canPlay = true, seenWeapon = false, seenRoom = false, seenPerson=false;
	protected JPanel foregroundGraphic;
	
	/**
	 * Constructor for player, inputs all of the data into the player
	 * @param playerColor
	 * @param name
	 * @param row
	 * @param col
	 */
	public Player(Color playerColor, String name, int row, int col) {
		playerDeck = new ArrayList<>();
		this.name = name;
		this.playerColor = playerColor;
		this.row = row;
		this.col = col;
		this.foregroundGraphic = new JPanel();
	}
	
	public abstract void createAccusation(Card room, Card person, Card weapon);
	
	public void updateSeen(Card card) {
		seenCards.add(card);
		if(card.getCardType() == CardType.WEAPON) {
			seenWeapon = true;
		}
	}
	
	/**
	 * Updates the players cards that they are dealt
	 * @param card
	 */
	public void updateHand(Card card) {
		playerDeck.add(card);
	}
	
	public Set<Card> getSeenCards() {
		return seenCards;
	}

	/**
	 * Shows a player a card which invalidates their suggestion
	 * @param suggestionSet
	 * @return
	 */
	public Card disproveSuggestion(Set<Card> suggestionSet) {
		Set<Card> suggestion = new HashSet<>(suggestionSet);
		boolean foundCard = false;
		List<Card> returnList = new ArrayList<>();
		
		for(Card card: playerDeck) {
			if(suggestion.contains(card)) {
				returnList.add(card);
				foundCard = true;
			}
		}
		
		Collections.shuffle(returnList);
		
		if(foundCard)
			return returnList.remove(0);
		return null;
	}
	
	public void drawPlayer(JPanel mainPanel) {
		// Update this to draw a circle on the cell instead of a square overriding the cell
		foregroundGraphic.setBackground(playerColor);
		
		mainPanel.add(foregroundGraphic);
		mainPanel.revalidate();
	}
	
	//Used for testing
	public String getName() {
		return this.name;
	}
	
	public void TEST_ONLY_emptyCards() {
		this.playerDeck = new ArrayList<>();
		this.seenCards = new HashSet<>();
	}
	
	public void TEST_ONLY_setCards(Card card) {
		TEST_ONLY_emptyCards();
		this.seenCards.add(card);
	}
	
	//Used for testing
	public List<Card> getCards() {
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

	public boolean getCanPlay() {
		return canPlay;
	}
	
	public Color getPlayerColor() {
		return playerColor;
	}

	public void setCanPlay(boolean canPlay) {
		this.canPlay = canPlay;
	}
	

	@Override
	public String toString() {
		return "Player [name=" + name + ", playerColor=" + playerColor + ", playerDeck=" + playerDeck + "]";
	}
}