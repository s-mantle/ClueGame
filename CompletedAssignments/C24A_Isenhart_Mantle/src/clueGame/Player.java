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
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
	protected boolean finished = false, isHuman = false;
	protected char room;
	
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
		this.room = '-';
	}
	
	public abstract void createAccusation(Card room, Card person, Card weapon);
	
	public void updateSeen(Card card) {
		seenCards.add(card);
		if(card.getCardType() == CardType.WEAPON) {
			seenWeapon = true;
		}
	}
	
	public void drawPlayer(Graphics g, int xPos, int yPos, int width, int height) {
		g.setColor(Color.BLACK);
		g.drawOval(xPos, yPos, width, height);
		g.setColor(playerColor);
		g.fillOval(xPos, yPos, width, height);
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

	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> ownCards = (ArrayList<Card>) playerDeck;
		Card person = suggestion.getPerson();
		Card room = suggestion.getRoom();
		Card weapon = suggestion.getWeapon();
		
		if (playerDeck.indexOf(person) == -1 && playerDeck.indexOf(room) == -1 && playerDeck.indexOf(weapon) == -1) {
			return null;
		}
		else {
			ArrayList<Card> matches = new ArrayList<>();
			
			for (Card card: playerDeck) {
				if (card.equals(person) || card.equals(room) || card.equals(weapon)) {
					matches.add(card);
				}
			}
				
			if (matches.size() == 1) {
				return matches.get(0);
			}
			else {
				Random random = new Random();
				return matches.get(random.nextInt(matches.size()));
			}
		}
	}
	
	public void drawPlayer(JPanel mainPanel) {
		// Update this to draw a circle on the cell instead of a square overriding the cell
		foregroundGraphic.setBackground(playerColor);
		
		mainPanel.add(foregroundGraphic);
		mainPanel.revalidate();
	}
	
	public void setFinished(boolean isFinished) {
		this.finished = isFinished;
	}
	
	public boolean getFinished() {
		return this.finished;
	}
	
	public void movePlayer(int newRow, int newCol) {
		this.row = newRow;
		this.col = newCol;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public void setHuman(boolean isHuman){
		this.isHuman = isHuman;
	}
	
	public boolean isHuman() {
		return this.isHuman;
	}
	
	public void setRoom(char room) {
		this.room = room;
	}

	public char getRoom() {
		return this.room;
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