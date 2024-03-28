package clueGame;

import java.util.ArrayList;

public class Solution{
	private Card room;
	private Card person;
	private Card weapon;
	private ArrayList<Card> solutionSet = new ArrayList<>();
	
	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
		solutionSet.add(room);
		solutionSet.add(person);
		solutionSet.add(weapon);
	}
	
	public ArrayList<Card> getSolutionSet() {
		return solutionSet;
	}
}