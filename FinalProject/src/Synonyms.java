
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;


public class Synonyms implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<LinkedList<String>> syn = new ArrayList<LinkedList<String>>();
	
	public void printAllSynonyms (int index, String str)
	{
		if (index > 0)
    	{
			int size = syn.get(index).size();
    		int i = 0;
    		System.out.print("Synonyms of "+ str + " : ");
    	
    		while (i < size)
    		{
    			if (!syn.get(index).get(i).equals(str))
    			{
    				if (i < syn.get(index).size() -1 )
    					System.out.print(syn.get(index).get(i) + ", ");
    				else 
    					System.out.print(syn.get(index).get(i) + " ");
    			}
    			i++;
    		}
    		System.out.println();
    	}
	}
}
