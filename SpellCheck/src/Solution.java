import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;


public class Solution {
	public static void main(String[] args) throws FileNotFoundException{
		 char[][] neighbour = new char[27][2];
		 //hardcoding the neighbours of the characters based on the Qwerty arrangement
		 //a
		 neighbour[0][0]= 's';
		 neighbour[0][1]= '@';
		 //b
		 neighbour[1][0]= 'v';
		 neighbour[1][1]= 'n';
		 //c
		 neighbour[2][0]= 'x';
		 neighbour[2][1]= 'v';
		 //d
		 neighbour[3][0]= 's';
		 neighbour[3][1]= 'f';
		 //e
		 neighbour[4][0]= 'w';
		 neighbour[4][1]= 'r';
		 //f
		 neighbour[5][0]= 'd';
		 neighbour[5][1]= 'g';
		 //g
		 neighbour[6][0]= 'f';
		 neighbour[6][1]= 'h';
		 //h
		 neighbour[7][0]= 'g';
		 neighbour[7][1]= 'j';
		 //i
		 neighbour[8][0]= 'u';
		 neighbour[8][1]= 'o';
		 //j
		 neighbour[9][0]= 'h';
		 neighbour[9][1]= 'k';
		 //k
		 neighbour[10][0]= 'j';
		 neighbour[10][1]= 'l';
		 //l
		 neighbour[11][0]= 'k';
		 neighbour[11][1]= '@';
		 //m
		 neighbour[12][0]= 'n';
		 neighbour[12][1]= '@';
		 //n
		 neighbour[13][0]= 'b';
		 neighbour[13][1]= 'm';
		 //o
		 neighbour[14][0]= 'i';
		 neighbour[14][1]= 'p';
		 //p
		 neighbour[15][0]= 'o';
		 neighbour[15][1]= '@';
		 //q
		 neighbour[16][0]= 'w';
		 neighbour[16][1]= '@';
		 //r
		 neighbour[17][0]= 'e';
		 neighbour[17][1]= 't';
		 //s
		 neighbour[18][0]= 'a';
		 neighbour[18][1]= 'd';
		 //t
		 neighbour[19][0]= 'r';
		 neighbour[19][1]= 'y';
		 //u
		 neighbour[20][0]= 'y';
		 neighbour[20][1]= 'i';
		 //v
		 neighbour[21][0]= 'c';
		 neighbour[21][1]= 'b';
		 //w
		 neighbour[22][0]= 'q';
		 neighbour[22][1]= 'e';
		 //x
		 neighbour[23][0]= 'z';
		 neighbour[23][1]= 'c';
		 //y
		 neighbour[24][0]= 't';
		 neighbour[24][1]= 'u';
		 //z
		 neighbour[25][0]= 'x';
		 neighbour[25][1]= '@';
		 
		 
		Scanner in = new Scanner(new File("words.txt"));
		int id;
		String s;
		Node BKroot = null;
		while(in.hasNext()){ //building the tree
			id = in.nextInt();
			s = in.next();
			BKroot = NodeFunctions.insertWord(s,BKroot);
		}
		System.out.println("Enter your word");
		Scanner br = new Scanner(System.in);
		s = br.next();
		s = s.toLowerCase();
		while(!s.equals("@")){
		System.out.println("Words resemebling the typed string are");
		PriorityQueue<order> misSpelt = new PriorityQueue<order>();
		NodeFunctions.spellCheck(BKroot, s, 2,misSpelt,neighbour);
		int i;
		for(i=0;i<misSpelt.size();i++){
			System.out.println(misSpelt.peek().word+" "+ misSpelt.poll().levDist ); //poll will pop the top element
		}
		System.out.println("Enter your next word");
		s = br.next();
		s = s.toLowerCase();
		}

	}
}