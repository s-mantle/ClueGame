package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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
	private int cellWidths, cellHeights;
	private static int playerTurn;
	
	
	Board board = Board.getInstance();
	
	private ArrayList<Player> allPlayers = board.getPlayerList();
	
	public ClueGame(int width, int height, int numRows, int numCols) {
		this.frameWidth = width;
		this.frameHeight = height;
		this.panelWidth = frameWidth - 150;
		this.panelHeight = frameHeight - 150;
		this.numRows = numRows;
		this.numCols = numCols;
		this.cellWidths = (int) this.panelWidth / numRows;
		this.cellHeights = (int) this.panelHeight / numCols;
		
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
	
	public void drawComponents() {
		board.drawBoard(mainPanel, cellWidths, cellHeights);
	}
	
	//This just listens for a click.  if next is clicked then it will ask if the player is human
	//If the player is human then it will get the roll number and display the targets
	private class BoardClickListener implements MouseListener {
		public void mousePressed(MouseEvent event) {}
		public void mouseReleased(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {
			// Is it human player turn?
			if(allPlayers.get(playerTurn).getName().equals("Mr. Red")) {
				
				// Was the click on a target?			
				int startX, startY = 0;
				for (int i = 0; i < numRows; i++) {
					startX = i * cellWidths;
					for (int j = 0; j < numCols; j++) {
						startY = j * cellHeights;
						if (board.getCell(i, j).containsClick(event.getX(), event.getY(), startX, startY, cellWidths, cellHeights)) {}
							// TODO				}
					}
				}
				
				// Move player
				
				// In room?
				
				// If so, handle suggestion
				
			}else {
				ComputerPlayer player = (ComputerPlayer)allPlayers.get(playerTurn);
				player.moveTo();
				if(board.getInstance().getCell(player.getRow(), player.getCol()).isRoom()) {
					//Create a suggestion
//					player.createSuggestion();
				}
				playerTurn = (playerTurn+1) % allPlayers.size();
			}
			
		}
	}
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayoutUpdatedWalkways.csv", "ClueSetupFinal.txt");
		board.initialize();
		board.dealCards();
		
		ClueGame main = new ClueGame(1000, 1000, board.getNumRows(), board.getNumColumns());
		main.drawComponents();
		playerTurn = 0;
	}
}
