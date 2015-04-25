
public class Node {
	String word;
	int LevDist; //Levenshtein distance from the parent node
	Node parent; //parent node 
	Node[] Children = new Node[50]; //Array to hold the children nodes of the node
	Node(Node p,String w,int d){
		word = w;
		parent = p;
		LevDist = d;
		for(int i=0;i<50;i++){
			Children[i] = null;
		}
	}
	Node(String w){
		word = w;
		parent = null;
		for(int i=0;i<50;i++){
			Children[i] = null;
		}
	}
}
