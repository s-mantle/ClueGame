package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private JFrame mainFrame;
	private JPanel mainPanel;
	
	private JPanel gameControlPanel;
	private JPanel cardPanel;
		
	private int frameWidth, frameHeight;
	private int panelWidth, panelHeight;
	private int numRows, numCols;
	
	public ClueGame(Board board, int width, int height, int numRows, int numCols) {
		this.frameWidth = width;
		this.frameHeight = height;
		this.panelWidth = frameWidth - 150;
		this.panelHeight = frameHeight - 150;
		this.numRows = numRows;
		this.numCols = numCols;
		
		mainFrame = new JFrame("Sam & Ben's Clue Game!");
		mainFrame.setSize(frameWidth, frameHeight);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		mainPanel = new JPanel();
		mainPanel.setSize(panelWidth, panelHeight);
		mainPanel.setLayout(new GridLayout(this.numRows, this.numCols));
		mainFrame.add(mainPanel, BorderLayout.CENTER);
		
		JLabel paneDialogue = new JLabel("<html><center>You are " + board.getPlayerList().get(0).getName() + "<br>Can you find the solution before the Computer players?");
		JOptionPane.showMessageDialog(mainFrame, paneDialogue, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
		gameControlPanel = new GameControlPanel(frameWidth, 150);
		mainFrame.add(gameControlPanel, BorderLayout.SOUTH);
		
		// CardPanel needs to be better integrated into this. It shouldn't take board as a parameter, but I have yet to refactor
		cardPanel = new CardPanel(150, frameHeight);
		mainFrame.add(cardPanel, BorderLayout.EAST);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
	}
	
	public void drawComponents(Board board) {
		int cellWidths = (int) panelWidth / numRows;
		int cellHeights = (int) panelHeight / numCols;
		board.drawBoard(mainPanel, cellWidths, cellHeights);
	}
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayoutUpdatedWalkways.csv", "ClueSetupFinal.txt");
		board.initialize();
		board.dealCards();
		
		ClueGame main = new ClueGame(board, 1000, 1000, board.getNumRows(), board.getNumColumns());
		main.drawComponents(board);
	}
}
