import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class AutoCompleteGui 
{
	JFrame mainFrame;
//	JLabel statusLabel;
	javax.swing.JLabel wordLabel;
	javax.swing.JLabel meaningLabel;
	javax.swing.JButton synonymButton;			// gives the synonyms on keypress
	javax.swing.JTextField synonymTextField;	// the text appears in this textfield
	
	
	public void createAndShowGUI() 
	{
		// Creates the main window. JFrame is a generic top level container.
		mainFrame = new JFrame("DICTIONARY");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		wordLabel = new javax.swing.JLabel();
		meaningLabel = new javax.swing.JLabel();
		synonymButton = new javax.swing.JButton();
		synonymTextField = new javax.swing.JTextField();
		
		
		wordLabel.setText("Word");
		meaningLabel.setText("Meaning");
		mainFrame.setMinimumSize(new Dimension(300, 300));
		
		 javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainFrame.getContentPane());
	        mainFrame.getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(synonymTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(wordLabel))
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(synonymButton)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(meaningLabel)))
	                .addContainerGap(27, Short.MAX_VALUE))
	        );
	 
	        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {synonymButton, synonymTextField});
	 
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(synonymTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(wordLabel))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(synonymButton)
	                    .addComponent(meaningLabel))
	                .addContainerGap(21, Short.MAX_VALUE))
	        );
		
		
		
		
		
		
		
		
		// We should set a minimum size to make the GUI look a little nicer
		// while making it.
		mainFrame.setMinimumSize(new Dimension(300, 300));

		Container mainPane = mainFrame.getContentPane();

		// Create a menu bar
		JMenuBar mBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		
		// Create new game- where the opponent plays randomly
//		JMenuItem newItem = menu.add("New");
		JMenuItem newRanItem = menu.add("New - Easy (Random)");			// here the moves taken by the computer are
		newRanItem.addActionListener(new ActionListener() {				// totally random, but valid moves
			@Override
			public void actionPerformed(ActionEvent arg0) {
			//	newGameRandom();
			}
		});
		JMenuItem newOptItem = menu.add("New -Moderate");				// here the computer plays in such a way that
		newOptItem.addActionListener(new ActionListener() {				// it makes a move that can make it win if 
			@Override													// possible, else it goes and blocks the 
			public void actionPerformed(ActionEvent arg0) {				// line of the user that has made maximum
			//	newGameOptimised();										// progress. Thus the computer can win, or
			}															// block, but not stop the user from making
		});																// a branch or a fork
		
		JMenuItem newTwoPlayerGame = menu.add("Two Players");
		newTwoPlayerGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			//	newGameTwoPlayers();
			}
		});
		
		
		// Quit
		JMenuItem quitItem = menu.add("Quit");
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
			}
		});
		mBar.add(menu);

		mainPane.add(mBar, BorderLayout.PAGE_START);

		// Create status bar
//		statusLabel = new JLabel("Start a new game");
//		mainPane.add(statusLabel, BorderLayout.PAGE_END);

		// Create a MxN grid layout
//		tttPanel = new JPanel(new GridLayout(tttBoard.M, tttBoard.N));

//		mainPane.add(tttPanel, BorderLayout.CENTER);
		// Pack is required actually decide how components should be laid out.
		// It should be called before the GUI is displayed.
		mainFrame.pack();
		// Of course, the frame needs to be made visible.
		mainFrame.setVisible(true);
	}



}
