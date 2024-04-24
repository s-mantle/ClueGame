/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 29 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * Card: Creates cards and stores their name and card type
 */
package clueGame;

import java.util.Objects;

public class Card{
	private String cardName;
	private CardType cardType;
	private Player origin;
	
	/**
	 * Constructor to create a new card
	 * @param cardName
	 * @param cardType
	 */
	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
		this.origin = null;
	}
	
	/**
	 * Custom equals method for comparing card to each other, compare their names and card types
	 */
	public boolean equals(Card comparedTo) {
		return ((comparedTo.getName().equals(cardName)) && (comparedTo.getCardType() == this.cardType));
	}
	
	public void setOrigin(Player player) {
		this.origin = player;
	}
	
	//Used for testing
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	//Used for testing
	public String getName() {
		return this.cardName;
	}
	
	//Used for testing
	public CardType getCardType() {
		return this.cardType;
	}

	//Used for testing
	@Override
	public String toString() {
		return "Card [cardName=" + cardName + "]";
	}

	public void setName(String name) {
		this.cardName = name;
	}
}