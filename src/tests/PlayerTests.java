package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.DoorDirection;
import clueGame.Player;

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
	
//	@Test
	public void testHumanPlayer() {
		// TODO
	}
	
//	@Test
	public void testComputerPlayer() {
		// TODO
	}
	
	@Test
	public void testDeck() {
		assertEquals(24, board.getCards().size());
		
		int roomCount = 0, playerCount = 0, weaponCount = 0;
		for (Card card: board.getCards()) {
			if (card.getCardType() == CardType.ROOM) { roomCount++; }
			else if (card.getCardType() == CardType.PERSON) { playerCount++; }
			else if (card.getCardType() == CardType.WEAPON) { weaponCount++; }
		}
		
		assertEquals(10, roomCount);
		assertEquals(8, playerCount);
		assertEquals(6, weaponCount);
	}
	
	@Test
	public void testDealCards() {
		boolean hasRoomCard = false, hasPlayerCard = false, hasWeaponCard = false;
		board.dealCards();
		for (Card card: board.getAnswerCards()) {
			if (card.getCardType() == CardType.ROOM) { hasRoomCard = true; }
			else if (card.getCardType() == CardType.PERSON) { hasPlayerCard = true; }
			else if (card.getCardType() == CardType.WEAPON) { hasWeaponCard = true; }
		}
		assertTrue(hasRoomCard);
		assertTrue(hasPlayerCard);
		assertTrue(hasWeaponCard);
		
		Map<Integer, String> playerColors = new HashMap<Integer, String>();
		playerColors.put(1, "Red");
		playerColors.put(2, "Blue");
		playerColors.put(3, "Green");
		playerColors.put(4, "Yellow");
		playerColors.put(5, "Cyan");
		playerColors.put(6, "Pink");
		playerColors.put(7, "Black");
		playerColors.put(8, "White");

		int maxCards = 0, minCards = 0;
		int numPlayers = board.getPlayerList().size();
		for (int i = 1; i <= numPlayers; i++) {
			int numCards = board.getPlayerList().get(playerColors.get(i)).getCards().size();
			if (i == 1) {
				maxCards = numCards;
				minCards = numCards;
			}
			else if (numCards > maxCards) { maxCards = numCards; }
			else if (numCards < minCards) { minCards = numCards; }
		}
		assertTrue((maxCards - minCards) <= 1);
		
		for (int i = 1; i <= numPlayers; i++) {
			int iNumCards = board.getPlayerList().get(playerColors.get(i)).getCards().size();
			for (int j = i + 1; j <= numPlayers; j++) {
				int jNumCards = board.getPlayerList().get(playerColors.get(j)).getCards().size();

				for (int k = 0; k < iNumCards; k++) {
					for (int l = 0; l < jNumCards; l++) {
//						assertTrue();	// TODO 
					}
				}
			}
		}
	}
}