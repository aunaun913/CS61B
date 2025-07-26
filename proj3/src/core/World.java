package core;

import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.Random;

public class World {
    public static final int GAME_WIDTH = 80;
    public static final int GAME_HEIGHT = 50;
    public TETile[][] myWorld;
    public House_Builder myBuilder;
    public Random random;
    public long mySeed;
    public World(long seed){
        myWorld = new TETile[GAME_WIDTH][GAME_HEIGHT];
        myBuilder = new House_Builder();
        random = new Random(seed);
        mySeed = seed;
    }

    public TETile[][] world_setup(){
        for (int x = 0; x < GAME_WIDTH; x++){
            for (int y = 0; y < GAME_HEIGHT; y++){
                myWorld[x][y] = Tileset.NOTHING;
            }
        }
        int num_of_house = RandomUtils.uniform(random, 20, 22);
        myBuilder.move(myWorld);
        while (num_of_house != 0){
            int old_xPos = myBuilder.posTracker.get(0);
            int old_yPos = myBuilder.posTracker.get(1);
            int new_xPos = myBuilder.posTracker.get(2);
            int new_yPos = myBuilder.posTracker.get(3);
            // build hallway
            // left hallway
            if (old_xPos - new_xPos > 0){
                for (int x = old_xPos - 1; x > new_xPos ; x--){
                    myWorld[x][old_yPos - 1] = Tileset.WALL;
                    myWorld[x][old_yPos + 1] = Tileset.WALL;
                    myWorld[x][old_yPos] = Tileset.FLOOR;
                }
            }
            // right hallway
            if (old_xPos - new_xPos < 0){
                for (int x = new_xPos - 1; x > old_xPos ; x--){
                    myWorld[x][old_yPos - 1] = Tileset.WALL;
                    myWorld[x][old_yPos + 1] = Tileset.WALL;
                    myWorld[x][old_yPos] = Tileset.FLOOR;
                }
            }
            // up hallway
            if (old_yPos - new_yPos > 0){
                for (int y = old_yPos - 1; y > new_yPos; y--){
                    myWorld[old_xPos - 1][y] = Tileset.WALL;
                    myWorld[old_xPos + 1][y] = Tileset.WALL;
                    myWorld[old_xPos][y] = Tileset.FLOOR;
                }
            }
            // down hallway
            if (old_yPos - new_yPos < 0){
                for (int y = new_yPos - 1; y > old_yPos; y--){
                    myWorld[old_xPos - 1][y] = Tileset.WALL;
                    myWorld[old_xPos + 1][y] = Tileset.WALL;
                    myWorld[old_xPos][y] = Tileset.FLOOR;
                }
            }
            myBuilder.House(myWorld, old_xPos, old_yPos);
            num_of_house--;
            myBuilder.move(myWorld);
        }
        myBuilder.House(myWorld, myBuilder.posTracker.get(0), myBuilder.posTracker.get(1));
        return myWorld;
    }
}