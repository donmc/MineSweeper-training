package minesweeper.shared;

import java.util.*;

public interface IMineSweeper {

	void resetGame(int cols, int rows, int mines);

	void addCounterListener(CounterListener listener);

	void addGameOverListener(GameOverListener listener);

	Map getSquares();

	void uncover(int location);

	void mark(int location);

	boolean isGameOver();

	int getMineCount();

	Object[][] getTopSweepers() throws Exception;

	boolean isHighScore();

	void addTopSweeper(String name) throws Exception;
}