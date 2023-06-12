//Matthew O'Toole

//Import special packages
import MethodBin.*;
import ObjectDefinitions.*;

//Import dependencies
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;

class Main extends JFrame implements KeyListener {

    //Create a new main() instance
    public static void main(String[] args) 
    {
        new Main();
    }

    //Create the temporary variable for graphics, so it can be updated independently from g and used globally
    Graphics gtemp;

    //Create ArrayLists for all the different types of objects
    //So that a theoretically infinite number of objects can be loaded
    public static String level = LevelMethods.getLevel();
    public ArrayList<ArrayList<GameObject>> GameObjects = new ArrayList<ArrayList<GameObject>>();

    //Initialize the main constructor
    public Main() 
    {
        //Create a new instance of Main so that it can be modified statically
        //This is used to reload the JFrame for differently sized levels
        currentInstance = this;

        //Create a list of objects by decoding the level
        //Read LevelMethods to see how the decoding process works
        GameObjects = LevelMethods.createObjects(LevelMethods.DecodeLevel(LevelMethods.ReadLevel(level)));

        //Add the key listener
        addKeyListener(this);

        //Set the size of the JFrame to be based on the amount of squares in the level
        int gridwidth = LevelMethods.findDoubleArrayLargest(LevelMethods.ReadLevel(level));
        setSize(((gridwidth+2)*50), ((LevelMethods.ReadLevel(level).size() + 2)*50) + 25);

        //Set the title of the JFrame to contain the level number and title
        setTitle("CHESH: Level " + LevelMethods.currentLevel + " - " + MiscellaneousMethods.getLevelName(LevelMethods.currentLevel));

        // Display the window
        setVisible(true);   
    }

    //Public method for updating the JFrame statically
    public static void updateMain()
    {
        //Does basically the same thing as the constructor above, but statically
        level = LevelMethods.getLevel();
        currentInstance.GameObjects = LevelMethods.createObjects(LevelMethods.DecodeLevel(LevelMethods.ReadLevel(level)));

        int gridwidth = LevelMethods.findDoubleArrayLargest(LevelMethods.ReadLevel(level));
        currentInstance.setSize(((gridwidth+2)*50), ((LevelMethods.ReadLevel(level).size() + 2)*50) + 25);
        currentInstance.setTitle("CHESH: Level " + (LevelMethods.currentLevel-1) + " - " + MiscellaneousMethods.getLevelName(LevelMethods.currentLevel));
    }

    //Create an instance of Main that can be modified
    public static Main currentInstance;

    //Globally track whether the graphics are in simple mode or not
    public static boolean simple = false;

    //Check for key input
    public void keyPressed(KeyEvent e) {
        //If arrow keys are pressed, move player using the movePlayers() function from misc
        char key = e.getKeyChar();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || key == 'd')
            MiscellaneousMethods.movePlayers(1,0,1,GameObjects);
        if (e.getKeyCode() == KeyEvent.VK_LEFT || key == 'a')
            MiscellaneousMethods.movePlayers(-1,0,2,GameObjects);
        if (e.getKeyCode() == KeyEvent.VK_DOWN || key == 's')
            MiscellaneousMethods.movePlayers(0,1,3,GameObjects);
        if (e.getKeyCode() == KeyEvent.VK_UP || key == 'w')
            MiscellaneousMethods.movePlayers(0,-1,4,GameObjects);

        //When space is pressed, toggle simple graphics
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //If graphics are already simple, make them normal
            if (simple)
                DrawingMethods.Purpley = new Color(113, 90, 148);
            //Otherwise, make them simple
            else
                DrawingMethods.Purpley = new Color(142, 123, 171);
            //Flip simple
            simple = !simple;
        }

        //If the key "r" is pressed - restart
        if (key == 'r')
            updateMain();
        //If the key "h" is pressed - return to the hub
        if (key == 'h')
        {
            //Set the level to the hub
            LevelMethods.currentLevel = 1;
            updateMain();
        }
        //If the key "~" is pressed, restart the save file and the game
        if (key == '~')
        {
            //Set the level back to the start
            LevelMethods.currentLevel = 0;

            //Attempt to modify the save file to -3 (code for hard restart)
            try {
                LevelMethods.updateSave(-3);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            updateMain();
        }

        //After moving, check to see if any doors should be opened, and open them
        GameObjects = ButtonMethods.UpdateDoors(GameObjects);
        
        //Check if a restart is necessary (i.e did the player fall into lava)
        if (MiscellaneousMethods.isRestartNeeded(GameObjects))
            //UpdateMain() reloads the level
            updateMain();
        
        //Check if a player is touching the win block
        if (MiscellaneousMethods.wonYet(GameObjects))
        {
            //Tick the level number up and load the level
            System.out.println("Level Completed!");
            
            //Check if the player is on the hub world, because levels are managed different
            if (LevelMethods.currentLevel == 1)
            {
                //Check what x position the player is on, and teleport them to the corresponding level
                for (GameObject i:GameObjects.get(1))
                {
                    LevelMethods.currentLevel = i.pos[0] - 1;
                }
            }
            else
                LevelMethods.currentLevel++;
            updateMain();
            //Try to update the save to the current level (Should never return an error)
            try {
                LevelMethods.updateSave(LevelMethods.currentLevel);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
     
        //Update the graphics variable before drawing
        gtemp = getGraphics();
        update(gtemp);
    }

    //These methods are required to implement KeyListener
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}


    //Paint what needs to be painted
    public void paint(Graphics g) {

        ArrayList<ArrayList<Integer>> Level = LevelMethods.ReadLevel(LevelMethods.getLevel());
        BufferedImage bi = new BufferedImage((LevelMethods.findDoubleArrayLargest(Level) + 2) * 50, (Level.size() + 2) * 50 + 25, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = bi.getGraphics();

        //Draw the checkerboarded (or solid) floor
        DrawingMethods.FloorSetup(g2);

        //For every gameobject that exists, call a method to draw it
        for (ArrayList<GameObject> i:GameObjects)
        {
            for (GameObject j:i)
            {
                //Hold back on printing smaller objects like the player and pushblock because they must be layered properly
                if (!(j.type.equals("Player") || j.type.equals("Push")))
                    DrawingMethods.drawGameObject(g2, j);
            }
        }
        
        //Draw the player and pushblocks
        for (ArrayList<GameObject> i:GameObjects)
        {
            for (GameObject j:i)
            {
                //This way they appear on top of buttons, and the player can see what they are on
                if (j.type.equals("Player") || j.type.equals("Push"))
                    DrawingMethods.drawGameObject(g2, j);
            }
        }

        //Draw the image to the JFrame (This stops the screen from flickering)
        g.drawImage(bi, 0, 0, this);
    }
}
