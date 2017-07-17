package minesweeper.domain;

public class CoveredSquareState extends SquareState {

  @Override
  public void uncover(Square square) {
    square.setState(SquareStateFlyweight.UncoveredSquareState);
    MineSweeper.getInstance().incrementUncoveredCount();
    square.goNude();
  }

  @Override
  public void mark(Square square) {
    MineSweeper.getInstance().decrementMineCounter();
    square.setState(SquareStateFlyweight.MarkedSquareState);
  }

}
