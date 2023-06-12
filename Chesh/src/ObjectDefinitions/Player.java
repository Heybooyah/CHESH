package ObjectDefinitions;

import java.awt.*;

public class Player extends GameObject
    {
        public Player(){
            //Initialize the type, color, and size of the player
            //Type was used for debugging, but isn't really used for the actual game
            super.type = "Player";
            super.col = new Color(8, 204, 116);
            super.size = new int[] {40,40};
        }
    }
