package minesweeper.gui;

import javax.swing.*;

public class MineSweeperSquare extends JLabel{

	private int _location;

	public MineSweeperSquare(int location) {
		super();
		_location = location;
	}

	public int getSquareLocation() {
		return _location;
	}
}