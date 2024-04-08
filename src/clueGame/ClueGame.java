package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
		mainFrame.setSize(panelWidth, panelHeight);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		mainPanel = new JPanel();
		mainPanel.setSize(600, 600);
		mainPanel.setLayout(new GridLayout(numRows, numCols));
		mainFrame.add(mainPanel, BorderLayout.CENTER);
		
//		gameControlPanel = new GameControlPanel(frameWidth, 200);
//		cardPanel = new CardPanel(200, frameHeight);
		
//		mainFrame.add(mainFrame);
	}
	
	public void paintComponent(Board board) {
//		super.paintComponents(/*Add parameters as needed*/);
		
		// This will need to be adjusted to not cutoff the control panels
		int cellWidths = (int) panelWidth / numRows;
		int cellHeights = (int) panelHeight / numCols;
		board.drawCells(mainPanel, cellWidths, cellHeights); // This seems to randomly misdraw the board - will need debugging
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
