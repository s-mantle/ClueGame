package experiment;

import java.util.Collections;
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
	private boolean partOfRoom = false;
	private boolean occupied = false;
	Set<TestBoardCell> boardCells = Collections.emptySet();


	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	public void addAdjacency(TestBoardCell cell) {
		boardCells.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return boardCells;
	}
	
	public void setRoom(boolean roomStatus) {
		this.partOfRoom = roomStatus;
	}
	
	public boolean isRoom() {
		return partOfRoom;
	}
	
	public void setOccupied(boolean occupiedStatus) {
		this.occupied = occupiedStatus;
	}
	
	public boolean getOccupied() {
		return occupied;
	}
	
}
