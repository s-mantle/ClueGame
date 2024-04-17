package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	private static GameControlPanel gameControlPanel;
	private static CardPanel cardPanel;
	private static Board board = Board.getInstance();
	
	public ClueGame() {
		setLayout(new BorderLayout());

		board = Board.getInstance();
		board.setConfigFiles("ClueLayoutUpdatedWalkways.csv", "ClueSetupFinal.txt");
		board.initialize();
		board.dealCards();
		add(board, BorderLayout.CENTER);
		
		gameControlPanel = new GameControlPanel(1000, 150);
		add(gameControlPanel, BorderLayout.SOUTH);
		
		cardPanel = new CardPanel(150, 1000);
		add(cardPanel, BorderLayout.EAST);
	}

	
	public static void main(String[] args) {
		ClueGame mainFrame = new ClueGame();
		mainFrame.setSize(1000, 1000);
		mainFrame.setTitle("Sam & Ben's Clue Game!");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		JLabel paneDialogue = new JLabel("<html><center>You are " + board.getPlayerList().get(0).getName() + "<br>Can you find the solution before the Computer players?");
		JOptionPane.showMessageDialog(mainFrame, paneDialogue, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
}
