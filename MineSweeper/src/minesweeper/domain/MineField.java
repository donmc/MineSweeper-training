package minesweeper.domain;
import java.util.*;

class MineField {

	private Map squares;
	private List mineLocations; 

	MineField() {
		squares = new HashMap(100);

		for(int i=0;i<100;i++) {
			squares.put(new Integer(i),new BlankSquare(i));
		}
		
		mineLocations = generateRandomNumbers(10);
		
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
		
		for(int location =0;location<100;location++) {

			Square square = (Square)squares.get(new Integer(location));
			List neighbors = getNeighbors(location);

			for(Iterator it = neighbors.iterator();it.hasNext();) {
				square.addNeighbor((Square)(squares.get((Integer)it.next())));
			}
		}
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
		if (location%(10)!=0) {
			neighbors.add(new Integer(location-1));
			if (location>9) {
				neighbors.add(new Integer(location-11));
			}
			if (location<90) {
				neighbors.add(new Integer(location+9));
			}
		}

		// if location is not on right side, then add neighbors from right
		if ((location+1)%(10)!=0){ 
			neighbors.add(new Integer(location+1));
			if (location>9) {
				neighbors.add(new Integer(location-9));
			}

			if (location<90) {
				neighbors.add(new Integer(location+11));
			}
		}

		// if location is not on top, then add neighbors from top
		if (location>9) {  
			neighbors.add(new Integer(location-10));
		}

		// if location is not on bottom, then add neighbors from bottom
		if (location<90) { 
			neighbors.add(new Integer(location+10));
		}

		return neighbors;
	}

	List generateRandomNumbers(int howMany) {
		List randomNumbers = new ArrayList(howMany);

		for(int i=0;i<howMany;i++) {
			Integer random = null;

			do {
				random = new Integer((int)(Math.random()*100));
			}while(randomNumbers.contains(random));

			randomNumbers.add(random);
		}
		
		return randomNumbers;
	}


	public String toString() {

		String board = "";

		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				board = board + "|"+ squares.get(new Integer(j+(i*10)));
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