package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTests {
	public static final int ROWS = 15;
	public static final int COLS = 13;

	private static Board board;
	
	static Card wrench = new Card("Wrench", CardType.WEAPON);
	static Card knife = new Card("Knife", CardType.WEAPON);
	static Card candlestick = new Card("Candlestick", CardType.WEAPON);
	static Card pistol = new Card("Pistol", CardType.WEAPON);
	static Card leadPipe = new Card("Lead Pipe", CardType.WEAPON);
	static Card rope = new Card("Rope", CardType.WEAPON);
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testPlayerSetup() {
//		System.out.println(board.getPlayers());
		assertEquals("One", board.getPlayers().get("Red").getName());
		assertEquals("Two", board.getPlayers().get("Blue").getName());
		assertEquals("Three", board.getPlayers().get("Green").getName());
		assertEquals("Four", board.getPlayers().get("Yellow").getName());
		assertEquals("Five", board.getPlayers().get("Cyan").getName());
		assertEquals("Six", board.getPlayers().get("Pink").getName());
		assertEquals("Seven", board.getPlayers().get("Black").getName());
		assertEquals("Eight", board.getPlayers().get("White").getName());
	}
	
	@Test
	public void testPlayerStartLocation() {
		assertEquals(4,board.getPlayers().get("Red").getRow());
		assertEquals(0,board.getPlayers().get("Red").getCol());
		
		assertEquals(0,board.getPlayers().get("Green").getRow());
		assertEquals(8,board.getPlayers().get("Green").getCol());
		
		assertEquals(10,board.getPlayers().get("Cyan").getRow());
		assertEquals(12,board.getPlayers().get("Cyan").getCol());
		
		assertEquals(14,board.getPlayers().get("Black").getRow());
		assertEquals(4,board.getPlayers().get("Black").getCol());
	}
	
	@Test
	public void testWeaponSetup() {
		assertTrue(wrench.equals(board.getWeaponList().get(0)));
		assertTrue(knife.equals(board.getWeaponList().get(1)));
		assertTrue(candlestick.equals(board.getWeaponList().get(2)));
		assertTrue(pistol.equals(board.getWeaponList().get(3)));
		assertTrue(leadPipe.equals(board.getWeaponList().get(4)));
		assertTrue(rope.equals(board.getWeaponList().get(5)));
	}
	
	@Test
	public void testPlayers() {
		// Note, the way in which a player chooses their color/ name will likely change
		int humanCount = 0, computerCount = 0;
		
		for (Player player: board.getPlayers().values()) {
			if (player instanceof HumanPlayer) { humanCount++; }
			else if (player instanceof ComputerPlayer) { computerCount++; }
		}
		assertEquals(1, humanCount);
		assertEquals(7, computerCount);
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
		for (Card card: board.getTheSolution()) {
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
		int numPlayers = board.getPlayers().size();
		for (int i = 1; i <= numPlayers; i++) {
			int numCards = board.getPlayers().get(playerColors.get(i)).getCards().size();
			if (i == 1) {
				maxCards = numCards;
				minCards = numCards;
			}
			else if (numCards > maxCards) { maxCards = numCards; }
			else if (numCards < minCards) { minCards = numCards; }
		}
		assertTrue((maxCards - minCards) <= 1);
		
		Set<Card> allCards = new HashSet<>();
		for (int i = 1; i <= numPlayers; i++) {
			allCards.addAll(board.getPlayers().get(playerColors.get(i)).getCards()); 
			System.out.println(board.getPlayers().get(playerColors.get(i)).getCards().size());
		}
		allCards.addAll(board.getTheSolution());
		
		System.out.println("Num Cards End: " + allCards.size());

		// Sets ensure uniqueness. Checking the number of created cards versus dealt cards ensures all cards were not only dealt,
		// but also ensures that each card is distinct from every other card
		assertEquals(24, allCards.size());
	}
}