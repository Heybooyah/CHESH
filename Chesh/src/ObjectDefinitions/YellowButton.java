package ObjectDefinitions;

import java.awt.*;

public class YellowButton extends GameObject
{
    public YellowButton(){
        //Initialize the type, color, and size of the yellow button
        //Type was used for debugging, but isn't really used for the actual game
        super.type = "YellowButton";
        super.col = new Color(204, 194, 106);
        super.size = new int[] {50,50};
    }
}
