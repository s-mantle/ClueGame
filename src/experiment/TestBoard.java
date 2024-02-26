package experiment;

import java.util.Collections;
import java.util.Set;

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
