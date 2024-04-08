package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private JFrame mainFrame;
	private JPanel mainPanel;
	
	private JPanel gameControlPanel;
	private JPanel cardPanel;
	
	private int frameWidth, frameHeight;
	private int panelWidth, panelHeight;
	private int numRows, numCols;
	
	public ClueGame(int width, int height, int numRows, int numCols) {
		this.frameWidth = width;
		this.frameHeight = height;
		this.panelWidth = frameWidth - 200;
		this.panelHeight = frameHeight - 200;
		this.numRows = numRows;
		this.numCols = numCols;
		
		mainFrame = new JFrame();
		mainFrame.setSize(frameWidth, frameHeight);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		mainPanel = new JPanel();
		mainPanel.setSize(panelWidth, panelHeight);
		mainPanel.setLayout(new GridLayout(this.numRows, this.numCols));
		mainFrame.add(mainPanel, BorderLayout.CENTER);
		
//		System.out.println(frameWidth + " - " + frameHeight + " - " + panelWidth + " - " + panelHeight);
		
		gameControlPanel = new GameControlPanel(frameWidth, 200);
		mainFrame.add(gameControlPanel, BorderLayout.SOUTH);
		
		cardPanel = new CardPanel(200, frameHeight);
		mainFrame.add(cardPanel, BorderLayout.EAST);
	}
	
	public void paintComponent(Board board) {
//		super.paintComponents(/*Add parameters as needed*/);
		
		int cellWidths = (int) panelWidth / numRows;
		int cellHeights = (int) panelHeight / numCols;
		board.drawBoard(mainPanel, cellWidths, cellHeights); // This seems to randomly misdraw the board - will need debugging
	}
	
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetupFinal.txt");
		board.initialize();
		board.dealCards();
		
		ClueGame main = new ClueGame(1000, 1000, board.getNumRows(), board.getNumColumns());
		main.paintComponent(board);
	}
}
