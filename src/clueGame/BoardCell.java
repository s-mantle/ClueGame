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
	private int row, col;	
	//If the cell is a doorway and its direction
	private DoorDirection doorDirection;
	private boolean isDoorway, isRoomLabel, isRoomCenter, isRoom, isOccupied, isTarget = false;
	//Cells secretPassage connection
	private char letter, secretPassage;	
	//Cells adj list of where a player can move
	private Set<BoardCell> adjList;
	
	private final static Color LIGHT_YELLOW = new Color(255, 255, 204);
	private final static Color BROWN = new Color(139, 69, 19);
	private JLabel roomLabel;
	
	/**
	 * Sets up the basic variables in TestBoardCell
	 * 
	 * @param row
	 * @param col
	 */
	public BoardCell(int row, int col) {
		super();
		this.row = row; this.col = col;
		this.secretPassage = '-';
		this.letter = ' ';
		this.adjList = new HashSet<BoardCell>();
		this.roomLabel = null;
	}
	
	public void drawCell(Graphics g, boolean target, int rowPos, int colPos, int cellWidth, int cellHeight) {
		// If the cell is a nonplayable area
		Color cellBorder = Color.BLACK;
		Color cellFilling = LIGHT_YELLOW;
		int borderWidth = cellWidth;
		int borderHeight = cellHeight;
		
		if (letter == 'W') {
			cellWidth -= 1;
			cellHeight -= 1;
		}
		if (target) {
			cellFilling = Color.GREEN;
		}
		else if (letter == 'X') {
			cellFilling = Color.BLACK;
		}
		else if (isRoom) {
			cellFilling = Color.LIGHT_GRAY;;
		}
		
		g.setColor(cellBorder);
		g.fillRect(rowPos, colPos, borderWidth, borderHeight);
		g.setColor(cellFilling);
		g.fillRect(rowPos, colPos, cellWidth, cellHeight);
	}
	
	public void drawDoor(Graphics g, int rowPos, int colPos, int cellWidth, int cellHeight) {
		if (isDoorway) {
			g.setColor(BROWN);
			int doorAdj = 5;
			
			if (getDoorDirection() == DoorDirection.UP) {
				g.fillRect(rowPos, colPos - doorAdj, cellWidth, doorAdj);
			}
			else if (getDoorDirection() == DoorDirection.DOWN) {
				g.fillRect(rowPos, colPos + cellHeight, cellWidth, doorAdj);	
			}
			else if (getDoorDirection() == DoorDirection.LEFT) {
				g.fillRect(rowPos - doorAdj, colPos, doorAdj, cellHeight);
			}
			else if (getDoorDirection() == DoorDirection.RIGHT) {
				g.fillRect(rowPos + cellWidth, colPos, doorAdj, cellHeight);
			}
		}
		
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
	
	public boolean isTarget() {
		return this.isTarget;
	}
	
	public void setTarget(boolean target) {
		this.isTarget = target;
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
	
	public boolean getRoomLabel() {
		return isRoomLabel;
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
