
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
	
	public String printAllSynonyms (int index, String str)
	{
		String stri = "";
		int flag = 0;
		if (index > 0)
    	{
			int size = syn.get(index).size();
    		int i = 0;
    		System.out.print("Synonyms of "+ str + " : ");
    		
    		while (i < size)
    		{
    			if (!syn.get(index).get(i).equals(str))
    			{
    				if (flag == 0)
    				{
    					stri = syn.get(index).get(i);
    					System.out.print(syn.get(index).get(i));
    					flag = 1;
    				}
    				else if (flag == 1)
    				{
    					stri = stri +", "+ syn.get(index).get(i);
    					System.out.print(", " + syn.get(index).get(i));
    				}
    			}
    			i++;
    		}
    		System.out.println();
    	}
		return stri;
	}
}
