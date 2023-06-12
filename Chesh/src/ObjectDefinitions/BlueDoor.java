package ObjectDefinitions;

import java.awt.*;

public class BlueDoor extends GameObject
{
    public BlueDoor(){
        //Initialize the type, color, and size of the blue door
        //Type was used for debugging, but isn't really used for the actual game
        super.type = "BlueDoor";
        super.col = new Color(50, 73, 186);
        super.size = new int[] {50,50};
        //Enable collision
        super.hasCollision = true;
    }
}
