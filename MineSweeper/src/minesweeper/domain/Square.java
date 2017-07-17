package minesweeper.domain;


public abstract class Square implements minesweeper.shared.ISquare{

	private int location;
	private SquareState state;
	
	public Square(int location) {
		this.location = location; 
		state = SquareStateFlyweight.CoveredSquareState;
	}

	public void addNeighbor(Square s) {
	};
	
	public void uncover() {
	  state.uncover(this);
	}

	public abstract int getValue();

	public abstract void goNude(); 
	
	public boolean isCovered() {
		return state == SquareStateFlyweight.CoveredSquareState;
	}

	public boolean isMarked() {
		return state == SquareStateFlyweight.MarkedSquareState;  
	}

	public int getLocation() {
		return location; 
	}

  public void mark() {
    state.mark(this);
  }
  
  public void setState(SquareState state) {
    this.state = state;
  }
}