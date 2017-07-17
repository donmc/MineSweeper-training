package minesweeper.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class WhenCreatingMineField {

  @Test
  public void shouldHave100Squares() {
    MineField field = new MineField();
    int numberOfSquares = field.getSquares().size();
    assertEquals(100, numberOfSquares);
  }

  @Test
  public void shouldHave10Mines() {
    MineField field = new MineField();
    Map<Integer, Square> squares = field.getSquares();
    int minecount = 0;
    for (Square square : squares.values()) {
      if (square instanceof MineSquare) {
        minecount++;
      }
    }

    assertEquals(10, minecount);
  }

  @Test
  public void shouldHaveSquaresInCorrectPlace() {
    MineField field = new MineField() {

      @Override
      protected List<Integer> generateRandomNumbers(int howMany) {
        Integer[] numbers = { 9, 27, 32, 33, 42, 52, 67, 82, 91, 93 };
        return Arrays.asList(numbers);
      }
    };

    Map<Integer, Square> squares = field.getSquares();
    assertTrue(squares.get(43) instanceof NumberSquare);
    assertTrue(((Square) squares.get(43)).getValue() == 4);
    assertTrue(((Square) squares.get(92)).getValue() == 3);
    assertTrue(((Square) squares.get(8)).getValue() == 1);
    assertTrue(((Square) squares.get(41)).getValue() == 3);
    System.out.println(field);
  }

  @Test
  public void shouldHaveSquaresInCorrectPlaceForCustomBoard() {
    MineField field = new MineField(5, 3, 4) {

      @Override
      protected List<Integer> generateRandomNumbers(int howMany) {
        Integer[] numbers = { 3, 4, 11, 14 };
        return Arrays.asList(numbers);
      }
    };

    Map<Integer, Square> squares = field.getSquares();

    assertTrue(squares.get(2).getValue() == 1);
    assertTrue(squares.get(8).getValue() == 3);
    assertTrue(squares.get(9).getValue() == 3);
    assertTrue(squares.get(10).getValue() == 1);
    assertTrue(squares.get(13).getValue() == 1);
    System.out.println(field);
  }

}
