import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class TST implements Serializable {

	// public int N; // size
	public Node root; // root of TST

	public boolean contains(String key) // returns 1 if the key is present. Else
										// 0
	{
		return get(key) != null;
	}

	 // return root of the sub-Trie corresponding to given string key
	private Node get(Node x, String key, int d) {
		if (key == null)
			throw new NullPointerException();
		if (key.length() == 0)
			throw new IllegalArgumentException("key must have length >= 1");
		if (x == null)
			return null;
		char c = key.charAt(d);
		if (c < x.ch)
			return get(x.left, key, d);
		else if (c > x.ch)
			return get(x.right, key, d);
		else if (d < key.length() - 1)
			return get(x.mid, key, d + 1);
		else				// if d (index) == key.length -1
			return x;
	}

	public LinkedList<Integer> get(String key) { // returns -1 if the string is not found
									// else returns the id
		if (key == null)
			throw new NullPointerException();
		if (key.length() == 0)
			throw new IllegalArgumentException("key must have length >= 1");
		Node x = get(root, key, 0);
		if (x == null)
			return null;
		return x.id;
	}

	public Node insert(Node x, String key, int val, int index) {
		char c = key.charAt(index);
		if (x == null) {
			x = new Node();
			x.ch = c;
		}
		if (c < x.ch)
			x.left = insert(x.left, key, val, index);
		else if (c > x.ch)
			x.right = insert(x.right, key, val, index);
		// else if c == x.ch
		else if (index < key.length() - 1)
			x.mid = insert(x.mid, key, val, index + 1);
		// if we have reached the end of the word
		else
		{
			if (x.id == null)
			{
				x.id = new LinkedList<Integer>();
				x.id.add(val);
			}
			
			else
			{
				LinkedList <Integer> ll = new LinkedList<Integer>();
				ll = x.id;
				ll.add(val);
				x.id = ll ;
			}
		}
			
		return x;
	}

	// function to return the string that is the longest prefix of the input
	// string inpString
	// and belongs to the dictionary
	public String longestPrefixOf(String inpString) {
		if (inpString == null || inpString.length() == 0)
			return null; // return null if no input itself is null or of 0
							// length
		int length = 0;
		Node x = root;
		int i = 0;
		while (x != null && i < inpString.length()) {
			char c = inpString.charAt(i);
			if (c < x.ch)
				x = x.left;
			else if (c > x.ch)
				x = x.right;
			else {
				i++;
				length = i;
				x = x.mid;
			}
		} // end of the while loop
		return inpString.substring(0, length);
	}

	// Function to search a given word in TST
	// returns the linkedList of id of the word if the word is found
	// else returns -2
	public LinkedList <Integer> searchTST(Node node, String word) {
		if (node == null) {
			return null;
		}

		if (word.charAt(0) < node.ch)
			return searchTST(node.left, word);

		else if (word.charAt(0) > node.ch)
			return searchTST(node.right, word);

		else // if word.charAt(0) == node.ch
		{
			if (word.length() == 1) // ie, if this word has got only a single
									// letter
				return node.id;

			return searchTST(node.mid, word.substring(1));
		}
	}
	
	public Node search2(Node node, String word)
	{
		if (node == null) {
			return null;
		}

		if (word.charAt(0) < node.ch)
			return search2(node.left, word);

		else if (word.charAt(0) > node.ch)
			return search2(node.right, word);
		
		else // if word.charAt(0) == node.ch
		{
			if (word.length() == 1) // ie, if this word has got only a single
									// letter
				return node;
			return search2(node.mid, word.substring(1));
		}
	}

	public void printBfs() // using the standard queue technique
	{
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node temp = queue.peek();
			if (temp.left != null)
				queue.add(temp.left);
			if (temp.mid != null)
				queue.add(temp.mid);
			if (temp.right != null)
				queue.add(temp.right);
			if (temp.ch != '#') {
				temp = new Node();
				temp.ch = '#';
				queue.add(temp);
			}
			System.out.print(queue.poll().ch + " ");
		}
		System.out.println();
	}

	public LinkedList<String> keysWithPrefix (String prefix) 
	{
		LinkedList<String> queue = new LinkedList<String>();
		String suffix = prefix;
		Node x = get(root, prefix, 0); // x now has got the subtree
										// corresponding to 'prefix'
		if (x == null)
			return queue;

		queue.add(prefix);
		printSuffix(x.mid, suffix, prefix.length(), queue);
		
		return queue;
	}

	/*
	 * Recursive helper function -- given a node, and a string containing the
	 * path from the "root node", (as defined by the prefix) up to but not including this node, print out all
	 * the "root"-leaf paths.
	 */
	void printSuffix(Node node, String suffix, int len, LinkedList<String> queue)
	{
		if (node== null)
			return;
	     
		String c = node.ch + "";
		
			suffix = suffix.subSequence(0, len) + c;
	     
	      /* it's a leaf, so print the path that led to here  */
	    
	    if (node.id != null)			// if we have found a complete suffix
	    {
	    	if (suffix.length() != 0)
	    	{
	    		System.out.println(suffix);
	    		queue.add(suffix);
	    	}
	    }
	     
	    printSuffix(node.left, suffix, len, queue);
	    printSuffix(node.mid, suffix, len + 1, queue);  
	    printSuffix(node.right, suffix, len, queue);    
	
	}

	/////////////////////////////////////////////////

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		TST tst = new TST();
		Synonyms syno = new Synonyms();
		Meanings mean = new Meanings();

		GetWordsWithIDs gwwi = new GetWordsWithIDs();
//		tst = gwwi.addWords(tst, syno, mean);
		// here we add all the words to the TST tst , and also add all synonyms
		// to
		// the arraylist of linked list of Strings 'syn'

		// tst.printBfs();
		Persist persist = new Persist();
		persist.persist(tst, syno, mean);
/*
		javax.swing.SwingUtilities.invokeLater( new Runnable()
		{
			
			@Override
			public void run()
			{
				AutoCompleteGui aut = new AutoCompleteGui();
				aut.createAndShowGUI();
			}
		});
*/		
		Scanner c = new Scanner(System.in);
		String str = c.next();
		while (!str.equals("end")) {
			LinkedList<Integer> index = tst.searchTST(tst.root, str);
			int p = 0;
			while (p < index.size())
			{
				System.out.println(index.get(p));
				syno.printAllSynonyms(index.get(p), str);
				mean.printMeaning(index.get(p));
				p++;
			}
			str = c.next();
		}

		str = c.next();
		while (!str.equals("end")) {
		//	System.out.println(tst.longestPrefixOf(str));
			tst.keysWithPrefix(str);
			str = c.next();
		}
	}
}
