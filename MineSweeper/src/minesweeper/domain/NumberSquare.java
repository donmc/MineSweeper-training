package minesweeper.domain;

public class NumberSquare extends Square{

	private int value;

	public NumberSquare(int location, int value) {
		super(location);
		setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void goNude() {
		if (!MineSweeper.getInstance().isGameOver()) {
			MineSweeper.getInstance().checkForWin(this);
		}
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void incrementValue() {
		value++;
	}

	public String toString() {
		return ""+value;
	}

}