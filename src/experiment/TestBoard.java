package experiment;

import java.util.Collections;
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
 * TestBoard: This class creates a fictitious board of test cells (again without any concrete implementations) to be tested
 */
public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets = Collections.emptySet();
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() throws FileNotFoundException{
		grid = new TestBoardCell[ROWS][COLS];
		try {
			File file = new File("TestBoardCSV.csv");
		}catch(Exception FileNotFoundException){
			System.out.println("The given file cannot be found");
		}
		File file = new File("TestBoardCSV.csv");
		Scanner scanner = new Scanner(file);
		
		for(int i = 0; i < ROWS; i++) {
			String[] line = scanner.nextLine().split(",");
			for(int j = 0; j < line.length; j++){
				TestBoardCell cell = new TestBoardCell(i,j);
				grid[i][j] = cell;
				
				cell.setLetter(line[j]);
				if(line[j] == "R") {
					cell.setRoom(true);
				}else if(line[j] == "O") {
					cell.setOccupied(true);
				}
			}
		}
		
		scanner.close();
		this.calcAdjList();
		
		//Should we be filling test board with the cells that are in it here?
		//Grid will be a 4 by 4 right now
		
	}
	
	//Use grid to calculate the adjacency list between each cell
	public void calcAdjList() {
		//Loops through all index in grid
		for(int i = 0; i < ROWS; i++)
		{
			for(int j = 0; j < COLS; j++){
				//Does not calculate for doors yet, purely all available cells
				if(i-1 > 0) { 
					grid[i][j].addAdjacency(grid[i-1][j]);
				}
				if(i+1 < ROWS) { 
					grid[i][j].addAdjacency(grid[i+1][j]); 
				}
				if(j-1 > 0) { 
					grid[i][j].addAdjacency(grid[i][j-1]); 
				}
				if(j+1 < COLS) { 
					grid[i][j].addAdjacency(grid[i][j+1]); 
				}
			}
		}
	}
	
	//Calculates the targets that the player can get to
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	//Does this return the cells adjacency list?
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell temp = new TestBoardCell(row, col);
		return temp;
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
}
