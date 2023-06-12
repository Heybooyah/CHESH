package ObjectDefinitions;

import java.awt.*;

public class YellowDoor extends GameObject
{
    public YellowDoor(){
        //Initialize the type, color, and size of the yellow door
        //Type was used for debugging, but isn't really used for the actual game
        super.type = "YellowDoor";
        super.col = new Color(255, 255, 0);
        super.size = new int[] {50,50};
        //Enable collision
        super.hasCollision = true;
    }
}
