
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
	public static Node insertWord(String str,Node root){
		if(root!=null){
		int d = levenshteinDistance(str,root.word);
		if(d==0){ //same word
			return root;
		}
		if(root.Children[d]==null)
			root.Children[d] = new Node(root,str,d);
		else
			root.Children[d] = insertWord(str,root.Children[d]);
		}
		else{
			root = new Node(str);
		}
		return root;
	}
	
	//A recursive function that lists all strings from the given string within a given tolerance (Levenshtein distance)
	public static void spellCheck(Node root,String str,int tolerance){
		int d = levenshteinDistance(str, root.word);
		int i;
		if(d<=tolerance){
			System.out.println(root.word+" "+d);
			for(i=1;i<=tolerance+d;i++){
				if(root.Children[i]!=null){
					spellCheck(root.Children[i], str, tolerance);
				}
			}
		}
		else{
			for(i=d-tolerance;i<=tolerance+d;i++){
				if(root.Children[i]!=null){
					spellCheck(root.Children[i], str, tolerance);
				}
			}
		}
		
		
	}
}
