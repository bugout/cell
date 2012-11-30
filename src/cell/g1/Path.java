package cell.g1;

import java.util.ArrayList;
import java.util.Arrays;

public class Path implements Comparable<Path>, Cloneable{
	int length;
	ArrayList<Node> locs;
	
	public Path(){
		locs=new ArrayList<Node>();
	}
	
	public void add(Node loc){
		locs.add(loc);
	}
	
	public void remove(Node loc){
		locs.remove(loc);
	}
	
	public Object clone(){
		Path p=null;
		try {
			p = (Path)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public int compareTo(Path o) {
		if(this.locs.size()>o.locs.size())
			return 1;
		else if(this.locs.size()<o.locs.size())
			return -1;
		else return 0;
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(Node n:locs){
			sb.append(Arrays.toString(n.getLocation()));
		}
		return sb.toString();
	}
}
