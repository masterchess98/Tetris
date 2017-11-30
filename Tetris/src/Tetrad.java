/**
 * Tetrad class to be completed for Tetris project
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
import java.util.ArrayList;

public class Tetrad {
	
    private Block[] blocks;
    
    /* 
	 * instantiate blocks Block array (length 4)
	 * initialize blocks array with new Block objects 
	 * declare color variable 
	 * declare and instantiate locs Location array (length 4) 
	 * declare shape variable and set equal to zero
	 */
    public Tetrad(BoundedGrid<Block> grid) {
    	
    	blocks = new Block[4];
    	for (int i = 0; i < blocks.length; i++) {
    		blocks[i] = new Block();
    	}
    	
    	Color color = Color.BLACK;
    	
    	//The 4 blocks to each tetrad
    	Location[] temp = new Location[4];
    	
        //choose a random integer from 0 to 6
    	int shape =(int)(Math.random()*7) ;
    	
        /* Branch (if statements) based on each shape number, to then
         * set the color variable for that shape
         * set the block locations for that shape
         */                      
        if(shape == 0){
        	color = Color.BLUE;
        	temp[1] = new Location(0,3);
        	temp[0] = new Location(0,4);
        	temp[2] = new Location(0,5);
        	temp[3] = new Location(0,6);
        }
        if(shape == 1){
        	color = Color.cyan;
        	temp[1] = new Location(0,3);
        	temp[0] = new Location(0,4);
        	temp[2] = new Location(0,5);
        	temp[3] = new Location(1,4);
        }
        if(shape == 2){
        	color = Color.red;
        	temp[1] = new Location(0,3);
        	temp[0] = new Location(0,4);
        	temp[2] = new Location(1,3);
        	temp[3] = new Location(1,4);
        }
        if(shape == 3){
        	color = Color.green;
        	temp[1] = new Location(0,4);
        	temp[0] = new Location(1,4);
        	temp[2] = new Location(2,4);
        	temp[3] = new Location(2,5);
        }
        if(shape == 4){
        	color = Color.pink;
        	temp[1] = new Location(0,4);
        	temp[0] = new Location(1,4);
        	temp[2] = new Location(2,4);
        	temp[3] = new Location(2,3);
        }
        if(shape == 5){
        	color = Color.ORANGE;
        	temp[0] = new Location(0,4);
        	temp[1] = new Location(1,4);
        	temp[2] = new Location(1,3);
        	temp[3] = new Location(0,5);
        }
        if(shape == 6){
        	color = Color.magenta;
        	temp[0] = new Location(0,4);
        	temp[1] = new Location(1,4);
        	temp[2] = new Location(0,3);
        	temp[3] = new Location(1,5);
        }
        
        /*
         * loop through the blocks array to
         * set the color of each block
         * call addToLocations
         */                 
        for (int i = 0; i < 4; i++) {
        	blocks[i].setColor(color);	
        }
        this.addToLocations(grid, temp);
    }

    //precondition:  blocks are not in any grid;
    //               blocks.length = locs.length = 4.
    //postcondition: The locations of blocks match locs,
    //               and blocks have been put in the grid.
    private void addToLocations(BoundedGrid<Block> grid, Location[] locs) {
         for (int i = 0; i < locs.length ; i++) {
        	 blocks[i].putSelfInGrid(grid, locs[i]);
         }
    }

    //precondition:  Blocks are in the grid.
    //postcondition: Returns old locations of blocks;
    //               blocks have been removed from grid. 
    //				 Used to translate, rotate, and/or change tetrads.
    private Location[] removeBlocks() {
    	Location[] temp = new Location[4];
    	for (int i = 0; i < 4; i++) {
    	   temp[i] = blocks[i].getLocation();
    	   blocks[i].removeSelfFromGrid();
       }
       return temp;    	   
    }

    //postcondition: Returns true if each of locs is
    //               valid (on the board) AND empty in
    //               grid; false otherwise.
    protected boolean areEmpty(BoundedGrid<Block> grid,
                             Location[] locs) {
    	ArrayList<Location> occup = grid.getOccupiedLocations();
    	boolean a = true;
    	for (int i = 0; i < locs.length; i++) {
			for (Location loc : occup) {
				if (loc.equals(locs[i]))
					a = false;
			}
			if (!grid.isValid(locs[i]) || !a) 
				return false;
		}
        return (a);
    }

    //postcondition: Attempts to move this tetrad deltaRow
    //               rows down and deltaCol columns to the
    //               right, if those positions are valid
    //               and empty; returns true if successful
    //               and false otherwise.
    public boolean translate(int deltaRow, int deltaCol) {
    	boolean b = false;
    	BoundedGrid<Block> temp = null;
    	Location[] tempLoc = new Location[4];
    	Location[] possLoc = new Location[4];
        
    	for (int i = 0; i < 4; i++) {
    		temp = blocks[i].getGrid();
    	}
    	
    	tempLoc = removeBlocks();
    	
    	for (int i = 0; i < 4; i++) {
    		possLoc[i] = new Location(tempLoc[i].getRow() + deltaRow, tempLoc[i].getCol() + deltaCol);
    	}
    	boolean x1 = true;    	
    	
    	for (int i = 0; i < 4; i++) {    	
    		if(possLoc[i].getCol() <= -1 && (this.areEmpty(temp, possLoc))) {
    			this.addToLocations(temp, tempLoc);
    			x1 = false;
    		}    		
    		else if (possLoc[i].getCol() > 9 && (this.areEmpty(temp, possLoc)))
    			x1 = false;
    		else if (possLoc[i].getRow() <= -1 && (this.areEmpty(temp, possLoc)))
    			x1 = false;
    		else if (possLoc[i].getRow() >= 20 && (this.areEmpty(temp, possLoc)))
    			x1 = false;    		
    	}
    	
    	if (!this.areEmpty(temp, possLoc))
    		x1 =false;
			
    	if(x1){
			this.addToLocations(temp, possLoc);
			b = true;
    	}
    	else 
    		this.addToLocations(temp, tempLoc);
    	
       return b;
    }

    //postcondition: Attempts to rotate this tetrad
    //               clockwise by 90 degrees about its
    //               center, if the necessary positions
    //               are empty; returns true if successful
    //               and false otherwise.
    public boolean rotate() {
        /* Ask any block for its grid and store value
         * remove the blocks (but save the locations)
         * check if the new locations are empty replace 
         * the tetrad in the proper place (rotated)
         */
    	boolean b = false;
    	BoundedGrid<Block> temp = null;
    	Location[] tempLoc = new Location[4];
    	Location[] possLoc = new Location[4];
    	
    	Block[] tempBlocks = new Block[3];
    	
    	for (int i = 0; i < 4; i++) {
    		temp = blocks[i].getGrid();
    	}
    	    	
    	int row0 = blocks[0].getLocation().getRow();
		int col0 = blocks[0].getLocation().getCol();
		
		tempLoc = removeBlocks();
		
    	for (int i = 0; i < 4; i++) {
    		int row1 = row0 - col0 + tempLoc[i].getCol();
    		int col1 = row0 + col0 - tempLoc[i].getRow();
    	    possLoc[i] = new Location(row1, col1);
    	}    	
    	boolean x1 = true;
    	
    	for (int i = 0; i < 4; i++) {
    		if(possLoc[i].getCol() <= -1) {
    			this.addToLocations(temp, tempLoc);
    			x1 = false;
    		}
    		else if (possLoc[i].getCol() > 9)
    			x1 = false;
    		else if (possLoc[i].getRow() <= -1)
    			x1 = false;
    		else if (possLoc[i].getRow() >= 20)
    			x1 = false;
    	}
    	if (!this.areEmpty(temp, possLoc))
    		x1 =false;

    	if (x1) {
			this.addToLocations(temp, possLoc);
			b = true;
    	}
    	else 
    		this.addToLocations(temp, tempLoc);
    	
       return b;      
    }
}