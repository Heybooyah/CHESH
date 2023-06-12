package MethodBin;

import ObjectDefinitions.*;
import java.util.ArrayList;

public class MiscellaneousMethods 
{

    //This function returns the GameObject at a specified position
    //In the case of a player or block standing on top of a button (and having the same position) - 
    //The player or block will be returned, as those are the more important ones
    public static GameObject findGameObject(int x, int y, ArrayList<ArrayList<GameObject>> objects)
    {

        //Due to how the game is coded, it's impossible for the for loop to not return j
        //The errorRemover is never returned, but if it doesn't exist I get an error saying the function may not return anything

        GameObject errorRemover = new GameObject();

        //Using try because there's a potential error that isn't actually possible to achieve
        try
        {
            //For every object, if its position equals the positions in the parameters, return that object
            for(ArrayList<GameObject> i:objects)
            {
                for(GameObject j:i)
                {
                    if (j.pos[0] == x && j.pos[1] == y)
                    return j;
                }
            }
        }
        //Neither of these things ever actually do anything, as the function always returns j
        catch(Exception e)
        {}

        return errorRemover;
    }

    //This function updates the position of all players
    public static ArrayList<ArrayList<GameObject>> movePlayers(int xChange, int yChange, int direction, ArrayList<ArrayList<GameObject>> objects)
    {
        //For each player
        for (GameObject i:objects.get(1))
            {
                //Move the player if it has an open space in the direction it is moving
                if (CollisionMethods.isAir(i.pos[0] + xChange, i.pos[1] + yChange, objects))
                {
                    i.pos[0] += xChange;
                    i.pos[1] += yChange;
                }
                else
                {
                    //If the player doesn't, check if it's attempting to move into a pushblock
                    GameObject collidedWith = findGameObject(i.pos[0] + xChange, i.pos[1] + yChange, objects);
                    if ((collidedWith.type).equals("Push"))
                    {
                        //If they are trying to push a block, check that that block is able to move in that direction
                        if (CollisionMethods.canIPushThatPushblock(i,xChange, yChange, objects))
                        {
                            //If it can move, move both the player and the block
                            i.pos[0] += xChange;
                            i.pos[1] += yChange;
                            collidedWith.pos[0] += xChange;
                            collidedWith.pos[1] += yChange;
                        }
                    }
                }
            }
        //Return the updated locations of all objects
        return objects;
    }

    //This method checks if the player has won the game yet
    //It is also not entirely useful, because it saves 0 lines
    //But it makes it easier to understand what is happening in the main program
    public static boolean wonYet(ArrayList<ArrayList<GameObject>> objects)
    {
        if (ButtonMethods.TwoTouching(1,3,objects))
        {
            return true;
        }

        return false;
    }

    //This method checks if the level requires a restart for any reason
    public static boolean isRestartNeeded(ArrayList<ArrayList<GameObject>> objects)
    {
        //Check if any player was standing inside a closing door
        for (GameObject i:objects.get(1))
        {
            if (!(CollisionMethods.isAir(i.pos[0],i.pos[1],objects)))
            {
                System.out.println("You were crushed to death...");
                return true;
            }
        }

        //Check if any player is burning in lava
        if (ButtonMethods.TwoTouching(1,8,objects))
        {
            System.out.println("You burned in the lava...");
            return true;
        }

        //Check if any block was inside the same block as a closing door
        if (ButtonMethods.TwoTouching(2,5,objects) || ButtonMethods.TwoTouching(2,7,objects))
        {
            System.out.println("You crushed a block, making the level impossible...");
            return true;
        }

        return false;
    }

    //Return the name of the level, given its number (Used for locating the files, and makes it easier to reorder levels)
    public static String getLevelName(int levelNumber)
    {
        //Write all the levels to a list in gameplay order
        //For whatever reason, I got an error when trying to define this as a public static list outside of any method
        ArrayList<String> LevelNames = new ArrayList<String>();

        LevelNames.add("CHESH");
        LevelNames.add("HubWorld");
        LevelNames.add("Hole");
        LevelNames.add("Door");
        LevelNames.add("Maze");
        LevelNames.add("Minefield");
        LevelNames.add("Pocket");
        LevelNames.add("Two-Piece");
        LevelNames.add("Teamwork2");
        LevelNames.add("MirrorMaze");
        LevelNames.add("Snake");
        LevelNames.add("Dungeon");
        LevelNames.add("Triplet");
        LevelNames.add("Hurdles");
        LevelNames.add("LeftTurn");
        LevelNames.add("Ying-Yang");
        LevelNames.add("Crossroads");
        LevelNames.add("Trailblazer");
        LevelNames.add("ControlRoom");
        LevelNames.add("ImaginaryFriend");
        LevelNames.add("TheSquad");
        LevelNames.add("BIG");
        LevelNames.add("WIN");

        //Return the name of the level
        return LevelNames.get(levelNumber);
    }
}
