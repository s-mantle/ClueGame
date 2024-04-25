package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Suggestion extends JDialog {
	private JLabel personLabel, roomLabel, weaponLabel;
	private JTextField roomSelection;
	private JComboBox<String> personBox, weaponBox;
	private JButton enterButton, exitButton;
	
	private Card personCard, roomCard, weaponCard;
	private ArrayList<Card> cards;
	private Board board;
	private Room currRoom;
	
	private GameControlPanel gameControlPanel;
	private CardPanel cardPanel;
	
	public Suggestion() {
		this.board = Board.getInstance();
		this.gameControlPanel = GameControlPanel.getInstance();
		this.cardPanel = CardPanel.getInstance();
		
		setTitle("Make Suggestion");
		setSize(300, 200);
		setLayout(new GridLayout(4, 2));
		
		roomLabel = new JLabel("Room");
		add(roomLabel);
		
		currRoom = board.getRoom(board.getCurrentPlayer().getRoom());
		roomCard = board.getCard(currRoom.getName(), CardType.ROOM);
		
		roomSelection = new JTextField(currRoom.getName());
		roomSelection.setEditable(false);
		add(roomSelection);
		
		
		personLabel = new JLabel("Person");
		add(personLabel);
		
		personBox = new JComboBox<String>();
		ArrayList<String> personNames = new ArrayList<String>() {
			{add("Mr. Red"); add("Mrs. Blue"); add("Sarge Green");
			 add("Mrs. Sunflower"); add("AQUAMAN!"); add("Mrs. Rose");}
		};
		for (String person: personNames) { personBox.addItem(person); }
		
		personBox.addActionListener(new CombinationListener());
		add(personBox);
		
		
		weaponLabel = new JLabel("Weapon");
		add(weaponLabel);
		
		weaponBox = new JComboBox<String>();
		ArrayList<String> weaponNames = new ArrayList<String>() {
			{add("Wrench"); add("Knife"); add("Candlestick");
			 add("Pistol"); add("Lead Pipe"); add("Rope");}
		};
		for (String weapon: weaponNames) { weaponBox.addItem(weapon); }
		
		weaponBox.addActionListener(new CombinationListener());
		add(weaponBox);
		
		
		enterButton = new JButton("Enter Suggestion");
		enterButton.addActionListener(new ButtonListener());
		add(enterButton);
		
		exitButton = new JButton("Exit Suggestion");
		exitButton.addActionListener(new ButtonListener());
		add(exitButton);
		
		cards = new ArrayList<Card>(board.getCards());
		
//		boolean personChoosen = false, weaponChoosen = false;
//		for (Card card: cards) {
//			if (personChoosen && weaponChoosen) { break; }
//			if (card.getCardType() == CardType.PERSON && (!personChoosen)) {
//				personCard = card;
//				personChoosen = true;
//			}
//			else if (card.getCardType() == CardType.WEAPON && (!weaponChoosen)) {
//				weaponCard = card;
//				weaponChoosen = true;
//			}
//		}
		personCard = new Card("Mr. Red", CardType.PERSON);
		weaponCard = new Card("Wrench", CardType.WEAPON);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == enterButton) {
//				System.out.println(roomCard);
//				System.out.println(weaponCard);
//				System.out.println(personCard);
				Solution suggestion = new Solution(roomCard, personCard, weaponCard);
				Board board = Board.getInstance();
				
				Card outcome = (board.manageSuggestion(suggestion, board.getCurrentPlayer(), board.getPlayerList()));
				if (outcome == null) {
					board.setSuggestionDisproven(false);
					board.setCurrentSolution(suggestion);
					String disprove = "Not Disproven";
					String guess = personCard.getName() + " in the " + roomCard.getName() + " with the " + weaponCard.getName();
					
					gameControlPanel.setGuessResult(disprove);
					gameControlPanel.setGuess(guess);
				}
				else {
					board.getCurrentPlayer().updateSeen(outcome);

					String result = outcome.getName();
					String guess = personCard.getName() + " in the " + roomCard.getName() + " with the " + weaponCard.getName();
					
					gameControlPanel.setGuessResult(result);
					gameControlPanel.setGuess(guess);
					
					cardPanel.updateAll();
				}
				setVisible(false);
			}
			else if (event.getSource() == exitButton) {
				dispose();
			}
		}
	}
	
	private class CombinationListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == personBox) {
				personCard = board.getCard(personBox.getSelectedItem().toString(), CardType.PERSON);
			}
			else if (event.getSource() == weaponBox) {
				weaponCard = board.getCard(weaponBox.getSelectedItem().toString(), CardType.WEAPON);
			}
		}
	}
}
