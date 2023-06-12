package ObjectDefinitions;

import java.awt.*;

public class Wall extends GameObject
{
    public Wall(){
        //Initialize the type, color, and size of the wall
        //Type was used for debugging, but isn't really used for the actual game
        super.type = "Wall";
        super.col = new Color(45, 45, 80);
        super.size = new int[] {50,50};
        //Enable collisino
        super.hasCollision = true;
    }
}
