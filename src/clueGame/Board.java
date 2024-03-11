/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 04 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * Board: Creates the Board and deals with populating it and checking .txt and .csv files
 * 
 */
package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Board{
	private BoardCell[][] grid;
	
	//ROWS and COLS set to -1 to ensure that they are updated within loadLayoutConfig()
	int COLS = -1;
	int ROWS = -1;

	//layout.csv and setup.txt files that are imported
	private String layoutConfigFile;
	private String setupConfigFile;
	
	//Map containing character, room pairs 
	private Map<Character, Room> roomMap = new HashMap<>();
	private Map<Character,BoardCell> roomCenterMap;
	
	//Sets used for calcTargets
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;

	
	//Private board
	private static Board theInstance = new Board();
	
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
		roomCenterMap = new HashMap<>();
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
							roomCenterMap.put(line[j].charAt(0), cell);
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
			scanner.close();
			theInstance.calcAdjList();
		}
		catch (Exception FileNotFoundException) {
			System.out.println("The given file cannot be found");
		}
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

		while (scanner.hasNext())
		{
			String[] line = scanner.nextLine().split(", ");
			if (line.length > 1) {
				//TODO check if line[2] is a character
				//Checks that Room/Space is correctly spelled, Room name and Room character are not null
				if ((line[0].equals("Room") || (line[0].equals("Space")) && line[1] != null && line[2] != null)){
					Room tempRoom = new Room(line[1]);
					roomMap.put(line[2].charAt(0), tempRoom);
				} else {
					throw new BadConfigFormatException("setupConfigFile - is not configured correctly");
				}
			}
		}
		scanner.close();
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
					roomCenterMap.get(secretLetter).addAdjacency(roomCenterMap.get(cellLetter));
					roomCenterMap.get(cellLetter).addAdjacency(roomCenterMap.get(secretLetter));
				}
			}
		}
	}

	private void calcAdjWalkways(int i, int j) {
//		if(i == 11 && j == 2) {
		//Checks for adjacent walkways
		if (i - 1 >= 0 && grid[i-1][j].getLetter() == 'W') {
//			System.out.println("Up ("+(i-1)+", "+j+")");
			grid[i][j].addAdjacency(grid[i-1][j]);
		}

		if (i + 1 < ROWS && grid[i+1][j].getLetter() == 'W') {
//			System.out.println("Down ("+i+", "+j+")");
			grid[i][j].addAdjacency(grid[i+1][j]);
		}

		if (j - 1 >= 0 && grid[i][j-1].getLetter() == 'W') {
//			System.out.println("Left ("+i+", "+(j-1)+")");
//			System.out.println(grid[i][j-1]);
			grid[i][j].addAdjacency(grid[i][j-1]);
		}

		if (j + 1 < COLS && grid[i][j+1].getLetter() == 'W') {
//			System.out.println("Right ("+i+", "+(j+1)+")");
			grid[i][j].addAdjacency(grid[i][j+1]);
		}
	}

	
	//Refactor :)
	private void calcAdjRooms(int i, int j) {
		
		if (grid[i][j].getDoorDirection() == DoorDirection.UP) {
			char roomLetter = grid[i-1][j].getLetter();
			grid[i][j].addAdjacency(roomCenterMap.get(roomLetter));
			roomCenterMap.get(roomLetter).addAdjacency(grid[i][j]);
		}
		
		if (grid[i][j].getDoorDirection() == DoorDirection.DOWN) {
			char roomLetter = grid[i+1][j].getLetter();
			grid[i][j].addAdjacency(roomCenterMap.get(roomLetter));
			roomCenterMap.get(roomLetter).addAdjacency(grid[i][j]);

		}
		if (grid[i][j].getDoorDirection() == DoorDirection.LEFT) {
			char roomLetter = grid[i][j-1].getLetter();
			grid[i][j].addAdjacency(roomCenterMap.get(roomLetter));
			roomCenterMap.get(roomLetter).addAdjacency(grid[i][j]);
		}
		if (grid[i][j].getDoorDirection() == DoorDirection.RIGHT) {
			char roomLetter = grid[i][j+1].getLetter();
			grid[i][j].addAdjacency(roomCenterMap.get(roomLetter));
			roomCenterMap.get(roomLetter).addAdjacency(grid[i][j]);
		}
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
				targets.add(roomCenterMap.get(adjCell.getLetter()));
				continue;
			}
			else if (adjCell.getOccupied()) {continue;}
				
			visited.add(adjCell);
			
			if (numSteps == 1) {
				targets.add(adjCell);
			}
			else {
				findAllTargets(adjCell, numSteps - 1);
			}
			
			visited.remove(adjCell);
		}
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
	 * @return
	 */
	public Room getRoom(char letter) {
		return theInstance.roomMap.get(letter);
	}

	/**
	 * Returns the character based on the cell given
	 * 
	 * @param cell
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
}