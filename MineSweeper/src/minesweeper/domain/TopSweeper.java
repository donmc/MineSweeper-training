package minesweeper.domain;

class TopSweeper {

	private int score;
	private String name;

	public TopSweeper(String n, int s) {
		score = s;
		name = n;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}
}