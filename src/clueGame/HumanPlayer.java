/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 29 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * HumanPlayer: Extends player, creates a new player
 */
package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {

	//Same as Player Constructor for now
	public HumanPlayer(Color playerColor, String name, int row, int col) {
		super(playerColor, name, row, col);
	}

	//TODO: Implement human interactions, such as suggestions, movement, accusations
}
