import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class Persist 
{
	
	public void persist(TST ternarySearchTree, Synonyms synon, Meanings mean) throws IOException 
    {
		// TODO Write the created Ternary Search Tree to a file called "dictionary.txt".
		
		FileOutputStream fos = new FileOutputStream("dictionary.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(ternarySearchTree);
		oos.writeObject(synon);
		oos.writeObject(mean);
		
		oos.flush();
		oos.close();
	}	
}
