package minesweeper.shared;

public interface ISquare {
	
	int getValue();

	boolean isCovered();

	boolean isMarked();
	
	int getLocation();
}