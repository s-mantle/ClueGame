package clueGame;

public class BadConfigFormatException extends Exception {
	private String error;
	
	public BadConfigFormatException() {
		super("Error: Invalid Configuration");
	}
	
	public BadConfigFormatException(String error) {
		super("Error: Invalid Configuration of " + error);
		this.error = error;
	}

	@Override
	public String toString() {
		return "The configuration of the file: " + error + "is not allowed";
	}
	
	
}