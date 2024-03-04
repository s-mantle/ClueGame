//Copy of TestBoard.java
package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * TODO
 * FINAl
 * Check all tests pass at the same time
 * Add exception handling to a log for extra points [5pts]
 * Refactor to git diff
 */

public class Board{
	/*
	 * variable and methods used for singleton pattern
	 */
	private BoardCell[][] grid;
	
	int COLS = -1;
	int ROWS = -1;

	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
		
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
	 * initialize the board (since we are using singleton pattern)
	 * 
	 */
	public void initialize() {
		//Not sure if we need to call loadLayoutConfigFile and loadSetupConfigFile here
		//They are need in order to get ROWS and COLS
		
		try {
			this.loadSetupConfig();
			this.loadLayoutConfig();
		}
		catch (Exception FileNotFoundException) {
			// Fix Later
		}
			
		grid = new BoardCell[ROWS][COLS];
		try {
			File file = new File(layoutConfigFile);
			Scanner scanner = new Scanner(file);
						
			for (int i = 0; i < ROWS; i++) {
				String[] line = scanner.nextLine().split(",");
				for (int j = 0; j < COLS; j++){
					BoardCell cell = new BoardCell(i,j);
					grid[i][j] = cell;
					
					cell.setLetter(line[j].charAt(0));
					if (roomMap.containsKey(cell.getLetter())) {
						cell.setRoom(true);
					}
					
					if (line[j].length() == 2){
						char designator = line[j].charAt(1);
						
						if (roomMap.containsKey(designator)) {
							// TODO: Implement secretPassages
							;
						}
						else if (designator == '#') {
							cell.setRoomLabel(true);
							roomMap.get(line[j].charAt(0)).setLabelCell(cell);
						}
						else if (designator == '*' ) {
							cell.setRoomCenter(true);
							roomMap.get(line[j].charAt(0)).setCenterCell(cell);
						}
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
					else {
						cell.setDoorDirection(DoorDirection.NONE);
					}
					
					
					

//					cell.setLetter(line[j]);
//					if(line[j] == "R") {
//						cell.setRoom(true);
//					}
//					else if(line[j] == "O") {
//						cell.setOccupied(true);
//					}
				}
			}
			scanner.close();
			this.calcAdjList();
		}
		catch (Exception FileNotFoundException) {
			System.out.println("The given file cannot be found");
		}
	}
	
	private void calcAdjList() {
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < COLS; j++){
				//Does not calculate for doors yet, purely all available cells
				if (i-1 >= 0) { 
					grid[i][j].addAdjacency(grid[i-1][j]);
				}
				if (i+1 < ROWS) { 
					grid[i][j].addAdjacency(grid[i+1][j]); 
				}
				if (j-1 >= 0) { 
					grid[i][j].addAdjacency(grid[i][j-1]); 
				}
				if (j+1 < COLS) { 
					grid[i][j].addAdjacency(grid[i][j+1]); 
				}
			}
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
			if (visited.contains(adjCell)) {
				continue;
			}
			
			if (adjCell.isRoom()) {
				targets.add(adjCell);
				continue;
			}
			else if (adjCell.getOccupied()) {
				continue;
			}
				
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
		return grid[row][col];
	}
	
	/**
	 * Gets the targets that can be moved to by the player
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void setConfigFiles(String csvFile, String txtFile) {
		this.layoutConfigFile = csvFile;
		this.setupConfigFile = txtFile;
	}
	
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		File file;
		//This works?  IDK
		try {
			file = new File(setupConfigFile);
		}
		catch(Exception FileNotFoundException) {
			System.out.println("The given file cannot be found");
		}
		
		
//		try {
			file = new File(setupConfigFile);
			Scanner scanner = new Scanner(file);

			while(scanner.hasNext())
			{
				String[] line = scanner.nextLine().split(",");
				if(line[0].substring(0,2)!="//") {
					//TODO check if line[2] is a character
					if(line[0] == "Room" && line[1] != null && line[2] != null){
						char roomChar = line[2].charAt(0);
						String roomName = line[1];
						Room tempRoom = new Room(roomName);
						roomMap.put(roomChar, tempRoom);
					}else {
						throw new BadConfigFormatException("setupConfigFile - is not configured correctly");
					}
				}

			}
			scanner.close();
//		}
//		catch (Exception FileNotFoundException) {
//			System.out.println("The given file cannot be found");
//		}
			
	}
	
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		File file;
		int columnLength = -1;
		int rows = 0;
		String indicatorChar = "*#^v<>";
		//This works?  IDK
		try {
			file = new File(layoutConfigFile);
		}
		catch(Exception FileNotFoundException) {
			System.out.println("The given file cannot be found");
		}
		

		//		try {
		file = new File(layoutConfigFile);
		Scanner scanner = new Scanner(file);

		while(scanner.hasNext())
		{
			String[] line = scanner.nextLine().split(",");
			if(columnLength == -1)
			{
				columnLength = line.length;
			}
			if(line.length != columnLength)
			{
				throw new BadConfigFormatException("layoutConfigFile - Number of columns is not constant");
			}
			
			for(int i = 0; i < line.length; i++)
			{
				if(line[i] == null) {
					throw new BadConfigFormatException("layoutConfigFile - Contains a null character in a row");
				}
				
				//May need to change because an index can be both centerCell and roomCenter
				if(line[i].length() >= 3) {
					throw new BadConfigFormatException("layoutConfigFile - Contains a string of 3 or more characters in a single index");
				}
				

				char roomChar = line[i].charAt(0);
				if(!roomMap.containsKey(roomChar)) {
					throw new BadConfigFormatException("layoutConfigFile - Contains a character not in setupConfigFile");
				}

				if(line[i].length() > 1) {
					if(indicatorChar.indexOf(line[i].charAt(1)) == -1){
						throw new BadConfigFormatException("layoutConfigFile - Contains an extra character that is not \"*#^v<>\"");
					}
				}
			}
			rows++;
		}
		scanner.close();
		ROWS = rows;
		COLS = columnLength;
	}
	
	public Room getRoom(char letter) {
		return roomMap.get(letter);
	}

	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getLetter());
	}
	
	public int getNumRows() {
		return ROWS;
	}
	
	public int getNumColumns() {
		return COLS;
	}
	
	// VERY TEMPORARY SETTERS
	public void setNumRows(int numRows) {
		this.ROWS = numRows;
	}
	
	public void setNumCols(int numCols) {
		this.COLS = numCols;
	}
}