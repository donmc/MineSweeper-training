package minesweeper.domain;

public class MineSquare extends Square{

	public MineSquare(int location) {
		super(location);
	}

	public void goNude() {
		if (!MineSweeper.getInstance().isGameOver()) {
			MineSweeper.getInstance().gameLostOn(this);
		}
	}
	public int getValue() {
		return 0;
	}

	public String toString() {

		return "*";
	}

}