package minesweeper.gui;

 import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import minesweeper.shared.CounterListener;
import minesweeper.shared.GameOverEvent;
import minesweeper.shared.GameOverListener;
import minesweeper.shared.ISquare;
//import the sun.audio package

 
public class MineSweeperApp extends javax.swing.JFrame {

	private minesweeper.shared.IMineSweeper game;
	private MineSweeperSquare[] squares;
	private JButton resetButton;
	private JPanel centerPanel = new JPanel();
	private JPanel topPanel;
	private JLabel mineCounter = new JLabel("000");
	private JLabel timer = new JLabel("000");

	private JMenuItem newGameMenuItem;
	private JMenuItem customMenuItem;
	private JMenuItem exitMenuItem; 
	private JMenuItem topSweepersMenuItem;

	private int columns = 10;
	private int rows = 10;
	private int mines = 10;
	private double dl;

	private ImageIcon flagImage = 	new ImageIcon(ClassLoader.getSystemResource("images/flag.gif"));
	private ImageIcon winIcon = 	new ImageIcon(ClassLoader.getSystemResource("images/happy.gif"));
	private ImageIcon loseIcon = 	new ImageIcon(ClassLoader.getSystemResource("images/r_bloody.jpg"));
	private ImageIcon resetIcon = 	new ImageIcon(ClassLoader.getSystemResource("images/r_ehh.jpg"));
	private ImageIcon[] images =  {	new ImageIcon(ClassLoader.getSystemResource("images/mine.gif")),
									new ImageIcon(ClassLoader.getSystemResource("images/one.gif")),
									new ImageIcon(ClassLoader.getSystemResource("images/two.gif")),
									new ImageIcon(ClassLoader.getSystemResource("images/three.gif")),
									new ImageIcon(ClassLoader.getSystemResource("images/four.gif")),
									new ImageIcon(ClassLoader.getSystemResource("images/five.gif")),
									new ImageIcon(ClassLoader.getSystemResource("images/six.gif")),
									new ImageIcon(ClassLoader.getSystemResource("images/seven.gif")),
									new ImageIcon(ClassLoader.getSystemResource("images/eight.gif"))};
	public MineSweeperApp()	{

		super("TDD Mine Sweeper");
		
		try {
			game = minesweeper.domain.MineSweeper.getInstance();
		}catch(Exception ioe) {
			JOptionPane.showMessageDialog(this, "Trouble Reading Data", "alert", JOptionPane.ERROR_MESSAGE); 
			System.exit(0);
		}
		
		initInterface();

		initMineField();

		initEvents();
	}
	
	public void initInterface() {

		try {
	        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
    	} catch (Exception ex_ignored) {
        	// go with default l&f
    	}

		setIconImage(flagImage.getImage());
		topPanel = new JPanel();
		JPanel outsideTopPanel = new JPanel();

		outsideTopPanel.setLayout(new BorderLayout());
		topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.X_AXIS));

		resetButton = new JButton();
		resetButton.setPreferredSize(new Dimension(30,30));
        resetButton.setFocusPainted(false);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");

		newGameMenuItem = new JMenuItem("New Game");
		customMenuItem = new JMenuItem("Custom...");
		exitMenuItem = new JMenuItem("Exit");
		topSweepersMenuItem = new JMenuItem("Top Sweepers...");

		gameMenu.add(newGameMenuItem);
		gameMenu.add(new JSeparator());
		gameMenu.add(customMenuItem);
		gameMenu.add(new JSeparator());
		gameMenu.add(topSweepersMenuItem);
		gameMenu.add(new JSeparator());
		gameMenu.add(exitMenuItem);

		menuBar.add(gameMenu);

		initCounters(mineCounter);
		initCounters(timer);
		
		outsideTopPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		topPanel.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLoweredBevelBorder(),
						BorderFactory.createEmptyBorder(5,6,5,5)));

		//topPanel.add(Box.createHorizontalStrut(5));
		topPanel.add(mineCounter);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(resetButton);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(timer);
		//topPanel.add(Box.createHorizontalStrut(5));

		setJMenuBar(menuBar);

		outsideTopPanel.add(topPanel);
		getContentPane().add(outsideTopPanel,BorderLayout.NORTH);

		setResizable(false);
	}

	public void centerFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screenSize.width + " x " + screenSize.height);
        setLocation((screenSize.width - getWidth()) / 2, ((screenSize.height - getHeight()) / 2));
    }

	public void initCounters(JLabel counter) {

		Dimension dimension = new Dimension(45,30);
		Font font = new Font("Dialog",Font.BOLD,20);

		counter.setOpaque(true);
		counter.setPreferredSize(dimension);
		counter.setFont(font);
		counter.setBorder(BorderFactory.createLoweredBevelBorder());
		counter.setForeground(new Color(255,50,50));
		counter.setBackground(new Color(80,80,80));
	}


	public void initEvents(){

		ActionListener reset = new ActionListener(){
			public void actionPerformed(ActionEvent ae)	{
				initMineField();
			}
		};

		resetButton.addActionListener(reset);
		newGameMenuItem.addActionListener(reset);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				doExit();
			}
		});

		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				doExit();
			}		
		});

		topSweepersMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				showTopSweepersDialog();	
			}
		});

		customMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				showCustomDialog();
			}
		});

		game.addCounterListener(new CounterListener() {
			public void counterChanged(Object counterValue) {
				setCounter(counterValue);
			}
		});

		game.addGameOverListener(new GameOverListener() {
			public void gameWon(GameOverEvent goe) {
				doWin();
			}

			public void gameLost(GameOverEvent goe) {
				doLoss((ISquare)goe.getSource());
			}
		});
	}
	public void doLoss(ISquare mine) {
		MineSweeperSquare square = squares[mine.getLocation()];
		square.setBackground(Color.red);
		resetButton.setIcon(loseIcon);

		try {
			
			new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
			      public void run() {
			    	  
			        try {
					  URL url = ClassLoader.getSystemResource("images/explosion.au");

			          Clip clip = AudioSystem.getClip();
			          AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);

			          clip.open(inputStream);
			          clip.start(); 
			        } catch (Exception e) {
			          System.err.println(e.getMessage());
			        }
			      }
			    }).start();

			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						shakeItUp();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.start();	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void shakeItUp() throws Exception{
		moveIt(Color.red,-5,-5);
		moveIt(Color.yellow,10,10);
		moveIt(Color.red,-5,-5);
		moveIt(Color.yellow,-5,-5);
		moveIt(Color.red,10,10);
		moveIt(getBackground(),-5,-5);
		moveIt(Color.red,-5,-5);
		moveIt(Color.yellow,10,10);
		moveIt(Color.red,-5,-5);
		moveIt(Color.yellow,-5,-5);
		moveIt(Color.red,10,10);
		moveIt(getBackground(),-5,-5);
		moveIt(Color.red,-5,-5);
		moveIt(Color.yellow,10,10);
		moveIt(Color.red,-5,-5);
		moveIt(Color.yellow,-5,-5);
		moveIt(Color.red,10,10);
		moveIt(getBackground(),-5,-5);
	}


	private void moveIt(final Color color, final int deltaX, final int deltaY) throws Exception{
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				try {
					centerPanel.setBackground(color);
					setLocation(getX() + deltaX, getY() + deltaY);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		Thread.sleep(80);
	}

	public void doWin() {
		resetButton.setIcon(winIcon);
		if (game.isHighScore()) {
			showNameInputDialog();
		}
	}

	public void setCounter(Object counterValue) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumIntegerDigits(3);
		timer.setText(format.format(counterValue));
		timer.repaint();
	}

	public void doExit() {
		dispose();
		System.exit(0);
	}
	
	public void initMineField()	{

		resetButton.setIcon(resetIcon);

		getContentPane().remove(this.centerPanel);
		centerPanel = new JPanel();
		centerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		getContentPane().add(centerPanel,BorderLayout.CENTER);

		game.resetGame(columns,rows,mines);

		squares = new MineSweeperSquare[rows*columns];
		topPanel.setBackground(centerPanel.getBackground());
		centerPanel.setLayout(new GridLayout(rows,columns,0,0));

		for (int i = 0; i < (rows*columns); i++) {
			initSquares(i);
			centerPanel.add(squares[i]);
		}	
		updateSquares();
		pack();
		centerFrame();
	}

	public void updateMineCounter() {
		int mineCount = game.getMineCount();
		NumberFormat format = NumberFormat.getInstance();
		if (mineCount<0) {
			format.setMinimumIntegerDigits(2);
		}else {
			format.setMinimumIntegerDigits(3);
		}
		mineCounter.setText(format.format(mineCount));
	}

	public void showCustomDialog() {

		JPanel widthPanel = new JPanel();
		JPanel heightPanel = new JPanel();
		JPanel minesPanel = new JPanel();

		JLabel wLabel = new JLabel("Width: ");
		JLabel hLabel = new JLabel("Height: ");
		JLabel mLabel = new JLabel("Mines: ");
        wLabel.setForeground(Color.black);
       	hLabel.setForeground(Color.black);
   		mLabel.setForeground(Color.black);

		JTextField widthText = new JTextField(5);
		JTextField heightText = new JTextField(5);
		JTextField minesText = new JTextField(5);

		
		widthPanel.add(wLabel);
		widthPanel.add(widthText);
		heightPanel.add(hLabel);
		heightPanel.add(heightText);
		minesPanel.add(mLabel);
		minesPanel.add(minesText);
		
		Object[] message = {widthPanel, heightPanel, minesPanel};

		JOptionPane pane = new JOptionPane();
	
		pane.setMessage(message);	// dialog message
		pane.setOptionType(JOptionPane.OK_CANCEL_OPTION);

		JDialog dialog = pane.createDialog(this, "Custom Mine Field");

		widthText.setText(""+columns);
		heightText.setText(""+rows);
		minesText.setText(""+mines);

		dialog.setResizable(false);
		dialog.setSize(200,180);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);

		if (pane.getValue()!=null && pane.getValue().toString().equals("0")) {
			try {
				columns = Integer.parseInt(widthText.getText());
				rows = Integer.parseInt(heightText.getText());
				mines = Integer.parseInt(minesText.getText());
				if(mines>(columns*rows)){
					mines=columns*rows;
				}
				initMineField();
			}catch(NumberFormatException nfe) {
			    // go with previous values
			}
		}
	}

	public void initSquares(int i)	{
		squares[i] = new MineSweeperSquare(i);
		squares[i].setPreferredSize(new Dimension(20,20));
		squares[i].setBorder(BorderFactory.createRaisedBevelBorder());
		squares[i].setHorizontalAlignment(JLabel.CENTER);

		squares[i].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (!game.isGameOver()) {
					MineSweeperSquare square = (MineSweeperSquare) me.getSource();
					if (!SwingUtilities.isLeftMouseButton(me)) {
						int location = square.getSquareLocation();
						markSquareAt(location);
					}else {
						square.setBorder(
							BorderFactory.createLoweredBevelBorder());
					}
				}
				
			}
			public void mouseReleased(MouseEvent me) {
				if (!game.isGameOver()) {
					MineSweeperSquare square = (MineSweeperSquare) me.getSource();
					if (square.contains(me.getX(),me.getY()) && SwingUtilities.isLeftMouseButton(me)) {
						int location = square.getSquareLocation();
						uncoverSquareAt(location);
					}
					updateSquares();
				}
			}
		});
	}

	public static void main(String[] args)
	{
		MineSweeperApp game = new MineSweeperApp();
		game.setVisible(true);
	}

	private void uncoverSquareAt(int location) {
		game.uncover(location);
	}

	private void markSquareAt(int location) {
		game.mark(location);
	}

	public void updateSquares()	{
		Map squaresMap = game.getSquares();
		centerPanel.setVisible(false);
		for (int i = 0; i < squaresMap.size(); i++)	{
			ISquare s = (ISquare)squaresMap.get(new Integer(i));
			if (!s.isCovered() && !s.isMarked()) {
				int value = s.getValue();
				if (value>=0) {
					squares[i].setIcon(images[value]);
				}
				squares[i].setBorder(BorderFactory.createLoweredBevelBorder());
			}else {
				if(s.isMarked()){
					squares[i].setIcon(flagImage);
				}else {
					squares[i].setIcon(null);
				}
				squares[i].setBorder(BorderFactory.createRaisedBevelBorder());
			}
		}
		updateMineCounter();
		centerPanel.setVisible(true);
	}	

	private void showTopSweepersDialog() {
		try {
			HighScoreDialog dialog = new HighScoreDialog(this,game.getTopSweepers());
			dialog.setVisible(true);
		}catch(Exception ioe) {
			JOptionPane.showMessageDialog(this, "Trouble Reading Data", "alert", JOptionPane.ERROR_MESSAGE); 
		}
	}

	private void showNameInputDialog() {
		JPanel labelPanel = new JPanel();
		JPanel textPanel = new JPanel();

		JLabel topLabel = new JLabel("You are a fast sweeper!");
		JLabel topLabel2 = new JLabel("Enter your name: ");
        topLabel.setForeground(Color.black);
       	
       	JTextField nameText = new JTextField(10);
		
		labelPanel.add(topLabel);
		labelPanel.add(topLabel2);
		textPanel.add(nameText);
		
		Object[] message = {labelPanel, textPanel};

		JOptionPane pane = new JOptionPane();
	
		pane.setMessage(message);	// dialog message

		//pane.setOptionType(JOptionPane.OK_OPTION);

		JDialog dialog = pane.createDialog(this, "High Score!");

		nameText.setText("Anonymous");

		dialog.setResizable(false);
		dialog.setSize(170,180);
		//dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
		try {
			game.addTopSweeper(nameText.getText());
		}catch(Exception ioe) {
			JOptionPane.showMessageDialog(this, "Trouble Saving Data", "alert", JOptionPane.ERROR_MESSAGE); 
		}
		 
	}
}