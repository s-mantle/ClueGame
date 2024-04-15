/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 04 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * BoardCell: Stores all information pertaining to the cell and its characteristics
 * 
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardCell {
	//Cells position on the board
	private int row;
	private int col;
	
	//If the cell is a doorway and its direction
	private boolean isDoorway;
	private DoorDirection doorDirection;
	
	//Cells center and label
	private boolean isRoomLabel;
	private boolean isRoomCenter;
	
	//Cells secretPassage connection
	private char secretPassage;
	
	//Cell is a room
	private boolean isRoom;
	
	//Cell is occupied by another player
	private boolean isOccupied;
	
	//Cells letter
	private char letter;
	
	//Cells adj list of where a player can move
	private Set<BoardCell> adjList;
	
	private JPanel backgroundGraphic;
	private JPanel doorGraphic;
	private final static Color LIGHT_YELLOW = new Color(255, 255, 204);
	private final static Color BROWN = new Color(139, 69, 19);
	

	/**
	 * Sets up the basic variables in TestBoardCell
	 * 
	 * @param row
	 * @param col
	 */
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.isDoorway = false;
		this.isRoomLabel = false;
		this.isRoomCenter = false;
		this.secretPassage = '-';
		this.isRoom = false;
		this.isOccupied = false;
		this.letter = ' ';
		this.adjList = new HashSet<BoardCell>();
		this.backgroundGraphic = new JPanel();
		this.doorGraphic = new JPanel();
	}
	
	public void drawCell(JPanel mainPanel) {
		if (letter == 'X') {
			backgroundGraphic.setBackground(Color.BLACK);
		}
		else if (isRoom) {
			backgroundGraphic.setBackground(Color.LIGHT_GRAY);
		}
		else if (!isRoom){
			// Either need to make Yellow a nonplayable color or adjust the background hue. I'm okay with either
			backgroundGraphic.setBackground(LIGHT_YELLOW);
			backgroundGraphic.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		
		mainPanel.add(backgroundGraphic);
		mainPanel.revalidate();
	}
	
	public void drawDoor(String side, int doorWidth, int doorHeight) {
		backgroundGraphic.setLayout(new BorderLayout());
		doorGraphic.setPreferredSize(new Dimension(doorWidth, doorHeight));
		doorGraphic.setBackground(BROWN);
		backgroundGraphic.add(doorGraphic, side);
		backgroundGraphic.revalidate();
	}
	
	public void drawRoomLabel(String roomName) {
		JLabel roomLabel = new JLabel(roomName);
		// Source: https://www.codejava.net/java-se/swing/jlabel-basic-tutorial-and-examples
		roomLabel.setFont(new java.awt.Font("Impact", Font.ITALIC, 20));
		roomLabel.setForeground(Color.BLUE);
		
		backgroundGraphic.add(roomLabel);
		backgroundGraphic.revalidate();
	}
	
	public boolean containsClick(int mouseX, int mouseY, int startX, int startY, int cellWidth, int cellHeight) {
		Rectangle rect = new Rectangle(startX, startY, cellWidth, cellHeight);
		if (rect.contains(new Point(mouseX, mouseY))) {
			return true;
		}
		return false;
	}
	
	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	/**
	 * Adds a cell to the adjacency list
	 * 
	 * @param cell
	 */
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	/**
	 * Returns adjacency list
	 */
	public Set<BoardCell> getAdjList() {
		return this.adjList;
	}
	
	@Override
	public String toString() {
		return "("+row + ", " + col+")";
	}

	/**
	 * Sets the cells door direction
	 * 
	 * @param direction
	 */
	public void setDoorDirection(DoorDirection direction) {
		this.doorDirection = direction;
	}
	
	/**
	 * Returns the cells door direction
	 */
	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}
	
	/**
	 * Helper method to return the letter that the cell is
	 */
	public char getLetter() {
		return this.letter;
	}
	
	/**
	 * Sets the cells letter to the given 
	 * @param letter
	 */
	public void setLetter(char letter) {
		this.letter = letter;
	}

	/**
	 * Sets isRoom variable to true 
	 * 
	 * @param roomStatus
	 */
	public void setRoom(boolean roomStatus) {
		this.isRoom = roomStatus;
	}
	
	/**
	 * Checks is a cell is a room
	 */
	public boolean isRoom() {
		return this.isRoom;
	}
	
	/**
	 * Sets a cell to occupied if a player is on it
	 * 
	 * @param occupiedStatus
	 */
	public void setOccupied(boolean occupiedStatus) {
		this.isOccupied = occupiedStatus;
	}
	
	/**
	 * Returns if a cell is occupied
	 */
	public boolean getOccupied() {
		return this.isOccupied;
	}
	
	/**
	 * Sets the cells doorway status
	 * 
	 * @param status
	 */
	public void setDoorway(boolean status) {
		this.isDoorway = status;
	}
	
	/**
	 * Returns if a cell is a doorway
	 */
	public boolean isDoorway() {
		return this.isDoorway;
	}
	

	/**
	 * Sets if the cell is a roomLabel
	 * @param status
	 */
	public void setRoomLabel(boolean status) {
		this.isRoomLabel = status;
	}
	
	/**
	 * Returns if the cell is a label cell
	 */
	public boolean isLabel() {
		return this.isRoomLabel;
	}
	
	/**
	 * Sets if the cell is a room center
	 * 
	 * @param status
	 */
	public void setRoomCenter(boolean status) {
		this.isRoomCenter = status;
	}
	
	/**
	 * Returns if the cell is a room center
	 */
	public boolean isRoomCenter() {
		return this.isRoomCenter;
	}
	
	/**
	 * Sets the cells secret passage connection
	 * 
	 * @param letter
	 */
	public void setSecretPassage(char letter) {
		this.secretPassage = letter;
	}
	
	/**
	 * Returns the cells secret passage connection
	 */
	public char getSecretPassage() {
		return this.secretPassage;
	}
}
