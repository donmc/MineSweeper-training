package minesweeper.domain;
import java.util.*;

class MineField {

	private Map squares;
	private List mineLocations; 
	private 	int squareCount;
	private int cols;
	private int rows;

	MineField(int cols, int rows, int mines) { 
		this.squareCount = cols * rows;
		this.cols = cols;
		this.rows = rows;
		squares = new HashMap(squareCount);

		for(int i=0;i<squareCount;i++) {
			squares.put(i,new BlankSquare(i));
		}
		
		mineLocations = generateRandomNumbers(mines);
		
		for(Iterator it = mineLocations.iterator();it.hasNext();) {
			Integer location = (Integer)it.next();
			Square mine = new MineSquare(location.intValue());
			squares.put(location,mine);

			List neighbors = getNeighbors(location.intValue());

			for(Iterator it2 = neighbors.iterator();it2.hasNext();) {
				location = (Integer)it2.next();

				if (squares.get(location) instanceof NumberSquare) {
					NumberSquare ns = (NumberSquare)squares.get(location);
					ns.incrementValue();
				}else if(squares.get(location) instanceof BlankSquare) {
					squares.put(location,new NumberSquare(location.intValue(),1));
				}
			}
		}		
		
		for(int location =0;location<squareCount;location++) {

			Square square = (Square)squares.get(new Integer(location));
			List neighbors = getNeighbors(location);

			for(Iterator it = neighbors.iterator();it.hasNext();) {
				square.addNeighbor((Square)(squares.get((Integer)it.next())));
			}
		}
	}

	public MineField() {
		this(10,10,10);
	}

	void uncoverMineSquares() {
		for(Iterator it = mineLocations.iterator();it.hasNext();) {
			Square mine = (Square)squares.get((Integer)it.next());
			mine.uncover();
		}
	}

	private List getNeighbors(int location) {
		
		List neighbors = new ArrayList();

		// if location is not on left side, then add neighbors from left
		if (location%(cols)!=0) {
			neighbors.add(location-1);
			if (location>(cols-1)) {
				neighbors.add(location-(cols+1));
			}
			if (location<(squareCount-cols)) {
				neighbors.add(location+(cols-1));
			}
		}

		// if location is not on right side, then add neighbors from right
		if ((location+1)%(cols)!=0){ 
			neighbors.add(location+1);
			if (location>cols-1) {
				neighbors.add(location-(cols-1));
			}

			if (location<(squareCount-cols)) {
				neighbors.add(new Integer(location+(cols+1)));
			}
		}

		// if location is not on top, then add neighbors from top
		if (location>(cols-1)) {  
			neighbors.add(location-cols);
		}

		// if location is not on bottom, then add neighbors from bottom
		if (location<squareCount-cols) { 
			neighbors.add(location+cols);
		}

		return neighbors;
	}

	List generateRandomNumbers(int howMany) {
		List randomNumbers = new ArrayList(howMany);

		for(int i=0;i<howMany;i++) {
			Integer random = null;

			do {
				random = new Integer((int)(Math.random()*squareCount));
			}while(randomNumbers.contains(random));

			randomNumbers.add(random);
		}
		
		return randomNumbers;
	}


	public String toString() {

		String board = "";

		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				board = board + "|"+ squares.get(j+(i*cols));
			}
			board = board + "\n";
		}
		return board;
	}


	Map getSquares(){
		return squares;
	}

	void uncover(int location) {
		Square square = (Square)squares.get(new Integer(location));
		square.uncover();
	}
}