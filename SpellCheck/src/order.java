
public class order implements Comparable<order>{
	String word;
	int levDist;
	int rank;
	order(String s,int d){
		word = s;
		levDist = d;
		rank = 4;
	}
	@Override
	public int compareTo(order o) {
		// TODO Auto-generated method stub
		int d= Double.compare(levDist, o.levDist);
		if(d!=0){
			return d;
		}
		else
			return Double.compare(rank,o.rank);
	}

}