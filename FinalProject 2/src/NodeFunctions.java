import java.util.PriorityQueue;



public class NodeFunctions {
	//A function to compute Levenshtein ditance of 2 strings
	public static int levenshteinDistance(String str1,String str2){
		int len1 = str1.length();
		int len2 = str2.length();
		if(len1==0) return len2;
		if(len2==0) return len1;
		int[][] d = new int[len1+1][len2+1];
		int i;
		for(i=0;i<=len1;i++){
			d[i][0] = i;
		}
		for(i=0;i<=len2;i++){
			d[0][i] = i;
		}
		int j;
		int match;
		for(i=1;i<=len1;i++){
			for(j=1;j<=len2;j++){
				 match = (str1.charAt(i-1)==str2.charAt(j-1)) ? 0:1;
				 d[i][j] = Math.min(Math.min(d[i-1][j]+1, d[i][j-1]+1),d[i-1][j-1]+match);		 
			}
		}
		return d[len1][len2];
	}
	
	//A recursive function to add a new word to the BKtree and return the root of the tree
	public static BKnode insertWord(String str,BKnode root){
		if(root!=null){
		int d = levenshteinDistance(str,root.word);
		if(d==0){ //same word
			return root;
		}
		if(root.Children[d]==null)
			root.Children[d] = new BKnode(root,str,d);
		else
			root.Children[d] = insertWord(str,root.Children[d]);
		}
		else{
			root = new BKnode(str);
		}
		return root;
	}
	
	//A recursive function that lists all strings from the given string within a given tolerance (Levenshtein distance)
	public static void spellCheck(BKnode root,String str,int tolerance,PriorityQueue<order> misSpelt,char[][] neighbour){
		int d = levenshteinDistance(str, root.word);
		int i;
		int j;
		if(d<=tolerance){
			order e = new order(root.word,d);
			if(root.word.length()==str.length()){ //implies the letters were just transpositioned //check if the characters are neighbours in the keyboard
					for(j=0;j<root.word.length();j++){
						if(root.word.charAt(j)!=str.charAt(j)){
							if(neighbour[str.charAt(j)-97][0]==root.word.charAt(j)||neighbour[str.charAt(j)-97][1]==root.word.charAt(j)){
								e.rank = 0; //if the letters are neighbours in the qwerty keyboard,give the highest rank
							}
							else{ //if the transpositioned letters are vowels , then assign a rank of 1
								if(root.word.charAt(j)=='a'||root.word.charAt(j)=='e'||root.word.charAt(j)=='i'||root.word.charAt(j)=='o'||root.word.charAt(j)=='u'||str.charAt(j)=='a'||str.charAt(j)=='e'||str.charAt(j)=='i'||str.charAt(j)=='o'||str.charAt(j)=='u'){
									e.rank = 1;
								}
							}
						}
					}
			}
			else{
				int min = Math.min(root.word.length(),str.length());
				int max = Math.max(root.word.length(),str.length());
				for(j=0;j<min;j++){
					if(root.word.charAt(j)!=str.charAt(j)){ //when we miss out the vowels in the words
						if(root.word.charAt(j)=='a'||root.word.charAt(j)=='e'||root.word.charAt(j)=='i'||root.word.charAt(j)=='o'||root.word.charAt(j)=='u'||str.charAt(j)=='a'||str.charAt(j)=='e'||str.charAt(j)=='i'||str.charAt(j)=='o'||str.charAt(j)=='u'){
							e.rank = 2;
							break;
					}
				}
				}	
			}
			misSpelt.add(e);
			for(i=1;i<=tolerance+d;i++){
				if(root.Children[i]!=null){
					spellCheck(root.Children[i], str, tolerance,misSpelt,neighbour);
				}
			}
		}
		else{
			for(i=d-tolerance;i<=tolerance+d;i++){
				if(root.Children[i]!=null){
					spellCheck(root.Children[i], str, tolerance,misSpelt,neighbour);
				}
			}
		}		
	}
}
