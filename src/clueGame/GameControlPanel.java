package clueGame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;

public class GameControlPanel extends JPanel {
	// Reference the layout template from C22A-1 to understand what each instance variable represents
	JPanel mainPanel;
	JPanel upperHalf, lowerHalf;
	
	JPanel upperLeftMost, upperLeftMiddle;
	JButton upperRightMiddle, upperRightMost;
	
	JLabel turnLabel; JTextField turnPlayerName;
	JLabel rollLabel; JTextField rollValue;
	
	JPanel lowerLeft, lowerRight;
	JTextField guessText, guessResults;
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		mainPanel = new JPanel();
		mainPanel.setVisible(true);
		
		upperHalf = new JPanel(); lowerHalf = new JPanel();
		add(upperHalf, BorderLayout.NORTH); add(lowerHalf, BorderLayout.SOUTH);
		upperHalf.setVisible(true); lowerHalf.setVisible(true);
		
		upperLeftMost = new JPanel(); upperLeftMiddle = new JPanel();
		upperRightMiddle = new JButton(); upperRightMost = new JButton();
		// Use GridLayouts when needed
		
		
		lowerLeft = new JPanel(); lowerRight = new JPanel();
		add(lowerLeft, lowerHalf); add(lowerRight, lowerHalf);
		lowerLeft.setVisible(true); lowerRight.setVisible(true);
		
		guessText = new JTextField("I have no guess!"); guessResults = new JTextField("So you have nothing?");
		guessText.setVisible(true); guessResults.setVisible(true);
		add(guessText, lowerLeft); add(guessResults, lowerRight);
	}
	
	
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
//		// test filling in the data
//		panel.setTurn(new ComputerPlayer(Color.RED, "Col. Mustard", 0, 0), 5);
//		panel.setGuess( "I have no guess!");
//		panel.setGuessResult( "So you have nothing?");
	}
}