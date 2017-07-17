package minesweeper.domain;

public class SquareStateFlyweight {

  public static final SquareState UncoveredSquareState = new UncoveredSquareState();
  public static final SquareState CoveredSquareState = new CoveredSquareState();
  public static final SquareState MarkedSquareState = new MarkedSquareState();
}
