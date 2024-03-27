package clueGame;

import java.awt.Color;
import java.util.Set;

public class Player {
	private int row;
	private int col;
	private String name;
	private Color playerColor;
	private Set<Card> playerDeck;
	
	public Player(Color playerColor, String name) {
		this.name = name;
		this.playerColor = playerColor;
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
	
}