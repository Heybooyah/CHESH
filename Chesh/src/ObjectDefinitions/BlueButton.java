package ObjectDefinitions;

import java.awt.*;

public class BlueButton extends GameObject
{
    public BlueButton(){
        //Initialize the type, color, and size of the blue button
        //Type was used for debugging, but isn't really used for the actual game
        super.type = "BlueButton";
        super.col = new Color(97, 112, 184);
        super.size = new int[] {50,50};
    }
}
