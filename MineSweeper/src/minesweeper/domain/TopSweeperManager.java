package minesweeper.domain;

import minesweeper.services.*;

class TopSweeperManager {

	Object[][] topSweepers = new Object[10][2];

	public TopSweeperManager() throws Exception{
		topSweepers = TopSweeperBroker.getInstance().getTopSweepers();
	}

	Object[][] getTopSweepers() throws Exception{
		topSweepers = TopSweeperBroker.getInstance().getTopSweepers();
		return topSweepers;
	}

	void addTopSweeper(String name, int score) throws Exception{
		System.out.println(name + " got " + score);

		for(int i=0;i<topSweepers.length;i++) {
			
			if (score < ((Integer)topSweepers[i][1]).intValue()) {
				
				for (int j = topSweepers.length-2;j>=i;j--) {
					topSweepers[j+1][1] = topSweepers[j][1];
					topSweepers[j+1][0] = topSweepers[j][0];
				}

				topSweepers[i][1] = new Integer(score);
				topSweepers[i][0] = name;

				TopSweeperBroker.getInstance().saveTopSweepers(topSweepers);
				return;
			}

		}
	}

	public boolean isHighScore(int counter) {

		if (counter<((Integer)topSweepers[9][1]).intValue()) {
			return true;
		}
		
		return false;
	}

}