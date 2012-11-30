package cell.g1;

import java.util.Arrays;

public class Node implements Comparable{
	int color;
	int x;
	int y;
	Node pi;
	
	public Node(int x, int y, int color){
		this.x=x;
		this.y=y;
		this.color=color;
	}
	
	public void setPi(Node n){
		this.pi=n;
	}
	
	public Node getPi(){
		return pi;
	}
	
	public int getColor(){
		return color;
	}
	
	public int[] getLocation(){
		return new int[]{x,y};
	}

	@Override
	public int compareTo(Object o) {
		Node n=(Node)o;
		if(this.x==n.x && this.y==n.y)
			return 0;
		else if(this.y>n.y)
			return 1;
		else if(this.y==n.y && this.x>n.x)
			return 1;
		else return -1;
					
	}
	
	public boolean equals(Node n){
		if(this.x==n.x && this.y==n.y)
			return true;
		else return false;
	}
	
	public String toString(){
		return Arrays.toString(this.getLocation());
	}
}
