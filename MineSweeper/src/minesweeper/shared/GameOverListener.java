package minesweeper.shared;

import java.util.*;

public interface GameOverListener extends EventListener{
	
	void gameLost(GameOverEvent event);

	void gameWon(GameOverEvent event);
}