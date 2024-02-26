package tests;


import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;

	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	@Test
	void testAdjacency0() {
		TestBoardCell cell = board.getCell(2, 2);
		Set<TestBoardCell> testList = cell.getAdjList();
		
		Assert.assertTrue(testList.contains(board.getCell(2,  1)));
		Assert.assertTrue(testList.contains(board.getCell(2,  3)));
		Assert.assertTrue(testList.contains(board.getCell(3,  2)));
		Assert.assertTrue(testList.contains(board.getCell(1,  2)));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	void testAdjacency1() {
		TestBoardCell cell = board.getCell(0, 1);
		Set<TestBoardCell> testList = cell.getAdjList();
		
		Assert.assertTrue(testList.contains(board.getCell(1,  0)));
		Assert.assertTrue(testList.contains(board.getCell(0,  2)));
		Assert.assertTrue(testList.contains(board.getCell(0,  0)));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	void testAdjacency2() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		
		Assert.assertTrue(testList.contains(board.getCell(1,  0)));
		Assert.assertTrue(testList.contains(board.getCell(0,  1)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	void testAdjacency3() {
		TestBoardCell cell = board.getCell(3, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		
		Assert.assertTrue(testList.contains(board.getCell(2,  3)));
		Assert.assertTrue(testList.contains(board.getCell(3,  2)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	void testAdjacency4() {
		TestBoardCell cell = board.getCell(1, 1);
		Set<TestBoardCell> testList = cell.getAdjList();
		
		Assert.assertTrue(testList.contains(board.getCell(1,  0)));
		Assert.assertTrue(testList.contains(board.getCell(0,  1)));
		Assert.assertTrue(testList.contains(board.getCell(2,  1)));
		Assert.assertTrue(testList.contains(board.getCell(1,  2)));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	void testTargetsNormal1() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
	void testTargetsNormal2() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
	}
	
	@Test
	void testTargetsNormal3() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
	}
	
	@Test
	void testTargetsNormal4() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> targets = board.getTargets();
		
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
	}
	
	@Test
	void testTargetsNormal5() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 5);
		Set<TestBoardCell> targets = board.getTargets();
		
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
	}
	
	@Test
	void testTargetsNormal6() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
	}
	
	@Test
	void testTargetsNormal0() {
		TestBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
	}
	
	@Test
	void testTargetsRoom1() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(1, 0).setRoom(true);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
	void testTargetsRoom2() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(1, 0).setRoom(true);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
	void testTargetsOccupied1() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(1, 0).setOccupied(true);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
	}
	
	@Test
	void testTargetsOccupied2() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(1, 0).setOccupied(true);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
	}
	
	@Test
	void testTargetsMixed1() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(1, 0).setOccupied(true);
		board.getCell(0, 2).setRoom(true);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
	}
	
	@Test
	void testTargetsMixed2() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(1, 0).setOccupied(true);
		board.getCell(1, 1).setRoom(true);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
	}
}
