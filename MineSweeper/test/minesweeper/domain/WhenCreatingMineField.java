package minesweeper.domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class WhenCreatingMineField {

	@Test
	public void shouldHave100Squares() {
		MineField field = new MineField();
		int numberOfSquares = field.getSquares().size();
		assertEquals(100, numberOfSquares);  
	}
	
	@Test
	public void shouldHave10MineSquares() {
		MineField field = new MineField();
		int mineSquareCount = 0;
		for (int i = 0; i < field.getSquares().size(); i++) {
			if (field.getSquares().get(i) instanceof MineSquare) mineSquareCount++;
		}
		assertEquals(10, mineSquareCount);
	} 
	
	@Test
	public void shouldHaveCorrectNumberOfSquaresAroundMinesForCustomBoard() {
		int rows = 3;
		int cols = 7;
		int mines = 3;
		
		MineField field = new MineField(cols, rows, mines) {
			@Override
			List<Integer> generateRandomNumbers(int howMany) {
				return Arrays.asList(6, 8, 12);
			}
		};

		Map<Integer, Square> squares = field.getSquares();

		assertEquals(1, ((Square)squares.get(0)).getValue());
		assertEquals(1, ((Square)squares.get(1)).getValue());
		assertEquals(1, ((Square)squares.get(2)).getValue());
		assertEquals(1, ((Square)squares.get(4)).getValue());
		assertEquals(2, ((Square)squares.get(5)).getValue());
		assertEquals(1, ((Square)squares.get(7)).getValue());
		assertEquals(1, ((Square)squares.get(9)).getValue());
		assertEquals(1, ((Square)squares.get(11)).getValue());
		assertEquals(2, ((Square)squares.get(13)).getValue());
		assertEquals(1, ((Square)squares.get(14)).getValue());
		assertEquals(1, ((Square)squares.get(15)).getValue());
		assertEquals(1, ((Square)squares.get(16)).getValue());
		assertEquals(1, ((Square)squares.get(18)).getValue());
		assertEquals(1, ((Square)squares.get(19)).getValue());
		assertEquals(1, ((Square)squares.get(20)).getValue());
		
//		System.out.println(field);
	}
	
	
	
	@Test
	public void shouldHaveCorrectNumberOfSquaresAroundMines() {
		MineField field = new MineField() {
			@Override
			List<Integer> generateRandomNumbers(int howMany) {
				return Arrays.asList(11, 17, 35, 44, 45, 52, 88, 89, 90, 98);
			}
		};

		Map<Integer, Square> squares = field.getSquares();

		assertEquals(1, ((Square)squares.get(0)).getValue());
		assertEquals(1, ((Square)squares.get(1)).getValue());
		assertEquals(1, ((Square)squares.get(2)).getValue());
		assertEquals(1, ((Square)squares.get(10)).getValue());
		assertEquals(1, ((Square)squares.get(12)).getValue());
		assertEquals(1, ((Square)squares.get(16)).getValue());
		assertEquals(1, ((Square)squares.get(18)).getValue());
		assertEquals(2, ((Square)squares.get(26)).getValue());
		assertEquals(3, ((Square)squares.get(34)).getValue());
		assertEquals(2, ((Square)squares.get(36)).getValue());
		assertEquals(2, ((Square)squares.get(53)).getValue());
		assertEquals(2, ((Square)squares.get(54)).getValue());
		assertEquals(2, ((Square)squares.get(55)).getValue());
		assertEquals(3, ((Square)squares.get(99)).getValue());
	}

}
