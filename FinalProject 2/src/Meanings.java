import java.io.Serializable;
import java.util.ArrayList;


public class Meanings implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String> meaning = new ArrayList<String>();
	
	public String printMeaning (int index)
	{
		if (index < 0)
			return "No meanings found";
		System.out.print("Meaning : ");
		System.out.println (meaning.get(index));
		return meaning.get(index);
	}
	
}
