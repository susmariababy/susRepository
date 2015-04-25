import java.io.Serializable;
import java.util.LinkedList;


public class Node implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public char ch;                        // character
     public Node left, mid, right;  // left, middle, and right sub-Tries
     public LinkedList <Integer> id;					// id associated with the given word   
     
     Node ()						// default constructor to initialize the id to zero
     {
    	 this.id = null;
     }
}
