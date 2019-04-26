import java.util.ArrayList;

/**
 * Holds the current GameState object, the current GameGrid object, the current Score object.
 * Also holds an ArrayList of all games played, stored as GameRecord objects.
 * This allows easy access by all classes, since Main is not allowed to instantiate objects.
 * @author Oliver
 * @version 1.2
 * */
public class GameContainer {
    private static GameState g;
    private static GameGrid grid;
    private static Score s;
    private static ArrayList<GameRecord> gamesPlayed = new ArrayList<>();
    
    /**
     * Private, empty constructor to prevent GameContainer objects from being instantiated.
     * */
    private GameContainer() {
    }
    
    /**
     * Gets the current static GameState object.
     * @return GameState The current GameState object.
     * */
    public static GameState getG() {
        return g;
    }
    
    /**
     * Sets the current static GameState object.
     * @param gridSize The size of the grid.
     * @param player1 The first User object.
     * @param player2 The second User object.
     * */
    public static void setG(int gridSize, User player1, User player2) {
        g = new GameState(gridSize, player1, player2);
    }
    
    /**
     * Returns the current static GameGrid object.
     * @return GameGrid The current GameGrid object.
     * */
    public static GameGrid getGrid() {
        return grid;
    }
    
    /**
     * Sets the current static GameGrid object.
     * @param gridSize The size of the grid.
     * */
    public static void setGrid(int gridSize) {
        grid = new GameGrid(gridSize);
    }
    
    /**
     * Gets the current static Score object.
     * @return Score The Score object.
     * */
    public static Score getS() {
        return s;
    }
    
    /**
     * Instantiates the current static Score object.
     * @param board The ending state of the board for scoring.
     * */
    public static void setS(int[][] board) {
        s = new Score(board);
    }
    
    /**
     * Gets the record of all games played.
     * @return ArrayList<GameRecord> An ArrayList containing GameRecord objects of each game played.
     * */
    public static ArrayList<GameRecord> getGamesPlayed() {
        return gamesPlayed;
    }
}