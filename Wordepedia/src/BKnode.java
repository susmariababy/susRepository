
public class BKnode {
	String word;
	int LevDist; //Levenshtein distance from the parent node
	BKnode parent; //parent node 
	BKnode[] Children = new BKnode[50]; //Array to hold the children nodes of the node
	BKnode(BKnode p,String w,int d){
		word = w;
		parent = p;
		LevDist = d;
		for(int i=0;i<50;i++){
			Children[i] = null;
		}
	}
	BKnode(String w){
		word = w;
		parent = null;
		for(int i=0;i<50;i++){
			Children[i] = null;
		}
	}
}
