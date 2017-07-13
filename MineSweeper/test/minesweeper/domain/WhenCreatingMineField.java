package minesweeper.domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
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
    Map squares = field.getSquares();
    int minecount = 0;
    for (Iterator Square = squares.values().iterator(); Square.hasNext();) {
      Square square = (Square) Square.next();
      if(square instanceof MineSquare) {
        minecount++;
      }
    }
    
    assertEquals(10, minecount);
  }
  
  @Test
  public void shouldHaveSquaresInCorrectPlace() {
    MineField field = new MineField() {

      @Override
      protected List generateRandomNumbers(int howMany) {
        Integer[] numbers = {9, 27, 32, 33, 42, 52, 67, 82, 91, 93};
        return Arrays.asList(numbers);
      }
    };
    
    Map squares = field.getSquares();
    assertTrue(squares.get(43) instanceof NumberSquare);
    assertTrue(((Square)squares.get(43)).getValue() == 4);
    assertTrue(((Square)squares.get(92)).getValue() == 3);
    assertTrue(((Square)squares.get(8)).getValue() == 1);
    assertTrue(((Square)squares.get(41)).getValue() == 3);
    System.out.println(field);
  }

}
