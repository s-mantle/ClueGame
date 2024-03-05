package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	//Cells are light orange
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the Armory that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(1, 1);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(13, 11)));
		
		// now test the Jailroom
		testList = board.getAdjList(9, 1);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(11, 4)));
		assertTrue(testList.contains(board.getCell(7, 2)));
		
		// one more room, the Fireplace
		testList = board.getAdjList(12, 6);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(11, 4)));
		assertTrue(testList.contains(board.getCell(11, 8)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(3, 8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2,6)));
		assertTrue(testList.contains(board.getCell(3,9)));

		testList = board.getAdjList(7, 2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(7, 1)));
		assertTrue(testList.contains(board.getCell(7,3)));
		assertTrue(testList.contains(board.getCell(9,1)));
		
		testList = board.getAdjList(6, 9);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(6,8)));
		assertTrue(testList.contains(board.getCell(5,9)));
		assertTrue(testList.contains(board.getCell(8,9)));
		assertTrue(testList.contains(board.getCell(5,11)));
	}
	
	// Test a variety of walkway scenarios
		// These tests are Dark Orange on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			// Test on bottom edge of board, just one walkway piece
			Set<BoardCell> testList = board.getAdjList(10, 0);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(10, 1)));
			
			testList = board.getAdjList(0, 4);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(1, 4)));
			
			testList = board.getAdjList(10, 12);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(10, 11)));
			
			testList = board.getAdjList(14, 4);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(13, 4)));
			
			//Test next to room but not a doorway
			testList = board.getAdjList(4, 1);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(4, 0)));
			assertTrue(testList.contains(board.getCell(3, 1)));
			assertTrue(testList.contains(board.getCell(4, 2)));
			
			// Test near a door but not adjacent
			testList = board.getAdjList(7, 8);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(6,8)));
			assertTrue(testList.contains(board.getCell(8,8)));
			assertTrue(testList.contains(board.getCell(7,9)));

			// Test adjacent to walkways
			testList = board.getAdjList(9, 4);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(9,3)));
			assertTrue(testList.contains(board.getCell(9,5)));
			assertTrue(testList.contains(board.getCell(8,4)));
			assertTrue(testList.contains(board.getCell(10,4)));

			// Test next to rooms
			testList = board.getAdjList(9,14);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(9, 15)));
			assertTrue(testList.contains(board.getCell(8, 14)));
			assertTrue(testList.contains(board.getCell(10, 14)));
		
		}
		
		
		// Tests out of room center, 1, 3 and 4
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsInBalletRoom() {		
			// test a roll of 1
			board.calcTargets(board.getCell(2, 6), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(3,4)));
			assertTrue(targets.contains(board.getCell(3,8)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(2, 6), 3);
			targets= board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCell(3,2)));
			assertTrue(targets.contains(board.getCell(4,5)));	
			assertTrue(targets.contains(board.getCell(3,10)));
			assertTrue(targets.contains(board.getCell(1,8)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(2, 6), 4);
			targets= board.getTargets();
			assertEquals(22, targets.size());
			assertTrue(targets.contains(board.getCell(4, 6)));
			assertTrue(targets.contains(board.getCell(1, 1)));	
			assertTrue(targets.contains(board.getCell(6, 8)));
			assertTrue(targets.contains(board.getCell(1, 11)));	
			
			//test not center cell
			board.calcTargets(board.getCell(1, 5), 4);
			targets= board.getTargets();
			assertEquals(0, targets.size());
		}
		
		@Test
		public void testTargetsInEngineRoom() {
			// test a roll of 1
			board.calcTargets(board.getCell(13, 11), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(11, 10)));
			assertTrue(targets.contains(board.getCell(1, 1)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(13, 11), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(1, 1)));
			assertTrue(targets.contains(board.getCell(12, 9)));	
			assertTrue(targets.contains(board.getCell(9, 11)));
			assertTrue(targets.contains(board.getCell(10, 11)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(13, 11), 4);
			targets= board.getTargets();
			assertEquals(11, targets.size());
			assertTrue(targets.contains(board.getCell(10, 10)));
			assertTrue(targets.contains(board.getCell(12, 6)));	
			assertTrue(targets.contains(board.getCell(11, 11)));
			assertTrue(targets.contains(board.getCell(1, 1)));	
			
			//test not center cell
			board.calcTargets(board.getCell(13, 10), 4);
			targets= board.getTargets();
			assertEquals(0, targets.size());
		}

		
}