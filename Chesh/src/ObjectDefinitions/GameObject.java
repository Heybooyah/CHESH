package ObjectDefinitions;

import java.awt.*;

public class GameObject {
    //Define the variables that all object types will use

    //This variable stores x and y position
    public int pos[] = new int[2];

    //This variable stores width and height
    public int size[] = new int[2];

    //This variable stores the type (for debugging)
    public String type = "Undetermined";

    //This variable stores the color as an RGB
    public Color col = new Color(0,0,0);

    //This variable stores whether or not this object has collision (False by default)
    public boolean hasCollision = false;

    //This variable is exclusively used for doors, to return them to their home position
    public int home;
}