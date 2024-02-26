package experiment;

import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private boolean partOfRoom;
	private boolean occupied;
	Set<TestBoardCell> boardCells;
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	void addAdjacency(TestBoardCell cell) {
		boardCells.add(cell);
	}
	
	Set<TestBoardCell> getAdjList() {
		return boardCells;
	}
	
	void setRoom(boolean roomStatus) {
		this.partOfRoom = roomStatus;
	}
	
	boolean isRoom() {
		return partOfRoom;
	}
	
	void setOccupied(boolean occupiedStatus) {
		this.occupied = occupiedStatus;
	}
	
	boolean getOccupied() {
		return occupied;
	}
	
}
