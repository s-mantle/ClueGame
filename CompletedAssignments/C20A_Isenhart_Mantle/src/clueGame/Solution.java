/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 29 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * Solution: Creates a new solution when called in dealCards().  Stores the cards that won't be dealt to players
 */
package clueGame;

import java.util.ArrayList;

public class Solution{
	private Card room;
	private Card person;
	private Card weapon;
	private ArrayList<Card> solutionSet = new ArrayList<>();
	
	/**
	 * Constructor used to set cards and fill a list with the cards for returning
	 * @param room
	 * @param person
	 * @param weapon
	 */
	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
		solutionSet.add(room);
		solutionSet.add(person);
		solutionSet.add(weapon);
	}
	
	/**
	 * Returns the solution list
	 */
	public ArrayList<Card> getSolutionSet() {
		return solutionSet;
	}

	//Used for testing Solution
	@Override
	public String toString() {
		return "Solution [room=" + room + ", person=" + person + ", weapon=" + weapon + "]";
	}
	
	//Used for testing, can be expanded to deal with accusations
	public boolean testSol() {
		if(room.getCardType() == CardType.ROOM && person.getCardType() == CardType.PERSON && weapon.getCardType() == CardType.WEAPON)
			return true;
		return false;
	}
	
	//Used for testing Solution
	public Card getRoom() {
		return room;
	}

	//Used for testing Solution
	public Card getPerson() {
		return person;
	}

	//Used for testing Solution
	public Card getWeapon() {
		return weapon;
	}

	
}