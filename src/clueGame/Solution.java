package clueGame;

public class Solution{
	private Card room;
	private Card person;
	private Card weapon;
	private Card[] solutionSet = new Card[3];
	
	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
		solutionSet[0] = room;
		solutionSet[1] = person;
		solutionSet[2] = weapon;
	}
}