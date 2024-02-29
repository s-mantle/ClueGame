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
	private boolean isRoom = false;
	private boolean isOccupied = false;
	Set<TestBoardCell> adjList = Collections.emptySet();


	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void setRoom(boolean roomStatus) {
		this.isRoom = roomStatus;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupiedStatus) {
		this.isOccupied = occupiedStatus;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
}
