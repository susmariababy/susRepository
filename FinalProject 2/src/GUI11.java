
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
import java.io.IOException;
import java.util.LinkedList;
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
	JComboBox<String> comboBox ;
	JButton synonymButton, meaningButton;
	
	TST tst;
	Synonyms syno;
	Meanings mean;


	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;

	// Constructor
	public GUI11() throws ClassNotFoundException, IOException {
		// Create the frame and container.

		tst = new TST();
		syno = new Synonyms();
		mean = new Meanings();
		GetWordsWithIDs gwi = new GetWordsWithIDs();
		Persist persist = new Persist();
		persist.persist(tst, syno, mean);

		String[] allWords = new String[144894];
		tst = gwi.addWords(tst, syno, mean, allWords);
		frame = new JFrame("Dictionary");
		frame.setMinimumSize(new Dimension(600, 300));
		panel = new JPanel();
		Container c = frame.getContentPane();
		panel.setLayout(new GridBagLayout());
	
		
//		JScrollBar hbar=new JScrollBar(JScrollBar.HORIZONTAL, 30, 20, 0, 300);
//	    JScrollBar vbar=new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);
	    
//	    hbar.addAdjustmentListener(new MyAdjustmentListener());
 //       vbar.addAdjustmentListener(new MyAdjustmentListener());

		// Add the widgets.
		comboBox = new JComboBox<String>(); 
		addWidgets();
		JScrollPane jsp = new JScrollPane(panel);
        c.add(jsp);

		// Add the panel to the frame.
//		frame.getContentPane().add(panel, BorderLayout.CENTER);
//		frame.getContentPane().add(hbar, BorderLayout.SOUTH);
//		frame.getContentPane().add(vbar, BorderLayout.EAST);

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
					LinkedList <String> ll = tst.keysWithPrefix(wordField.getText().toLowerCase());
					
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
				if (tst.search2(tst.root, wordField.getText().toLowerCase()) != null )
				{
					LinkedList<String> lll = new LinkedList<String>();
					lll = tst.keysWithPrefix(wordField.getText().toLowerCase());
					String[] blah = new String[lll.size()];
					for (int k = 0; k< lll.size(); k++)
						blah[k] = lll.get(k);
	//				JComboBox<String> jcom = new JComboBox<String>(blah);			
	//				comboBox.setModel((ComboBoxModel<String>) jcom);
					// this also doesn't work
					
					comboBox.setModel(new DefaultComboBoxModel <String>(blah));
					
					
					// = new JComboBox<String>(blah);
		//			addWidgets();
//					data = new Vector<String>(lll);
		//			frame.removeAll();
		//			frame.setMinimumSize(new Dimension (600,300));
		//			frame.revalidate();
		//			frame.repaint();
//					frame.add(list(), BorderLayout.WEST);
		//			frame.add(editPanel());
		//			frame.revalidate();
		//			frame.repaint();
				}
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
        //   repaint();
        }
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

//		JScrollPane scroller = new JScrollPane(synonymLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		I am commenting this out for the time being coz I don't know how to use this thing up 
		
		
		
//		Dimension mSize = new Dimension(400, 60);
//		synonymLabel.setMinimumSize(mSize); // // why is this not working ???/

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
		if (shouldFill) {
			// natural height, maximum width
			c.fill = GridBagConstraints.HORIZONTAL;
		}

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
				if (!syno.printAllSynonyms(index.get(p), str).equals(""))
				{
					synony = synony +"<p>" +j +". " + syno.printAllSynonyms(index.get(p), str) ;		//+ "\n";
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
				meany = meany +"<p>"+ k++ +". "+ mean.printMeaning(index.get(p)) ;		//+ "\n"; this doesn't work either way
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
