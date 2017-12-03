/**
 * Block class to be completed for Tetris project
 * the Blocks for each tetrad
 */
import java.awt.Color;

public class Block {
	
	/** the grid for blocks to go on. */
    private BoundedGrid<Block> grid;
    
    /** Location of the blocks. */
    private Location location;
    
    /** Color of the blocks. */
    Color color;
    
    /**
     * constructs a default blue block.
     */
    public Block() {
        color = Color.BLUE;
        grid = null;
        location = null;
    }

    /**
     * gets the color of this block
     * @return color of this block
     */
    public Color getColor() {
    	return color;
    }

    /**
     * sets the color of this block to newColor.
     * @param newColor color to set this block to
     */
    public void setColor(Color newColor) {
    	color = newColor;
    }

    /**
     * gets the grid of this block, or null if this block is not contained in a grid
     * @return grid of this block, or null if this block is not contained in a grid
     */
    public BoundedGrid<Block> getGrid() {
    	return grid;
    }

    /**
     * gets the location of this block, or null if this block is not contained in a grid
     * @return location of this block, or null if this block is not contained in a grid
     */
    public Location getLocation() {
    	return location;
    }

    /**
     * removes this block from its grid
     * precondition:  this block is contained in a grid
     */
    public void removeSelfFromGrid() {
    	grid.remove(this.getLocation());
    	location = null;
    	grid = null;
    }

    /**
     * puts this block into location loc of grid gr
     * if there is another block at loc, it is removed
     * preconditions:  
     * (1) this block is not contained in a grid
     * (2) loc is valid in gr
     * @param gr grid
     * @param loc location
     */
    //
    public void putSelfInGrid(BoundedGrid<Block> gr, Location loc) {
        Block block = gr.get(loc);
        if (block != null) {
        	block.removeSelfFromGrid();
        }
        gr.put(loc, this);
        grid = gr;
        location = loc;
    }

    /**
     * moves this block to newLocation
     * if there is another block at newLocation, it is removed
     * precondition:  
     * (1) this block is contained in a grid
     * (2) newLocation is valid in the grid of this block
     * @param newLocation new location for the block
     */           
    public void moveTo(Location newLocation) {
        grid.remove(location);
        Block other = grid.get(newLocation);
        if (other != null) {
        	other.removeSelfFromGrid();
        }
        location = newLocation;
        grid.put(location,this);
    }

    /**
     * returns a string with the location and color of this block
     */
    public String toString() {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}