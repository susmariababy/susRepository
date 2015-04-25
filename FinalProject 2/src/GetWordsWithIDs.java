import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;



public class GetWordsWithIDs 
{
	public TST addWords ( TST tst , Synonyms synon, Meanings mean, String[] allWords) throws IOException, ClassNotFoundException 
	{	
		
		BufferedReader brR = new BufferedReader(new FileReader("words.txt"));
		String currentLine;
		int id = 0;
		String str;
	
		synon.syn.add(new LinkedList<String>());
		LinkedList<String> ll = new LinkedList<String>();
		int count1 = 0;
		int wordCount = 0;
		while ((currentLine = brR.readLine()) != null)
		{
			count1 ++;
			String[] words = currentLine.split(" ");
			id = Integer.parseInt(words[0]);
			while (id >= synon.syn.size())	
				synon.syn.add(new LinkedList<String>());

			str = words[1];
			{
				if (tst.searchTST(tst.root, str) == null)
				allWords[wordCount++] = str;
			}
			
			
			tst.root = tst.insert(tst.root, str, id , 0);
			ll = synon.syn.get(id);
			ll.add(str);
			synon.syn.set(id, ll);
		}
		
		System.out.println("yo  "+ count1 +"  "+ wordCount);
		brR = new BufferedReader(new FileReader("glossary.txt"));
		int count2 = 0;
		while ((currentLine = brR.readLine()) != null)
		{
			count2++;
			String[] words = currentLine.split(" ");
			
			id = Integer.parseInt(words[0]);
			
			while (id >= mean.meaning.size())	
				mean.meaning.add("");
			
			int h = 1;
			str = mean.meaning.get(id);
			while (h < words.length)
			{
				str = str + words[h] + " ";
				h++;
			}
			mean.meaning.set(id, str);
		}
		
		System.out.println("yo  "+ count2);
		return tst;
	}

}
