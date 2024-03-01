package experiment;

import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Authors: Ben Isenhart & Sam Mantle
 * Date 2 - 26 - 2024
 * Collaborators: None
 * Sources: None
 * 
 * TestBoard: This class creates a board of test cells to be tested
 */
public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	/**
	 * Constructor for TestBoard, populates the 2D array grid with cells given by the 4x4 TestBoardCSV.csv
	 * Also determines if cells are occupied or if cells are a room
	 * 
	 * @throws FileNotFoundException
	 */
	public TestBoard() throws FileNotFoundException{
		grid = new TestBoardCell[ROWS][COLS];
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
				TestBoardCell cell = new TestBoardCell(i,j);
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
	
	/**
	 * Calculates the adjacency list for each cell in grid[][]
	 */
	public void calcAdjList() {
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
	 * Calculates the targets that the player can move to using the adjacency list of each cell
	 * 
	 * @param startCell
	 * @param pathlength
	 */
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	/**
	 * Returns the cell at the grids row,col
	 * 
	 * @param row
	 * @param col
	 */
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
	/**
	 * Gets the targets that can be moved to by the player
	 */
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
}
