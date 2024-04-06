package clueGame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

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
		mainPanel.setLayout(new GridLayout(2,0));
		
		mainPanel.add(gameInfoPanel());
		mainPanel.add(guessFieldPanel());
		setVisible(true);
		add(mainPanel);
		
//		upperHalf = new JPanel(); lowerHalf = new JPanel();
//		add(upperHalf, BorderLayout.NORTH); add(lowerHalf, BorderLayout.SOUTH);
//		upperHalf.setVisible(true); lowerHalf.setVisible(true);
//		
//		upperLeftMost = new JPanel(); upperLeftMiddle = new JPanel();
//		upperRightMiddle = new JButton(); upperRightMost = new JButton();
//		// Use GridLayouts when needed
//		
//		
//		lowerLeft = new JPanel(); lowerRight = new JPanel();
//		add(lowerLeft, lowerHalf); add(lowerRight, lowerHalf);
//		lowerLeft.setVisible(true); lowerRight.setVisible(true);
//		
//		guessText = new JTextField("I have no guess!"); guessResults = new JTextField("So you have nothing?");
//		guessText.setVisible(true); guessResults.setVisible(true);
//		add(guessText, lowerLeft); add(guessResults, lowerRight);
	}
	
	public JPanel gameInfoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));
		panel.setVisible(true);
		
		panel.add(turnPanel());
		panel.add(rollPanel());
		panel.add(accusationButton());
		panel.add(nextButton());
		return panel;
	}
	
	public JPanel rollPanel() {
		JPanel panel = new JPanel();
		panel.setVisible(true);
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("Roll:");
		//Arbitrary Player and Player Color
		JTextField rollNumber = new JTextField("Aribtrary roll");
		
		panel.add(label);
		panel.add(rollNumber);
		return panel;
	}
	
	public JPanel turnPanel() {
		JPanel panel = new JPanel();
		panel.setVisible(true);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("Whose Turn?");
		//Arbitrary Player and Player Color
		JTextField playerTurn = new JTextField("Aribtrary Player");
		playerTurn.setBackground(Color.YELLOW);
		
		panel.add(label);
		panel.add(playerTurn);
		return panel;
	}
	
	public JButton nextButton() {
		JButton button = new JButton("NEXT!");
		button.setVisible(true);
		return button;
	}
	
	public JButton accusationButton() {
		JButton button = new JButton("Make Accusation");
		button.setVisible(true);
		return button;
	}
	
	public JPanel guessFieldPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.setVisible(true);
		
		panel.add(guessPanel(), BorderLayout.CENTER);
		panel.add(guessResultPanel(), BorderLayout.EAST);
		return panel;
	}
	
	public JPanel guessPanel() {
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		guessPanel.setVisible(true);
		
		guessText = new JTextField("I have no guess!");
		guessText.setVisible(true);
		guessPanel.add(guessText);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return guessPanel;
		
	}
	
	public JPanel guessResultPanel() {
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1,0));
		resultPanel.setVisible(true);
		
		guessResults =  new JTextField("So you have nothing?");
		guessResults.setVisible(true);
		resultPanel.add(guessResults);
		resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return resultPanel;
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
//		frame.setContentPane(panel); // put the panel in the frame
//		frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true); // make it visible
		
//		// test filling in the data
//		panel.setTurn(new ComputerPlayer(Color.RED, "Col. Mustard", 0, 0), 5);
//		panel.setGuess( "I have no guess!");
//		panel.setGuessResult( "So you have nothing?");
	}
}