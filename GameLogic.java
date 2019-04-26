import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Only contains static variables and functions that contain the logic of the game. Cannot be instantiated.
 * The coordinates take the top left hand corner of the board as (0, 0).
 * @author Oliver
 * @version 1.6
 * */
public class GameLogic {
    private static Set<List<Integer>> visited = new HashSet<List<Integer>>();
    private static boolean willBeCaptured = true;
    
    /**
     * Private, empty constructor to prevent GameContainer objects from being instantiated.
     * */
    private GameLogic() {
    }
    
    /**
     * Checks to see if the desired move is illegal.
     * @param previousBoard The state of the board in the previous turn (for checking "ko").
     * @param board The current state of the board.
     * @param y The y coordinate of the desired new piece.
     * @param x The x coordinate of the desired new piece.
     * @param currentPlayerColour The current player's colour as an integer.
     * @return A boolean value which is true if the move is illegal and false otherwise.
     * */
    public static boolean moveIsIllegal(int[][] previousBoard, int[][] board, int y, int x, int currentPlayerColour) {
        //check move is legal: place is free?
        if (board[y][x] != 0) {
            InformationBox.display("Illegal Move", "There is already a piece on this intersection, please choose a different one or click 'Pass'.");
            return true;
        }
        //setup final proposed board for checking
        int[][] initialProposedBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                initialProposedBoard[i][j] = board[i][j];
            }
        }
        if (currentPlayerColour == GameContainer.getG().WHITE) initialProposedBoard[y][x] = GameContainer.getG().WHITE;
        else initialProposedBoard[y][x] = GameContainer.getG().BLACK;
        int[][] finalProposedBoard = updateBoard(initialProposedBoard, y, x, currentPlayerColour);
        //check move is legal: non-suicidal
        visited.clear();
        willBeCaptured = true;
        if (willBeCaptured(finalProposedBoard, y, x, currentPlayerColour)) {
            InformationBox.display("Illegal Move", "Suicide is forbidden, please choose a different intersection or click 'Pass'.");
            return true;
        }
        //check move is legal: there is at least 1 liberty around the new piece after captures accounted for
        if (getLiberties(y, x, finalProposedBoard).length == 0) {
            InformationBox.display("Illegal Move", "There are no liberties around this intersection, please choose a different one or click 'Pass'.");
            return true;
        }
        //check move is legal: not a repeat of previous game board
        boolean Same = true;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (finalProposedBoard[i][j] != previousBoard[i][j]) {
                    Same = false;
                }
            }
        }
        if (Same) {
            InformationBox.display("Illegal Move", "Repeat board states are forbidden (ko), please choose a different intersection or click 'Pass'.");
        }
        return Same;
    }
    
    /**
     * Updates the board.
     * @param board The current state of the board.
     * @param y The y coordinate of the desired new piece.
     * @param x The x coordinate of the desired new piece.
     * @param currentPlayerColour The current player's colour as an integer.
     * @return A 2D int array which is the new board after the move is made.
     * */
    public static int[][] updateBoard(int[][] board, int y, int x, int currentPlayerColour) { //updates board as a result of current player placing a piece at (x, y)
        int[] adjacentCoordinates = getAdjacentCoordinates(y, x, board.length);
        for (int i = 0; i < adjacentCoordinates.length - 1; i += 2) {
            if (board[adjacentCoordinates[i]][adjacentCoordinates[i + 1]] == GameContainer.getG().WHITE && currentPlayerColour != GameContainer.getG().WHITE) {
                visited.clear();
                willBeCaptured = true;
                boolean capturing = willBeCaptured(board, adjacentCoordinates[i], adjacentCoordinates[i + 1], GameContainer.getG().WHITE);
                if (capturing) {
                    while (visited.size() != 0) {
                        List<Integer> point = visited.iterator().next();
                        visited.remove(point);
                        int temp_y = point.get(0);
                        int temp_x = point.get(1);
                        board[temp_y][temp_x] = 0;
                    }
                }
            }
            else if (board[adjacentCoordinates[i]][adjacentCoordinates[i + 1]] == GameContainer.getG().BLACK && currentPlayerColour == GameContainer.getG().WHITE) {
                visited.clear();
                willBeCaptured = true;
                boolean capturing = willBeCaptured(board, adjacentCoordinates[i], adjacentCoordinates[i + 1], GameContainer.getG().BLACK);
                if (capturing) {
                    while (visited.size() != 0) {
                        List<Integer> point = visited.iterator().next();
                        visited.remove(point);
                        int temp_y = point.get(0);
                        int temp_x = point.get(1);
                        board[temp_y][temp_x] = 0;
                    }
                }
            }
        }
        return board;
    }
    
    /**
     * Checks to see if the given piece will be captured.
     * @param board The current state of the board.
     * @param y The y coordinate of the piece to be checked.
     * @param x The x coordinate of the piece to be checked.
     * @param colour The colour of the piece to be checked.
     * @return A boolean value which is true if the piece will be captured, false otherwise.
     * */
    private static boolean willBeCaptured(int[][] board, int y, int x, int colour) {
        visited.add(Arrays.asList(y, x));
        if (getLiberties(y, x, board).length != 0) {
            return false;
        }
        int[] adjs = getAdjacentCoordinates(y, x, board.length);
        for (int i = 0; i < adjs.length - 1; i += 2) {
            if (willBeCaptured && board[adjs[i]][adjs[i + 1]] == colour && !(visited.contains(Arrays.asList(adjs[i], adjs[i + 1])))) {
                willBeCaptured = willBeCaptured(board, adjs[i], adjs[i + 1], colour);
            }
        }
        return willBeCaptured;
    }
    
    /**
     * Gets the coordinates of all existing adjacent places (NESW) to an input coordinate pair.
     * @param y The y coordinate of the input location.
     * @param x The x coordinate of the input location.
     * @param height The height of the board.
     * @return An int[] of all adjacent coordinates as consecutive (y, x) pairs.
     * */
    public static int[] getAdjacentCoordinates(int y, int x, int height) {
        //1. interior point case
        if (x > 0 && x < height - 1 && y > 0 && y < height - 1) {
            int[] t = new int[8];
            t[0] = y - 1; t[1] = x;
            t[2] = y;     t[3] = x + 1;
            t[4] = y + 1; t[5] = x;
            t[6] = y;     t[7] = x - 1;
            return t;
        }
        //2. corner point cases
        else if (x == 0 && y == 0) {
            int[] t = new int[4];
            t[0] = y;     t[1] = x + 1;
            t[2] = y + 1; t[3] = x;
            return t;
        }
        else if (x == 0 && y == height - 1) {
            int[] t = new int[4];
            t[0] = y - 1; t[1] = x;
            t[2] = y;     t[3] = x + 1;
            return t;
        }
        else if (x == height - 1 && y == 0) {
            int[] t = new int[4];
            t[0] = y + 1; t[1] = x;
            t[2] = y;     t[3] = x - 1;
            return t;
        }
        else if (x == height - 1 && y == height - 1) {
            int[] t = new int[4];
            t[0] = y;     t[1] = x - 1;
            t[2] = y - 1; t[3] = x;
            return t;
        }
        //3. edge point cases
        else if (x == 0) {
            int[] t = new int[6];
            t[0] = y - 1; t[1] = x;
            t[2] = y;     t[3] = x + 1;
            t[4] = y + 1; t[5] = x;
            return t;
        }
        else if (x == height - 1) {
            int[] t = new int[6];
            t[0] = y - 1; t[1] = x;
            t[2] = y + 1; t[3] = x;
            t[4] = y;     t[5] = x - 1;
            return t;
        }
        else if (y == 0) {
            int[] t = new int[6];
            t[0] = y;     t[1] = x + 1;
            t[2] = y + 1; t[3] = x;
            t[4] = y;     t[5] = x - 1;
            return t;
        }
        else { //y == height - 1
            int[] t = new int[6];
            t[0] = y - 1; t[1] = x;
            t[2] = y;     t[3] = x + 1;
            t[4] = y;     t[5] = x - 1;
            return t;
        }
    }
    
    /**
     * Gets the coordinates of all existing liberties around an input coordinate pair.
     * @param y The y coordinate of the input location.
     * @param x The x coordinate of the input location.
     * @param board The current state of the board.
     * @return An int[] of all liberties' coordinates as consecutive (y, x) pairs.
     * */
    private static int[] getLiberties(int y, int x, int[][] board) {
        int[] adjacents = getAdjacentCoordinates(y, x, board.length);
        int libertyCount = 0;
        for (int i = 0; i < adjacents.length - 1; i += 2) {
            if (board[adjacents[i]][adjacents[i + 1]] == 0) {
                ++libertyCount;
            }
        }
        int[] liberties = new int[2*libertyCount];
        int libertyIndex = 0;
        for (int i = 0; i < adjacents.length - 1; i += 2) {
            if (board[adjacents[i]][adjacents[i + 1]] == 0) {
                liberties[libertyIndex] = adjacents[i];
                ++libertyIndex;
                liberties[libertyIndex] = adjacents[i + 1];
                ++libertyIndex;
            }
        }
        return liberties;
    }
}