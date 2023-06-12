package ObjectDefinitions;

import java.awt.*;

public class Push extends GameObject
    {
        public Push(){
            //Initialize the type, color, and size of the blue button
            //Type was used for debugging, but isn't really used for the actual game
            super.type = "Push";
            super.col = new Color(111, 115, 109);
            super.size = new int[] {40,40};
            //Enable collision
            super.hasCollision = true;
        }

        
    }