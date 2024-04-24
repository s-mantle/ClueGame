package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Accusation extends JDialog {
	private JLabel personLabel, roomLabel, weaponLabel;
	private JComboBox<String> personBox, roomBox, weaponBox;
	private JButton enterButton, exitButton;
	private Card personCard, roomCard, weaponCard;
	
	public Accusation() {
		setTitle("Make Accusation");
		setSize(300, 200);
		setLayout(new GridLayout(4, 2));
		
		roomLabel = new JLabel("Room");
		add(roomLabel);
		
		roomBox = new JComboBox<String>();
		ArrayList<String> roomNames = new ArrayList<String>() {
			{add("Armory"); add("Ballet Room"); add("Choir Hall"); add("Dungeon"); add("Engine Room");
			 add("Fireplace"); add("Great Hall"); add("Hospital"); add("Inglenook"); add("Jailroom");}
		};
		for (String room: roomNames) { roomBox.addItem(room); }
		
		roomBox.addActionListener(new CombinationListener());
		add(roomBox);
		
		
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
		
		
		enterButton = new JButton("Enter Accusation");
		enterButton.addActionListener(new ButtonListener());
		add(enterButton);
		
		exitButton = new JButton("Exit Accusation");
		exitButton.addActionListener(new ButtonListener());
		add(exitButton);
		
		personCard = new Card("Arbitrary", CardType.PERSON);
		roomCard = new Card("Arbitrary", CardType.ROOM);
		weaponCard = new Card("Arbitrary", CardType.WEAPON);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == enterButton) {
				System.out.println("Room Card: "+roomCard);
				System.out.println("Person Card: "+personCard);
				System.out.println("Weapon Card: "+weaponCard);
				Solution accusation = new Solution(roomCard, personCard, weaponCard);
				Board board = Board.getInstance();
				
				if (board.checkAccusation(roomCard, personCard, weaponCard)) {
					JOptionPane.showMessageDialog(null, "You have won the game!");
				}
				else {
					JOptionPane.showMessageDialog(null, "You guessed incorrectly! You have lost.");
				}
				System.exit(0);
			}
			else if (event.getSource() == exitButton) {
				dispose();
			}
		}
	}
	
	private class CombinationListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == personBox) {
				personCard.setName(personBox.getSelectedItem().toString());
			}
			else if (event.getSource() == roomBox) {
				roomCard.setName(roomBox.getSelectedItem().toString());
			}
			else if (event.getSource() == weaponBox) {
				weaponCard.setName(weaponBox.getSelectedItem().toString());
			}
		}
	}
}
