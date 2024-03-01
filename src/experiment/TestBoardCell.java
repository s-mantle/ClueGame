package experiment;

import java.util.HashSet;
import java.util.Set;

/**
 * Authors: Ben Isenhart & Sam Mantle
 * Date 2 - 26 - 2024
 * Collaborators: None
 * Sources: None
 * 
 * TestBoardCell: This class creates a fictitious board cell (without any concrete implementations) to be tested
 */
public class TestBoardCell {
	private int row;
	private int col;
	private boolean isRoom;
	private boolean isOccupied;
	private String letter;
	private Set<TestBoardCell> adjList;

	/**
	 * Sets up the basic variables in TestBoardCell
	 * 
	 * @param row
	 * @param col
	 */
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.isRoom = false;
		this.isOccupied = false;
		this.adjList = new HashSet<TestBoardCell>();
	}
	
	/**
	 * Helper method to return the letter that the cell is
	 * W-Walkway
	 * R-Room
	 * O-Occupied
	 */
	public String getLetter() {
		return letter;
	}
	
	/**
	 * Sets the cells letter to the given 
	 * @param letter
	 */
	public void setLetter(String letter) {
		this.letter = letter;
	}
	
	/**
	 * Adds a cell to the adjacency list
	 * 
	 * @param cell
	 */
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	/**
	 * Returns adjacency list
	 */
	public Set<TestBoardCell> getAdjList() {
		return adjList;
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
		return isRoom;
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
		return isOccupied;
	}
	
}
