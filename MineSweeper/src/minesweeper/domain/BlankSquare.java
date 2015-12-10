package minesweeper.domain;
import java.util.*;

public class BlankSquare extends Square{

	private List neighbors = new ArrayList();

	public BlankSquare(int numOfSquares) {
		super(numOfSquares);
	}

	public void goNude() {
		if (!MineSweeper.getInstance().isGameOver()) {

			MineSweeper.getInstance().checkForWin(this);

			for(Iterator it = neighbors.iterator();it.hasNext();) {
				Square square = (Square) it.next();
				square.uncover();
			}
		}
	}

	public int getValue() {
		return -1;
	}
	
	public String toString() {
		return "0";
	}

	public void addNeighbor(Square s) {
		neighbors.add(s);
	}

}