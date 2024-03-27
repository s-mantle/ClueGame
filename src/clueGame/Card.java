package clueGame;

public class Card{
	private String cardName;
	private CardType cardType;
		
	public Card(String cardName) {
		this.cardName = cardName;
	}
	
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
}