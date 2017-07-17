package minesweeper.domain;

public class MarkedSquareState extends SquareState {

  @Override
  public void mark(Square square) {
    MineSweeper.getInstance().incrementMineCounter();
    square.setState(SquareStateFlyweight.CoveredSquareState);
  }

}
