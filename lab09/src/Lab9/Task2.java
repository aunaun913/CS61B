package Lab9;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.Random;

/**
 * Draws a world initially full of trees.
 */
public class Task2 {
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    private static final long SEED = 12312;
    private static final Random RANDOM = new Random(SEED);
    private static void fillWithTrees(TETile[][] world) {
        int w = world.length;
        int h = world[0].length;
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                world[x][y] = Tileset.TREE;
            }
        }
    }
    private static TETile randomTile(){
        int tileNum = RANDOM.nextInt(3);
        return switch (tileNum){
            case 0 -> Tileset.FLOWER;
            case 1 -> Tileset.WALL;
            default -> Tileset.WATER;
        };
    }
    private static void drawSquare(TETile[][] world, int startX, int startY, int size, TETile tile){
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                int targetX = startX - x;
                int targetY = startY - y;
                if (targetX >= 0 && targetY >= 0){
                    world[targetX][targetY] = tile;
                }
            }
        }
    }

    private static void addRandomSquare(TETile[][] world, Random rand){
        int size = RandomUtils.uniform(rand, 3, 8);
        int randomStartX = RandomUtils.uniform(rand, 0, world.length);
        int randomStartY = RandomUtils.uniform(rand, 0, world[0].length);
        TETile tile = randomTile();
        drawSquare(world, randomStartX, randomStartY, size, tile);
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
        for (int i = 0; i < 5; i++){
            addRandomSquare(MyWorld, RANDOM);
        }
        ter.renderFrame(MyWorld);
    }
}