import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * class MinefieldDisplay.
 * 
 * DO NOT MODIFY
 * 
 * This class manages the Minesweeper GUI
 * 
 * @author Mr. Page
 * @version 100906
 */
public class MinefieldDisplay implements MouseListener
{
    private final static Dimension BUTTON_SIZE = new Dimension(12,18);
    // instance variables
    // references to the minefield and the current mine sweeper game
    private Minefield theField;
    private Minesweeper theGame;
    // display grid and frame
    private JButton[][] grid;
    private JFrame frame;
    private JPanel gridPanel;
    private Box topPanel;
    private JTextField numMines;
    private JLabel face;
    private JTextField elapsedTime;
    
    private int time;
    private static final int DELAY = 1000;
    private Timer timer;
    // constructor   
    public MinefieldDisplay(Minesweeper game, Minefield field)
    {
        theField = field;
        theGame = game;
        grid = new JButton[theField.numRows()][theField.numCols()];
        time = 0;
        // Schedule a job for the event-dispatchin thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
        // wait until the display has been drawn
        try
        {
            while(frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * create the GUI and show it.  For thread saftey
     * this method should be invoked from the 
     * event-dispatching thread.
     * 
     */ 
    public void createAndShowGUI()
    {
       // create and set up the window
       frame = new JFrame("Minesweeper");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // prevent the frame from resizing
       frame.setResizable(false);
       frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
       // construct the box that will sit above the grid
       topPanel = Box.createHorizontalBox();
       topPanel.add(Box.createGlue());       
       numMines = new JTextField(2);    // text field with 2 columns to display the number of mines
       // set the background to black, the text color to red and adjust the font size
       numMines.setBackground(Color.black);
       numMines.setForeground(Color.red);
       numMines.setFont(new Font("sans serif", Font.BOLD, 28));
       // keep the user from changing the text area
       numMines.setEditable(false);
       numMines.setText("10");
       numMines.setMaximumSize(new Dimension(30,30));
       topPanel.add(numMines);
       topPanel.add(Box.createVerticalStrut(30));
       // create a button to hold a smiley face
       face = new JLabel(new ImageIcon("smiley2.gif"));
       face.setPreferredSize(new Dimension(30,30));
       topPanel.add(face);
       topPanel.add(Box.createVerticalStrut(30));
       // create a text box to display the time - need an instance variable
       elapsedTime = new JTextField(3); 
       elapsedTime.setBackground(Color.black);
       elapsedTime.setForeground(Color.red);
       elapsedTime.setFont(new Font("sans serif", Font.BOLD, 20));
       elapsedTime.setMaximumSize(new Dimension(30,30));       
       elapsedTime.setEditable(false);
       elapsedTime.setText("0");
       topPanel.add(elapsedTime);
       topPanel.add(Box.createGlue());
       
       gridPanel = new JPanel();
       gridPanel.setLayout(new GridLayout(theField.numRows(), theField.numCols()));       
       // create each square component
       for(int row = 0; row < grid.length; row++)
           for(int col = 0; col < grid[0].length; col++)
           {
               JButton aButton = new JButton();
               aButton.setPreferredSize(BUTTON_SIZE);
               aButton.setBackground(Color.RED);
               aButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
               aButton.setActionCommand("" + row + "," + col );
               //aButton.addActionListener(this);
               // use a MouseListener instead of the button action so that
               // right mouse clicks can be intercepted
               aButton.addMouseListener(this);
               grid[row][col] = aButton;
               
               gridPanel.add(grid[row][col]);
           }
        gridPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        topPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));        
        frame.add(topPanel);  
        frame.add(gridPanel);
        // set up the timer
        // set up the action listener for the timer as an inside class
        ActionListener TimerTask = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                elapsedTime.setText("" + time);
                time++;
            }
        };
        timer = new Timer(DELAY, TimerTask);
        // show the grid
        frame.pack();
        frame.setVisible(true);
    }
    
    public void update()
    {
        for(int row = 0; row < grid.length; row++)
            for(int col = 0; col < grid[0].length; col++)
            {
                if(theField.isVisited(row,col))
                {
                    disable(row,col);
                    if(theGame.countMines(row,col) != 0) setText(row,col,theGame.countMines(row,col));
                }
            }
     }     
     
    public void showAll()
    {
        stopTimer();
        for(int row = 0; row < theField.numRows(); row++)
            for(int col = 0; col < theField.numCols(); col++)
            {
                if(theField.isMine(row,col)) setText(row,col,row);
                disable(row,col);
            }        
    }
    // disable the current button and set its background to white
    // if it contains a mine, display the mine
    // this method is called when a button is pressed, or when performing the
    // recursive search for mines
    public void disable(int row, int col)
    {
        grid[row][col].setEnabled(false);
        grid[row][col].setBackground(Color.WHITE);
        if(theField.isMine(row,col))
            grid[row][col].setText("B");
    }
   
    public void setText(int row, int col, int text)
    {
        if(!theField.isMine(row,col))
        {
            if (text == 0) grid[row][col].setText("");
            grid[row][col].setText("" + text);
        }
    }
    
    /*
     * set the text in a grid element
     */
    public void setText(int row, int col, String text)
    {
        if(!theField.isMine(row,col))
        {
            grid[row][col].setText(text);            
        }

    } 
    
    /*
     * start the timer
     */
    public void startTimer()
    {
        timer.start();
    }
    
    /*
     * stop the timer
     */
    public void stopTimer()
    {
        timer.stop();
    }
    
    public void setFace(String faceName)
    {
        face.setIcon(new ImageIcon(faceName));
    }
    
    public void mouseClicked(MouseEvent event)
    {
        if((event.getComponent() instanceof JButton) && 
                    event.getComponent().isEnabled())
        {
            JButton button = (JButton) event.getComponent();
            String command = button.getActionCommand();
            int row = Integer.parseInt(command.substring(0, command.indexOf(",")));
            int col = Integer.parseInt(command.substring(command.indexOf(",")+1));
            if(event.getButton() == 1)
                theGame.pressed(row,col,false);
            else
                theGame.pressed(row, col, true);
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
                    
}
