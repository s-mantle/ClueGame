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
	public void testPlayerSetup() {
		assertEquals("One", board.getPlayerList().get("Red").getName());
		assertEquals("Two", board.getPlayerList().get("Blue").getName());
		assertEquals("Three", board.getPlayerList().get("Green").getName());
		assertEquals("Four", board.getPlayerList().get("Yellow").getName());
		assertEquals("Five", board.getPlayerList().get("Cyan").getName());
		assertEquals("Six", board.getPlayerList().get("Pink").getName());
		assertEquals("Seven", board.getPlayerList().get("Black").getName());
		assertEquals("Eight", board.getPlayerList().get("White").getName());
	}
	
	@Test
	public void testWeaponSetup() {
		assertEquals("Wrench", board.getWeaponList().get("Wrench").getName());
		assertEquals("Knife", board.getWeaponList().get("Knife").getName());
		assertEquals("Candlestick", board.getWeaponList().get("Candlestick").getName());
		assertEquals("Pistol", board.getWeaponList().get("Pistol").getName());
		assertEquals("Lead Pipe", board.getWeaponList().get("Lead Pipe").getName());
		assertEquals("Rope", board.getWeaponList().get("Rope").getName());
	}
	
	@Test
	public void testHumanPlayer() {
		// TODO
	}
	
	@Test
	public void testComputerPlayer() {
		// TODO
	}
	
	@Test
	public void testDeck() {
		// TODO
	}
	
	@Test
	public void testDealCards() {
		// TODO
	}
}