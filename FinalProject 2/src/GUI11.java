import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class GUI11 {
	JFrame frame;
	JPanel panel;
	JTextField wordField;
	JLabel wordLabel, synonymLabel, meaningLabel;
	JComboBox<String> comboBox;
	JButton synonymButton, meaningButton;
	BKnode BKroot = null;
	TST tst;
	Synonyms syno;
	Meanings mean;
	PriorityQueue<order> misSpelt = new PriorityQueue<order>();
	private char[][] neighbour = new char[27][2];
	// final static boolean shouldFill = true;
	// final static boolean shouldWeightX = true;
	// final static boolean RIGHT_TO_LEFT = false;

	// Constructor
	public GUI11() throws ClassNotFoundException, IOException {
		// Create the frame and container.

		tst = new TST();
		syno = new Synonyms();
		mean = new Meanings();
		GetWordsWithIDs gwi = new GetWordsWithIDs();
		Persist persist = new Persist();
		persist.persist(tst, syno, mean);

		fillNeighbours();
		String[] allWords = new String[144894];
		tst = gwi.addWords(tst, syno, mean, allWords);

		Scanner in = new Scanner(new File("words.txt"));
		int id;
		String s;
		while (in.hasNext()) { // building the tree
			id = in.nextInt();
			s = in.next();
			BKroot = NodeFunctions.insertWord(s, BKroot);
		} // Building the BK tree
		in.close();

		frame = new JFrame("Dictionary");
		frame.setMinimumSize(new Dimension(600, 300));
		panel = new JPanel();
		Container c = frame.getContentPane();
		panel.setLayout(new GridBagLayout());

		// Add the widgets.
		comboBox = new JComboBox<String>();
		addWidgets();
		JScrollPane jsp = new JScrollPane(panel);
		c.add(jsp);

		// Exit when the window is closed.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wordField.setText(comboBox.getSelectedItem().toString());
			}
		});

		DocumentListener documentListener = new DocumentListener() {
			public void changedUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			public void insertUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			public void removeUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			private void printIt(DocumentEvent documentEvent) {
				DocumentEvent.EventType type = documentEvent.getType();
				String typeString = null;
				if (type.equals(DocumentEvent.EventType.CHANGE)) {
					typeString = "Change";
					LinkedList<String> ll = tst.keysWithPrefix(wordField
							.getText().toLowerCase());

				} else if (type.equals(DocumentEvent.EventType.INSERT)) {
					typeString = "Insert";
				} else if (type.equals(DocumentEvent.EventType.REMOVE)) {
					typeString = "Remove";
				}
				System.out.print("Type : " + typeString);
				Document source = documentEvent.getDocument();
				int length = source.getLength();
				System.out.println("Length: " + length);
				synonymLabel.setText("Synonyms:");
				meaningLabel.setText("Meanings");
				if (wordField.getText().length() != 0)
					if (tst.search2(tst.root, wordField.getText().toLowerCase()) != null) {
						LinkedList<String> lll = new LinkedList<String>();
						lll = tst.keysWithPrefix(wordField.getText()
								.toLowerCase());
						String[] blah = new String[lll.size()];
						for (int k = 0; k < lll.size(); k++)
							blah[k] = lll.get(k);
						// JComboBox<String> jcom = new JComboBox<String>(blah);
						// comboBox.setModel((ComboBoxModel<String>) jcom);
						// this also doesn't work

						comboBox.setModel(new DefaultComboBoxModel<String>(blah));

						// comboBox.add(wordField);

					}
					//
					else {
						NodeFunctions.spellCheck(BKroot, wordField.getText()
								.toLowerCase().toLowerCase(), 3, misSpelt,
								neighbour);
						ArrayList<String> list = new ArrayList<String>();
						for (int i = 0; i < misSpelt.size(); i++) {
							list.add(misSpelt.poll().word); // add the words of
															// the priority
															// Queue to a list
							
							String[] blah = new String[list.size()];
							for (int k = 0; k < list.size(); k++)
								blah[k] = list.get(k);
							comboBox.setModel(new DefaultComboBoxModel<String>(blah));
						}

					}
				//
			}
		};
		wordField.getDocument().addDocumentListener(documentListener);

		// Show the converter.
		frame.pack();
		frame.setVisible(true);
	}

	class MyAdjustmentListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			synonymLabel.setText("    New Value is " + e.getValue() + "      ");
			// repaint();
		}
	}
	
	void fillNeighbours()
	{
		//hardcoding the neighbours of the characters based on the Qwerty arrangement
		 //a
		 neighbour[0][0]= 's';
		 neighbour[0][1]= '@';
		 //b
		 neighbour[1][0]= 'v';
		 neighbour[1][1]= 'n';
		 //c
		 neighbour[2][0]= 'x';
		 neighbour[2][1]= 'v';
		 //d
		 neighbour[3][0]= 's';
		 neighbour[3][1]= 'f';
		 //e
		 neighbour[4][0]= 'w';
		 neighbour[4][1]= 'r';
		 //f
		 neighbour[5][0]= 'd';
		 neighbour[5][1]= 'g';
		 //g
		 neighbour[6][0]= 'f';
		 neighbour[6][1]= 'h';
		 //h
		 neighbour[7][0]= 'g';
		 neighbour[7][1]= 'j';
		 //i
		 neighbour[8][0]= 'u';
		 neighbour[8][1]= 'o';
		 //j
		 neighbour[9][0]= 'h';
		 neighbour[9][1]= 'k';
		 //k
		 neighbour[10][0]= 'j';
		 neighbour[10][1]= 'l';
		 //l
		 neighbour[11][0]= 'k';
		 neighbour[11][1]= '@';
		 //m
		 neighbour[12][0]= 'n';
		 neighbour[12][1]= '@';
		 //n
		 neighbour[13][0]= 'b';
		 neighbour[13][1]= 'm';
		 //o
		 neighbour[14][0]= 'i';
		 neighbour[14][1]= 'p';
		 //p
		 neighbour[15][0]= 'o';
		 neighbour[15][1]= '@';
		 //q
		 neighbour[16][0]= 'w';
		 neighbour[16][1]= '@';
		 //r
		 neighbour[17][0]= 'e';
		 neighbour[17][1]= 't';
		 //s
		 neighbour[18][0]= 'a';
		 neighbour[18][1]= 'd';
		 //t
		 neighbour[19][0]= 'r';
		 neighbour[19][1]= 'y';
		 //u
		 neighbour[20][0]= 'y';
		 neighbour[20][1]= 'i';
		 //v
		 neighbour[21][0]= 'c';
		 neighbour[21][1]= 'b';
		 //w
		 neighbour[22][0]= 'q';
		 neighbour[22][1]= 'e';
		 //x
		 neighbour[23][0]= 'z';
		 neighbour[23][1]= 'c';
		 //y
		 neighbour[24][0]= 't';
		 neighbour[24][1]= 'u';
		 //z
		 neighbour[25][0]= 'x';
		 neighbour[25][1]= '@';

	}

	// Create and add the widgets for converter.
	private void addWidgets() {
		// Create widgets.
		wordField = new JTextField(3);
		wordLabel = new JLabel("Word", SwingConstants.LEFT);
		synonymButton = new JButton("Synonyms");
		synonymLabel = new JLabel("Synonym", SwingConstants.LEFT);
		meaningButton = new JButton("Meaning");
		meaningLabel = new JLabel(" Meaning", SwingConstants.LEFT);

		// JScrollPane scroller = new JScrollPane(synonymLabel,
		// JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		// JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// I am commenting this out for the time being coz I don't know how to
		// use this thing up

		// Dimension mSize = new Dimension(400, 60);
		// synonymLabel.setMinimumSize(mSize); // // why is this not working
		// ???/

		// Listen to events from Convert button.
		synonymButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				actionPerformedNumber1();
			}
		});

		meaningButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				actionPerformedNumber2();
			}
		});

		// Add widgets to container.
		// panel.add(wordField);
		// panel.add(wordLabel);
		// panel.add(synonymButton);
		// panel.add(synonymLabel);
		// panel.add(meaningButton);
		// panel.add(meaningLabel);

		GridBagConstraints c = new GridBagConstraints();

		// natural height, maximum width
		c.fill = GridBagConstraints.HORIZONTAL;

		// if (shouldWeightX)
		// c.weightx = 0.5;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 30;
		panel.add(wordField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(wordLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 5;
		panel.add(comboBox, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.ipady = 40;
		panel.add(synonymButton, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		c.ipadx = 300;
		panel.add(synonymLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.ipadx = 0;
		panel.add(meaningButton, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 3;
		c.ipadx = 300;
		panel.add(meaningLabel, c);

		wordLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		synonymLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		meaningLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

	}

	public void actionPerformedNumber1() {

		String str = wordField.getText().toLowerCase();
		LinkedList<Integer> index = tst.searchTST(tst.root, str);
		String synony = "";
		if (index == null)
			synony = "word does not exit";
		else {
			int p = 0;
			int j = 1;
			synony = "<html><h>Synonyms :<h>";
			while (p < index.size()) {
				System.out.print(index.get(p) + " ");
				if (!syno.printAllSynonyms(index.get(p), str).equals("")) {
					synony = synony + "<p>" + j + ". "
							+ syno.printAllSynonyms(index.get(p), str); // +
																		// "\n";
					j++;
				}
				p++;
			}
			if (synony.equals("<html><h>Synonyms :<h>"))
				synony = synony + "No synonyms found";
			synony = synony + "<html>";
		}
		synonymLabel.setText(synony);

	}

	public void actionPerformedNumber2() {

		String str = wordField.getText().toLowerCase();
		LinkedList<Integer> index = tst.searchTST(tst.root, str);
		String meany = "";
		if (index == null)
			meany = "word does not exist";
		else {
			meany = "<html><h>Meanings :<h>";
			int p = 0;
			int k = 1;
			while (p < index.size()) {
				System.out.print(index.get(p) + " ");
				meany = meany + "<p>" + k++ + ". "
						+ mean.printMeaning(index.get(p)); // + "\n"; this
															// doesn't work
															// either way
				p++;
			}
			meany += "<html>";
		}
		meaningLabel.setText(meany);
	}

	// main method

	public static void main(String[] args) throws ClassNotFoundException,
			IOException { // Set the look and feel.
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}

		GUI11 gui = new GUI11();
	}
}
