import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameLogic {
    static Set<List<Integer>> visited = new HashSet<List<Integer>>();
    
    public static boolean moveIsIllegal(int[][] previousBoard, int[][] board, int y, int x, int currentPlayerColour) {
        //check move is legal: place is free?
        if (board[y][x] != 0) {
            System.out.println("someone's already here: x=" + x + ", y=" + y); //don't need this in the long-term?
            return true;
        }
        //setup final proposed board for checking
        int[][] initialProposedBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                initialProposedBoard[i][j] = board[i][j];
            }
        }
        if (currentPlayerColour == 2) initialProposedBoard[y][x] = 2; //need str.equals() here and elsewhere?
        else initialProposedBoard[y][x] = 1;
        int[][] finalProposedBoard = updateBoard(initialProposedBoard, y, x, currentPlayerColour);
        //check move is legal: non-suicidal
        visited.clear();
        if (willBeCaptured(finalProposedBoard, y, x, currentPlayerColour)) {
            System.out.println("suicide is forbidden");
            return true;
        }
        //check move is legal: there is at least 1 liberty around the new piece after captures accounted for
        if (getLiberties(y, x, finalProposedBoard).length == 0) {
            System.out.println("no liberties around your piece here");
            return true;
        }
        //check move is legal: not a repeat of previous game board
        boolean Same = true;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (finalProposedBoard[i][j] != previousBoard[i][j]) Same = false;
            }
        }
        if (Same) System.out.println("no ko, sorry");
        return Same;
    }
    
    public static int[][] updateBoard(int[][] board, int y, int x, int currentPlayerColour) { //updates board as a result of current player placing a piece at (x, y)
        int[] adjacentCoordinates = getAdjacentCoordinates(y, x, board.length);
        for (int i = 0; i < adjacentCoordinates.length-1; i += 2) {
            if (board[adjacentCoordinates[i]][adjacentCoordinates[i+1]] == 2 && currentPlayerColour != 2) {
                visited.clear();
                boolean capturing = willBeCaptured(board, adjacentCoordinates[i], adjacentCoordinates[i+1], 2);
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
            else if (board[adjacentCoordinates[i]][adjacentCoordinates[i+1]] == 1 && currentPlayerColour == 2) {
                visited.clear();
                boolean capturing = willBeCaptured(board, adjacentCoordinates[i], adjacentCoordinates[i+1], 1);
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
    
    //need to empty the hashset before each use...?
    private static boolean willBeCaptured(int[][] board, int y, int x, int colour) {
        visited.add(Arrays.asList(y, x));
        if (getLiberties(y, x, board).length != 0) return false;
        int[] adjs = getAdjacentCoordinates(y, x, board.length);
        boolean willBeCaptured = true;
        for (int i = 0; i < adjs.length-1; i += 2) {
            if (board[adjs[i]][adjs[i+1]] == colour && !(visited.contains(Arrays.asList(adjs[i], adjs[i+1])))) {
                willBeCaptured = willBeCaptured(board, adjs[i], adjs[i+1], colour);
            }
        }
        return willBeCaptured;
    }
    
    //function gets the coordinates of all existing adjacent places (NESW) to an input coordinate pair
    //returns them as consecutive (y, x) pairs, e.g. first two elements in int[] being 3, 2 means y=3, x=2
    public static int[] getAdjacentCoordinates(int y, int x, int height) {
        //1. interior point case
        if (x > 0 && x < height-1 && y > 0 && y < height-1) {
            int[] t = new int[8];
            t[0] = y+1; t[1] = x;
            t[2] = y;   t[3] = x+1;
            t[4] = y-1; t[5] = x;
            t[6] = y;   t[7] = x-1;
            return t;
        }
        //2. corner point cases
        else if (x == 0 && y == 0) {
            int[] t = new int[4];
            t[0] = y;   t[1] = x+1;
            t[2] = y+1; t[3] = x;
            return t;
        }
        else if (x == 0 && y == height-1) {
            int[] t = new int[4];
            t[0] = y-1; t[1] = x;
            t[2] = y;   t[3] = x+1;
            return t;
        }
        else if (x == height-1 && y == 0) {
            int[] t = new int[4];
            t[0] = y+1; t[1] = x;
            t[2] = y;   t[3] = x-1;
            return t;
        }
        else if (x == height-1 && y == height-1) {
            int[] t = new int[4];
            t[0] = y;   t[1] = x-1;
            t[2] = y-1; t[3] = x;
            return t;
        }
        //3. edge point cases
        else if (x == 0) {
            int[] t = new int[6];
            t[0] = y+1; t[1] = x;
            t[2] = y;   t[3] = x+1;
            t[4] = y-1; t[5] = x;
            return t;
        }
        else if (x == height-1) {
            int[] t = new int[6];
            t[0] = y+1; t[1] = x;
            t[2] = y-1; t[3] = x;
            t[4] = y;   t[5] = x-1;
            return t;
        }
        else if (y == 0) {
            int[] t = new int[6];
            t[0] = y;   t[1] = x+1;
            t[2] = y+1; t[3] = x;
            t[4] = y;   t[5] = x-1;
            return t;
        }
        else { //y == height-1
            int[] t = new int[6];
            t[0] = y-1; t[1] = x;
            t[2] = y;   t[3] = x+1;
            t[4] = y;   t[5] = x-1;
            return t;
        }
    }
    
    //function gets the coordinates of all existing liberties around an input coordinate pair
    //returns them as consecutive (y, x) pairs, e.g. first two elements in int[] being 3, 2 means y=3, x=2
    private static int[] getLiberties(int y, int x, int[][] board) {
        int[] adjacents = getAdjacentCoordinates(y, x, board.length);
        int libertyCount = 0;
        for (int i = 0; i < adjacents.length-1; i += 2) {
            if (board[adjacents[i]][adjacents[i+1]] == 0) ++libertyCount;
        }
        int[] liberties = new int[2*libertyCount];
        int libertyIndex = 0;
        for (int i = 0; i < adjacents.length-1; i += 2) {
            if (board[adjacents[i]][adjacents[i+1]] == 0) {
                liberties[libertyIndex] = adjacents[i];
                ++libertyIndex;
                liberties[libertyIndex] = adjacents[i+1];
                ++libertyIndex;
            }
        }
        return liberties;
    }
}