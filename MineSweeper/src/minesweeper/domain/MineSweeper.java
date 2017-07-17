package minesweeper.domain;

import java.util.*;
import minesweeper.shared.*;

public class MineSweeper implements IMineSweeper{

	private static MineSweeper game;
	private TopSweeperManager topSweeperManager;

	private MineField board;
	private int mineCount;
	private int counter;
	private int uncoveredCount = 0;
	private boolean counterRunning;
	private boolean gameOver = true;

	private List<CounterListener> counterListeners = new ArrayList<>();
	private List<GameOverListener> gameOverListeners = new ArrayList<>();
	
	private MineSweeper(int cols, int rows, int mines) throws Exception{
		topSweeperManager = new TopSweeperManager();
		initialize(cols,rows,mines); 
	}

	private MineSweeper() throws Exception{
		this(10,10,10);
	}

	public static MineSweeper getInstance(){
		try {
			if (game==null) {
				game = new MineSweeper();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return game;
	}

	void checkForWin(Object lastNonMine) {
		int numOfSquares = board.getSquares().size();
		if (uncoveredCount + board.getNumberOfMines() == numOfSquares) {
			gameOver = true;
			fireGameOver(true, lastNonMine);
		}
	}

	public boolean isHighScore() {

		return topSweeperManager.isHighScore(counter);
	}

	void gameLostOn(Object mine) {
		gameOver = true;

		board.uncoverMineSquares();
		fireGameOver(false,mine);
	}

	private void fireGameOver(boolean isWin, Object lastUncoveredSquare) {
		
		stopCounter();
		for (GameOverListener gol : gameOverListeners) {
      
			if (isWin) {
				gol.gameWon(new GameOverEvent(lastUncoveredSquare));
			}else {
				gol.gameLost(new GameOverEvent(lastUncoveredSquare));
			}
		}		
	}	

	public void resetGame(int cols, int rows, int mines) {
		initialize(cols,rows,mines);
	}

	public void addCounterListener(CounterListener listener)	 {
		counterListeners.add(listener);
	}

	public void addGameOverListener(GameOverListener listener) {
		gameOverListeners.add(listener);
	}

	private void startTimer() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (counterRunning) {
					try {
						incrementCounter();
						Thread.sleep(1000);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	private void stopCounter() {
		counterRunning=false;
	}

	private void initialize(int cols, int rows, int mines) {
	
		board = new MineField(cols, rows, mines);

		gameOver = false;
		uncoveredCount = 0;
		resetCounter();
		stopCounter();
		mineCount = mines;
		
		System.out.println(board);
	}

	private void resetCounter() {
		counter=0;
		fireCounterChanged();
	}

	private void incrementCounter() {
		if (counter<999) {
			counter++;
			fireCounterChanged();
		}else {
			counterRunning = false;
		}
	}

	private void fireCounterChanged() {
	  for (CounterListener cl : counterListeners) {
			cl.counterChanged(counter);
		}
	}

	public Map<Integer, Square> getSquares(){

		return board.getSquares();
	}

	public void uncover(int location) {
		if (counter==0) {
			counterRunning = true;
			startTimer();
		}
		board.uncover(location);
	}

	public void mark(int location) {
		board.mark(location);
	}

	void incrementUncoveredCount() {
		uncoveredCount++;
	}
	void incrementMineCounter() {
		mineCount++;
	}
	void decrementMineCounter() {
		mineCount--;
	}

	public int getMineCount() {
		return mineCount;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public Object[][] getTopSweepers() throws Exception{
		return topSweeperManager.getTopSweepers();
	}

	public void addTopSweeper(String name) throws Exception{
		topSweeperManager.addTopSweeper(name,counter);
	}
}