package Lab9;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world initially full of trees.
 */
public class Task1 {
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    private static void fillWithTrees(TETile[][] world) {
        int w = world.length;
        int h = world[0].length;
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                world[x][y] = Tileset.TREE;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 20);
        TETile[][] MyWorld = new TETile[30][15];
        for(int x = 0; x < 30; x++){
            for (int y = 0; y < 15; y++){
                MyWorld[x][y] = Tileset.NOTHING;
            }
        }
        fillWithTrees(MyWorld);
        ter.renderFrame(MyWorld);
    }
}