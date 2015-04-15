import java.io.Serializable;
import java.util.ArrayList;


public class Meanings implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String> meaning = new ArrayList<String>();
	
	public void printMeaning (int index)
	{
		System.out.print("Meaning : ");
		System.out.println (meaning.get(index));
	}
	
}
