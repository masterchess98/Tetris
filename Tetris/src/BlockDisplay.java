/**
 * BlockDisplay class provided for Tetris project
 * Used to display the Blocks and GUI
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class BlockDisplay implements KeyListener {
	
	/** Background color. */
    private static final Color BACKGROUND = Color.BLACK;
    
    /** Title Screen. */
    private JFrame title;
    
    /** game board. */
    private BoundedGrid<Block> board;
    
    /** Game screen. */
    private JFrame frame;
    
    /** Listener. */
    private ArrowListener listener;
    
    /** Label for game screen. */
    private BufferedImage image;
    
    /**
     * Run the GUI
     * @param board Grid for the game
     */
    public BlockDisplay(BoundedGrid<Block> board) {
        this.board = board;

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowTitle();
                }
            });

        //Wait until display has been drawn
        try {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        } catch(InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Create a title screen to choose to start the game on
     */
    public void createAndShowTitle() {
    	title = new JFrame();
    	title.setSize(500, 1000);
    	title.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	title.addKeyListener(this);
    	GridLayout grid = new GridLayout(2,1);
    	title.setLayout(grid);
    	
    	ImageIcon pic = createImageIcon("tetris.jpg", null);
    	title.add(new JLabel(pic));
    	// add button to start the game
    	final JButton play = new JButton("PLAY");
    	//Add action to do if button is pressed
    	play.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			//If button is pressed start game and change this frame to be invisible
    			createAndShowGUI();
    			title.setVisible(false);
    		}
    	});
    	title.add(play);
    	title.setVisible(true);
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public void createAndShowGUI() {
        frame = new JFrame();
        image = new BufferedImage(board.getNumCols()*30,board.getNumRows()*30,BufferedImage.TYPE_INT_RGB);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.add(new JLabel(new ImageIcon(image)));
        showBlocks();
        frame.pack();
        System.out.println(frame.getSize());
        frame.setVisible(true);
    }
    
    /**
     * Set visibility of this frame
     */
    public void visible() {
        frame.setVisible(!frame.isVisible());
    }

    /**
     * Redraws the board to include the pieces and border colors.
     */
    public void showBlocks() {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(BACKGROUND);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setColor(new Color(150,0,255));
        graphics.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
        graphics.dispose();
        for (int row = 0; row < image.getHeight()/30; row++) {
            for (int col = 0; col < image.getWidth()/30; col++) {
                Location loc = new Location(row, col);
                Block square = board.get(loc);
                if (square != null) {
                    graphics = image.createGraphics();
                    graphics.setColor(square.getColor());
                    graphics.fillRect(col*30+1, row*30+1, 28, 28);
                    graphics.dispose();
                    frame.repaint();
                }
                frame.repaint();
            }
            frame.repaint();
        }
        frame.repaint();
    }

    /**
     *  Sets the title of the window.
     * @param title Title of window
     */
    public void setTitle(String title) {
        frame.setTitle(title);
    }
    
    /**
     * What to do if key is typed (NOTHING)
     */
    public void keyTyped(KeyEvent e){}
    
    /**
     * What to do if key is released (NOTHING)
     */
    public void keyReleased(KeyEvent e) {}
    
    /**
     * What to do when certain keys are pressed
     */
    public void keyPressed(KeyEvent e) {
        if (listener == null) {
        	return;
        }
        
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
        	listener.leftPressed();
        } else if (code == KeyEvent.VK_RIGHT) {
        	listener.rightPressed();
        } else if (code == KeyEvent.VK_DOWN) {
        	listener.downPressed();
        } else if (code == KeyEvent.VK_UP) {
        	listener.upPressed();
        } else if (code == KeyEvent.VK_SPACE) {
        	listener.spacePressed();
        }
    }
    
    /**
     * Set the listener
     * @param listener the listener
     */
    public void setArrowListener(ArrowListener listener) {
        this.listener = listener;
    }
    /** 
     * Returns an ImageIcon, or null if the path was invalid.
     * Used so that Image is displayed on title screen
     * @param path the path of the image file
     * @param description the description of the image
     * @return the ImageIcon or null if the path was invalid
     */
    protected ImageIcon createImageIcon(String path,
                                               String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}