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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import clueGame.Board;
import clueGame.Card;

public class ComputerPlayer extends Player {

	//Same as Player Constructor for now
	public ComputerPlayer(Color playerColor, String name, int row, int col) {
		super(playerColor, name, row, col);
	}

	@Override
	public void createAccusation(Card room, Card person, Card weapon) {
		if(Board.getInstance().checkAccusation(room, person, weapon)){
			setCanPlay(false);
		}
	}
	
	
	public Solution createSuggestion() {
		Board board = Board.getInstance();
		BoardCell cell = board.getCell(this.row, this.col);
		// All queried cells should be room centers. This if statement validates such and returns null if we are not at a room center
		if (cell.isRoomCenter()) {
			 Room room = board.getRoom(cell);
			 Card roomCard = new Card(room.getName(), CardType.ROOM);
			 
			 // Generate a list of cards which are known such that we can invert the list to cards we can query
			 List<Card> knownCards = new ArrayList<>(this.playerDeck);
			 knownCards.addAll(this.seenCards);
			 List<Card> queryableCards = new ArrayList<>();
			 
			 for (Card card: board.getCards()) {
				 if (!knownCards.contains(card)) {
					 queryableCards.add(card);
				 }
			 }
			 // Randomize our list to simulate random card selection
			 Collections.shuffle(queryableCards);
			 
			 Card[] solutionCards = new Card[3];
			 solutionCards[0] = roomCard;
			 // booleans are needed to manage which cards we are still looking for to build a suggestion
			 boolean weaponFound = false, personFound = false;
			 // Solution Cards are added such that position 0 always contains the room, position 1 always contains the person,
			 // and position 2 always contains the weapon
			 for (Card card: queryableCards) {
				 if ((card.getCardType() == CardType.PERSON) && (!personFound)) {
					 solutionCards[1] = card;
					 personFound = true;
				 }
				 else if ((card.getCardType() == CardType.WEAPON) && (!weaponFound)) {
					 solutionCards[2] = card;
					 weaponFound = true;
				 }
				 if (personFound && weaponFound) {
					 Solution suggestion = new Solution(solutionCards[0], solutionCards[1], solutionCards[2]);
					 return suggestion;
				 }
			 }
			 
			 // Handling the case that a computer has seen all persons or all weapons already (aka arbitrary card values)
			 if (!personFound && !weaponFound) {
				 Card arbitraryPerson = null;
				 Card arbitraryWeapon = null;
				 // Draws the first card of each necessary type from a shuffled list. The card doesn't matter at this point since the computer
				 // already knows it isn't a part of the solution, so the returned value is unimportant
				 for (Card card: knownCards) {
					 if (card.getCardType() == CardType.PERSON) {
						 arbitraryPerson = card;
					 }
					 else if (card.getCardType() == CardType.WEAPON) {
						 arbitraryWeapon = card;
					 }
					 
					 if ((arbitraryPerson != null) && (arbitraryWeapon != null)) {
						 Solution suggestion = new Solution(solutionCards[0], arbitraryPerson, arbitraryWeapon);
						 return suggestion;
					 }
				 }
			 }
			 // Same idea as the above if-statement. Handles each case decomposed instead of both cases simultaneously.
			 // Admittedly, if the above if-statement is true, then the computer knows the answer
			 else if (!personFound) {
				 for (Card card: knownCards) {
					 if (card.getCardType() == CardType.PERSON) {
						 Solution suggestion = new Solution(solutionCards[0], card, solutionCards[2]);
						 return suggestion;
					 }
				 }
			 }
			 else if (!weaponFound) {
				 for (Card card: knownCards) {
					 if (card.getCardType() == CardType.WEAPON) {
						 Solution suggestion = new Solution(solutionCards[0], solutionCards[1], card);
						 return suggestion;
					 }
				 }
			 }
		}
		// This should never be returned. A null return means our query was not from a room center. However, the compiler
		// doesn't understand that so it exists as a catch-all for our edge case representing a bad call
		return null;
	}
	
	public BoardCell moveTo() {
		// Creates a randomizes a list of targets for our existing BoardCell such that we can implement a (very poor) AI algorithm
		// to decide where a computer will move to
		Board board = Board.getInstance();
		ArrayList<BoardCell> targets = new ArrayList<>(board.getTargets());
		Collections.shuffle(targets);
		
		// Sets of cells which should be removed from or added to targets are necessary since mutating targets during a for-loop
		// iteration is both bad practice and java compiler restricted
		Set<BoardCell> cellsToRemove = new HashSet<>();
		Set<BoardCell> cellsToAdd = new HashSet<>();
		
		for (BoardCell cell: targets) {
			// If our destination cell is a doorway, then we must check which way the door faces to find a room.
			if (cell.isDoorway()) {
				BoardCell roomCell = null;
				if (cell.getDoorDirection() == DoorDirection.UP) {
					roomCell = board.getCell(cell.getRow() - 1, cell.getCol());
				}
				else if (cell.getDoorDirection() == DoorDirection.DOWN) {
					roomCell = board.getCell(cell.getRow() + 1, cell.getCol());
				}
				else if (cell.getDoorDirection() == DoorDirection.LEFT) {
					roomCell = board.getCell(cell.getRow(), cell.getCol() - 1);
				}
				else if (cell.getDoorDirection() == DoorDirection.RIGHT) {
					roomCell = board.getCell(cell.getRow(), cell.getCol() + 1);
				}
				
				// Once we have a cell inside the room, we can find the room's center which is where the computer should be moved to
				// assuming that they have not visited this room
				String roomName = board.getRoom(roomCell).getName();
				boolean uniqRoom = true;
				for (Card card: this.seenCards) {
					// If-statement checks to see if the room has been visited already. If it has, we do not immediately enter the room
					// and instead return it as a possible cell to travel to
					if (roomName.equals(card.getName())) {
						uniqRoom = false;
						cellsToRemove.add(cell);
						cellsToAdd.add(roomCell);
						break;
					}
				}
				// If the room has not already been visited, then we enter it without further target checking. While this omits some potential
				// targets from being queried, targets is shuffled initially, so random selection is ensured
				if (uniqRoom) {
					this.row = board.getRoom(roomCell).getCenterCell().getRow();
					this.col = board.getRoom(roomCell).getCenterCell().getCol();
					return board.getCell(this.row, this.col);
				}
			}
		}
		
		// Updates the targets list if and only if we discover a room that we have already seen. In that instance, we need to make sure
		// we still enter the room instead of ending at the doorway. The below code ensures that
		targets.removeAll(cellsToRemove);
		targets.addAll(cellsToAdd);
		
		this.row = targets.get(0).getRow();
		this.col = targets.get(0).getCol();
		return board.getCell(this.row, this.col);
	}
}
