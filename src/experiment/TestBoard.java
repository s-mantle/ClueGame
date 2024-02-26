package experiment;

import java.util.Set;

public class TestBoard {
	Set<TestBoardCell> targets;
	
	void calcTargets(TestBoardCell startCell, int pathlength) {
		targets.add(startCell);
	}
	
	TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(0, 0);
	}
	
	Set<TestBoardCell> getTargets() {
		return targets;
	}
}
