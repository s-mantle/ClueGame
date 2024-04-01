package tests;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

public class ComputerAITest {
	public static final int ROWS = 15;
	public static final int COLS = 13;

	private static Board board;
	
	private List<Player> playerList;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testSelectTargets() {
		// if no rooms in list, select randomly
		// if room in list that has not been seen, select it
		// if room in list that has been seen, each target (including room) selected randomly
	}
	
	@Test
	public void testCreateSuggestion() {
		// Room matches current location
		// If only one weapon not seen, it's selected
		// If only one person not seen, it's selected (can be same test as weapon)
		// If multiple weapons not seen, one of them is randomly selected
		// If multiple persons not seen, one of them is randomly selected
	}
}
