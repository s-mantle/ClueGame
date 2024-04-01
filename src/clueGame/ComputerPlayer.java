/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 29 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * ComputerPlayer: Extends player, creates a new player
 */
package clueGame;

import java.awt.Color;
import clueGame.Board;
import clueGame.Card;

public class ComputerPlayer extends Player {

	//Same as Player Constructor for now
	public ComputerPlayer(Color playerColor, String name, int row, int col) {
		super(playerColor, name, row, col);
	}
	
	@Override
	public void createAccusation(Card room, Card person, Card weapon) {
		if(checkAccusation(room, person, weapon) == true) {
			
		}
	}
	//TODO: Implement computer interactions, such as suggestions, movement, accusations
}
