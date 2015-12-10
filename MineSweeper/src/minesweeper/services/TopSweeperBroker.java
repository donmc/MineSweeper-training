package minesweeper.services;

import java.io.*;
import java.net.*;
import java.util.*;

public class TopSweeperBroker {

	private static TopSweeperBroker broker = new TopSweeperBroker();

	private URL url = ClassLoader.getSystemResource("images/topSweepers.dat");

	private TopSweeperBroker() {

	}

	public static TopSweeperBroker getInstance() {

		return broker;
	}

	public Object[][] getTopSweepers() throws Exception{
		Object[][] topSweepers = new Object[10][2];
		System.out.println(url.getFile());
		String s = url.getPath();
		System.out.println(s.substring(1));
		FileReader fileReader = new FileReader(s);
		BufferedReader reader = new BufferedReader(fileReader);
		String topSweeper = reader.readLine();

		for (int i=0;i<10;i++) {

			String name = null;
			Integer score = null;

			if (topSweeper==null) {
				name = "Anonymous";
				score = new Integer(999);
			}else {

				StringTokenizer tokenizer = new StringTokenizer(topSweeper,",");
				name = tokenizer.nextToken().trim();
				score = new Integer(tokenizer.nextToken().trim());
			}			
			topSweepers[i][0] = name;
			topSweepers[i][1] = score;
			topSweeper = reader.readLine();
		}

		return topSweepers;
	}

	public void saveTopSweepers(Object[][] topSweepers) throws IOException{
	
		FileWriter fileWriter = new FileWriter(url.getFile());
		BufferedWriter writer = new BufferedWriter(fileWriter);

		for (int i=0;i<topSweepers.length;i++) {
			String line = topSweepers[i][0] + "," + topSweepers[i][1];
			writer.write(line);
			writer.newLine();
			writer.flush();
		}
	}
}