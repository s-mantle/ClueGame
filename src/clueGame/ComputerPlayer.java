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
		// TODO Auto-generated method stub
		if(Board.getInstance().checkAccusation(room, person, weapon)){
			setCanPlay(false);
		}
	}
	
	
	//TODO: Implement computer interactions, such as suggestions, movement, accusations
	public Solution createSuggestion() {
		Board board = Board.getInstance();
		BoardCell cell = board.getCell(this.row, this.col);
		if (cell.isRoomCenter()) {
			 Room room = board.getRoom(cell);
			 Card roomCard = new Card(room.getName(), CardType.ROOM);
			 
			 List<Card> knownCards = new ArrayList<>(this.playerDeck);
			 knownCards.addAll(this.seenCards);
			 List<Card> queryableCards = new ArrayList<>();
			 
			 for (Card card: board.getCards()) {
				 if (!knownCards.contains(card)) {
					 queryableCards.add(card);
				 }
			 }
			 
			 Collections.shuffle(queryableCards);
			 
//			 List<Card> solutionCards = new ArrayList<>();
			 Card[] solutionCards = new Card[3];
//			 solutionCards.add(roomCard);
			 solutionCards[0] = roomCard;
			 boolean weaponFound = false, personFound = false;
			 // Solution Cards are added such that position 0 always contains the room, position 1 always contains the person,
			 // and position 2 always contains the weapon
			 for (Card card: queryableCards) {
				 if ((card.getCardType() == CardType.PERSON) && (!personFound)) {
//					 solutionCards.add(1, card);
					 solutionCards[1] = card;
					 personFound = true;
				 }
				 else if ((card.getCardType() == CardType.WEAPON) && (!weaponFound)) {
//					 solutionCards.add(2, card);
					 solutionCards[2] = card;
					 weaponFound = true;
				 }
				 else if (personFound && weaponFound) {
//					 Solution suggestion = new Solution(solutionCards.get(0), solutionCards.get(1), solutionCards.get(2));
					 Solution suggestion = new Solution(solutionCards[0], solutionCards[1], solutionCards[2]);
					 return suggestion;
				 }
			 }
			 
			 // Handling the case that a computer has seen all persons or all weapons already (aka arbitrary card values)
			 if (!personFound && !weaponFound) {
				 Card arbitraryPerson = null;
				 Card arbitraryWeapon = null;
				 for (Card card: knownCards) {
					 if (card.getCardType() == CardType.PERSON) {
						 arbitraryPerson = card;
					 }
					 else if (card.getCardType() == CardType.WEAPON) {
						 arbitraryWeapon = card;
					 }
					 
					 if ((arbitraryPerson != null) && (arbitraryWeapon != null)) {
//						 Solution suggestion = new Solution(solutionCards.get(0), arbitraryPerson, arbitraryWeapon);
						 Solution suggestion = new Solution(solutionCards[0], arbitraryPerson, arbitraryWeapon);
						 return suggestion;
					 }
				 }
			 }
			 else if (!personFound) {
				 for (Card card: knownCards) {
					 if (card.getCardType() == CardType.PERSON) {
//						 Solution suggestion = new Solution(solutionCards.get(0), card, solutionCards.get(2));
						 Solution suggestion = new Solution(solutionCards[0], card, solutionCards[2]);
						 return suggestion;
					 }
				 }
			 }
			 else if (!weaponFound) {
				 for (Card card: knownCards) {
					 if (card.getCardType() == CardType.WEAPON) {
//						 Solution suggestion = new Solution(solutionCards.get(0), solutionCards.get(1), card);
						 Solution suggestion = new Solution(solutionCards[0], solutionCards[1], card);
						 return suggestion;
					 }
				 }
			 }
		}
		return null;
	}
	
	public void moveTo() {
		Board board = Board.getInstance();
		ArrayList<BoardCell> targets = new ArrayList<>(board.getTargets());
		Collections.shuffle(targets);
		
		Set<BoardCell> cellsToRemove = new HashSet<>();
		Set<BoardCell> cellsToAdd = new HashSet<>();
		
		for (BoardCell cell: targets) {
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
				
				String roomName = board.getRoom(roomCell).getName();
				boolean uniqRoom = true;
				for (Card card: this.seenCards) {
					if (roomName.equals(card.getName())) {
						uniqRoom = false;
						cellsToRemove.add(cell);
						cellsToAdd.add(roomCell);
						break;
					}
				}
				
				if (uniqRoom) {
					this.row = board.getRoom(roomCell).getCenterCell().getRow();
					this.col = board.getRoom(roomCell).getCenterCell().getCol();
					return;
				}
			}
		}
		
		targets.removeAll(cellsToRemove);
		targets.addAll(cellsToAdd);
		
		this.row = targets.get(0).getRow();
		this.col = targets.get(0).getCol();
	}
}
