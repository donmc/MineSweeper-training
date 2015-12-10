package minesweeper.shared;

import java.util.*;

public interface CounterListener extends EventListener{
	
	void counterChanged(Object counterValue);
}