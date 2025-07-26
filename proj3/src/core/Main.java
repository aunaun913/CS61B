package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;

import java.awt.*;

public class Main {
    private static final int WINDOW_WIDTH = 80;
    private static final int WINDOW_HEIGHT = 50;

    public static StringBuilder sb;
    public static void main(String[] args) {
        long seed;
        TERenderer ter = new TERenderer();
        sb = new StringBuilder();
        ter.initialize(WINDOW_WIDTH , WINDOW_HEIGHT);
        ter.resetFont();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        while (true){
            StdDraw.text(40, 45, "CS61B: BYOW");
            StdDraw.text(40, 39, "(N) New Game");
            StdDraw.text(40, 33, "(L) Load Game");
            StdDraw.text(40, 27, "You can press 'Q' to quit game when gaming.");
            StdDraw.text(40, 21, "You can press 'M' to menu when gaming.");
            StdDraw.text(40, 15, "You can press 'F' to save current status when gaming.");
            StdDraw.text(40, 9, "Do not walk on water or you could drown!");
            StdDraw.show(); // menu
            if (StdDraw.hasNextKeyTyped()){
                switch (StdDraw.nextKeyTyped()){
                    case 'N':
                    case 'n':
                        StdDraw.clear(Color.BLACK);
                        while (true){
                            StdDraw.text(40, 30, "Enter seed followed by pressing 'S'.");
                            StdDraw.show();
                            if (StdDraw.hasNextKeyTyped()){
                                char key = StdDraw.nextKeyTyped();
                                switch (key){
                                    case 'S':
                                    case 's':
                                    case '\n':
                                        StdDraw.clear(Color.BLACK);
                                        if (sb.isEmpty()){
                                            seed = 0;
                                        }else {
                                            seed = Integer.parseInt(sb.toString());
                                        }
                                        World world = new World(seed);
                                        Game game = new Game(world);
                                        game.game_loop(ter, false);
                                    default:
                                        sb.append(key);
                                        StdDraw.clear(Color.BLACK);
                                        StdDraw.text(40, 30, "Enter seed followed by pressing 'S'.");
                                        StdDraw.text(40, 25, sb.toString());
                                        StdDraw.show();
                                        break;
                                }
                            }
                        }
                    case 'q':
                    case 'Q':
                        System.exit(0);
                        break;
                    case 'L':
                    case 'l':
                        In in = new In("game_status.txt");
                        String loaded_seed = in.readLine();
                        World loaded_world = new World(Integer.parseInt(loaded_seed));
                        Game loaded_game = new Game(loaded_world);
                        loaded_game.game_loop(ter, true);
                        break;
                }
            }
        }
    }
}
