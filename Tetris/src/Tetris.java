import java.sql.Time;
import java.util.Timer;

/**

 * Tetris class to be completed for Tetris project

 * 

 * @author (your name) 

 * @version (a version number or a date)
 * 
 * We might wanna change code to check and make sure preconditions are 
 * satisfied in an attempt to avoid possible errors.

 */

public class Tetris implements ArrowListener {
	
	/* A timer variable.
	 * I was gonna use this to make the blocks fade away instead of just disappear. 
	 * When I tried last (years ago) I had some trouble with this.
	 * We should collaborate in next lab about this */
	private Timer timer;
	private BoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad activeTetrad;
    
    // Make the game itself
    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.play();
    }
    
    //Set up game board and make listeners for keys
    public Tetris() {
        grid = new BoundedGrid<Block>(20, 10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        activeTetrad = new Tetrad(grid);
        display.setArrowListener(this);     
    }
    
    // Rotate tetrad if up arrow pressed
    public void upPressed() {
    	activeTetrad.rotate();
    	display.showBlocks();
    }
    
    // Translate tetrad down 1 if down arrow pressed
    public void downPressed() {
    	activeTetrad.translate(1, 0);
    	display.showBlocks();
    }
    
    // Translate tetrad left 1 if left arrow pressed
    public void leftPressed() {
    	activeTetrad.translate(0, -1);
    	display.showBlocks();
    }
    
    // Translate tetrad right 1 if the right arrow pressed
    public void rightPressed() {
    	activeTetrad.translate(0, 1);
    	display.showBlocks();
    }
    
    // Keep translating tetrad down 1 until it cant move down more if space is pressed
    public void spacePressed() { 
    	while (activeTetrad.translate(1, 0)) {
    		display.showBlocks();
    	}
    }
    
    // Plays the game
    public void play() {
    	//checks to see if game ended
    	boolean gameOver = false;
    	while (!gameOver) {
    		try { 
    			Thread.sleep(1000);
    			} 
    		catch(Exception e) {}

    		//for some reason this if-else was completely necessary
            if (activeTetrad.translate(1, 0)) {
            }
            else {
            	activeTetrad = new Tetrad(grid);
            	
            	if(!topRowsEmpty()) {
            		//checking if game ended
            		gameOver = true;;
            	}
            	display.showBlocks();
            }
            clearCompletedRows();
            display.showBlocks();
        }
    }
    
    //precondition:  0 <= row < number of rows
    //postcondition: Returns true if every cell in the
    //               given row is occupied;
    //               returns false otherwise.
    private boolean isCompletedRow(int row) {
    	for(int i = 0; i < grid.getNumCols(); i++) {
            if(grid.get(new Location(row, i)) == null)
                return false;
        }
        return true;
    }
    
    //precondition:  0 <= row < number of rows;
    //               given row is full of blocks
    //postcondition: Every block in the given row has been
    //               removed, and every block above row
    //               has been moved down one row.
    private void clearRow(int row) {
    	//Helper method for clearCompletedRows()
    	//check if row is completed 
    	//if so, clears it
    	if(isCompletedRow(row)) {
    		for(int o = 0; o < grid.getNumCols(); o++) {
                    Block block = grid.get(new Location(row, o));
                    block.removeSelfFromGrid();
    		}
    		
    		//Takes rows above one that just got cleared and moves them down 1
    		//Add code so that it can move down more than one if more than one row got cleared
            for (int row1 = row-1; row1 > 0 ; row1--) {
            	for (int col = 0; col < grid.getNumCols(); col++) {
            		if(grid.get(new Location(row1, col)) != null) {
            			Block moved = grid.get(new Location(row1, col));
            			moved.removeSelfFromGrid();
            			moved.putSelfInGrid(grid, new Location(row1+1, col));
            		}
            	}
            }
    	}
    }
    
    //postcondition: All completed rows have been cleared.
    private void clearCompletedRows() {
    	for (int i = 0; i < grid.getNumRows(); i++) {
    		clearRow(i);
    	}
    }
    
    //returns true if top two rows of the grid are empty (no blocks), false otherwise
    private boolean topRowsEmpty() {
    	boolean a = true;
    	for(int i = 0; i < grid.getNumCols(); i++) {
    		if(grid.get(new Location(2, i)) != null && grid.get(new Location(3, i)) != null) {
    			if(grid.get(new Location(0, i)) != null && grid.get(new Location(1, i)) != null) {
    				a = false;
    			}
    		}
    	}
    	return a;
    }
}