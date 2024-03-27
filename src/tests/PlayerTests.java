package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class PlayerTests {
	public static final int ROWS = 15;
	public static final int COLS = 13;

	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
	}
	
	@Test
	public static void testPlayerSetup() {
		assertEquals("One",playerList.get("Red").getName());
		assertEquals("Two",playerList.get("Blue").getName());
		assertEquals("Three",playerList.get("Green").getName());
		assertEquals("Four",playerList.get("Yellow").getName());
		assertEquals("Five",playerList.get("Cyan").getName());
		assertEquals("Six",playerList.get("Pink").getName());
		assertEquals("Seven",playerList.get("Black").getName());
		assertEquals("Eight",playerList.get("White").getName());
	}
}