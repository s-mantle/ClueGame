package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileInitTests {
	public static final int ROWS = 15;
	public static final int COLS = 13;
	public static final int LEGEND_SIZE = 12;

	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testLayoutAndSetupLoaded() {
		assertEquals("Armory", board.getRoom('A').getName());
		assertEquals("Ballet Room", board.getRoom('B').getName());
		assertEquals("Choir Hall", board.getRoom('C').getName());
		assertEquals("Dungeon", board.getRoom('D').getName());
		assertEquals("Engine Room", board.getRoom('E').getName());
		assertEquals("Fireplace", board.getRoom('F').getName());
		assertEquals("Great Hall", board.getRoom('G').getName());
		assertEquals("Hospital", board.getRoom('H').getName());
		assertEquals("Inglenook", board.getRoom('I').getName());
		assertEquals("Jailroom", board.getRoom('J').getName());
		
		// These may need to be altered if we remove or change the getLetter() method
		assertEquals('A', board.getCell(0, 0).getLetter());
		assertEquals('E', board.getCell(14, 12).getLetter());
		assertEquals('X', board.getCell(6, 6).getLetter());
	}
	
	@Test
	public void testRowsAndColumnsCount() {
		assertEquals(ROWS, board.getNumRows());
		assertEquals(COLS, board.getNumColumns());
	}
	
	@Test
	public void testDoorwaysEachDirection() {
		BoardCell cell;
		cell = board.getCell(3, 2);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
	
		cell = board.getCell(11, 2);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		cell = board.getCell(11, 8);	// Updated column from 9 -> 8. See not counting issue #4
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		cell = board.getCell(3, 4);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
	
		cell = board.getCell(6, 6);
		assertFalse(cell.isDoorway());
		cell = board.getCell(0, 12);
		assertFalse(cell.isDoorway());
		cell = board.getCell(14, 12);
		assertFalse(cell.isDoorway());	
	}
	
	@Test
	public void testCorrectNumberOfDoors() {
		int doorCount = 0;
		BoardCell cell;
		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumColumns(); j++) {
				cell = board.getCell(i, j);
				if (cell.isDoorway()) {
					doorCount += 1;
				}
			}
		}
		assertEquals(16, doorCount);	// Updated count from 14 -> 16. I can't count (3rd time)
	}
	
	@Test
	public void testRandomCellValues() {
		// Updated all cells to be rooms - I misunderstand walkways and unused spaces initially
		BoardCell cell;
		
		cell = board.getCell(5, 4);	// Updated col from 5 -> 4. Fifth times the charm for counting!
		assertFalse(cell.isLabel());
		assertFalse(cell.isDoorway());
		assertTrue(cell.isRoom()); 
		assertFalse(cell.isRoomCenter());
		assertEquals('W', cell.getLetter());
		
		cell = board.getCell(11, 7);
		assertFalse(cell.isLabel());
		assertFalse(cell.isDoorway());
		assertTrue(cell.isRoom());
		assertFalse(cell.isRoomCenter());
		assertEquals('F', cell.getLetter());
		
		cell = board.getCell(0, 10);
		assertFalse(cell.isLabel());
		assertFalse(cell.isDoorway());
		assertTrue(cell.isRoom());
		assertFalse(cell.isRoomCenter());
		assertEquals('X', cell.getLetter());
		
		cell = board.getCell(7, 12);
		assertFalse(cell.isLabel());
		assertTrue(cell.isDoorway());
		assertTrue(cell.isRoom());
		assertFalse(cell.isRoomCenter());
		assertEquals('W', cell.getLetter());
		
		cell = board.getCell(2, 6);
		assertFalse(cell.isLabel());
		assertFalse(cell.isDoorway());
		assertTrue(cell.isRoom());
		assertTrue(cell.isRoomCenter());
		assertEquals('B', cell.getLetter());
	
		cell = board.getCell(1, 6);
		assertTrue(cell.isLabel());
		assertFalse(cell.isDoorway());
		assertTrue(cell.isRoom());
		assertFalse(cell.isRoomCenter());
		assertEquals('B', cell.getLetter());
	}
}