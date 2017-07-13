package minesweeper.domain;


public abstract class Square implements minesweeper.shared.ISquare{

	private int location;
	private boolean isCovered;
	
	public Square(int location) {
		this.location = location; 
		isCovered = true;
	}

	public void addNeighbor(Square s) {
	};
	
	public void uncover() {
		if (isCovered) {
			MineSweeper.getInstance().incrementUncoveredCount();
			isCovered = false;
			goNude();
		}
	}

	public abstract int getValue();

	public abstract void goNude();
	
	public boolean isCovered() {
		return isCovered;
	}

	public boolean isMarked() {
		//TODO: not yet implemented!!
		return false;  
	}

	public int getLocation() {
		return location;
	}
}