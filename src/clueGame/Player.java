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
	
	public Player(Color playerColor, String name, int row, int col) {
		playerDeck = new HashSet<>();
		this.name = name;
		this.playerColor = playerColor;
		this.row = row;
		this.col = col;
	}
	
	public void updateHand(Card card) {
		playerDeck.add(card);
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Set<Card> getCards() {
		return this.playerDeck;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}