/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 29 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * Board: Creates the Board and deals with populating it and checking .txt and .csv files, deals cards and creates cards
 * 
 */
package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Board extends JPanel{
	private BoardCell[][] grid;
	
	//ROWS and COLS set to -1 to ensure that they are updated within loadLayoutConfig()
	int COLS = -1;
	int ROWS = -1;

	//layout.csv and setup.txt files that are imported
	private String layoutConfigFile;
	private String setupConfigFile;
	
	//Map containing character, room pairs 
	private Map<Character, Room> roomMap = new HashMap<>();
	
	//Sets used for calcTargets
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	//Set used to store the players may not be neccessary not sure yet
	private Map<String, Player> playerMap = new HashMap<>();
	private ArrayList<Player> allPlayers;
	private Map<Player, List<Card>> playerCards = new HashMap<>();
	
	//Lists used to store the people, weapons, and rooms
	ArrayList<Card> personList = new ArrayList<>();
	ArrayList<Card> weaponList = new ArrayList<>();
	ArrayList<Card> roomList = new ArrayList<>();
	
	
	//Set used to store all of the cards - could be changed to store each card type?
	private Set<Card> cards = new HashSet<>();
	
	private Player currentPlayer;
	public Solution currentSolution;
	private int rollNumber;
	private boolean suggestionDisproven;
	
	//Private board, solution
	private static Board theInstance = new Board();
	public static Solution theSolution;
	
	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	/*
	 * Initializes and populates the board.  (Using the singleton pattern)
	 * Loops through layout file to fill the grid[][] with cells, while checking if cells are rooms, doorways ect...
	 */
	public void initialize() {
		grid = new BoardCell[ROWS][COLS];
		
		try {
			File file = new File(layoutConfigFile);
			Scanner scanner = new Scanner(file);
			//Loops through the rows
			for (int i = 0; i < ROWS; i++) {
				String[] line = scanner.nextLine().split(",");
				
				//Loops through the columns
				for (int j = 0; j < COLS; j++){
					
					BoardCell cell = new BoardCell(i,j);
					grid[i][j] = cell;
					cell.setLetter(line[j].charAt(0));
					cell.setDoorDirection(DoorDirection.NONE);
					char cellLetter = cell.getLetter();
					//Checks if the cell is a room
					if (cellLetter != 'W' && cellLetter != 'X') {
						cell.setRoom(true);
						roomMap.get(cell.getLetter()).addRoomCell(cell);
					}
					
					//Checks if the cell has any extra character (could be a door, labelCell, ect...)
					if (line[j].length() == 2){
						char designator = line[j].charAt(1);
						
						//Checks if the cell is a secret passage
						if (roomMap.containsKey(designator)) {
							cell.setSecretPassage(designator);
						}
						//Checks if the cell is a labelCell
						else if (designator == '#') {
							cell.setRoomLabel(true);
							roomMap.get(line[j].charAt(0)).setLabelCell(cell);
						}
						//Checks if the cell is a roomCenter
						//Add another set that stores room centers?
						else if (designator == '*' ) {
							cell.setRoomCenter(true);
							roomMap.get(line[j].charAt(0)).setCenterCell(cell);
						}
						//Checks if the cells are doorways, and the direction they face
						else {
							cell.setDoorway(true);
							if (designator == '^' ) {
								cell.setDoorDirection(DoorDirection.UP);
							}
							else if (designator == 'v' ) {
								cell.setDoorDirection(DoorDirection.DOWN);
							}
							else if (designator == '<' ) {
								cell.setDoorDirection(DoorDirection.LEFT);
							}
							else if (designator == '>' ) {
								cell.setDoorDirection(DoorDirection.RIGHT);
							}
						}
					}
				}
			}
			
			// Added for C23A
			for (Player player: allPlayers) {
				grid[player.getRow()][player.getCol()].setOccupied(true);
			}
			
			scanner.close();
			theInstance.calcAdjList();
		}
		catch (Exception FileNotFoundException) {
			System.out.println("The given file cannot be found");
		}
		
		suggestionDisproven = true;
		setSize(850, 850);
		theInstance.addMouseListener(new BoardListener());
	}
	
	/**
	 * Sets the configFiles to their appropriate variables
	 * Calls loadSetupConfig to ensure setup.txt is valid
	 * Calls loadLayoutConfig to ensure ClueLayout.csv is valid
	 * 
	 * @param csvFile
	 * @param txtFile
	 */
	public void setConfigFiles(String csvFile, String txtFile) {
		theInstance.layoutConfigFile = "Data/" + csvFile;
		theInstance.setupConfigFile = "Data/" + txtFile;
		
		try {theInstance.loadSetupConfig();}
		catch (FileNotFoundException | BadConfigFormatException e) {
			System.out.println("loadSetupConfig(): " + e.toString());
		}
		
		try {theInstance.loadLayoutConfig();}
		catch (FileNotFoundException | BadConfigFormatException e) {
			System.out.println("loadLayoutConfig(): " + e.toString());
		}
	}
	
	/**
	 * Opens the setupConfigFile, and loops through to ensure the file adheres to the guidelines set for the files
	 * If the inputs do adhere to the guidelines, create new cards and/or players and add them to the appropriate Lists
	 * 
	 * Throws BadConfigException if there are any null inputs, or if Room/Space is miss spelled
	 * Throws FileNotFOundException if the given file cannot be found
	 * 
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 */
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		File file;
		file = new File(setupConfigFile);
		Scanner scanner = new Scanner(file);

		//Color map of the colors used in ClueSetup.txt, used to set the colors of each player
		final Map<String,Color> COLORMAP = new HashMap<String,Color>();
		COLORMAP.put("Red", Color.RED);COLORMAP.put("Blue", Color.BLUE);
		COLORMAP.put("Green", Color.GREEN);COLORMAP.put("Yellow", Color.YELLOW);
		COLORMAP.put("Pink", Color.PINK);COLORMAP.put("Cyan", Color.CYAN);
		COLORMAP.put("Black", Color.BLACK);COLORMAP.put("White", Color.WHITE);
		
		//Creates the lists for 
		personList = new ArrayList<>();
		roomList = new ArrayList<>();
		weaponList = new ArrayList<>();
		theInstance.cards = new HashSet<>();

		//Loops through the entire file
		while (scanner.hasNext())
		{
			String[] line = scanner.nextLine().split(", ");
			
			//Checks that there is the proper number of line in ClueSetup.text
			if (line.length == 3 || line.length == 6) {
				//Checks that the Room/Weapon/Person is not null, and the name is not null
				if ((line[0] != null && line[1] != null)) {
					//Creates new rooms for Rooms and Spaces
					if (line[0].equals("Room") || (line[0].equals("Space")) && line[2].length() == 1) {
						Room tempRoom = new Room(line[1]);
						roomMap.put(line[2].charAt(0), tempRoom);
						//Creates cards for the rooms and checks their uniqueness
						if (line[0].equals("Room")) {
							Card newCard = new Card(line[1], CardType.ROOM);
							if(!cards.contains(newCard)) {
								cards.add(newCard);
								roomList.add(newCard);
							}
						}
					//Checks for players and create new human/computer players while recording their appropriate data
					}else if (line[0].equals("Player")) {
						if (line[3].equals("Human")) {
							Player tempPlayer = new HumanPlayer(COLORMAP.get(line[1]), line[2], Integer.parseInt(line[4]), Integer.parseInt(line[5]));
							playerMap.put(line[1], tempPlayer);
						}
						else if (line[3].equals("Computer")){
							Player tempPlayer = new ComputerPlayer(COLORMAP.get(line[1]), line[2], Integer.parseInt(line[4]), Integer.parseInt(line[5]));
							playerMap.put(line[1], tempPlayer);
						}
						
						//Creates new card for the people and checks uniqueness
						Card newCard = new Card(line[2], CardType.PERSON);
						if(!cards.contains(newCard)) {
							cards.add(newCard);
							personList.add(newCard);
						}
					//Checks for weapons and create a new card while checking for uniqueness	
					}else if (line[0].equals("Weapon")) {
						Card newCard = new Card(line[1], CardType.WEAPON);
						if(!cards.contains(newCard)) {
							cards.add(newCard);
							weaponList.add(newCard);
						}
						
					}
					else {
						throw new BadConfigFormatException("setupConfigFile - is not configured correctly");
					}
				}else {
					throw new BadConfigFormatException("setupConfigFile - is not configured correctly");
				}
			}
		}
		allPlayers = new ArrayList<>(playerMap.values());
		currentPlayer = allPlayers.get(0);
		currentPlayer.setHuman(true);
		
		scanner.close();
	}
	
	public void throwSetupException() throws BadConfigFormatException, FileNotFoundException{
		throw new BadConfigFormatException("setupConfigFile - is not configured correctly");
	}
	/**
	 * Opens the layoutConfigFile and loops through to ensure that the columns are the same length, and there are no null entires
	 * Checks that all characters inside of the csv file are listed within the setup file, and that there are no invalid characters
	 * Sets ROWS and COLS so that grid[][] can be made correctly
	 * 
	 * Throws BadConfigException if the file does not adhere to the guidelines set
	 * Throws FileNotFOundException if the given file cannot be found
	 * 
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		File file;
		int columnLength = -1;
		int rows = 0;
		String indicatorChar = "*#^v<>";
		
		file = new File(layoutConfigFile);
		Scanner scanner = new Scanner(file);
		
		//Continues running while there are still lines in the csv file
		while (scanner.hasNext())
		{
			String[] line = scanner.nextLine().split(",");
			if (columnLength == -1){columnLength = line.length;}
			
			//Checks if the columns are not the same length
			if (line.length != columnLength){
				throw new BadConfigFormatException("layoutConfigFile - Number of columns is not constant");
			}
			
			//Loops through the line array (row i that contains n columns)
			for (int i = 0; i < line.length; i++)
			{
				char roomChar = line[i].charAt(0);
				//Checks if an index is null
				if (line[i] == null) {
					throw new BadConfigFormatException("layoutConfigFile - Contains a null character in a row");
				}
				
				//Checks if an index has more than 3 characters (not possible by the guidelines set for ClueGame)
				if (line[i].length() >= 3) {
					throw new BadConfigFormatException("layoutConfigFile - Contains a string of 3 or more characters in a single index");
				}
				
				//Checks if the character is listed in the setup file
				if (!roomMap.containsKey(roomChar)) {
					throw new BadConfigFormatException("layoutConfigFile - Contains a character not in setupConfigFile");
				}

				//Checks if there is a second character and if it is a valid character (Room char or a doorway/label char)
				if (line[i].length() > 1 && (indicatorChar.indexOf(line[i].charAt(1)) == -1) && !roomMap.containsKey(roomChar)) {
					throw new BadConfigFormatException("layoutConfigFile - Contains an extra character that is not \"*#^v<>\"");
				}
			}
			rows++;
		}
		scanner.close();
		theInstance.ROWS = rows;
		theInstance.COLS = columnLength;
	}
	
	/**
	 * Loops through the grid[][] to populate the cells inside with their adj cells
	 * Currently does not account for X spots or if a room can be moved into with a doorway
	 */
	private void calcAdjList() {
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < COLS; j++){
				char cellLetter = grid[i][j].getLetter();
				if(cellLetter == 'W') {
					theInstance.calcAdjWalkways(i,j);

					//Means that this is a door
					if(grid[i][j].getDoorDirection() != DoorDirection.NONE) {
						theInstance.calcAdjRooms(i, j);
					}
				}

				//Calculates Secret Passages
				char secretLetter = grid[i][j].getSecretPassage();
				if(secretLetter != '-') {
					roomMap.get(secretLetter).getCenterCell().addAdjacency(roomMap.get(cellLetter).getCenterCell());
					roomMap.get(cellLetter).getCenterCell().addAdjacency(roomMap.get(secretLetter).getCenterCell());
				}
			}
		}
	}

	/**
	 * Calculates for adjacent walkways in the cardinal directions
	 * 
	 * @param i - row number
	 * @param j - col number
	 */
	private void calcAdjWalkways(int i, int j) {
		if (i - 1 >= 0 && grid[i-1][j].getLetter() == 'W') {
			grid[i][j].addAdjacency(grid[i-1][j]);
		}
		if (i + 1 < ROWS && grid[i+1][j].getLetter() == 'W') {
			grid[i][j].addAdjacency(grid[i+1][j]);
		}
		if (j - 1 >= 0 && grid[i][j-1].getLetter() == 'W') {
			grid[i][j].addAdjacency(grid[i][j-1]);
		}
		if (j + 1 < COLS && grid[i][j+1].getLetter() == 'W') {
			grid[i][j].addAdjacency(grid[i][j+1]);
		}
	}

	
	/**
	 * Calculates adjacent rooms by finding the room that a door points to, then links the 
	 * 
	 * @param i - row number
	 * @param j - col number
	 */
	private void calcAdjRooms(int i, int j) {
		char roomLetter = ' ';
		
		if (grid[i][j].getDoorDirection() == DoorDirection.UP) {
			roomLetter = grid[i-1][j].getLetter();
		}else if (grid[i][j].getDoorDirection() == DoorDirection.DOWN) {
			roomLetter = grid[i+1][j].getLetter();
		}else if (grid[i][j].getDoorDirection() == DoorDirection.LEFT) {
			roomLetter = grid[i][j-1].getLetter();
		}else if (grid[i][j].getDoorDirection() == DoorDirection.RIGHT) {
			roomLetter = grid[i][j+1].getLetter();
		}
		
		grid[i][j].addAdjacency(roomMap.get(roomLetter).getCenterCell());
		roomMap.get(roomLetter).getCenterCell().addAdjacency(grid[i][j]);
	}

	/**
	 * Initializes visited and targets, calls findAllTargets
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<>();
		targets = new HashSet<>();
	
		visited.add(startCell);
						
		findAllTargets(startCell, pathLength);
	}
	
	/**
	 * Calculates the targets that the player can move to using the adjacency list of each cell
	 * 
	 * @param startCell
	 * @param numSteps
	 */
	private void findAllTargets(BoardCell startCell, int numSteps) {
		for (BoardCell adjCell: startCell.getAdjList()) {
			if (visited.contains(adjCell)) {continue;}
			
			if (adjCell.isRoom()) {
				targets.add(roomMap.get(adjCell.getLetter()).getCenterCell());
				continue;
			}
			else if (adjCell.getOccupied()) {continue;}
				
			visited.add(adjCell);
			
			if (numSteps == 1) {
				targets.add(adjCell);
				adjCell.setTarget(true);
			}
			else {
				findAllTargets(adjCell, numSteps - 1);
			}
			
			visited.remove(adjCell);
		}
	}
	
	/**
	 * Deals all of the cards to the players, First creates the solution my shuffling the room, weapon, person cards
	 * and then add them to solution.  Then adds all cards to a larger list, shuffles the list and iterates through 
	 * the list to add the cards to a players hand and remove them
	 */
	public void dealCards() {
		//Separate lists of cards that will be incrementally removed from, done so that the big list of cards will remain un-mutated
		ArrayList<Card> allComputerCards = new ArrayList<>();
		List<Card> playerList2 = new ArrayList<>(personList);
		List<Card> weaponList2 = new ArrayList<>(weaponList);
		List<Card> roomList2 = new ArrayList<>(roomList);
				
		//Shuffle all of the cards for randomness when choosing the solution
		Collections.shuffle(playerList2);
		Collections.shuffle(weaponList2);
		Collections.shuffle(roomList2);
		
		//First deal solutions
		theSolution = new Solution(roomList2.remove(0), playerList2.remove(0), weaponList2.remove(0));
		
		//Add all cards into a big list and then shuffle the list
		allComputerCards.addAll(playerList2);
		allComputerCards.addAll(weaponList2);
		allComputerCards.addAll(roomList2);
		Collections.shuffle(allComputerCards);
		
		//Iterate through the entire list of cards and players, remove a card and add it to a players hand
		//Also deals with uneven amounts are cards given to each player
		while (!allComputerCards.isEmpty()) {
			for (Player player: playerMap.values()) {
				if (!allComputerCards.isEmpty()) {
					player.updateHand(allComputerCards.remove(0));
				}
			}
		}
		
		for(Player player : playerMap.values()) {
			playerCards.put(player, player.getCards());
		}
	}
	
	/**
	 * Compares a player's accusation to the actual solution
	 * @param room
	 * @param person
	 * @param weapon
	 * @return True if the accusation is correct. False if the accusation is incorrect
	 */
	public boolean checkAccusation(Card room, Card person, Card weapon) {
		Solution accusation = new Solution(room, person, weapon);
		return accusation.equals(theSolution);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(Color.BLACK);

		int cellHeight = (int) (getHeight() / ROWS);
		int cellWidth = (int) (getWidth() / COLS);
		
		int xPos = this.getX() + ((getWidth() - cellWidth * COLS) / 2);
		int yPos = this.getY();
		
		// Handles painting each cell
		for (BoardCell[] row: grid) {
			for (BoardCell cell: row) {
				if (cell.isRoom()) {
					if (roomMap.get(cell.getLetter()).getCenterCell().isTarget()) {
						cell.setTarget(true);
					}
					else {
						cell.setTarget(false);
					}
				}
				cell.drawCell(g, cell.isTarget(), xPos, yPos, cellWidth, cellHeight);
				xPos += cellWidth;
			}
			xPos = this.getX() + ((getWidth() - cellWidth * COLS) / 2);
			yPos += cellHeight;
		}
		
		// Handles painting room names
		for (Map.Entry<Character, Room> vals: roomMap.entrySet()) {
			Room room = vals.getValue();
			Font font = new Font("Times New Roman", Font.BOLD, 25);
			g.setFont(font);
			g.setColor(Color.BLUE);
			
			if (room.getLabelCell() != null) {
				int rowAdj = 1, colAdj = 0;

				int xPosLabel = (int) ((room.getLabelCell().getCol() + colAdj) * cellWidth) + ((getWidth() - cellWidth * COLS) / 2);
				int yPosLabel = (int) (room.getLabelCell().getRow() + rowAdj) * cellHeight;
				g.drawString(room.getName(), xPosLabel, yPosLabel);
			}
		}
		
		// Handles painting players
		ArrayList<Player> playersToPaint = (ArrayList<Player>) allPlayers.clone();
		int xOffset = 3, yOffset = 3, xPlayerOffset = 1, yPlayerOffset = 1;
		
		for (Map.Entry<Character, Room> vals: roomMap.entrySet()) {
			for (Player player: ((Room) vals.getValue()).getPlayers()) {
				int xPosPlayer = (player.getCol() * cellWidth) + (((Room) vals.getValue()).getPlayers().indexOf(player) * cellWidth / 4);
				int yPosPlayer = player.getRow() * cellHeight;
				player.drawPlayer(g, xPosPlayer + xPlayerOffset + ((getWidth() - cellWidth * COLS) / 2), yPosPlayer + yPlayerOffset,
								  cellWidth - xOffset, cellHeight - yOffset);
				playersToPaint.remove(player);
			}
		}
		
		for (Player player: playersToPaint) {
			int xPosPlayer = getX() + player.getCol() * cellWidth;
			int yPosPlayer = getY() + player.getRow() * cellHeight;
			
			if (player.getRoom() != '-') {
				Room room = roomMap.get(player.getRoom());
				if (room.getPlayerCount() > 1) {
					xPosPlayer += room.getPlayers().indexOf(player) * cellWidth / 4;
				}
			}
			player.drawPlayer(g, xPosPlayer + xPlayerOffset + ((getWidth() - cellWidth * COLS) / 2), yPosPlayer + yPlayerOffset,
					  		  cellWidth - xOffset, cellHeight - yOffset);
		}
		
		// Door time!
		int xPosDoor = 0, yPosDoor = 0;
		for (BoardCell[] row: grid) {
			for (BoardCell cell: row) {
				cell.drawDoor(g, xPosDoor + ((getWidth() - cellWidth * COLS) / 2), yPosDoor, cellWidth, cellHeight);
				xPosDoor += cellWidth;
			}
			xPosDoor = 0;
			yPosDoor += cellHeight;
		}
	}
	
	private class BoardListener implements MouseListener {
		public void mousePressed (MouseEvent event) {}
		public void mouseReleased (MouseEvent event) {}
		public void mouseEntered (MouseEvent event) {}
		public void mouseExited (MouseEvent event) {}
		public void mouseClicked (MouseEvent event) {
			if (!currentPlayer.isHuman()) {
				return;
			}
			
			int cellWidth = (int) (getWidth() / COLS);
			int cellHeight = (int) (getHeight() / ROWS);
			
			int xPos = event.getX();
			int yPos = event.getY();
			
			boolean selectedTile = false;

			for (BoardCell cell: targets) {
				if (selectedTile) { break; }
				
				// Double check this
				int cellX = getX() + cell.getCol() * cellWidth + ((getWidth() - cellWidth * COLS) / 2);
				int cellY = getY() + cell.getRow() * cellHeight;

				if (cell.isRoom()) {
					Room room = roomMap.get(cell.getLetter());
					for (BoardCell roomCell: room.getRoomCells()) {
						int roomCellX = getX() + cell.getCol() * cellWidth + ((getWidth() - cellWidth * COLS) / 2);
						int roomCellY = getY() + cell.getRow() * cellHeight;
						
						if ((xPos > roomCellX) && (yPos > roomCellY) && (yPos < roomCellY + cellHeight) && (xPos < roomCellX + cellWidth)) {
							if (currentPlayer.getRoom() != '-') {
								roomMap.get(currentPlayer.getRoom()).removePlayer(currentPlayer);
								currentPlayer.setRoom('-');
							}
							currentPlayer.movePlayer(room.getCenterCell().getRow(), room.getCenterCell().getCol());
							currentPlayer.setRoom(room.getName().charAt(0));
							selectedTile = true;
							
							// TODO: Handle Suggestions
							currentPlayer.setFinished(true);
							room.addPlayer(currentPlayer);
							break;
						}
					}
				}
				else {
					if ((xPos > cellX) && (yPos > cellY) && (yPos < cellY + cellHeight) && (xPos < cellX + cellWidth)) {
						currentPlayer.movePlayer(cell.getRow(), cell.getCol());
						
						if (currentPlayer.getRoom() != '-') {
							roomMap.get(currentPlayer.getRoom()).removePlayer(currentPlayer);
							currentPlayer.setRoom('-');
						}
						
						selectedTile = true;
						currentPlayer.setFinished(true);
						break;
					}
				}
			}
			if (!selectedTile) {
				JOptionPane.showMessageDialog(null, "That is not a legal target", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				for (BoardCell cell: targets) {
					cell.setTarget(false);
				}
			}
			
			repaint();
		}
	}
	
	public void turnOperator(GameControlPanel gameControlPanel) {
		System.out.println(currentPlayer.getName() + ": " + currentPlayer.getFinished());
		if (!currentPlayer.getFinished()) {
			JOptionPane.showMessageDialog(null, "Please finish your turn first", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else {
			currentPlayer = updateCurrentPlayer();
			
			Random random = new Random();
			int roll = random.nextInt(6) + 1;
			
			calcTargets(grid[currentPlayer.getRow()][currentPlayer.getCol()], roll);
			gameControlPanel.setTurn(currentPlayer, roll);
			
			if (currentPlayer.isHuman()) {
				repaint();
				currentPlayer.setFinished(false);
				System.out.println(currentPlayer.getName() + ":= " + currentPlayer.getFinished());
			}
			else {
				ComputerPlayer compPlayer = (ComputerPlayer) currentPlayer;
				if ((!suggestionDisproven) && (theSolution != null)) {
					Solution accusation = getAccuation();
					if (checkAccusation(theSolution, accusation)) {
						JOptionPane.showMessageDialog(null, compPlayer.getName() + " wins! The solution is " + theSolution.getPerson().getName() + " in the " + 
													  theSolution.getRoom().getName() + " with the " + theSolution.getWeapon().getName() + '.');
						System.exit(0);
					}
					else {
						suggestionDisproven = true;
						setCurrentSolution(null);
					}
				}
				BoardCell compCell = compPlayer.moveTo();
				
				for (BoardCell cell: targets) {
					cell.setTarget(false);
				}
				
				if (compCell.isRoom()) {
					Room room = roomMap.get(compCell.getLetter());
					
					if (currentPlayer.getRoom() != '-') {
						roomMap.get(currentPlayer.getRoom()).removePlayer(currentPlayer);
						currentPlayer.setRoom('-');
					}
					room.addPlayer(currentPlayer);
					currentPlayer.setRoom(room.getName().charAt(0));
					
					Solution compSuggestion = compPlayer.createSuggestion();
					Card result = manageSuggestion(compSuggestion, compPlayer, allPlayers);
					
					if (result == null) {
						suggestionDisproven = false;
						setCurrentSolution(compSuggestion);
						
						String guess = compSuggestion.getPerson().getName() + ", " + compSuggestion.getRoom().getName() + ", " + compSuggestion.getWeapon().getName();
						gameControlPanel.setGuessResult("Not Disproven");
						gameControlPanel.setGuess(guess);
					}
					else {
						compPlayer.updateSeen(result);
						String guess = compSuggestion.getPerson().getName() + ", " + compSuggestion.getRoom().getName() + ", " + compSuggestion.getWeapon().getName();
						gameControlPanel.setGuessResult(result.getName());
						gameControlPanel.setGuess(guess);
					}
					
					currentPlayer.setRow(room.getCenterCell().getRow());
					currentPlayer.setCol(room.getCenterCell().getCol());
				}
				else {
					if (currentPlayer.getRoom() != '-') {
						roomMap.get(currentPlayer.getRoom()).removePlayer(currentPlayer);
						currentPlayer.setRoom('-');
					}
					currentPlayer.setRow(compCell.getRow());
					currentPlayer.setCol(compCell.getCol());
				}
				currentPlayer.setFinished(true);
				repaint();
			}
		}
	}
	
	public Card manageSuggestion(Solution suggestion, Player accuser, ArrayList<Player> players) {
		Card givenCard;
		Player accused = getPlayer(suggestion.getPerson().getName());
		
		if (accused.getRoom() != accuser.getRoom() && !accuser.equals(accused)) {
			roomMap.get(accuser.getRoom()).addPlayer(accused);
			
			if (accused.getRoom() != '-') {
				roomMap.get(accused.getRoom()).removePlayer(accused);
			}
			
			accused.setRoom(accuser.getRoom());
			accused.setRow(roomMap.get(accuser.getRoom()).getCenterCell().getRow());
			accused.setCol(roomMap.get(accuser.getRoom()).getCenterCell().getCol());
		}
		repaint();
		
		for (Player player: allPlayers) {
			if (!player.equals(accuser)) {
				givenCard = player.disproveSuggestion(suggestion);
				if (givenCard != null) {
					givenCard.setOrigin(player);
					return givenCard;
				}
			}
		}
		return null;
	}
	
	public void prepareFirstTurn(GameControlPanel gameControlPanel) {
		currentPlayer = allPlayers.get(0);
		Random random = new Random();
		int roll = random.nextInt(6) + 1;
		calcTargets(grid[currentPlayer.getRow()][currentPlayer.getCol()], roll);
		gameControlPanel.setTurn(currentPlayer, roll);
		repaint();
	}
	
	public Player getPlayer(String playerName) {
		for (Player player: allPlayers) {
			if (player.getName().equals(playerName)) {
				return player;
			}
		}
		return null;
	}
	
	
	public void setSuggestionDisproven (boolean status) {
		theInstance.suggestionDisproven = status;
	}
	
	public Solution getAccuation() {
		return theInstance.currentSolution;
	}
	
	public void setCurrentSolution(Solution sol) {
		theInstance.currentSolution = sol;
	}
	
	public Solution getCurrentSolution() {
		return theInstance.currentSolution;
	}
	
	public boolean checkAccusation(Solution answer, Solution guess) {
		boolean same = true;
		
		if (!theSolution.getRoom().equals(answer.getRoom())) {
			same = false;
		}
		else if (!theSolution.getPerson().equals(answer.getPerson())) {
			same = false;
		}
		else if (!theSolution.getWeapon().equals(answer.getWeapon())) {
			same = false;
		}
		
		return same;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Player updateCurrentPlayer() {
		int index = allPlayers.indexOf(currentPlayer);
		if (index == 5) { index = -1; }
		return allPlayers.get(index + 1);
	}
	
	public int rollDice() {
		Random random = new Random();
		rollNumber = random.nextInt(6)+1;
		return rollNumber;
	}
	
	/**
	 * Returns the cell at the grids row,col
	 * 
	 * @param row
	 * @param col
	 */
	public BoardCell getCell(int row, int col) {
		return theInstance.grid[row][col];
	}
	
	/**
	 * Gets the targets that can be moved to by the player
	 */
	public Set<BoardCell> getTargets() {
		return theInstance.targets;
	}
	
	/**
	 * Returns the room based on the character given
	 * 
	 * @param letter
	 */
	public Room getRoom(char letter) {
		return theInstance.roomMap.get(letter);
	}

	/**
	 * Returns the character based on the cell given
	 * 
	 */
	public Room getRoom(BoardCell cell) {
		return theInstance.roomMap.get(cell.getLetter());
	}
	
	/**
	 * Returns the number of rows
	 */
	public int getNumRows() {
		return theInstance.ROWS;
	}
	
	/**
	 * Returns the number of columns
	 */
	public int getNumColumns() {
		return theInstance.COLS;
	}
	
	/**
	 * Returns the adj list for the tests to run
	 * @param row
	 * @param col
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		return theInstance.grid[row][col].getAdjList();
	}
	
	/**
	 * Returns the card playerList - Testing
	 */
	public ArrayList<Card> getPersonList() {
		return theInstance.personList;
	}
	
	/**
	 * Returns the map of players - testing
	 */
	public Map<String, Player> getPlayers() {
		return theInstance.playerMap;
	}
	
	public ArrayList<Player> getPlayerList() {
		return theInstance.allPlayers;
	}
	
	/**
	 * Returns the Card weaponList - testing
	 */
	public ArrayList<Card> getWeaponList() {
		return theInstance.weaponList;
	}
	
	/**
	 * Returns the Card roomList - testing
	 */
	public ArrayList<Card> getRoomList() {
		return theInstance.roomList;
	}
	
	/**
	 * Returns the all the cards made
	 */
	public Set<Card> getCards() {
		return theInstance.cards;
	}
	
	public Map<Player, List<Card>> getPlayerCardMap(){
		return theInstance.playerCards;
	}
	
	/**
	 * Returns the solution cards
	 */
	public ArrayList<Card> getTheSolution() {
		return theSolution.getSolutionSet();
	}
	/**
	 * Returns theSolution
	 */
	public static Solution getSolution() {
		return theSolution;
	}
	
	public static void setSolution(Solution testSol) {
		theSolution = testSol;
	}
}