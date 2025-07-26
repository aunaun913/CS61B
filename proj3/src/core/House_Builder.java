package core;

import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.HashMap;
import java.util.Random;

public class House_Builder {
    public int xPos;
    public int yPos;
    public Random random;
    public long seed = 1000;
    public HashMap<Integer, Integer> posTracker;
    public HashMap<Integer, DIRECTION> dirTracker;
    public int[] sizeTracker;
    public House_Builder(){
        random = new Random(seed);
        xPos = RandomUtils.uniform(random, 5, 15);
        yPos = RandomUtils.uniform(random, 5, 15);
        posTracker = new HashMap<>();
        dirTracker = new HashMap<>();
        sizeTracker = new int[2];// index 0 -> former house_width, index 1 -> former house_height
        posTracker.put(0, xPos);
        posTracker.put(1, yPos);
        posTracker.put(2, xPos);
        posTracker.put(3, yPos);// key -> 0 is old_xPos, 1 is old_yPos, 2 is new_xPos, 3 is new_yPos
        dirTracker.put(0, null);
        dirTracker.put(1, null);// key -> 0 is old_dir, 1 is new_dir
    }
    public enum DIRECTION{
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }
    private DIRECTION choose_direction(){
        return switch (RandomUtils.uniform(random, 0, 4)) {
            case 0 -> DIRECTION.UP;
            case 1 -> DIRECTION.DOWN;
            case 2 -> DIRECTION.LEFT;
            case 3 -> DIRECTION.RIGHT;
            default -> null;
        };
    }

    private void saveDir(DIRECTION d){
        // put dir into dirTracker
        if (dirTracker.get(0) == null && dirTracker.get(1) == null){
            dirTracker.put(0, d);
        } else if (dirTracker.get(0) != null && dirTracker.get(1) == null) {
            dirTracker.put(1, d);
        }else {
            dirTracker.put(0, dirTracker.get(1));
            dirTracker.put(1, d);
        }
    }
    private boolean isClear(int xPos, int yPos, int distance, DIRECTION d, TETile[][] world){
        if (d == DIRECTION.UP){
            for (int x = xPos - 1; x <= xPos + 1; x++){
                for (int y = yPos + sizeTracker[1]/2 + 1 ; y <= yPos + distance + 2; y++){
                    if (world[x][y] != Tileset.NOTHING){
                        return false;
                    }
                }
            }
        } else if (d == DIRECTION.DOWN){
            for (int x = xPos - 1; x <= xPos + 1; x++){
                for (int y = yPos - sizeTracker[1]/2 - 1; y >= yPos - distance - 2; y--){
                    if (world[x][y] != Tileset.NOTHING){
                        return false;
                    }
                }
            }
        } else if (d == DIRECTION.LEFT){
            for (int y = yPos - 1; y <= yPos + 1; y++){
                for (int x = xPos - sizeTracker[0]/2 - 1; x >= xPos - distance - 2; x--){
                    if (world[x][y] != Tileset.NOTHING){
                        return false;
                    }
                }
            }
        }else if (d == DIRECTION.RIGHT){
            for (int y = yPos - 1; y <= yPos + 1; y++){
                for (int x = xPos + sizeTracker[0]/2 + 1; x <= xPos + distance + 2; x++){
                    if (world[x][y] != Tileset.NOTHING){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void move(TETile[][] world){
        DIRECTION d = choose_direction();
        int move_distance = RandomUtils.uniform(random, 7, 13);
        if (d == DIRECTION.UP){
            if (yPos + move_distance > World.GAME_HEIGHT - 7){
                move(world);
            } else if (!isClear(xPos, yPos, move_distance, d, world)) {
                move(world);
            } else {
                posTracker.put(0, posTracker.get(2));
                posTracker.put(1, posTracker.get(3));
                posTracker.put(3, posTracker.get(3) + move_distance);
                yPos = posTracker.get(3);
                saveDir(d);
            }
        } else if (d == DIRECTION.DOWN) {
            if (yPos - move_distance < 10){
                move(world);
            } else if (!isClear(xPos, yPos, move_distance, d, world)) {
                move(world);
            } else {
                posTracker.put(0, posTracker.get(2));
                posTracker.put(1, posTracker.get(3));
                posTracker.put(3, posTracker.get(3) - move_distance);
                yPos = posTracker.get(3);
                saveDir(d);
            }
        } else if (d == DIRECTION.LEFT){
            if (xPos - move_distance < 7){
                move(world);
            } else if (!isClear(xPos, yPos, move_distance, d, world)) {
                move(world);
            } else {
                posTracker.put(0, posTracker.get(2));
                posTracker.put(1, posTracker.get(3));
                posTracker.put(2, posTracker.get(2) - move_distance);
                xPos = posTracker.get(2);
                saveDir(d);
            }
        } else if (d == DIRECTION.RIGHT){
            if (xPos + move_distance > World.GAME_WIDTH - 7){
                move(world);
            } else if (!isClear(xPos, yPos, move_distance, d, world)) {
                move(world);
            } else {
                posTracker.put(0, posTracker.get(2));
                posTracker.put(1, posTracker.get(3));
                posTracker.put(2, posTracker.get(2) + move_distance);
                xPos = posTracker.get(2);
                saveDir(d);
            }
        }
    }
    private int random_size(){
        return RandomUtils.uniform(random, 7, 11);
    }
    private void buildEntrance(TETile[][] world, int xPos, int yPos, int house_width, int house_height){
        if (dirTracker.get(1) == null){
            switch (dirTracker.get(0)){
                case UP -> world[xPos][yPos + house_height / 2] = Tileset.FLOOR;
                case DOWN -> world[xPos][yPos - house_height / 2] = Tileset.FLOOR;
                case LEFT -> world[xPos - house_width / 2][yPos] = Tileset.FLOOR;
                case RIGHT -> world[xPos + house_width / 2][yPos] = Tileset.FLOOR;
            }
        }else {
            switch (anti_dir(dirTracker.get(0))){
                case UP -> world[xPos][yPos + house_height / 2] = Tileset.FLOOR;
                case DOWN -> world[xPos][yPos - house_height / 2] = Tileset.FLOOR;
                case LEFT -> world[xPos - house_width / 2][yPos] = Tileset.FLOOR;
                case RIGHT -> world[xPos + house_width / 2][yPos] = Tileset.FLOOR;
            }
            switch (dirTracker.get(1)){
                case UP -> world[xPos][yPos + house_height / 2] = Tileset.FLOOR;
                case DOWN -> world[xPos][yPos - house_height / 2] = Tileset.FLOOR;
                case LEFT -> world[xPos - house_width / 2][yPos] = Tileset.FLOOR;
                case RIGHT -> world[xPos + house_width / 2][yPos] = Tileset.FLOOR;
            }
        }
    }

    private DIRECTION anti_dir(DIRECTION d){
        DIRECTION result = null;
        if (d == DIRECTION.UP){
            result = DIRECTION.DOWN;
        }
        if (d == DIRECTION.DOWN){
            result = DIRECTION.UP;
        }
        if (d == DIRECTION.LEFT){
            result = DIRECTION.RIGHT;
        }
        if (d == DIRECTION.RIGHT){
            result = DIRECTION.LEFT;
        }
        return result;
    }

    public void House(TETile[][] world, int xPos, int yPos){
        int house_width = random_size();
        int house_height = random_size();
        sizeTracker[0] = house_width;
        sizeTracker[1] = house_height;
        for (int y = yPos - house_height / 2; y < yPos + house_height / 2; y++){
            world[xPos - house_width / 2][y] = Tileset.WALL;
            world[xPos + house_width / 2][y] = Tileset.WALL;
        }
        for (int x = xPos - house_width / 2; x < xPos + house_width / 2; x++){
            world[x][yPos - house_height / 2] = Tileset.WALL;
            world[x][yPos + house_height / 2] = Tileset.WALL;
        }
        for (int x = xPos - house_width / 2 + 1; x < xPos + house_width / 2; x++) {
            for (int y = yPos - house_height / 2 + 1; y < yPos + house_height / 2; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
        buildEntrance(world, xPos, yPos, house_width, house_height);
    }
}
