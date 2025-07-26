package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Game {
    public World world_class;
    public HashMap<Integer, Integer> avatarPosTracker;
    public TETile[][] Game_World;
    private int flower_cnt;
    private int Gamer_cnt;
    private int aim;
    private int water_cnt;
    private StringBuilder saver;
    public Game(World world){
        world_class = world;
        water_cnt = 0;
        avatarPosTracker = new HashMap<>();
        avatarPosTracker.put(0, 0); // key 0 is xPos
        avatarPosTracker.put(1, 0); // ket 1 is yPos
        saver = new StringBuilder();
    }

    private int random_xPos(){
        return RandomUtils.uniform(world_class.random, 10, 70);
    }
    private int random_yPos(){
        return RandomUtils.uniform(world_class.random, 10, 40);
    }
    private int random_flower_cnt(){
        return RandomUtils.uniform(world_class.random, 4, 8);
    }
    private void spawn_avatar(){
        int xPos = random_xPos();
        int yPos = random_yPos();
        if (Game_World[xPos][yPos] == Tileset.FLOOR) {
            Game_World[xPos][yPos] = Tileset.AVATAR;
            avatarPosTracker.put(0, xPos);
            avatarPosTracker.put(1, yPos);
        } else {
            spawn_avatar();
        }
    }
    private void isGameOver(char key){
        switch (key){
            case 'w':
            case 'W':
                if (Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) + 1] == Tileset.NOTHING && aim == Gamer_cnt){
                    final_page();
                }
                break;
            case 's':
            case 'S':
                if (Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) - 1] == Tileset.NOTHING && aim == Gamer_cnt){
                    final_page();
                }
                break;
            case 'a':
            case 'A':
                if (Game_World[avatarPosTracker.get(0) - 1][avatarPosTracker.get(1)] == Tileset.NOTHING && aim == Gamer_cnt){
                    final_page();
                }
                break;
            case 'd':
            case 'D':
                if (Game_World[avatarPosTracker.get(0) + 1][avatarPosTracker.get(1)] == Tileset.NOTHING && aim == Gamer_cnt){
                    final_page();
                }
                break;
        }
        if (water_cnt != 0){
            fail_page();
        }
    }
    private void spawn_flower(){
        int flower_xPos = random_xPos();
        int flower_yPos = random_yPos();
        if (Game_World[flower_xPos][flower_yPos] == Tileset.FLOOR) {
            Game_World[flower_xPos][flower_yPos] = Tileset.FLOWER;
            flower_cnt--;
        }else {
            spawn_flower();
        }
        if (flower_cnt != 0){
            spawn_flower();
        }
    }

    private void addRandomWaterAreas(TETile[][] world, int count) {
        Random random = world_class.random;
        int placed = 0;
        int maxAttempts = 1000; // 避免死循环
        int attempts = 0;

        while (placed < count && attempts < maxAttempts) {
            int x = random.nextInt(world.length);
            int y = random.nextInt(world[0].length);

            if (world[x][y] == Tileset.FLOOR && isValidWaterPosition(world, x, y)) {
                world[x][y] = Tileset.WATER;
                placed++;
            }
            attempts++;
        }
    }

    private boolean isValidWaterPosition(TETile[][] world, int x, int y) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx < 0 || ny < 0 || nx >= world.length || ny >= world[0].length) {
                    return false; // 越界不行
                }
                if (world[nx][ny] == Tileset.WALL) {
                    return false;
                }
            }
        }
        return true;
    }
    private void final_page(){
        while (true){
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            StdDraw.text(40, 35, "Congrats! You've collected all the flowers");
            StdDraw.text(40, 30, "I hope to become an AI engineer, but the road ahead is full of obstacles and uncertainties.");
            StdDraw.text(40, 25, "I wish I can forge my way through and eventually find a fulfilling job.");
            StdDraw.text(40, 20, "If there is a second person who happens to read these words,");
            StdDraw.text(40, 15, "I wish you good health and great success in your career.");
            StdDraw.text(40, 10, "Press 'R' to restart");
            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                switch (key){
                    case 'r':
                    case 'R':
                        Main.main(new String[]{});
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private void fail_page(){
        while (true){
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 30));
            StdDraw.text(40, 25, "You fall into water, press 'R' to restart");
            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                switch (key){
                    case 'r':
                    case 'R':
                        Main.main(new String[]{});
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public void processKey(char key, TERenderer ter) {
        switch (key) {
            case 'm':
            case 'M':
                Main.main(new String[]{});
                break;

            case 'w':
            case 'W':
                if (Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) + 1] != Tileset.WALL) {
                    if (Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) + 1] == Tileset.FLOWER) {
                        Gamer_cnt++;
                    }
                    if (Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) + 1] == Tileset.WATER) {
                        water_cnt++;
                    }
                    isGameOver(key);
                    Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) + 1] = Tileset.AVATAR;
                    Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1)] = Tileset.FLOOR;
                    avatarPosTracker.put(1, avatarPosTracker.get(1) + 1);
                    redrawWorld(ter);
                }
                break;

            case 's':
            case 'S':
                if (Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) - 1] != Tileset.WALL) {
                    if (Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) - 1] == Tileset.FLOWER) {
                        Gamer_cnt++;
                    }
                    if (Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) - 1] == Tileset.WATER) {
                        water_cnt++;
                    }
                    isGameOver(key);
                    Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1) - 1] = Tileset.AVATAR;
                    Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1)] = Tileset.FLOOR;
                    avatarPosTracker.put(1, avatarPosTracker.get(1) - 1);
                    redrawWorld(ter);
                }
                break;

            case 'a':
            case 'A':
                if (Game_World[avatarPosTracker.get(0) - 1][avatarPosTracker.get(1)] != Tileset.WALL) {
                    if (Game_World[avatarPosTracker.get(0) - 1][avatarPosTracker.get(1)] == Tileset.FLOWER) {
                        Gamer_cnt++;
                    }
                    if (Game_World[avatarPosTracker.get(0) - 1][avatarPosTracker.get(1)] == Tileset.WATER) {
                        water_cnt++;
                    }
                    isGameOver(key);
                    Game_World[avatarPosTracker.get(0) - 1][avatarPosTracker.get(1)] = Tileset.AVATAR;
                    Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1)] = Tileset.FLOOR;
                    avatarPosTracker.put(0, avatarPosTracker.get(0) - 1);
                    redrawWorld(ter);
                }
                break;

            case 'd':
            case 'D':
                if (Game_World[avatarPosTracker.get(0) + 1][avatarPosTracker.get(1)] != Tileset.WALL) {
                    if (Game_World[avatarPosTracker.get(0) + 1][avatarPosTracker.get(1)] == Tileset.FLOWER) {
                        Gamer_cnt++;
                    }
                    if (Game_World[avatarPosTracker.get(0) + 1][avatarPosTracker.get(1)] == Tileset.WATER) {
                        water_cnt++;
                    }
                    isGameOver(key);
                    Game_World[avatarPosTracker.get(0) + 1][avatarPosTracker.get(1)] = Tileset.AVATAR;
                    Game_World[avatarPosTracker.get(0)][avatarPosTracker.get(1)] = Tileset.FLOOR;
                    avatarPosTracker.put(0, avatarPosTracker.get(0) + 1);
                    redrawWorld(ter);
                }
                break;

            case 'q':
            case 'Q':
                System.exit(0);
                break;

            case 'f':
            case 'F':
                Out out = new Out("game_status.txt");
                out.println(world_class.mySeed);
                out.print(saver.toString());
                out.close();
                break;

            default:
                break;
        }
    }

    private void redrawWorld(TERenderer ter) {
        StdDraw.clear(Color.BLACK);
        ter.drawTiles(Game_World);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        StdDraw.text(10, 47, "Collected Flower: " + Gamer_cnt);
        StdDraw.show();
    }

    public void game_loop(TERenderer ter, boolean isloaded){
        flower_cnt = random_flower_cnt();
        aim = flower_cnt;
        Gamer_cnt = 0;
        Game_World = world_class.world_setup();
        spawn_avatar();
        spawn_flower();
        addRandomWaterAreas(Game_World, 50);
        if (isloaded){
            In in2 = new In("game_status.txt");
            in2.readLine();
            String operation = in2.readLine();
            for (char key : operation.toCharArray()){
                saver.append(key);
                processKey(key, ter);
            }
        }
        ter.drawTiles(Game_World);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        StdDraw.text(10, 47, "Collected Flower: " + Gamer_cnt);
        StdDraw.show();
        while (true){
            if (StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                saver.append(key);
                processKey(key, ter);
            }
        }
    }
}
