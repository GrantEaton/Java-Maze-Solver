# Java-Maze-Solver
a program that takes a text file maze input and outputs the solution with three algorithms:
breadth-first search, depth-first search and uniform cost search.

To run the program: the program is designed to be run in a Java IDE. You will need to manually change the name of the text file in the code.  **THERE IS TWO LOCATIONS YOU WILL NEED TO CHANGE**

1. Download all files
2. Create a new project in your IDE
3. Add the two files (Main.java and Node.java) into the project, they should be inside of "default packages".
4. Add the maze files into your project (drag and drop into the main project folder should work, if not, google where to add files to be read from for your IDE)
5. Run the project. If you get a "file not found" exception, try moving the location of the mazes folder.

The program runs with three algorithms.
Breadth-first search (BFS): a search algorithm that finds the "shallowest solution" (in this situattion,
  BFS will find the answer that is the shortest amount of moves away).
  
Depth-First search (DFS): a search algorithm that will look down the first branch it finds, then come back and keep 
  trying branches until the answer is found. 
  
Uniform cost search (UCS): this search algorthm optimizes cost, which in this case, border-positions have a cost of 
  11 to move search them and all others have a cost of 1. Basically, UCS will find solutions that avoid the edge of
  the maze.
  
  
TO SET WHICH MAZE IS SEARCHED, just look at the top of the Main file, where the file I/O is created, or control-f "maze"
you can set the program to search mazes 1-8.

The mazes look like
5 6
 - - - - - - 
|. . . .|. F|
   -       - 
|.|. .|. .|.|
 - -   -     
|. . .|. . .|
     -   -   
|.|. .|.|.|.|
 -     -     
|S .|. . .|.|
 - - - - - - 
 
 where the numbers in the first line are the dimensions of the maze, "." (periods) represent open spaces, where you can occupy,
 "|" are vertical walls, "-" are horizontal walls, and " "(empty spaces) represent an area where there is no wall.
