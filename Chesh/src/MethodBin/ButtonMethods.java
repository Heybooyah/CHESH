package MethodBin;

//Import dependencies
import java.util.ArrayList;
import ObjectDefinitions.*;

public class ButtonMethods {
    
    //This method checks if two specified objects are colliding (Used for checking if a player or block is on a button)
    public static boolean TwoTouching(int ob1, int ob2, ArrayList<ArrayList<GameObject>> objects) {
        //For every instance of the first object, if it shares a position with an object of the other type return true
        for (GameObject i : objects.get(ob1)) {
            for (GameObject j : objects.get(ob2)) {
                if (i.pos[0] == j.pos[0] && i.pos[1] == j.pos[1])
                    return true;
            }
        }
        //Otherwise return false
        return false;
    }

    //This method updates the position of the doors to place them outside the boundaries of the level if a button is pressed
    public static ArrayList<ArrayList<GameObject>> UpdateDoors(ArrayList<ArrayList<GameObject>> objects) {
        //If a player or a block is on a yellow button
        if (TwoTouching(1, 4, objects) || TwoTouching(2, 4, objects)) {
            for (GameObject i : objects.get(5)) {
                //Set the yellow door's position to -2, which is unreachable regardless of level size
                i.pos[1] = -2;
            }
        } else {
            //If no yellow button is pressed, return the doors to their home positions
            for (GameObject i : objects.get(5)) {
                i.pos[1] = i.home;
            }
        }

        //Do the same thing, but for blue buttons and doors instead
        if (TwoTouching(1, 6, objects) || TwoTouching(2, 6, objects)) {
            for (GameObject i : objects.get(7)) {
                i.pos[1] = -2;
            }
        } else {
            for (GameObject i : objects.get(7)) {
                i.pos[1] = i.home;
            }
        }
        
        //Return the updated object positions
        return objects;
    }
}
