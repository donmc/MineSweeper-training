package minesweeper.gui;

 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;

class HighScoreDialog extends JDialog {

	private JLabel[][] topSweepersGUI = new JLabel[10][3];
	private Object[][] topSweepersData;

	public HighScoreDialog(JFrame owner, Object[][] data) {
		super(owner, "Top Sweepers", true);
		topSweepersData = data;

		initialize();
        setResizable(false);
		setLocationRelativeTo(owner);
	}

	public void initialize() {
		JPanel scorePanel = new JPanel();
		scorePanel.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
		scorePanel.setLayout(new GridLayout(10,3,5,5));
		for(int i=0;i<topSweepersGUI.length;i++) {
			topSweepersGUI[i][0] = new JLabel(" " + (i+1) + ".  ");
			topSweepersGUI[i][1] = new JLabel("" + topSweepersData[i][0]);
			topSweepersGUI[i][2] = new JLabel(" " + topSweepersData[i][1]);
            topSweepersGUI[i][0].setHorizontalAlignment(JLabel.CENTER);
            topSweepersGUI[i][2].setHorizontalAlignment(JLabel.CENTER);
			scorePanel.add(topSweepersGUI[i][0]);
			scorePanel.add(topSweepersGUI[i][1]);
            scorePanel.add(topSweepersGUI[i][2]);
			
            for(int j = 0; j<3; j++) {
                topSweepersGUI[i][j].setFont(new Font(null,Font.BOLD,12));
            }
		}	

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		JButton okButton = new JButton(" OK ");
        buttonPanel.add(okButton);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});

		getContentPane().add(scorePanel);
		getContentPane().add(buttonPanel,BorderLayout.SOUTH);
		pack();
	}

}