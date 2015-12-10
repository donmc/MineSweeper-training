package minesweeper.shared;

import java.util.*;

public class GameOverEvent extends EventObject{
	
	private boolean _isWin;

	public GameOverEvent(Object source) {
		super(source);
	}
}