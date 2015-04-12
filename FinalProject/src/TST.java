import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class TST implements Serializable
{
	
	//    public int N;              // size
	    public Node root;   	 // root of TST
	    
	    public boolean contains(String key) 	// returns 1 if the key is present.  Else 0
	    {
	        return get(key) != -1;
	    }
	    
	    public int get(String key) 
	    {								// returns -1 if the string is not found
	    								// else returns the id
	        if (key == null) throw new NullPointerException();
	        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
	        Node x = get(root, key, 0);
	        if (x == null) return -1;
	        return x.id;
	    }
	    
	    								// return sub-Trie corresponding to given key
	    private Node get(Node x, String key, int d) 
	    {
	        if (key == null) throw new NullPointerException();
	        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
	        if (x == null) return null;
	        char c = key.charAt(d);
	        if      (c < x.ch)              return get(x.left,  key, d);
	        else if (c > x.ch)              return get(x.right, key, d);
	        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
	        else                           return x;
	    }

	    private Node insert (Node x, String key, int val, int index) 
	    {
	        char c = key.charAt(index);
	        if (x == null) 
	        {
	            x = new Node();
	            x.ch = c;
	        }
	        if      (c < x.ch)               x.left  = insert(x.left,  key, val, index);
	        else if (c > x.ch)               x.right = insert(x.right, key, val, index);
	        							// else if c == x.ch
	        else if (index < key.length() - 1)  x.mid   = insert(x.mid,   key, val, index+1);
	        			// if we have reached the end of the word
	        else                            x.id   = val;
	        return x;
	    }
	    
	    				// function to return the string that is the longest prefix of the input string inpString
	    public String longestPrefixOf(String inpString) 
	    {
	        if (inpString == null || inpString.length() == 0) 
	        	return null;						// return null if no input itself is null or of 0 length
	        int length = 0;
	        Node x = root;
	        int i = 0;
	        while (x != null && i < inpString.length()) 
	        {
	            char c = inpString.charAt(i);
	            if      (c < x.ch) x = x.left;
	            else if (c > x.ch) x = x.right;
	            else 
	            {
	                i++;
	                if (x.id != -1) length = i;
	                x = x.mid;
	            }
	        }								// end of the while loop
	        return inpString.substring(0, length);
	    }
	    
	    				// Function to search a given word in TST
	    				// returns the id of the word if the word is found 
	    				// else returns -2
	    public int searchTST(Node node, String word)
	    {
	        if (node == null)
	        {
	        	System.out.println ("yo");
	        	return -2;
	        }
	        
	        if (word.charAt(0) < node.ch)
	            return searchTST(node.left, word); 			
	     
	        else if (word.charAt(0) > node.ch)
	            return searchTST(node.right, word);
	     
	        else			// if word.charAt(0) == node.ch
	        {
	            if (word.length() == 1)				// ie, if this word has got only a single letter
	                return node.id;
	     
	            return searchTST(node.mid, word.substring(1));
	        }
	    }
	    
	    public void printBfs()			// using the standard queue technique
		{
			LinkedList <Node> queue = new LinkedList <Node> ();			
			queue.add(root);			
			while (!queue.isEmpty())
			{
				Node temp = queue.peek();
				if (temp.left != null)
					queue.add(temp.left);
				if (temp.mid != null)
					queue.add(temp.mid);
				if (temp.right != null)
					queue.add(temp.right);
				if (temp.ch != 'z')
				{
					temp  = new Node();
					temp.ch = 'z';
					queue.add(temp);
				}
				System.out.print(queue.poll().ch + " ");
			}
			System.out.println();
		}
	    
	    public static void main (String[] args) throws IOException
	    {
	    	TST tst = new TST();
	    	Scanner  c = new Scanner(System.in);
	    	String str = c.nextLine();
	    	int i = 1;			// note that I am starting the ids from 1
	    	while (!str.equals("end"))
	    	{
	    		tst.root = tst.insert(tst.root, str, i , 0);
	    		i++;
	    		str = c.nextLine();
	    	}
	    	
	    //	tst.printBfs();
	    	Persist persist = new Persist();
	    	persist.persist(tst);
	    	
	    	System.out.println (tst.searchTST(tst.root, "able"));
	    	System.out.println (tst.searchTST(tst.root, "acad"));
	    	System.out.println (tst.searchTST(tst.root, "adam"));
	    	System.out.println (tst.searchTST(tst.root, "agile"));
	    	System.out.println (tst.searchTST(tst.root, "acd"));
	    }
}
