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
 * Read file first and ensure that it is valid ie. same # of columns
 * Need to figure out the width and height of the grid, done by reading by row and then splitting rows
 * 	Does not matter if the first column is short because subsequently when reading the other rows this will be found out?
 * Need to cross check the symbols with the room layout.txt
 */

public class Board{
	/*
	 * variable and methods used for singleton pattern
	 */
	private BoardCell[][] grid;
	
	int COLS = 24;
	int ROWS = 25;

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
		grid = new BoardCell[ROWS][COLS];
		try {
			File file = new File(layoutConfigFile);
			Scanner scanner = new Scanner(file);
						
			for (int i = 0; i < ROWS; i++) {
				String[] line = scanner.nextLine().split(",");
				for (int j = 0; j < COLS; j++){
					BoardCell cell = new BoardCell(i,j);
					grid[i][j] = cell;

					cell.setLetter(line[j]);
					if(line[j] == "R") {
						cell.setRoom(true);
					}
					else if(line[j] == "O") {
						cell.setOccupied(true);
					}
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
	
	// FIX IMPLEMENTATION
	public void loadSetupConfig() throws BadConfigFormatException {
		System.out.println(setupConfigFile);
		try {
			File file = new File(setupConfigFile);
			Scanner scanner = new Scanner(file);

			while(scanner.hasNext())
			{
				String[] line = scanner.nextLine().split(",");
				if(line[0].substring(0,2)!="//") {
					//TODO check is line[2] is a character
					if(line[0] == "Room" && line[1] != null && line[2] != null){
						char roomChar = line[2].charAt(0);
						String roomName = line[1];
						Room tempRoom = new Room(roomName);
						roomMap.put(roomChar, tempRoom);
					}else {
						throw new BadConfigFormatException(setupConfigFile);
					}
				}

			}
			scanner.close();
		}
		catch (Exception FileNotFoundException) {
			System.out.println("The given file cannot be found");
		}
			
	}
	// FIX IMPLEMENTATION
	public void loadLayoutConfig() {
		
	}
	
	// FIX IMPLEMENTATION
	public Room getRoom(char letter) {
		return new Room();
	}
	// FIX IMPLEMENTATION
	public Room getRoom(BoardCell cell) {
		return new Room();
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