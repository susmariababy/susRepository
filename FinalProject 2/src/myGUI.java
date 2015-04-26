import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;

public class myGUI {
	private char[][] neighbour = new char[27][2];
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel synonymLabel, meaningLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel mPanel, moPanel;
	private JPanel sPanel, soPanel;
	private JTextArea gloss, Syn;
	private JScrollPane mPane, sPane;
	private Vector<String> data;
	PriorityQueue<order> misSpelt = new PriorityQueue<order>();
	BKnode BKroot = null;
	private JComboBox<String> comboBox;

	TST tst;
	Synonyms syno;
	Meanings mean;
	String[] allWords;

	public myGUI() {
		// hardcoding the neighbours of the characters based on the Qwerty
		// arrangement
		// a
		neighbour[0][0] = 's';
		neighbour[0][1] = '@';
		// b
		neighbour[1][0] = 'v';
		neighbour[1][1] = 'n';
		// c
		neighbour[2][0] = 'x';
		neighbour[2][1] = 'v';
		// d
		neighbour[3][0] = 's';
		neighbour[3][1] = 'f';
		// e
		neighbour[4][0] = 'w';
		neighbour[4][1] = 'r';
		// f
		neighbour[5][0] = 'd';
		neighbour[5][1] = 'g';
		// g
		neighbour[6][0] = 'f';
		neighbour[6][1] = 'h';
		// h
		neighbour[7][0] = 'g';
		neighbour[7][1] = 'j';
		// i
		neighbour[8][0] = 'u';
		neighbour[8][1] = 'o';
		// j
		neighbour[9][0] = 'h';
		neighbour[9][1] = 'k';
		// k
		neighbour[10][0] = 'j';
		neighbour[10][1] = 'l';
		// l
		neighbour[11][0] = 'k';
		neighbour[11][1] = '@';
		// m
		neighbour[12][0] = 'n';
		neighbour[12][1] = '@';
		// n
		neighbour[13][0] = 'b';
		neighbour[13][1] = 'm';
		// o
		neighbour[14][0] = 'i';
		neighbour[14][1] = 'p';
		// p
		neighbour[15][0] = 'o';
		neighbour[15][1] = '@';
		// q
		neighbour[16][0] = 'w';
		neighbour[16][1] = '@';
		// r
		neighbour[17][0] = 'e';
		neighbour[17][1] = 't';
		// s
		neighbour[18][0] = 'a';
		neighbour[18][1] = 'd';
		// t
		neighbour[19][0] = 'r';
		neighbour[19][1] = 'y';
		// u
		neighbour[20][0] = 'y';
		neighbour[20][1] = 'i';
		// v
		neighbour[21][0] = 'c';
		neighbour[21][1] = 'b';
		// w
		neighbour[22][0] = 'q';
		neighbour[22][1] = 'e';
		// x
		neighbour[23][0] = 'z';
		neighbour[23][1] = 'c';
		// y
		neighbour[24][0] = 't';
		neighbour[24][1] = 'u';
		// z
		neighbour[25][0] = 'x';
		neighbour[25][1] = '@';
		prepareGUI();
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		myGUI Listener = new myGUI();
		Listener.run();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("WORDEPEDIA");
		mainFrame.setSize(800, 800);
		mainFrame.setLayout(new GridLayout(8, 8));

		headerLabel = new JLabel("", SwingConstants.CENTER);
		statusLabel = new JLabel("", SwingConstants.CENTER);
		meaningLabel = new JLabel("Glossary", SwingConstants.CENTER);
		synonymLabel = new JLabel("Synonyms", SwingConstants.CENTER);
		gloss = new JTextArea();
		Syn = new JTextArea();
		mPane = new JScrollPane();
		sPane = new JScrollPane();

		headerLabel.setSize(350, 20);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		controlPanel = new JPanel();
		mPanel = new JPanel();
		sPanel = new JPanel();
		moPanel = new JPanel();
		soPanel = new JPanel();

		mPanel.setVisible(false);
		sPanel.setVisible(false);
		moPanel.setVisible(false);
		soPanel.setVisible(false);

		controlPanel.setLayout(new FlowLayout());
		mPanel.setLayout(new FlowLayout());
		sPanel.setLayout(new FlowLayout());
		moPanel.setLayout(new BorderLayout());
		soPanel.setLayout(new BorderLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(mPanel);
		mainFrame.add(moPanel);
		mainFrame.add(sPanel);
		mainFrame.add(soPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	}

	private JTextField textField;

	private void run() throws IOException, ClassNotFoundException {
		// to get the tree , the synonyms and the meaning
		tst = new TST();
		syno = new Synonyms();
		mean = new Meanings();
		GetWordsWithIDs gwi = new GetWordsWithIDs();
		Persist persist = new Persist();
		persist.persist(tst, syno, mean);

		allWords = new String[144894];
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

		headerLabel.setText("Enter your Word");
		textField = new JTextField(20);
		textField.setPreferredSize(new Dimension(200, 28));
		JButton searchButton = new JButton("Search");
		searchButton.setBackground(Color.GREEN);

		// ///////////
		comboBox = new JComboBox<String>();

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText(comboBox.getSelectedItem().toString());
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
					LinkedList<String> ll = tst.keysWithPrefix(textField
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
				if (textField.getText().length() != 0)
					if (tst.search2(tst.root, textField.getText().toLowerCase()) != null) {
						LinkedList<String> lll = new LinkedList<String>();
						lll = tst.keysWithPrefix(textField.getText()
								.toLowerCase());
						String[] blah = new String[lll.size()];
						for (int k = 0; k < lll.size(); k++)
							blah[k] = lll.get(k);

						comboBox.setModel(new DefaultComboBoxModel<String>(blah));

						mPanel.setVisible(false);
													
						moPanel.removeAll();
						moPanel.setVisible(false);
						sPanel.setVisible(false);
						soPanel.setVisible(false);
						moPanel.add(comboBox, BorderLayout.CENTER);
						moPanel.setVisible(true);

					}
			}
		};
		textField.getDocument().addDocumentListener(documentListener);

		// //////////////

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // onClick of the
															// search button
				if (tst.searchTST(tst.root, textField.getText().toLowerCase()) != null) {
					mPanel.setVisible(true);
					moPanel.removeAll();

					sPanel.setVisible(true);
					moPanel.setVisible(true);
					soPanel.setVisible(true);
					statusLabel.setText("Word in Action :  "
							+ textField.getText().toLowerCase());
					displayGlossary();
					displaySynonym();
					moPanel.add(mPane, BorderLayout.CENTER);
				}

				else { // here we have to display the spellCheck suggestions for
						// the word
					statusLabel.setText("Word not found");

					NodeFunctions.spellCheck(BKroot, textField.getText()
							.toLowerCase(), 3, misSpelt, neighbour);
					ArrayList<String> list = new ArrayList<String>();
					for (int i = 0; i < misSpelt.size(); i++) {
						list.add(misSpelt.poll().word); // add the words of the
														// priority Queue to a
														// list
					}

					data = new Vector<String>(list);

					mPanel.setVisible(false);// I will be using the moPanel to
												// display the spellCheck
												// suggestions
					moPanel.removeAll();
					moPanel.setVisible(false);
					sPanel.setVisible(false);
					soPanel.setVisible(false);
					moPanel.add(list(), BorderLayout.CENTER);
					moPanel.setVisible(true);
				}
			}
		});

		controlPanel.add(textField);
		controlPanel.add(searchButton);
		mPanel.add(meaningLabel);

		sPanel.add(synonymLabel);
		soPanel.add(sPane, BorderLayout.CENTER);
		meaningLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		synonymLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

		mainFrame.setVisible(true);
	}

	

	public void displayGlossary() {
		String str = textField.getText().toLowerCase();
		LinkedList<Integer> index = tst.searchTST(tst.root, str);
		String meany = "";
		if (index == null)
			meany = "Word does not exist";
		else {
			meany = "";
			int p = 0;
			int k = 1;
			while (p < index.size() - 1) {
				System.out.print(index.get(p) + " ");
				meany = meany + k++ + ". " + mean.printMeaning(index.get(p))
						+ "\r\n";
				p++;
			}
			meany = meany + k++ + ". " + mean.printMeaning(index.get(p));
		}
		gloss.setText(meany);
		gloss.setEditable(false);
		mPane.getViewport().add(gloss);
	}

	public void displaySynonym() {
		String str = textField.getText().toLowerCase();
		LinkedList<Integer> index = tst.searchTST(tst.root, str);
		String synony = "";
		if (index == null)
			synony = "Word does not exit";
		else {
			int p = 0;
			int j = 1;
			synony = "";
			while (p < index.size() - 1) {
				System.out.print(index.get(p) + " ");
				if (!syno.printAllSynonyms(index.get(p), str).equals("")) {
					synony = synony + j + ". "
							+ syno.printAllSynonyms(index.get(p), str) + "\r\n";
					j++;
				}
				p++;
			}
			synony = synony + j + ". "
					+ syno.printAllSynonyms(index.get(p), str);
			if (synony.equals(""))
				synony = synony + "No synonyms found";
		}
		Syn.setText(synony);
		Syn.setEditable(false);
		sPane.getViewport().add(Syn);
	}

	private JComponent list() {
		final JList<String> llist = new JList<String>(data);
		llist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int i = llist.getSelectedIndex();
				textField.setText(i >= 0 ? data.get(i) : "");
			}
		});
		return new JScrollPane(llist);
	}
}
