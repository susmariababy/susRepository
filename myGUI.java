import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;


import javax.swing.*;

public class myGUI {
	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel synonymLabel, meaningLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;
	   private JPanel mPanel,moPanel;
	   private JPanel sPanel,soPanel;
	   private JTextArea gloss,Syn;
	   private JScrollPane mPane,sPane;
	   TST tst;
	   Synonyms syno;
	   Meanings mean;
	   String[] allWords;

	   public myGUI(){
	      prepareGUI();
	   }

	   public static void main(String[] args) throws IOException, ClassNotFoundException{
	      myGUI  Listener = new myGUI();  
	      Listener.run();
	   }

	   private void prepareGUI(){
	      mainFrame = new JFrame("WORDEPEDIA");
	      mainFrame.setSize(800,800);
	      mainFrame.setLayout(new GridLayout(8, 8));

	      headerLabel = new JLabel("",SwingConstants.CENTER);
	      statusLabel = new JLabel("",SwingConstants.CENTER);
	      meaningLabel = new JLabel("Glossary", SwingConstants.CENTER);
	      synonymLabel = new JLabel("Synonyms", SwingConstants.CENTER);
	      gloss = new JTextArea();
	      Syn = new JTextArea();
	      mPane = new JScrollPane();
	      sPane = new JScrollPane();
	      
	      headerLabel.setSize(350,20);
	     
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
		        System.exit(0);
	         }        
	      });    
	      controlPanel = new JPanel();
	      mPanel = new JPanel();
	      sPanel = new JPanel();
	      moPanel = new JPanel();
	      soPanel = new JPanel();
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
	   
	   private void run() throws IOException, ClassNotFoundException{
		  //to get the tree , the synonyms and the meaning
		  tst = new TST();
		  syno = new Synonyms();
		  mean = new Meanings();
		  GetWordsWithIDs gwi = new GetWordsWithIDs();
		  Persist persist = new Persist();
		  persist.persist(tst, syno, mean);
		  
		  ArrayList<String> list = new ArrayList<String>();
		  allWords = new String[144894];
		  tst = gwi.addWords(tst, syno, mean,allWords);
		  for (int p = 0 ; p < 144894; p++)
			  list.add(allWords[p]);
			
		  
	      headerLabel.setText("Enter your Word");      
	      textField  = new JTextField(20);
	      textField.setPreferredSize( new Dimension( 200, 28 ) );
	      textField.addKeyListener(new CustomKeyListener());
	      JButton searchButton = new JButton("Search");
	      searchButton.setBackground(Color.GREEN);
	      
	      searchButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) { //onClick of the search button
	        	 if (tst.searchTST(tst.root, textField.getText().toLowerCase()) != null) {
	        		    statusLabel.setText("Word in Action :  " + textField.getText().toLowerCase() );
						displayGlossary();
						displaySynonym();
					}
					
					else
					{ //here we have to display the spellCheck suggestions for the word
						statusLabel.setText("Word not found");
					}
	         }
	      });
	      
	      controlPanel.add(textField);
	      controlPanel.add(searchButton);   
	      mPanel.add(meaningLabel);
	      moPanel.add(mPane,BorderLayout.CENTER);
	      sPanel.add(synonymLabel);
	      soPanel.add(sPane,BorderLayout.CENTER);
	      
	      mainFrame.setVisible(true);  
	   }

	   class CustomKeyListener implements KeyListener{
	      public void keyTyped(KeyEvent e) {
	      }

	      public void keyPressed(KeyEvent e) {
	         if(e.getKeyCode() == KeyEvent.VK_ENTER){
	           
	         }
	      }
	      
	      public void keyReleased(KeyEvent e) {
	      }   
	   }
	   
	   public void displayGlossary(){
		   String str = textField.getText().toLowerCase();
			LinkedList<Integer> index = tst.searchTST(tst.root, str);
			String meany = "";
			if (index == null)
				meany = "Word does not exist";
			else {
				meany = "";
				int p = 0;
				int k = 1;
				while (p < index.size()-1) {
					System.out.print(index.get(p) + " ");
					meany = meany+ k++ + ". "
							+ mean.printMeaning(index.get(p))+"\r\n" ; 
					p++;
				}
				meany = meany+ k++ + ". "
						+ mean.printMeaning(index.get(p));
			}
			gloss.setText(meany);
			gloss.setEditable(false);
			mPane.getViewport().add(gloss);
			}
	   
	   
	   public void displaySynonym(){
		   String str = textField.getText().toLowerCase();
			LinkedList<Integer> index = tst.searchTST(tst.root, str);
			String synony = "";
			if (index == null)
				synony = "Word does not exit";
			else {
				int p = 0;
				int j = 1;
				synony = "";
				while (p < index.size()-1) {
					System.out.print(index.get(p) + " ");
					if (!syno.printAllSynonyms(index.get(p), str).equals("")) {
						synony = synony + j + ". "
								+ syno.printAllSynonyms(index.get(p), str)+"\r\n"; 
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


}
