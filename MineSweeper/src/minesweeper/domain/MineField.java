package minesweeper.domain;

import java.util.*;

class MineField {

  private Map<Integer, Square> squares;
  private List<Integer> mineLocations;
  private int numberOfSquares;
  private int rows;
  private int cols;
  private int mines;

  public MineField() {
    this(10, 10, 10);
  }

  MineField(int cols, int rows, int mines) {
    this.rows = rows;
    this.cols = cols;
    this.mines = mines;
    this.numberOfSquares = cols * rows;
    squares = new HashMap<>(numberOfSquares); 

    createBlankSquares();
    createMineSquares();
    getToKnowNeighbors();
  }

  private void getToKnowNeighbors() {
    for (int location = 0; location < numberOfSquares; location++) {

      Square square = (Square) squares.get(new Integer(location));
      List<Integer> neighbors = getNeighbors(location);
      
      for (Integer neighbor : neighbors) {
        square.addNeighbor(squares.get(neighbor));
      }
    }
  }

  private void createMineSquares() {
    mineLocations = generateRandomNumbers(mines);

    for (Integer location : mineLocations) {
      
      Square mine = new MineSquare(location);
      squares.put(location, mine);

      List<Integer> neighbors = getNeighbors(location);

      createNumberSquares(neighbors);
    }
  }

  private void createNumberSquares(List<Integer> neighbors) {
    for (Integer neighbor : neighbors) {

      if (squares.get(neighbor) instanceof NumberSquare) {
        NumberSquare ns = (NumberSquare) squares.get(neighbor);
        ns.incrementValue();
      } else if (squares.get(neighbor) instanceof BlankSquare) {
        squares.put(neighbor, new NumberSquare(neighbor, 1));
      }
    }
  }

  private void createBlankSquares() {
    for (int i = 0; i < numberOfSquares; i++) {
      squares.put(i, new BlankSquare(i));
    }
  }

  void uncoverMineSquares() {
    for (Integer mineLocation : mineLocations) {
      Square mine = squares.get(mineLocation);
      mine.uncover();
    }
  }

  private List<Integer> getNeighbors(int location) {

    List<Integer> neighbors = new ArrayList<>();

    // if location is not on left side, then add neighbors from left
    if (location % (cols) != 0) {
      neighbors.add(location - 1);
      if (location > (cols-1)) {
        neighbors.add(location - (cols+1));
      }
      if (location < (numberOfSquares-10)) {
        neighbors.add(location + (cols-1));
      }
    }

    // if location is not on right side, then add neighbors from right
    if ((location + 1) % (cols) != 0) {
      neighbors.add(location + 1);
      if (location > (cols-1)) {
        neighbors.add(location - (cols-1));
      }

      if (location < (numberOfSquares-10)) {
        neighbors.add(location + (cols+1));
      }
    }

    // if location is not on top, then add neighbors from top
    if (location > (cols-1)) {
      neighbors.add(location - cols);
    }

    // if location is not on bottom, then add neighbors from bottom
    if (location < (numberOfSquares-10)) {
      neighbors.add(location + cols);
    }

    return neighbors;
  }

  protected List<Integer> generateRandomNumbers(int howMany) {
		List<Integer> randomNumbers = new ArrayList<>(howMany);

		for(int i=0;i < howMany;i++) {
			Integer random = null;

			do {
				random = (int)(Math.random()*numberOfSquares);
			}while(randomNumbers.contains(random));

			randomNumbers.add(random);
		}
		
		return randomNumbers;
	}

  public String toString() {

    String board = "";

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        board = board + "|" + squares.get(j + (i * cols));
      }
      board = board + "\n";
    }
    return board;
  }

  Map<Integer,Square> getSquares() {
    return squares;
  }

  void uncover(int location) {
    Square square = squares.get(location);
    square.uncover();
  }
}