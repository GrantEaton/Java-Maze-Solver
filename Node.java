import java.util.Comparator;

public class Node  {
	public static class CostComparator implements Comparator<Node> {
		public int compare(Node a, Node b)
		{
			return a.cost - b.cost;
		}
	}
	public static class levelComparator implements Comparator<Node> {
		public int compare(Node a, Node b)
		{
			return b.level - a.level;
		}
	}
	public static class NoComparator implements Comparator<Node> {
		public int compare(Node a, Node b)
		{
			return 1;
		}
	}

	public Node(int r, int c, String p, int cst)
	{
		row = r;
		col = c;
		path = p;
		cost = cst;
		level = 0;
	}
	public boolean rowColEqual(int r, int c){
		if(row == r && col == c){
			return true;
		}
		else return false;
	}
	public boolean rowEqual(int r){
		if(row == r){
			return true;
		}
		else return false;
	}
	public boolean colEqual(int c){
		if(col == c){
			return true;
		}
		else return false;
	}
	public String toString() { return "(" + row + "," + col + ")"; }
	public int getRow() { return row; }
	public int getCol() { return col; }
	public int getKey() { return (row*100)+col; }
	public int row;
	public int col;
	public String path;
	public int cost;
	public int level;
}
