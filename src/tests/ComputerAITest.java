package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Room;
import clueGame.Solution;

public class ComputerAITest {
	public static final int ROWS = 15;
	public static final int COLS = 13;

	private static Board board;
	
	private static Card armoryCard, balletRoomCard, choirHallCard, dungeonCard, engineRoomCard, fireplaceCard,
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
		fireplaceCard = new Card("Fireplace", CardType.ROOM);
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
	
	@Test
	public void testSelectTargets() {
		// if no rooms in list, select randomly
		ComputerPlayer computer = (ComputerPlayer) board.getPlayerList().get(5);
		board.calcTargets(board.getCell(14, 8), 1);
		computer.moveTo();
		assertEquals(board.getCell(13, 8), board.getCell(computer.getRow(), computer.getCol()));
		
		computer = (ComputerPlayer) board.getPlayerList().get(1);
		board.calcTargets(board.getCell(0, 4), 1);
		computer.moveTo();
		assertEquals(board.getCell(1, 4), board.getCell(computer.getRow(), computer.getCol()));
		
		// if room in list that has not been seen, select it
		computer = (ComputerPlayer) board.getPlayerList().get(3);
		computer.TEST_ONLY_emptyCards();
		board.calcTargets(board.getCell(4, 12), 3);
		computer.moveTo();
		assertEquals(board.getCell(1, 11), board.getCell(computer.getRow(), computer.getCol()));
		
		computer = (ComputerPlayer) board.getPlayerList().get(6);
		computer.TEST_ONLY_emptyCards();
		board.calcTargets(board.getCell(14, 4), 6);
		computer.moveTo();
		assertEquals(board.getCell(9, 1), board.getCell(computer.getRow(), computer.getCol()));
		
		// if room in list that has been seen, each target (including room) selected randomly
		// TODO: The below is poorly implemented and does not account for randomness well
		computer = (ComputerPlayer) board.getPlayerList().get(7);
		computer.TEST_ONLY_setCards(jailroomCard);
		board.calcTargets(board.getCell(10, 0), 2);
		computer.moveTo();
		if (computer.getRow() == 11 && computer.getCol() == 1) {
			assertEquals(board.getCell(11, 1), board.getCell(computer.getRow(), computer.getCol()));
		}
		else if (computer.getRow() == 9 && computer.getCol() == 1) {
			assertEquals(board.getCell(9, 1), board.getCell(computer.getRow(), computer.getCol()));
		}
		
		computer = (ComputerPlayer) board.getPlayerList().get(4);
		computer.TEST_ONLY_setCards(inglenookCard);
		board.calcTargets(board.getCell(10, 12), 2);
		computer.moveTo();
		if (computer.getRow() == 9 && computer.getCol() == 11) {
			assertEquals(board.getCell(9, 11), board.getCell(computer.getRow(), computer.getCol()));
		}
		else if (computer.getRow() == 11 && computer.getCol() == 11) {
			assertEquals(board.getCell(11, 11), board.getCell(computer.getRow(), computer.getCol()));
		}
	}
	
	// Room matches current location
	// If only one weapon not seen, it's selected
	// If only one person not seen, it's selected (can be same test as weapon)
	// If multiple weapons not seen, one of them is randomly selected
	// If multiple persons not seen, one of them is randomly selected
	
	@Test
	public void testSuggestionSameRoom() {
		ComputerPlayer player = new ComputerPlayer(Color.RED,"Test Player",1,1 );
		Solution suggestion = player.createSuggestion();
		assertTrue(suggestion.getRoom().equals(armoryCard));
		assertFalse(suggestion.getRoom().equals(balletRoomCard));
		assertFalse(suggestion.getRoom().equals(fireplaceCard));
	}
	
	@Test
	public void testSuggestionOnePersonNotSeen() {
		ComputerPlayer player = new ComputerPlayer(Color.RED,"Test Player",1,1 );
		player.updateSeen(redCard);
		player.updateSeen(blueCard);
		player.updateSeen(greenCard);
		player.updateSeen(yellowCard);
		player.updateSeen(pinkCard);
		player.updateSeen(cyanCard);
		player.updateSeen(blackCard);
		Solution suggestion = player.createSuggestion();
		assertTrue(suggestion.getRoom().equals(armoryCard));
		assertTrue(suggestion.getPerson().equals(whiteCard));
		assertTrue(suggestion.getWeapon().getCardType().equals(CardType.WEAPON));
	}
}
