package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

public class GameControlPanel extends JPanel {

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
//		setTitle("Game Control Panel");
		setSize(300,300);
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
//		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
//		// test filling in the data
//		panel.setTurn(new ComputerPlayer(Color.RED, "Col. Mustard", 0, 0), 5);
//		panel.setGuess( "I have no guess!");
//		panel.setGuessResult( "So you have nothing?");
	}
}