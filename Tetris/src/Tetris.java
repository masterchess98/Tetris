import java.awt.Color;
import java.sql.Time;
import java.util.Timer;
import java.time.Duration;

import javax.swing.JOptionPane;

/**
 * Tetris class to be completed for Tetris project
 * We might wanna change code to check and make sure preconditions are 
 * satisfied in an attempt to avoid possible errors.
 */
public class Tetris implements ArrowListener {
	/** grid */
	private BoundedGrid<Block> grid;
    /** display of game */
	private static BlockDisplay display;
    /** Active piece */
	private Tetrad activeTetrad;
    /** Score in game */
	private static long score = 0;
    
	/** first slot to check if empty */
    private static final int firstSlot = 3;
    
    /** last slot to check if empty */
    private static final int lastSlot = 6;
    
    // Make the game itself
    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.play();
        
        while (playAgain()) {
        	tetris = new Tetris();
        	tetris.play();
        }
        
    }
    
    /**
     * Check to see if they want to play again
     * @return true if yes, false otherwise
     */
    public static boolean playAgain() {
    	JOptionPane.showMessageDialog(null, "Your score is: " + score);
        String answer = "";
        answer = JOptionPane.showInputDialog(null, "Would you like to play again?");
        if (answer.contains("y")) {
        	return true;
        }
		return false;
    }
    /**
     * Set up game board and make listeners for keys
     */
    public Tetris() {
        grid = new BoundedGrid<Block>(20, 10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        activeTetrad = new Tetrad(grid);
        display.setArrowListener(this);
        score = 0;
    }
    
    /**
     * Rotate tetrad if up arrow pressed
     */
    public void upPressed() {
    	activeTetrad.rotate();
    	display.showBlocks();
    }
    
    /**
     * Translate tetrad down 1 if down arrow pressed
     */
    public void downPressed() {
    	if (activeTetrad.translate(1, 0)) {
    		activeTetrad.translate(1, 0);
    		display.showBlocks();
    	}
    }
    
    /**
     * Translate tetrad left 1 if left arrow pressed
     */
    public void leftPressed() {
    	activeTetrad.translate(0, -1);
    	display.showBlocks();
    }
    
    /**
     * Translate tetrad right 1 if the right arrow pressed
     */
    public void rightPressed() {
    	activeTetrad.translate(0, 1);
    	display.showBlocks();
    }
    
    /**
     * Keep translating tetrad down 1 until it cant move down more if space is pressed
     */
    public void spacePressed() { 
    	while (activeTetrad.translate(1, 0)) {
    		display.showBlocks();
    	}
    }
    
    /**
     * Plays the game
     */
    public void play() {
    	//checks to see if game ended
    	boolean gameOver = false;
    	while (!gameOver) {
    		try { 
    			if (score < 2000) {
    				Thread.sleep(1000);
    			} else if (score >= 2000 && score < 4000) {
    				Thread.sleep(900);
    			} else if (score >= 4000 && score < 6000) {
    				Thread.sleep(800);
    			} else if (score >= 6000 && score < 8000) {
    				Thread.sleep(700);
    			} else if (score >=8000 && score <10000) {
    				Thread.sleep(600);
    			} else if (score >= 10000 && score < 12000) {
    				Thread.sleep(500);
    			} else if (score >= 12000 && score <14000) {
    				Thread.sleep(400);
    			} else if (score >= 14000 && score < 16000) {
    				Thread.sleep(300);
    			} else if (score >= 16000 && score < 18000) {
    				Thread.sleep(200);
    			} else {
    				Thread.sleep(100);
    			}

    			score += 1;
    			} catch(Exception e) {}

    		//for some reason this if-else was completely necessary
            if (activeTetrad.translate(1, 0)) {
            } else {
            	activeTetrad = new Tetrad(grid);
            	if(!topRowsEmpty()) {
            		//checking if game ended
            		gameOver = true;;
            	}
            }
            clearCompletedRows();
            display.showBlocks();
        }
    }
    
    /**
     * precondition:  0 <= row < number of rows
     * postcondition: Returns true if every cell in the
     * given row is occupied;
     * returns false otherwise.
     * @param row given row to check if full
     * @return true if every cell in the row is occupied, false otherwise
     */
    private boolean isCompletedRow(int row) {
    	for(int i = 0; i < grid.getNumCols(); i++) {
            if(grid.get(new Location(row, i)) == null) {
                return false;
            }
        }    	
    	score += 1000;
        return true;
    }
    
    /**
     * precondition:  0 <= row < number of rows; given row is full of blocks
     * postcondition: Every block in the given row has been removed, and every block above row
     * has been moved down one row.
     * @param row row to clear
     */
    private void clearRow(int row) {
    	//Helper method for clearCompletedRows()
    	//check if row is completed 
    	//if so, clears it
    	if(isCompletedRow(row)) {
    		for(int o = 0; o < grid.getNumCols(); o++) {
                    Block block = grid.get(new Location(row, o));
                    while (block.getColor().getRGB() > Color.BLACK.getRGB()) {
                		block.color = block.color.darker();
                		display.showBlocks();
                		try {
                			//cool effect
            				Thread.sleep(3);
            			} catch (InterruptedException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}
                	}
                    block.removeSelfFromGrid();
    		}
    		
    		//Takes rows above one that just got cleared and moves them down 1
    		//Add code so that it can move down more than one if more than one row got cleared
            for (int row1 = row-1; row1 > 1 ; row1--) {
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
    
    /**
     * postcondition: All completed rows have been cleared.
     */
    private void clearCompletedRows() {
    	for (int i = 0; i < grid.getNumRows(); i++) {
    		clearRow(i);
    	}
    }
    
    /**
     * returns true if top two rows of the grid are empty (no blocks), false otherwise
     * @return true if 2 top rows in grid are empty, false otherwise
     */
    private boolean topRowsEmpty() {
    	for(int i = firstSlot; i < lastSlot; i++) {
    		if(grid.get(new Location(2, i)) != null && grid.get(new Location(3, i)) != null) {
    			if(grid.get(new Location(0, i)) != null && grid.get(new Location(1, i)) != null) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
}