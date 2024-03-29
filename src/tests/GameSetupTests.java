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
import clueGame.Solution;

public class GameSetupTests {
	public static final int ROWS = 15;
	public static final int COLS = 13;

	private static Board board;
	
	private static Card armoryCard, balletRoomCard, choirHallCard, dungeonCard, engineRoomCard, fireplace,
	greatHallCard, hospitalCard, inglenookCard, jailroomCard, redCard, blueCard, greenCard, yellowCard, cyanCard,
	pinkCard, blackCard, whiteCard, wrenchCard, knifeCard, candlestickCard, pistolCard, leadpipeCard, ropeCard;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		//10 rooms
		armoryCard = new Card("Armory", CardType.ROOM);
		balletRoomCard  = new Card("Ballet Room", CardType.ROOM);
		choirHallCard = new Card("Choir Hall", CardType.ROOM);
		dungeonCard = new Card("Dungeon", CardType.ROOM);
		engineRoomCard = new Card("Engine Room", CardType.ROOM);
		fireplace = new Card("Fireplace", CardType.ROOM);
		greatHallCard = new Card("Great Hall", CardType.ROOM);
		hospitalCard = new Card("Hospital", CardType.ROOM);
		inglenookCard = new Card("Inglenook", CardType.ROOM);
		jailroomCard = new Card("Jailroom", CardType.ROOM);
		
		//8 players
		redCard = new Card("One", CardType.PERSON);
		blueCard = new Card("Two", CardType.PERSON);
		greenCard = new Card("Three", CardType.PERSON);
		yellowCard = new Card("Four", CardType.PERSON);
		cyanCard = new Card("Five", CardType.PERSON);
		pinkCard = new Card("Six", CardType.PERSON);
		blackCard = new Card("Seven", CardType.PERSON);
		whiteCard = new Card("Eight", CardType.PERSON);
		
		//6 weapons
		wrenchCard = new Card("Wrench", CardType.WEAPON);
		knifeCard = new Card("Knife", CardType.WEAPON);
		candlestickCard = new Card("Candlestick", CardType.WEAPON);
		pistolCard = new Card("Pistol", CardType.WEAPON);
		leadpipeCard = new Card("Lead Pipe", CardType.WEAPON);
		ropeCard = new Card("Rope", CardType.WEAPON);
	}
	
	/**
	 * Tests that the players are correctly set up and have the correct names
	 * Arbitrarily testing for 8 because we have 8 "Start locations"
	 */
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
	
	/**
	 * Tests that the players are properly recording the correct start location
	 * given to them by ClueSetup.txt
	 */
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
	
	/**
	 * Tests that the weapons are correctly made and that their card type and name 
	 * are correct
	 */
	@Test
	public void testWeaponSetup() {
		assertTrue(wrenchCard.equals(board.getWeaponList().get(0)));
		assertTrue(knifeCard.equals(board.getWeaponList().get(1)));
		assertTrue(candlestickCard.equals(board.getWeaponList().get(2)));
		assertTrue(pistolCard.equals(board.getWeaponList().get(3)));
		assertTrue(leadpipeCard.equals(board.getWeaponList().get(4)));
		assertTrue(ropeCard.equals(board.getWeaponList().get(5)));
	}
	
	/**
	 * Tests that the Person cards are set up correctly and that their card name and type match
	 */
	@Test
	public void testPersonSetup() {
		assertTrue(redCard.equals(board.getPersonList().get(0)));
		assertTrue(blueCard.equals(board.getPersonList().get(1)));
		assertTrue(greenCard.equals(board.getPersonList().get(2)));
		assertTrue(yellowCard.equals(board.getPersonList().get(3)));
		assertTrue(cyanCard.equals(board.getPersonList().get(4)));
		assertTrue(pinkCard.equals(board.getPersonList().get(5)));
		assertTrue(blackCard.equals(board.getPersonList().get(6)));
		assertTrue(whiteCard.equals(board.getPersonList().get(7)));
	}
	
	/**
	 * Tests that there are the correct number of human players and computer players
	 * currently 1 Human, 7 computer - could be subject to change as the project
	 * evolves
	 */
	@Test
	public void testPlayerType() {
		// Note, the way in which a player chooses their color/ name will likely change
		int humanCount = 0, computerCount = 0;
		
		for (Player player: board.getPlayers().values()) {
			if (player instanceof HumanPlayer) { humanCount++; }
			else if (player instanceof ComputerPlayer) { computerCount++; }
		}
		assertEquals(1, humanCount);
		assertEquals(7, computerCount);
	}
	
	/**
	 * Tests that the deck has the proper amount of each type of card
	 */
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
	
	/**
	 * Tests that the solution is set up correctly, and the only cards inside are the right ones
	 */
	@Test
	public void testSolution() {
		Solution testSolution = new Solution(armoryCard, whiteCard, ropeCard);
		assertEquals(armoryCard, testSolution.getRoom());
		assertEquals(whiteCard, testSolution.getPerson());
		assertEquals(ropeCard, testSolution.getWeapon());
		
		assertNotEquals(engineRoomCard, testSolution.getRoom());
		assertNotEquals(blueCard, testSolution.getPerson());
		assertNotEquals(knifeCard, testSolution.getWeapon());
		assertNotEquals(candlestickCard, testSolution.getWeapon());
	}
	
	/**
	 * Tests the deal of the cards, and that each player has a similar amount of cards
	 * Uses a set to test for uniqueness
	 */
	@Test
	public void testDealCards() {
		board.dealCards();
		
		assertTrue(board.getSolution().testSol());
		
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
		}
		allCards.addAll(board.getTheSolution());

		// Sets ensure uniqueness. Checking the number of created cards versus dealt cards ensures all cards were not only dealt,
		// but also ensures that each card is distinct from every other card
		assertEquals(24, allCards.size());
	}
}