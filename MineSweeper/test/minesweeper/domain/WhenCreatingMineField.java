package minesweeper.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class WhenCreatingMineField {

	@Test
	public void shouldHave100Squares() {
		MineField mineField = new MineField();
		
		assertEquals(100, mineField.getSquares().size());
	}
	
	@Test
	public void shouldHave10MineSquares() {
		MineField mineField = new MineField();
		int mineCounter = 0;
		for (int i = 0; i < mineField.getSquares().size(); i++) {
			Square square = (Square)mineField.getSquares().get(i);
			if(square instanceof MineSquare) 
				mineCounter++;
		}
		
		assertEquals(10, mineCounter);
	}
	
	@Test
	public void shouldHaveSquaresInCorrectPlace() {
		MineField mineField = new MineField() {
			@Override
			List generateRandomNumbers(int howMany) {
				return Arrays.asList(new Integer[]{11, 14, 28, 44, 47, 48, 57, 62, 81, 98});
			}
		};
		
		Map squares = mineField.getSquares();

		assertTrue(squares.get(38) instanceof NumberSquare);
		assertEquals(3, ((Square)squares.get(38)).getValue());
		assertTrue(squares.get(9) instanceof BlankSquare);
		assertTrue(squares.get(0) instanceof NumberSquare);
		System.out.println(mineField);
	}
	
	
	@Test
	public void shouldHaveCustomMinefield() {
		MineField mineField = new MineField(5, 3, 4) {
			@Override
			List generateRandomNumbers(int howMany) {
				return Arrays.asList(new Integer[]{2, 5, 6, 11});
			}
		};
		
		Map squares = mineField.getSquares();

		assertTrue(squares.get(0) instanceof NumberSquare);
		assertEquals(2, ((Square)squares.get(0)).getValue());
		assertTrue(squares.get(14) instanceof BlankSquare);
		assertEquals(3, ((Square)squares.get(10)).getValue());
		System.out.println(mineField);
	}

} 







