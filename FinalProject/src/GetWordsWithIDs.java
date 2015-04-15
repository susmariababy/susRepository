import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;



public class GetWordsWithIDs 
{
	public static TST addWords ( TST tst , Synonyms synon, Meanings mean) throws IOException, ClassNotFoundException 
	{	
		
		BufferedReader brR = new BufferedReader(new FileReader("words.txt"));
		String currentLine;
		int id = 0;
		String str;
		synon.syn.add(new LinkedList<String>());
		LinkedList<String> ll = new LinkedList<String>();
		
		while ((currentLine = brR.readLine()) != null)
		{

			String[] words = currentLine.split(" ");
			id = Integer.parseInt(words[0]);
			while (id >= synon.syn.size())	
				synon.syn.add(new LinkedList<String>());

			str = words[1];
	
			tst.root = tst.insert(tst.root, str, id , 0);
			ll = synon.syn.get(id);
			ll.add(str);
			synon.syn.set(id, ll);
		}
		
		System.out.println("yo");
		brR = new BufferedReader(new FileReader("glossary.txt"));
			
		while ((currentLine = brR.readLine()) != null)
		{

			String[] words = currentLine.split(" ");
			
			id = Integer.parseInt(words[0]);
			
			while (id >= mean.meaning.size())	
				mean.meaning.add("");
			
			int h = 1;
			str = "";
			while (h < words.length)
			{
				str = str + words[h] + " ";
				h++;
			}

			mean.meaning.set(id, str);
		}
		
		System.out.println("yo");
		return tst;
	}

}
