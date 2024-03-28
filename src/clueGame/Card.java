package clueGame;

import java.util.Objects;

public class Card{
	private String cardName;
	private CardType cardType;
		
	public Card(String cardName) {
		this.cardName = cardName;
	}
	
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	public String getName() {
		return this.cardName;
	}
	
	public CardType getCardType() {
		return this.cardType;
	}

	@Override
	public boolean equals(Object obj) {
		Card other = (Card) obj;
		return Objects.equals(cardName, other.cardName) && cardType == other.cardType;
	}


}