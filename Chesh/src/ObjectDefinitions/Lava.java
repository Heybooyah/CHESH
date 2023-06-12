package ObjectDefinitions;

import java.awt.*;

public class Lava extends GameObject
{
    public Lava(){
        //Initialize the type, color, and size of the lava
        //Type was used for debugging, but isn't really used for the actual game
        super.type = "Lava";
        super.col = new Color(255, 0, 0);
        super.size = new int[] {50,50};
    }
}
