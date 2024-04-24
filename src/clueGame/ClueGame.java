package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	private static Board board = Board.getInstance();
	private static GameControlPanel gameControlPanel = GameControlPanel.getInstance();
	private static CardPanel cardPanel = CardPanel.getInstance();
	
	public ClueGame() {
		setLayout(new BorderLayout());
		
		gameControlPanel.initialize();
		add(gameControlPanel, BorderLayout.SOUTH);
		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayoutUpdatedWalkways.csv", "ClueSetupFinal.txt");
		board.initialize();
		board.dealCards();
		board.prepareFirstTurn(gameControlPanel);
		add(board, BorderLayout.CENTER);
		
		cardPanel.initialize();
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
