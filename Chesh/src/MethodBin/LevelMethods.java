//Package this
package MethodBin;

//Import dependencies
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.ArrayList;
import ObjectDefinitions.*;

public class LevelMethods {
    public LevelMethods() {
    }
    
    //This int keeps track of what level the player is currently in
    public static int currentLevel = 0;

    //This updates the player's save file to whatever level you give
    public static void updateSave(int levelNum) throws IOException
    {
        //Create some files
        File save = new File("src/saveFile.txt");
        File temp = new File("src/temp.txt");
        FileWriter filew = new FileWriter(temp);
        PrintWriter writer = new PrintWriter(filew);

        //Subtract 2 from the level because of the 2 starting levels that don't count
        int i = levelNum - 2;

        //Check if updating the save would set back the player's progress
        if (i > getSave()|| i == -5)
        {
            //-5 is the input that happens on a hard restart
            //It needs to be different from 0 so it can bypass the check above
            if (i == -5)
                i = 0;
            //Write the level to the temp file and copy it over to the save
            writer.print(i);
            writer.flush();
            Files.copy(temp.toPath(), save.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        //Delete the temp file
        temp.delete();
    }

    //This reads the level currently stored in the save file
    public static int getSave()
    {
        //Save a variable as the save file
        File file = new File("src/saveFile.txt");
        Scanner sc = new Scanner(System.in);

        //Without the try function this gives an error for a potential filenotfound
        try {
            sc = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Can't find level file");
            sc.close();
        }

        int i = 0;

        //Read the next line and set the return variable to that
        if (sc.hasNextLine())
        {
            i = sc.nextInt();
        }
        else
        {
            System.out.println("file is empty apparently");
        }

        sc.close();
        return i;
    }

    //This returns the filepath to the current level file
    public static String getLevel()
    {
        //Edit this to get the current level
        return "src/Levels/" + MiscellaneousMethods.getLevelName(currentLevel) + ".txt";
    }

    //This converts the .txt file into the 2D array of integers it represents
    public static ArrayList<ArrayList<Integer>> ReadLevel(String filename) {

        File levelFile = new File(filename);
        Scanner sc = new Scanner(System.in);

        //Once again this returns a filenotfound without a try/catch
        try {
            sc = new Scanner(levelFile);
        } catch (Exception e) {
            System.out.println("Can't find level file");
            sc.close();
        }

        ArrayList<ArrayList<Integer>> Level = new ArrayList<ArrayList<Integer>>();

        // BLOCK IDENTIFIERS SO I REMEMBER
        // 0 - EMPTY SQUARE
        // 1 - WALL
        // 2 - PLAYER
        // 3 - PUSHBLOCK
        // 4 - GOAL
        // 5 - YELLOW BUTTON
        // 6 - YELLOW DOOR
        // 7 - BLUE BUTTON
        // 8 - BLUE DOOR
        // 9 - LAVA

        while (sc.hasNextLine()) {
            ArrayList<Integer> tlist = new ArrayList<Integer>();
            // Store every 8 digits in the list as one line of the level
            // This would totally break the system if any room wasn't 8x8, but that won't be
            // an issue as long as I'm careful

            boolean newLine = false;
            while (newLine == false)
            {
                //Save the int to the list unless the int is 99
                int t = sc.nextInt();
                if (t == 99) {
                    //If the int is 99, set newline to true so the program moves on to the next line in the array
                    //I couldn't get the program to detect \n characters so I'm doing this
                    newLine = true;
                } else {
                    tlist.add(t);
                }
            }
            // Add that level line to the master level list
            Level.add(tlist);
        }
        sc.close();
        return Level;
    }

    //This returns a 2d array containing information about objects
    public static ArrayList<ArrayList<Integer>> DecodeLevel(ArrayList<ArrayList<Integer>> Level) {
        int n = 0;
        ArrayList<ArrayList<Integer>> ObjectLocations = new ArrayList<ArrayList<Integer>>();

        // Loop with i as a reference to the list rather than an iterative count
        // This is so i.size() can be used, otherwise code might return errors
        // n takes i's place as the iterator
        for (ArrayList<Integer> i : Level) {
            int q = 0;
            for (int j : i) {
                ArrayList<Integer> tlist = new ArrayList<Integer>();
                if (j != 0) {
                    //Add the values to the list
                    tlist.add(j);
                    tlist.add(q);
                    tlist.add(n);
                    ObjectLocations.add(tlist);
                }
                q += 1;
            }
            n += 1;
        }

        n = 0;

        //This entire block of code is for adding walls if a level's widths are inconsistent
        //This doesn't actually do anything in any of the levels, but if I ever add more levels,
        //Then this could be something useful to save storage
        for (ArrayList<Integer> a:Level)
        {
            //Find the largest width in the array
            if (a.size() < findDoubleArrayLargest(Level))
            {
                //If any line is less than the largest width, add walls until it is equal size
                for (int i = 0; i < (findDoubleArrayLargest(Level) - a.size()); i++)
                {
                    ArrayList<Integer> tlist = new ArrayList<Integer>();
                    tlist.add(1);
                    tlist.add(a.size() + i);
                    tlist.add(n);
                    ObjectLocations.add(tlist);
                }
            }
            n += 1;
        }

        return ObjectLocations;
    }

    //Simple search method to find the largest array size in a double array
    public static int findDoubleArrayLargest(ArrayList<ArrayList<Integer>> a)
    {
        int l = 0;

        //For every number, if it is larger than the current i, set i to the current number
        for (ArrayList<Integer> i:a)
        {
            if (i.size() > l)
            {
                l = i.size();
            }
        }
        
        return l;
    }

    //This long method takes in the object information generated by DecodeLevel
    //It converts the object information into actual objects that can be displayed in the level
    public static ArrayList<ArrayList<GameObject>> createObjects(ArrayList<ArrayList<Integer>> a)
    {
        //Create a master object list called GameObjects
        ArrayList<ArrayList<GameObject>> GameObjects = new ArrayList<ArrayList<GameObject>>();

        //Organize the individual arrays in GameObjects by object type
        ArrayList<GameObject> Walls = new ArrayList<GameObject>();
        ArrayList<GameObject> Players = new ArrayList<GameObject>();
        ArrayList<GameObject> Pushblocks = new ArrayList<GameObject>();
        ArrayList<GameObject> Goals = new ArrayList<GameObject>();
        ArrayList<GameObject> YellowButtons = new ArrayList<GameObject>();
        ArrayList<GameObject> YellowDoors = new ArrayList<GameObject>();
        ArrayList<GameObject> BlueButtons = new ArrayList<GameObject>();
        ArrayList<GameObject> BlueDoors = new ArrayList<GameObject>();
        ArrayList<GameObject> Lavas = new ArrayList<GameObject>();

        //For each set of object values
        for (ArrayList<Integer> GameObj:a)
        {
            //Near the top of this file, there's a legend for which type value corresponds to each object
            //Each GameObject stores 3 values:
            //0 - Type (Legend is above)
            //1 - X position
            //2 - Y poision

            //If the object's type indicates it should be a wall
            if(GameObj.get(0) == 1)
            {
                //Create a new wall object and add it to the walls list
                Wall toj = new Wall();
                toj.pos[0] = GameObj.get(1);
                toj.pos[1] = GameObj.get(2);

                Walls.add(toj);
            }

            //These next couple blocks are pretty much the same thing, but for different object types
            //This could've been simplified A LOT if I didn't organize things by object type, but organization is useful
            if(GameObj.get(0) == 2)
            {
                //Create a new player object and add it to the walls list
                Player toj = new Player();
                toj.pos[0] = GameObj.get(1);
                toj.pos[1] = GameObj.get(2);

                Players.add(toj);
            }

            //Same thing
            if(GameObj.get(0) == 3)
            {
                Push toj = new Push();
                toj.pos[0] = GameObj.get(1);
                toj.pos[1] = GameObj.get(2);

                Pushblocks.add(toj);
            }

            //Same thing
            if(GameObj.get(0) == 4)
            {
                Goal toj = new Goal();
                toj.pos[0] = GameObj.get(1);
                toj.pos[1] = GameObj.get(2);

                Goals.add(toj);
            }

            //Same thing
            if(GameObj.get(0) == 5)
            {
                YellowButton toj = new YellowButton();
                toj.pos[0] = GameObj.get(1);
                toj.pos[1] = GameObj.get(2);

                YellowButtons.add(toj);
            }

            //Same thing
            if(GameObj.get(0) == 6)
            {
                YellowDoor toj = new YellowDoor();
                toj.pos[0] = GameObj.get(1);
                toj.pos[1] = GameObj.get(2);
                toj.home = GameObj.get(2);

                YellowDoors.add(toj);
            }

            //Same thing
            if(GameObj.get(0) == 7)
            {
                BlueButton toj = new BlueButton();
                toj.pos[0] = GameObj.get(1);
                toj.pos[1] = GameObj.get(2);

                BlueButtons.add(toj);
            }

            //Same thing
            if(GameObj.get(0) == 8)
            {
                BlueDoor toj = new BlueDoor();
                toj.pos[0] = GameObj.get(1);
                toj.pos[1] = GameObj.get(2);
                toj.home = GameObj.get(2);

                BlueDoors.add(toj);
            }

            //Same thing
            if(GameObj.get(0) == 9)
            {
                Lava toj = new Lava();
                toj.pos[0] = GameObj.get(1);
                toj.pos[1] = GameObj.get(2);
                toj.home = GameObj.get(2);

                Lavas.add(toj);
            }
        }

        //If the current level is 1 (The hub/Level select screen)
        if (currentLevel == 1)
        {
            //Create a wall that blocks the player from accessing levels farther than their save
            Wall toj = new Wall();
            toj.pos[0] = getSave() + 4;
            toj.pos[1] = 10;
            Walls.add(toj);
        }

        //Add all the organized lists to the master list and return it
        GameObjects.add(Walls);
        GameObjects.add(Players);
        GameObjects.add(Pushblocks);
        GameObjects.add(Goals);
        GameObjects.add(YellowButtons);
        GameObjects.add(YellowDoors);
        GameObjects.add(BlueButtons);
        GameObjects.add(BlueDoors);
        GameObjects.add(Lavas);

        return GameObjects;
    }
}
