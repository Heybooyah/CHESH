package MethodBin;

//Import dependencies
import ObjectDefinitions.*;
import java.util.ArrayList;

public class CollisionMethods 
{
    //Check if a specified coordinate is empty or not (Used in movement code)
    public static boolean isAir(int x, int y, ArrayList<ArrayList<GameObject>> level)
    {
        //Define the boundaries of the grid, because the player can collide with them as well
        int maxbounds = LevelMethods.findDoubleArrayLargest(LevelMethods.ReadLevel(LevelMethods.getLevel()));
        int maxbounds_y = LevelMethods.ReadLevel(LevelMethods.getLevel()).size();

        //If the position specified is outside of the boundaries, return false
        if (x < 0 || x > maxbounds - 1 || y < 0 || y > maxbounds_y - 1)
                {
                    //System.out.println("you are COLLIDING with a BOUNDARY");
                    return false;
                }

        //For every object
        for (ArrayList<GameObject> i:level)
        {
            for (GameObject j:i)
            {
                //If the position specified shares a position with that object -
                //Return false because the square is not empty
                if (j.pos[0] == x && j.pos[1] == y && j.hasCollision)
                {
                    //System.out.println("you are COLLIDING with a " + j.type);
                    return false;
                }
            }
        }

        //Otherwise return true
        return true;
    }

    //This method checks if a pushblock is able to be pushed in a given direction
    public static boolean canIPushThatPushblock(GameObject player, int xChange, int yChange, ArrayList<ArrayList<GameObject>> level)
    {
        //Set to false by default
        boolean output = false;

        //directions
        // 1 = right
        // 2 = left
        // 3 = up
        // 4 = down

        //Create a new position list for the spot the player would go to
        int newPos[] = new int[2];
        newPos[0] = player.pos[0] + xChange;
        newPos[1] = player.pos[1] + yChange;

        //Check if there even is a block that the player is colliding with (There should always be)
        boolean doesBlockExist = false;
        doesBlockExist = !isAir(newPos[0], newPos[1], level);
        
        if (doesBlockExist)
        {
            //If the block exists, check if it can move in the same direction the player is moving
            GameObject Block = MiscellaneousMethods.findGameObject(newPos[0],newPos[1], level);
            output = isAir(Block.pos[0] + xChange, Block.pos[1] + yChange, level);
        }

        //Return whether or not the block can be pushed
        else{return false;}

        return output;
    }
}
