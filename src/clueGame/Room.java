/**
 * @Author Ben Isenhart
 * @Author Sam Mantle
 * Date 3 - 04 - 2024
 * Collaborators: None
 * Sources: JavaDocs
 * 
 * Room: Stores the rooms name, center of the room, and the rooms label cell
 * 
 */
package clueGame;


public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	/**
	 * Constructor: Creates a new Room and sets its name
	 * 
	 * @param name
	 */
	public Room(String name) {
		this.name = name;
	}

	/**
	 * Returns the rooms name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the rooms labelCell
	 * 
	 * @param cell
	 */
	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}
	
	/**
	 * Returns the rooms labelCell
	 */
	public BoardCell getLabelCell() {
		return this.labelCell;
	}

	/**
	 * Sets the rooms centerCell
	 * 
	 * @param cell
	 */
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}

	/**
	 * Returns the rooms centerCell
	 */
	public BoardCell getCenterCell() {
		return this.centerCell;
	}
}