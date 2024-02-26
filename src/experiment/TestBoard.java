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
	Set<TestBoardCell> targets = Collections.emptySet();
	
	public TestBoard() {
		
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
//		targets.add(startCell);
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell temp = new TestBoardCell(row, col);
		return temp;
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}
