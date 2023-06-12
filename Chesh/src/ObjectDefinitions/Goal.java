package ObjectDefinitions;

import java.awt.*;

public class Goal extends GameObject
    {
        public Goal(){
            //Initialize the type, color, and size of the goal
            //Type was used for debugging, but isn't really used for the actual game
            super.type = "Goal";
            super.col = new Color(255, 101, 36);
            super.size = new int[] {50,50};
        }
    }
