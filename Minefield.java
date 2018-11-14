
/**
 * class Minefield.
 * 
 * Students write this
 * 
 * This class encapsulates the mine field grid
 * 
 * @author Mr. Page
 * @version 100906 
 */
public class Minefield
{
    // instance variables
    // theField is an array of booleans that indicate the locations of the mines
    // visited is an arrray of booleans indicating wether a location has been visited
    private boolean theField[][];
    private boolean visited[][];
    // mineCount keeps track of how many mines are currently in the game
    private int mineCount;
    // constructors
    /**
     * creates a mine field with the specified number of rows and columns
     */
    public Minefield(int rows, int cols)
    {
        theField = new boolean[rows][cols];
        visited = new boolean[rows] [cols];
    }
    /**
     * creates a 9 by 9 mine field
     */
    public Minefield()
    {
        this(9,9);
    }
    /**
     * numRows returns the number of rows in this field
     * 
     */
    public int numRows()
    {
        return theField.length;
    }
    /**
     * numCols returns the number of columns in this field
     */
    public int numCols()
    {
        return theField[0].length;
    }
    
    /**
     * returns true if the row and col given are within the grid
     * 
     * Write this method!
     */
    public boolean isValid(int row, int col)
    {
       if (this.numRows()  - 1>= row && this.numCols() - 1>= col)
        { 
            return true;
        }
       return false;
       // throw new RuntimeException("Implement me");
    }
    
    /**
     * returns true if the row and col given correspond to a valid location 
     * within the grid and the location does contain a mine
     */
    public boolean isMine(int row, int col)
    {
        return isValid(row,col) && theField[row][col];
    }
    
    /**
     * adds a mine to the grid
     */
    public void add(int row, int col)
    {
        if(!isMine(row,col))
        {
            theField[row][col] = true;
            mineCount++;
        }
    }
    
    /**
     * marks an empty location as visited
     * locations containing mines cannot be marked as visited, otherwise the recursive scan
     * will show the mine locations!  Use the display disable method to display the mine locations     * 
     */
    public void markVisited(int row,int col)
    {
        // mark a location as visited, if it is not a mine
        if(isValid(row,col) && !theField[row][col]) visited[row][col] = true;        
    }

    /**
     * returns true if the location specified has been visited
     */
    public boolean isVisited(int row, int col)
    {
        return isValid(row,col) && visited[row][col];
    }

    /**
     * return the number of mines in this mine filed
     * 
     */
    public int numMines()
    {
        return mineCount;
        //throw new RuntimeException("Implement me");        
    }
}
