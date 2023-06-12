package MethodBin;

import java.awt.*;
import java.util.ArrayList;

public class DrawingMethods
{
    public DrawingMethods(){}
    public static void drawGameObject(Graphics g, ObjectDefinitions.GameObject o) {

        //System.out.println("drawing");
        g.setColor(o.col);
    
        // Calculate the offset the GameObject must be displayed at in order for it to be
        // centered in the square
        int TileCenteringOffset[] = new int[2];
        TileCenteringOffset[0] = (50 - o.size[0]) / 2;
        TileCenteringOffset[1] = (50 - o.size[1]) / 2;
    
        g.fillRect((o.pos[0] + 1) * 50 + TileCenteringOffset[0], (o.pos[1] + 1) * 50 + TileCenteringOffset[1] + 25, o.size[0], o.size[1]);
    }

    //Set colors
    public static Color Purpley = new Color(113, 90, 148);
    public static Color Whiteish = new Color(142, 123, 171);
    public static Color Darkish = new Color(45, 45, 80);

    public static void FloorSetup(Graphics g) {
        ArrayList<ArrayList<Integer>> level = LevelMethods.ReadLevel(LevelMethods.getLevel());
        int gridsize = LevelMethods.findDoubleArrayLargest(level);

        // Define the colors for the game board

        // Create two for loops to tile the grid (one loop for height, one for width)
        for (int h = 0; h < level.size() + 2; h++) {
            for (int w = 0; w < gridsize + 2; w++) {
                // This function is what creates the checkerboarded pattern
                if (w % 2 + h % 2 == 1)
                    g.setColor(Purpley);
                else
                    g.setColor(Whiteish);

                // Check if it is an edge tile, and if it is display a wall instead.
                if (w == 0 || w == gridsize + 1 || h == 0 || h == level.size() + 1)
                    g.setColor(Darkish);

                // Draw the square with the determined color
                g.fillRect(w * 50, h * 50 + 25, 50, 50);
            }
        }
    }
}