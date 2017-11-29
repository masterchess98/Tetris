import java.sql.Time;
import java.util.Timer;

/**

 * Tetris class to be completed for Tetris project

 * 

 * @author (your name) 

 * @version (a version number or a date)

 */

public class Tetris implements ArrowListener {

	//private Timer timer;
	private BoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad activeTetrad;
    
    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.play();
    }
    
    public Tetris() {
        grid = new BoundedGrid<Block>(20, 10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        activeTetrad = new Tetrad(grid);
        display.setArrowListener(this);     
    }
    
    public void upPressed() {
    	activeTetrad.rotate();
    	display.showBlocks();
    }
    
    public void downPressed() {
    	activeTetrad.translate(1, 0);
    	display.showBlocks();
    }
    
    public void leftPressed() {
    	activeTetrad.translate(0, -1);
    	display.showBlocks();
    }
    
    public void rightPressed() {
    	activeTetrad.translate(0, 1);
    	display.showBlocks();
    }

    public void spacePressed() { 
    	while (activeTetrad.translate(1, 0)) {
    		display.showBlocks();
    	}
    }

    public void play() {
    	while (true) {
    		try { 
    			Thread.sleep(1000);
    			} 
    		catch(Exception e) {}
            //Insert Exercise 3.3 code here
            if (activeTetrad.translate(1, 0)) {
            }
            else {
            	activeTetrad = new Tetrad(grid);
            	if(topRowsEmpty()==false)
            		break;
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
    	if(isCompletedRow(row)) {
    		for(int o = 0; o < grid.getNumCols(); o++) {
                    Block block = grid.get(new Location(row, o));
                    block.removeSelfFromGrid();
    		}
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