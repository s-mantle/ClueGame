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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControlPanel extends JPanel {
	// Reference the layout template from C22A-1 to understand what each instance variable represents
	private JPanel mainPanel,gameInfoPanel, guessFieldPanel, turnPanel, rollPanel, guessPanel, resultPanel;
	private JButton nextButton, accusationButton;
	private JLabel turnLabel, rollLabel; 
	private JTextField turnPlayerName, rollNumber, guessText, guessResults;
	
	private String rollNum, playerName, guess, result;
	private Color playerColor;
	
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel(int width, int height)  {
		mainPanel = new JPanel();
		mainPanel.setVisible(true);
		mainPanel.setLayout(new GridLayout(2,0));
		
		mainPanel.setSize(new Dimension(width, height));	// Added for C23A
		mainPanel.setPreferredSize(new Dimension(width, height));	// Added for C23A
		setVisible(true);		
		
		gameInfoPanel = new JPanel();
		gameInfoPanel.setLayout(new GridLayout(1,4));
		
		//roll Panel ************************************************************************
		rollPanel = new JPanel(); rollLabel = new JLabel("Roll:");
		//Arbitrary Player and Player Color
		rollNumber = new JTextField(rollNum);
		rollNumber.setEditable(false);
		rollPanel.add(rollLabel); rollPanel.add(rollNumber);
		//***********************************************************************************
		
		//turnPanel**************************************************************************
		turnPanel = new JPanel();
		turnPanel.setLayout(new BoxLayout(turnPanel, BoxLayout.Y_AXIS));
		
		turnLabel = new JLabel("Whose Turn?");
		//Arbitrary Player and Player Color
		turnPlayerName = new JTextField(playerName);
		turnPlayerName.setBackground(playerColor);
		turnPlayerName.setEditable(false);
		turnPanel.add(turnLabel);
		turnPanel.add(turnPlayerName);
		//***********************************************************************************
		
		//accustaionButton*******************************************************************
		accusationButton = new JButton("Make Accusation");
		//***********************************************************************************
		
		//nextButton*************************************************************************
		nextButton = new JButton("NEXT!");
		//***********************************************************************************
		
		gameInfoPanel.add(turnPanel);
		gameInfoPanel.add(rollPanel);
		gameInfoPanel.add(accusationButton);
		gameInfoPanel.add(nextButton);
		
		
		guessFieldPanel = new JPanel();
		guessFieldPanel.setLayout(new GridLayout(0,2));
		
		//guessPanel*************************************************************************
		guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		
		guessText = new JTextField(guess);
		guessText.setEditable(false);
		guessPanel.add(guessText);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		//***********************************************************************************
		
		//guessResultPanel*******************************************************************
		resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1,0));
		
		guessResults =  new JTextField(result);
		guessResults.setEditable(false);
		resultPanel.add(guessResults);
		resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		//***********************************************************************************
		
		guessFieldPanel.add(guessPanel, BorderLayout.CENTER);
		guessFieldPanel.add(resultPanel, BorderLayout.EAST);
		
		mainPanel.add(gameInfoPanel);
		mainPanel.add(guessFieldPanel);
		add(mainPanel);
		
		// Added for C23A
		// -------------------------------------------------
		setTurn(new ComputerPlayer(Color.ORANGE, "Col. Mustard", 0, 0), 5);
		setGuess( "I have no guess");
		setGuessResult( "So you have nothing?");
		
		updateDisplay();
		// -------------------------------------------------
	}
	
	public void setTurn(Player player, int rollNumber) {
		this.playerName = player.getName();
		this.playerColor = player.getPlayerColor();
		this.rollNum = Integer.toString(rollNumber);
	}
	
	public void updateDisplay() {
		guessText.setText(guess);
		guessResults.setText(result);
		rollNumber.setText(rollNum);
		turnPlayerName.setText(playerName);
		turnPlayerName.setBackground(playerColor);
	}
	
	public void setGuess(String guess) {
		this.guess = guess;
	}
	
	public void setGuessResult(String result) {
		this.result = result;
	}
	
	class ButtonListener implements ActionListener {
		// Next player pressed
		public void actionPerformed(ActionEvent e) {
			// Current human player finished?
			
			// Update current player
			
			// Roll the dice
			
			// Calc targets
			
			// Update GameControlPanel
			
			// Is new player human?
			
			// If human, display targets
			
			// If human, flag unfinished
			
			// If not human, do accusation?
			
			// If not human, do move
			
			// If not human, make suggestion? 
		}
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel(750, 180);  // Added for C23A
		JFrame frame = new JFrame();  // create the frame 
	    frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer(Color.ORANGE, "Col. Mustard", 0, 0), 5);
		panel.setGuess( "I have no guess");
		panel.setGuessResult( "So you have nothing?");
		
		panel.updateDisplay();
	}
}