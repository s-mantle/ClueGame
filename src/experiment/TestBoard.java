package experiment;

import java.util.Collections;
import java.util.Set;

/**
 * Authors: Ben Isenhart & Sam Mantle
 * Date 2 - 26 - 2024
 * Collaborators: None
 * Sources: None
 * 
 * TestBoard: This class creates a fictitious board of test cells (again without any concrete implementations) to be tested
 */
public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets = Collections.emptySet();
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		
		//Should we be filling test board with the cells that are in it here?
		//Grid will be a 4 by 4 right now
		
	}
	
	//Use gird to calculate the adjacency list between each cell
	public void calcAdjList() {
		
	}
	
	//Calculates the targets that the player can get to
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	//Does this return the cells adjacency list?
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell temp = new TestBoardCell(row, col);
		return temp;
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}
