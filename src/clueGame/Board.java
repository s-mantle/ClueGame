//Copy of TestBoard.java
package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Board{
	/*
	 * variable and methods used for singleton pattern
	 */
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;

	final static int COLS = 4;
	final static int ROWS = 4;

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
	 */
	public void initialize() throws FileNotFoundException {
		grid = new BoardCell[ROWS][COLS];
		try {
			File file = new File("TestBoardCSV.csv");
		}
		catch (Exception FileNotFoundException) {
			System.out.println("The given file cannot be found");
		}
		
		File file = new File("TestBoardCSV.csv");
		Scanner scanner = new Scanner(file);
		
		for (int i = 0; i < ROWS; i++) {
			String[] line = scanner.nextLine().split(",");
			for (int j = 0; j < line.length; j++){
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
}