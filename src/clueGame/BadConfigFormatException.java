package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	private String error;
	
	public BadConfigFormatException() throws FileNotFoundException {
		super("Error: Invalid Configuration");
		
		PrintWriter errorFile = new PrintWriter("logfile.txt");
		errorFile.println("Error: Invalid Configuration");
		errorFile.close();
	}
	
	public BadConfigFormatException(String error) throws FileNotFoundException {
		super("Error: Invalid Configuration of " + error);
		this.error = error;
		
		PrintWriter errorFile = new PrintWriter("logfile.txt");
		errorFile.println("Error: Invalid Configuration of " + error);
		errorFile.close();
	}

	@Override
	public String toString() {
		return error;
	}
	
	
}