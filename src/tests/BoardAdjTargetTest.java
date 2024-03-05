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
	
	
	
}