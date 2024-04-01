package tests;

import static org.junit.Assert.*;

import java.awt.Color;
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

public class GameSolutionTest {
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
		Player testPlayer = new HumanPlayer(Color.RED, "Test Player", 1, 1);
		
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
	public void testSolution() {
		Solution solTest = new Solution(armoryCard, redCard, wrenchCard);
		Board.getInstance().setSolution(solTest);;
		assertTrue(Board.getInstance().checkAccusation(armoryCard, redCard, wrenchCard));
		
		assertFalse(Board.getInstance().checkAccusation(balletRoomCard, redCard, wrenchCard));
		assertFalse(Board.getInstance().checkAccusation(armoryCard, greenCard, wrenchCard));
		assertFalse(Board.getInstance().checkAccusation(armoryCard, redCard, knifeCard));
	}
	
	
	
	
	
}