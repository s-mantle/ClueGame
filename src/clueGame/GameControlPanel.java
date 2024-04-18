package clueGame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JTextField guessField, resultField, turnField, rollField;
	private JButton nextButton, accusationButton;
	
	private Board board = Board.getInstance();
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		setLayout(new GridLayout(2, 0));
		
		// Build Control Panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(0, 4));
		
		JPanel turnPanel = new JPanel();
		JLabel turnLabel = new JLabel("Whose turn is it?");
		
		turnField = new JTextField(15);
		turnPanel.add(turnLabel);
		turnPanel.add(turnField);
		controlPanel.add(turnPanel);
		// -----------------------------------------------------------
		
		// Build Roll Panel
		JPanel rollPanel = new JPanel();
		JLabel roll = new JLabel("Roll: ");
		rollField = new JTextField(5);
		rollPanel.add(roll);
		rollPanel.add(rollField);
		controlPanel.add(rollPanel);
		// -----------------------------------------------------------

		// Build Accusation Button
		accusationButton = new JButton("Make an Accusation?");
		accusationButton.addActionListener(new ButtonListener());
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(new ButtonListener());
		controlPanel.add(accusationButton);
		controlPanel.add(nextButton);
		// -----------------------------------------------------------
		
		add(controlPanel);
		
		// Build Guess Panel
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(0, 2));
		
		JPanel guessPanelNested = new JPanel();
		guessPanelNested.setLayout(new GridLayout(1, 0));
		guessPanelNested.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		guessField = new JTextField(20);
		guessPanelNested.add(guessField);
		guessPanel.add(guessPanelNested);
		// -----------------------------------------------------------
		
		// Build Guess Results Panel
		JPanel guessResultsPanel = new JPanel();
		guessResultsPanel.setLayout(new GridLayout(1, 0));
		guessResultsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		guessPanel.add(guessResultsPanel);
		resultField = new JTextField(20);
		guessResultsPanel.add(resultField);
		guessPanel.add(guessResultsPanel);
		// -----------------------------------------------------------
		
		add(guessPanel);
		
	}
	
	public void setTurn(Player player, int rollValue) {
		this.turnField.setText(player.getName());
		this.turnField.setBackground(player.getPlayerColor());
		this.rollField.setText("" + rollValue);
	}
	
	public void setGuess(String guess) {
		this.guessField.setText(guess);
	}
	
	public void setGuessResult(String result) {
		this.resultField.setText(result);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == nextButton) {
				boardTurnCaller();
			}
			else {
				if (board.getCurrentPlayer().isHuman() && !board.getCurrentPlayer().getFinished()) {
					// TODO: Implement Accusation & Suggestions
				}
				else {
					JOptionPane.showMessageDialog(null, "Please wait for your turn", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	public void boardTurnCaller() {
		board.turnOperator(this);
	}
}