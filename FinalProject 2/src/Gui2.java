import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.Arrays;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.Document;

public class Gui2 {
	JFrame frame;
	JPanel panel;
	JTextField wordTextField;
	JLabel wordLabel, synonymLabel, meaningLabel;
	TST tst;
	Synonyms syno;
	Meanings mean;
	String[] allWords;
	
	private Vector<String> data; 

	public static void main(String[] args) throws ClassNotFoundException,
			IOException {
		Gui2 s = new Gui2();
		s.run();
	}

	public void run() throws IOException, ClassNotFoundException {
		tst = new TST();
		syno = new Synonyms();
		mean = new Meanings();
		GetWordsWithIDs gwi = new GetWordsWithIDs();
		Persist persist = new Persist();
		persist.persist(tst, syno, mean);

		ArrayList<String> list = new ArrayList<String>();
		allWords = new String[146212];
		tst = gwi.addWords(tst, syno, mean,allWords);
		for (int p = 0 ; p < 146212; p++)
			list.add(allWords[p]);
		
		java.util.Collections.sort(list);
		data = new Vector<String>(list);
		frame = new JFrame("Dictionary");
		frame.setMinimumSize(new Dimension(600, 300));
		frame.setMinimumSize(new Dimension(600, 300));
		panel = new JPanel();

		JFrame frame = new JFrame("Dictionary");
		frame.add(list(), BorderLayout.WEST);
		frame.add(editPanel());

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
					LinkedList<String> ll = tst.keysWithPrefix(wordTextField
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
				if (tst.searchTST(tst.root, wordTextField.getText().toLowerCase()) != null) {
					actionPerformedNumber1();
					actionPerformedNumber2();
				}
				else
				{
					synonymLabel.setText("Word does not exist");
					meaningLabel.setText("Word does not exist");
				}
			}
		};
		wordTextField.getDocument().addDocumentListener(documentListener);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(600, 300));
		
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformedNumber1() {

		String str = wordTextField.getText().toLowerCase();
		LinkedList<Integer> index = tst.searchTST(tst.root, str);
		String synony = "";
		if (index == null)
			synony = "word does not exit";
		else {
			int p = 0;
			int j = 1;
			synony = "<html>";
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
			if (synony.equals("<html>"))
				synony = synony + "No synonyms found";
			synony = synony + "<html>";
		}
		synonymLabel.setText(synony);
	}

	public void actionPerformedNumber2() {

		String str = wordTextField.getText().toLowerCase();
		LinkedList<Integer> index = tst.searchTST(tst.root, str);
		String meany = "";
		if (index == null)
			meany = "word does not exist";
		else {
			meany = "<html>";
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

	private JComponent list() {
		final JList list = new JList(data);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int i = list.getSelectedIndex();
				wordTextField.setText(i >= 0 ? data.get(i) : "");
			}
		});
		return new JScrollPane(list);
	}

	private JComponent editPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		/*
		 * panel.add( new JLabel("Word:") ); wordTextField = new JTextField(10);
		 * panel.add( wordTextField );
		 * 
		 * panel.add( new JLabel("Word22:") ); JTextField wordTextField2 = new
		 * JTextField(10); panel.add( wordTextField2 );
		 * 
		 * panel.add(new JLabel("Meanings: ")); meaningLabel = new JLabel();
		 * panel.add(meaningLabel);
		 */

		// GridBagConstraints c = new GridBagConstraints();

		wordTextField = new JTextField(0);
		Dimension d = new Dimension(500, 20);
		wordTextField.setMaximumSize(d);
		panel.add(wordTextField);

		meaningLabel = new JLabel("Meaning", SwingConstants.LEFT);
		panel.add(meaningLabel);// , c);

		synonymLabel = new JLabel("Synonyms", SwingConstants.LEFT);
		panel.add(synonymLabel);

		// wordTextField.setBorder(BorderFactory.createEmptyBorder(20, 10, 20,
		// 10));
		meaningLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		synonymLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

		return panel;
	}
}