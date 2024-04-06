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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardPanel extends JPanel {
	// Reference the layout template from C22A-1 to understand what each instance variable represents
	private JPanel mainPanel, peoplePanel;
	private static HumanPlayer player;
	private static Board board;
	private ArrayList<Card> people = new ArrayList<>();
	private ArrayList<Card> weapons = new ArrayList<>();
	private ArrayList<Card> rooms = new ArrayList<>();
	private static Map<Player, List<Card>> playerCards;
	JLabel handLabel, seenLabel;
	JTextField noneLabel;
	
	private static final Color LIGHT_RED = new Color(255,51,51); private static final Color LIGHT_BLUE = new Color(51,153,255);
	private static final Color LIGHT_GREEN = new Color(0,255,51);
	private static final Map<Color, Color> COLORMAP = new HashMap<>();
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public CardPanel()  {
		mainPanel = new JPanel();
		mainPanel.setVisible(true);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		
		setVisible(true);
		add(mainPanel, BorderLayout.CENTER);
		handLabel = new JLabel("In Hand:");
		seenLabel = new JLabel("Seen:");
		noneLabel = new JTextField("None");
		
		//Set Person List
		//Should this be refactored to loop through playerHand in peoplePanel to reduce the need for 3 different lists?
		for(Card card : player.getCards()) {
			if (card.getCardType() == CardType.PERSON) {
				people.add(card);
			}else if(card.getCardType() == CardType.WEAPON) {
				weapons.add(card);
			}else {
				rooms.add(card);
			}
		}
		
		mainPanel.add(createCardPanel("People", CardType.PERSON));
		mainPanel.add(createCardPanel("Rooms", CardType.ROOM));
		mainPanel.add(createCardPanel("Weapons", CardType.WEAPON));
		
	}
	
	private JPanel createCardPanel(String title, CardType cardType) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JLabel("In Hand:"));
		panel.setBorder(new TitledBorder (new EtchedBorder(), title));
		
		//In Hand List
		if(people.isEmpty()) {
			panel.add(new JTextField("None"));
		}else{
			for(Card card : player.getCards()) {
				if(card.getCardType() == cardType) {
					JTextField people = new JTextField(card.getName());
					people.setBackground(Color.LIGHT_GRAY);
					panel.add(people);
				}
			}
		}
		//Seen List
		panel.add(new JLabel("Seen"));
		boolean seenCard = false;
		for(Card card : player.getSeenCards()) {
			if(card.getCardType() == cardType) {
				JTextField people = new JTextField(card.getName());
				people.setBackground(getPlayerCardColor(card));
				panel.add(people);
				seenCard = true;
			}
		}
		if(!seenCard) {
			panel.add(new JTextField("None"));
		}
		return panel;
	}
	
	private Color getPlayerCardColor(Card card) {
		for(Player player : playerCards.keySet()) {
			if(playerCards.get(player).contains(card)) {
				return COLORMAP.get(player.getPlayerColor());
			}
		}
		System.out.println("You fucked up");
		return Color.DARK_GRAY;
	}
	
	public void setTurn(Player player, int rollNumber) {
		
	}
	
	public void updateDisplay() {
		
	}
	
	public void setGuess(String guess) {
		
	}
	
	public void setGuessResult(String result) {
		
	}
	
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.dealCards();
		
		COLORMAP.put(Color.RED, LIGHT_RED);
		COLORMAP.put(Color.BLUE, LIGHT_BLUE);
		COLORMAP.put(Color.GREEN, LIGHT_GREEN);
		COLORMAP.put(Color.YELLOW, Color.ORANGE);
		COLORMAP.put(Color.PINK, Color.PINK);
		COLORMAP.put(Color.CYAN, Color.CYAN);
		
		player = (HumanPlayer)board.getPlayers().get("Red");
		System.out.println(player);
		playerCards = board.getPlayerCardMap();
		
		
		CardPanel panel = new CardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
	    frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 720);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
//		// test filling in the data
//		panel.setTurn(new ComputerPlayer(Color.ORANGE, "Col. Mustard", 0, 0), 5);
//		panel.setGuess( "I have no guess");
//		panel.setGuessResult( "So you have nothing?");
		for(Player playerL : playerCards.keySet()) {
			if(playerL.getName() != "Red") {
				for(Card card : playerCards.get(playerL)) {
					player.updateSeen(card);
					System.out.println("The player has seen: "+ card);
				}
			}
		}
		panel.updateDisplay();
	}
}