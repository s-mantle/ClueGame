package tests;


import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import experiment.TestBoard;
import experiment.TestBoardCell;


/**
 * Authors: Sam Mantle & Ben Isenhart
 * Date 2 - 26 - 2024
 * Collaborators: None
 * Sources: None
 * 
 * BoardTestsExp: This class creates and implements 18 unique test cases to ensure that TestBoardCell & TestBoard are working as intended.
 * These tests primarily pertain to movement validation under a variety of circumstances (room presence, player presence, edges, corners, etc.)
 */
public class BoardTestsExp {
	TestBoard board;

	@BeforeEach
	public void setUp() throws FileNotFoundException{
		board = new TestBoard();
	}
	
	/*
	 * The testAdjaceny# test cases all validate which squares a player is adjacent to given their current starting tile. These test cases
	 * contain anywhere from 2 - 4 cells in their adjacency lists based on whether the player is at a corner (2 adjacent cells), at an edge
	 * (3 adjacent cells), or in the "center" (4 adjacent cells)
	 */
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
				
		Assert.assertTrue(testList.contains(board.getCell(1,  1)));	// Edited from (1, 0) -> (1, 1). Was impossible before
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
	
	/*
	 * The testTargetsNormal# test cases all validate which squares a player may move to given a starting cell and a number rolled on the die.
	 * There is assumed to be no rooms or players obstructing movement for these cases. The test cases vary primarily by roll (1 - 6), but also
	 * the final test case starts at a different position. There is a variable number of possible moves which are accounted for on a case by 
	 * case basis.
	 */
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
				
		Assert.assertEquals(6, targets.size());	// Edited from 4 -> 6 due to the realization of new possible cells
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));	// Added, we didn't consider this movement
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));	// Added, we didn't consider this movement
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

	/*
	 * The testTargetsRoom#, testTargetsOccupied#, & testTargetsMixed# test cases all validate which squares a player may move to given a 
	 * starting cell and a number rolled on the die. However, unlike the previous test cases, there are rooms and/ or players obstructing movement.
	 * There is a variable number of possible moves which are accounted for on a case by case basis, and the presence of players or rooms also 
	 * creates different blockers to movement or ending cells respectively.
	 */
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
				
		Assert.assertEquals(3, targets.size());	// Edited from 2 -> 3, we literally just typed the wrong number
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
		
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
	}
}
