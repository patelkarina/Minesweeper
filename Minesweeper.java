
/**
 * class Minesweeper - encapsulates the minesweeper game.
 * 
 * This is where student code goes
 * 
 * @author Mr. Page
 * @version 
 */
public class Minesweeper
{
    // constants for the size of the field, can be deleted if using default constructor
    private static final int GRID_ROWS = 9;
    private static final int GRID_COLS = 9;
    private static final int NUM_MINES = 10;
    // references to the model and the view
    private MinefieldDisplay theDisplay;
    private Minefield theField;

    private boolean timerStarted;
    // constructor
    public Minesweeper()
    {
        // construct the grid and the display
        theField = new Minefield();
        theDisplay = new MinefieldDisplay(this, theField);
        // set up the mines
        setMines(NUM_MINES);

        timerStarted = false;
    }

    /**
     * pressed - handles button presses.  this method is called whenever the user selects and clicks
     * on a location within the mine field that has not been previously selected.
     * 
     * The view actionPerformed method calls this method and passes the row and col information
     * as int values
     * 
     */

    public void pressed(int row, int col, boolean rightButton)
    { 
        if (!rightButton)
        {
            if (theField.isValid(row, col) && ! theField.isVisited(row, col))
            {
                if (theField.isMine(row, col))
                { 
                    theDisplay.stopTimer();
                    theDisplay.showAll();
                }
                else 
                {
                    scanField(row, col);
                    theDisplay.update();
                }
            }
        }
        else
        {
            theDisplay.setFace("/Users/karinapatel/Desktop/Americah_flag.gif");
        }
    }
            

    /**
     * recursively scan the field and reveal all squares that do not contain mines
     * label the squares adjacent to mines with the number of mines adjacent
     */

    public void scanField(int row,int col)
    {   
        if (countMines(row, col) == 0)
        {
            theField.markVisited(row, col);
        }
        if (theField.isValid(row + 1, col) && ! theField.isVisited(row + 1, col))
        {
            scanField(row + 1, col);
        }
        if (theField.isValid(row, col + 1) && ! theField.isVisited(row, col + 1))
        {
            scanField(row, col + 1);
        }
        if (theField.isValid(row, col - 1) && ! theField.isVisited(row, col - 1))
        {
            scanField(row, col - 1);
        }
        if (theField.isValid(row - 1, col) && ! theField.isVisited(row - 1, col))
        {
            scanField(row - 1, col);
        }
    }   

    /**
     * set up the mine field according to the probability given
     * 
     */

    public void setMines (int numMines)
    {
        // distribute numMines uniformily around the grid
        for (int i = 0; i < numMines; i++)
        { 
          int rows = (int)(Math.random() * (9 - 0) + 0);
          int columns = (int)(Math.random() * (9 - 0) + 0);
          theField.add(rows, columns);
        }
    }
    

    /**
     * count the number of mines adjacent to a given location
     * 
     * students write this 
     * 
     */
    public int countMines(int row, int col)
    {
        //throw new RuntimeException("Implement me");
        int count = 0;
        if (theField.isMine(row + 1, col) && theField.isValid(row + 1, col))
            { 
                count++;
            }
        if (theField.isMine(row, col + 1) && theField.isValid(row, col + 1))
            {
                count++;
            }
        if (theField.isMine(row + 1, col + 1) && theField.isValid(row + 1, col + 1))
            {
                count++;
            }
        if (theField.isMine(row - 1, col) && theField.isValid(row - 1, col))
            {
                count++;
            }
        if (theField.isMine(row, col - 1) && theField.isValid(row, col - 1))
            {
                count++;
            }
        if (theField.isMine(row - 1, col - 1) && theField.isValid(row - 1, col - 1))
            {
                count++;
            }
        if (theField.isMine(row + 1, col - 1) && theField.isValid(row + 1, col - 1))
            {
                count++;
            }
        if (theField.isMine(row  - 1, col + 1) && theField.isValid(row  - 1, col + 1))
            {
                count++;
            }
         return count;
        }
    }

        

        
            
        
        
    
    


