/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 04 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * BadConfigFormatException: Exception used to tell the terminal if a file is not setup properly
 * 
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	private String error;
	
	/**
	 * Default constructor for the exception
	 * Writes to a log file
	 * 
	 * @throws FileNotFoundException
	 */
	public BadConfigFormatException() throws FileNotFoundException {
		super("Error: Invalid Configuration");
		
		PrintWriter errorFile = new PrintWriter("logfile.txt");
		errorFile.println("Error: Invalid Configuration");
		errorFile.close();
	}
	
	/**
	 * Constructor that takes a string to write a more specific error message
	 * Writes to a log file
	 * 
	 * @param error
	 * @throws FileNotFoundException
	 */
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