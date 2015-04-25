import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Solution {
	public static void main(String[] args) throws FileNotFoundException{
		Scanner in = new Scanner(new File("words.txt"));
		int id;
		String s;
		Node BKroot = null;
		while(in.hasNext()){ //building the tree
			id = in.nextInt();
			s = in.next();
			BKroot = NodeFunctions.insertWord(s,BKroot);
		}
		Scanner br = new Scanner(System.in);
		s = br.next();
		System.out.println("Words resemebling the typed string are");
		NodeFunctions.spellCheck(BKroot, s, 2);
	}
}