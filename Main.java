import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    static int[] startingLocation = new int[2];
    static int[] finishLocation = new int[2];
    static String[][] matrix = null;
    static int maxCols = 0;
    static int maxRows = 0;
    static boolean finishSearch = false;
    static Node finishNode;
    
	public static void main(String[] args) {
		
		int row = 0;
		
        
		try{						//change the maze to be searched here AND BELOW
			BufferedReader br = new BufferedReader(new FileReader("maze8.txt")) ;
			
			//count up all the lines to know how many rows to do. 
			// this was a lazy way to do it, and making a buffer reader was also
			//extremely lazy					//change the maze to be searched here AND ABOVE
			BufferedReader lineReader = new BufferedReader(new FileReader("maze8.txt"));
			String line1;
			lineReader.readLine();
			//go through the file and get the maximum rows and columns
			while ((line1 = lineReader.readLine()) != null) { 
				maxRows++;
				String[] vals1 = line1.trim().split("");
				if (vals1.length > maxCols)
					maxCols = vals1.length;
			}
			//initialize our matrix based on the maximum grid
			matrix = new String[maxRows][maxCols];
			lineReader.close();
			
			
			String line;
			br.readLine();
			//go through all lines, split every char into array, then add to matrix
			while ((line = br.readLine()) != null) {

				String[] vals = line.split("");
				
				//.split was leaving off the first whitespace in horizontal wall lines, so i made this to fix that
				if(vals[0].equals("-")){ //check if its a wall line
					//add 1 more space than split() does to array
					String[] temp = new String[vals.length+1];
					temp[0] = " "; //add in initial space
					//add all the chars back to new array
					for (int i = 0; i < vals.length; i++) 
						temp[i+1] = vals[i];
					
					vals = temp;
				}
				
				//add the line array to the matrix at the right line
				for (int col = 0; col < vals.length; col++) {
	                matrix[row][col] = vals[col];
	                if(vals[col].equals("S")){ //catch starting location
	                	startingLocation[0] = row;
	                	startingLocation[1] = col;
	                	//System.out.println(row);
	                	//System.out.println(col);
	                }
	                else if(vals[col].equals("F")){ //catch finish location
	                	finishLocation[0] = row;
	                	finishLocation[1] = col;
	                	//System.out.println(row);
	                	//System.out.println(col);
	                }
	            }

	            row++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//make all null values in matrix spaces instead
		fixNulls();
		//print the ze
		printMaze(matrix);
		
		UCS(matrix);
		BFS(matrix);
		DFS(matrix);
		
		
	}
	
	
	private static void DFS(String[][] matrix){
			
			resetGlobals();
			Comparator<Node> comp = new Node.levelComparator();
			PriorityQueue PQ = new PriorityQueue<>(1000, comp);
			HashMap<String,String> searched = new HashMap <String,String>();
			
			Node root = new Node(startingLocation[0],startingLocation[1], "",0);
			PQ.add(root);
			
			try{
				while(finishSearch == false){
					Node temp = (Node) PQ.peek();
					PQ.remove(PQ.peek());
					searched.put(temp.toString(), "searched");
					checkNSEWandAddToQ(temp, PQ, searched);
					
					//System.out.println(temp.toString());
				}
			}
			catch(NullPointerException e){
				System.out.println("The program did not find a solution. Quitting");
			}
			
			System.out.println("DFS:" + finishNode.path+", Cost:"+finishNode.cost);
			
	}
	
	private static void UCS(String[][] matrix){
		
		resetGlobals();
		Comparator<Node> comp = new Node.CostComparator();
		PriorityQueue PQ = new PriorityQueue<>(1000, comp);
		HashMap<String,String> searched = new HashMap <String,String>();
		
		Node root = new Node(startingLocation[0],startingLocation[1], "",0);
		PQ.add(root);
		
		try{
			while(finishSearch == false){
				Node temp = (Node) PQ.peek();
				PQ.remove(PQ.peek());
				searched.put(temp.toString(), "searched");
				checkNSEWandAddToQ(temp, PQ, searched);
				
				//System.out.println(temp.toString());
			}
		}
		catch(NullPointerException e){
			System.out.println("The program did not find a solution. Quitting");
		}
		
		System.out.println("UCS:" + finishNode.path+", Cost:"+finishNode.cost);
		
}
	
	
	
	
	
	
	
	
	
	
	
	
	private static void BFS(String[][] matrix){
		//reset all the gobal variables for a new search
		resetGlobals();
		// create a new comparator with the same priority (1)
		Comparator<Node> comp = new Node.NoComparator();
		//PQ with 1000 max quantity
		PriorityQueue PQ = new PriorityQueue<>(1000, comp);
		//hashmap to see what we have previously searched.
		HashMap<String,String> searched = new HashMap <String,String>();
		//create a node at the start location and add it to Q
		Node root = new Node(startingLocation[0],startingLocation[1], "",0);
		PQ.add(root);
		
		//catch a null pointer exception, which means it didnt find a finish
		try{
			//loop through, pop Q, add to searched, check/add surrounding locations
			while(finishSearch == false){
				Node temp = (Node) PQ.peek();
				PQ.remove(PQ.peek());
				searched.put(temp.toString(), "searched");
				checkNSEWandAddToQ(temp, PQ, searched);
				//System.out.println(temp.toString());
			}
		}
		//Happens if maze doesnt finish
		catch(NullPointerException e){
			System.out.println("The program did not find a solution. Quitting BFS");
		}
		//print finished path
		System.out.println("BFS:" + finishNode.path+", Cost:"+finishNode.cost);
		
	}
	
	//function that checks NSEW, then adds any found to Q 
	public static void checkNSEWandAddToQ(Node node, PriorityQueue PQ, HashMap searched){
		//make sure left isnt null or in searched
		if(left(node) != null && searched.get(left(node).toString()) == null){
			//get the node (for some reason saying temp = left(node); didnt work so this was my lazy solution
			Node temp = new Node(node.row,node.col-2,node.path + "W", node.cost+getCost(node));
			temp.level = node.level +1;
			//if were at the finish, end the search
			if(temp.row == finishLocation[0] && temp.col == finishLocation[1]){
				finishSearch = true;
				finishNode = temp;
			}
			PQ.add(temp);
		}
		
		
		if(down(node) != null && searched.get(down(node).toString()) == null){
			Node temp = new Node(node.row+2,node.col, node.path + "S", node.cost+getCost(node));
			temp.level = node.level +1;
			if(temp.row == finishLocation[0] && temp.col == finishLocation[1]){
				finishSearch = true;
				finishNode = temp;
			}
			PQ.add(temp);
		}
		
		
		if(right(node) != null && searched.get(right(node).toString()) == null){
			Node temp = new Node(node.row,node.col+2, node.path + "E", node.cost+getCost(node));
			temp.level = node.level +1;
			if(temp.row == finishLocation[0] && temp.col == finishLocation[1]){
				finishSearch = true;
				finishNode = temp;
			}
			PQ.add(temp);
		}
		if(up(node) != null && searched.get(up(node).toString()) == null){
			Node temp = new Node(node.row-2,node.col, node.path + "N", node.cost+getCost(node));
			temp.level = node.level +1;
			if(temp.row == finishLocation[0] && temp.col == finishLocation[1]){
				finishSearch = true;
				finishNode = temp;
			}
			PQ.add(temp);
		}
		
	}
	
	//checks the left spot of the current node
	public static Node left(Node node){
		//avoid out of bounds error for -1
		if(node.col == 1){
			return null;
		}
		//make sure not null, is a dot or the finish (F), and there is no wall
		else if(matrix[node.row][node.col-2] != null && (matrix[node.row][node.col-2].equals(".") || matrix[node.row][node.col-2].equals("F")) && !matrix[node.row][node.col-1].equals("|")){
			//create new node with cost, direction and Row, col updated
			Node temp = new Node(node.row,node.col-2, node.path+"W",node.cost+getCost(node));
			return temp;
		}
		//found nothing
		return null;
	}
	public static Node right(Node node){
		if(node.col == maxCols-2){
			return null;
		}
		else if(matrix[node.row][node.col+2] != null && (matrix[node.row][node.col+2].equals(".") || matrix[node.row][node.col+2].equals("F")) && !matrix[node.row][node.col+1].equals("|")){
			Node temp = new Node(node.row,node.col+2,node.path+"E",node.cost+getCost(node));
			return temp;
		}
		return null;
	}
	public static Node up(Node node){
		if(node.row == 1){
			return null;
		}
		else if(matrix[node.row-2][node.col] != null && (matrix[node.row-2][node.col].equals(".") || matrix[node.row-2][node.col].equals("F")) && !matrix[node.row-1][node.col].equals("-")){
			Node temp = new Node(node.row-2,node.col,node.path+"N",node.cost+getCost(node));
			return temp;
		}
		return null;
	}
	public static Node down(Node node){
		if(node.row == maxRows-2){
			return null;
		}
		else if(matrix[node.row+2][node.col] != null && (matrix[node.row+2][node.col].equals(".") || matrix[node.row+2][node.col].equals("F")) && !matrix[node.row+1][node.col].equals("-")){
			Node temp = new Node(node.row+2,node.col,node.path+"S",node.cost+getCost(node));
			return temp;
		}
		return null;
	}
	
	//print the maze, with Row, col indexes
	private static void printMaze(String[][] matrix){
		System.out.println(" 0123456789TET");//col index
		for (int i = 0; i < matrix.length; i++) {
			System.out.print(i);//row Index
		    for (int j = 0; j < matrix[i].length; j++) {
		    		System.out.print(matrix[i][j]);
		    }
		    System.out.println();
		    //System.out.println(matrix[11][0]);
		}
	}
	//get rid of null values in the maze, by making them spaces
	private static void fixNulls(){
		for (int i = 0; i < matrix.length; i++) {
		    for (int j = 0; j < matrix[i].length; j++) {
		    	if(matrix[i][j] == null){
		    		matrix[i][j] = " ";
		    	}    
		    }
		}
	}
	
	//return the cost of a given node based on its location (edge = 10, any other is 1)
	private static int getCost(Node N){
		if(N.col==1 || N.col==(maxCols-2) || N.row==1 || N.row==(maxRows-2)){
			return 11;
		}
		else return 1;
	}
	
	//we use globals in each search, so clear them each new search
	private static void resetGlobals(){
		//startingLocation[0] = 0;
		//startingLocation[1] = 0;
		//finishLocation[0] = 0;
		//finishLocation[1] = 0;
		
		finishSearch = false;
	    finishNode = new Node(-1,-1,"",0);
	}

}
