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
	JPanel gameInfoPanel, guessFieldPanel;
	
	JPanel turnPanel, rollPanel;
	JButton nextButton, accusationButton;
	
	JLabel turnLabel; JTextField turnPlayerName;
	JLabel rollLabel; JTextField rollValue;
	
	JPanel guessPanel, resultPanel;
	JTextField guessText, guessResults, rollNumber;
	private String rollNum, playerName, guess, result;
	private Color playerColor;
	
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		mainPanel = new JPanel();
		mainPanel.setVisible(true);
		mainPanel.setLayout(new GridLayout(2,0));
		
		setVisible(true);
		add(mainPanel);
		
		
		gameInfoPanel = new JPanel();
		gameInfoPanel.setLayout(new GridLayout(1,4));
		gameInfoPanel.setVisible(true);
		
		//roll Panel ************************************************************************
		rollPanel = new JPanel();
		rollPanel.setVisible(true);
		rollLabel = new JLabel("Roll:");
		//Arbitrary Player and Player Color
		rollNumber = new JTextField(rollNum);
		rollPanel.add(rollLabel); rollPanel.add(rollNumber);
		//***********************************************************************************
		
		//turnPanel**************************************************************************
		turnPanel = new JPanel();
		turnPanel.setVisible(true);
		turnPanel.setLayout(new BoxLayout(turnPanel, BoxLayout.Y_AXIS));
		
		turnLabel = new JLabel("Whose Turn?");
		//Arbitrary Player and Player Color
		turnPlayerName = new JTextField(playerName);
		turnPlayerName.setBackground(playerColor);
		turnPanel.add(turnLabel);
		turnPanel.add(turnPlayerName);
		//***********************************************************************************
		
		//accustaionButton*******************************************************************
		accusationButton = new JButton("Make Accusation");
		accusationButton.setVisible(true);
		//***********************************************************************************
		
		//nextButton*************************************************************************
		nextButton = new JButton("NEXT!");
		nextButton.setVisible(true);
		//***********************************************************************************
		
		gameInfoPanel.add(turnPanel);
		gameInfoPanel.add(rollPanel);
		gameInfoPanel.add(accusationButton);
		gameInfoPanel.add(nextButton);
		
		
		guessFieldPanel = new JPanel();
		guessFieldPanel.setLayout(new GridLayout(0,2));
		guessFieldPanel.setVisible(true);
		
		//guessPanel*************************************************************************
		guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		guessPanel.setVisible(true);
		
		guessText = new JTextField(guess);
		guessText.setVisible(true);
		guessPanel.add(guessText);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		//***********************************************************************************
		
		//guessResultPanel*******************************************************************
		resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1,0));
		resultPanel.setVisible(true);
		
		guessResults =  new JTextField(result);
		guessResults.setVisible(true);
		resultPanel.add(guessResults);
		resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		//***********************************************************************************
		
		guessFieldPanel.add(guessPanel, BorderLayout.CENTER);
		guessFieldPanel.add(resultPanel, BorderLayout.EAST);
		
		mainPanel.add(gameInfoPanel);
		mainPanel.add(guessFieldPanel);
		
	}
	
	public void setTurn(Player player, int rollNumber) {
		this.playerName = player.getName();
		this.playerColor = player.getPlayerColor();
		this.rollNum = Integer.toString(rollNumber);
		System.out.println("Players name is: " + playerName);
		System.out.println("Players color is: " + playerColor);
		System.out.println("Roll number is: " + rollNum);
		repaint();
	}
	
	public void setGuess(String guess) {
		this.guess = guess;
	}
	
	public void setGuessResult(String result) {
		this.result = result;
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
		panel.setTurn(new ComputerPlayer(Color.RED, "Col. Mustard", 0, 0), 5);
		panel.setGuess( "I have public void setGuess");
		panel.setGuessResult( "So you have nothing?");
		
//		repaint();
	}
}