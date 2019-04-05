import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameLogic {
    static Set<List<Integer>> visited = new HashSet<List<Integer>>();
    
    public static boolean moveIsIllegal(int[][] previousBoard_, int[][] board_, int y_, int x_, int height_, String CPT, String white_) { //replace height_ parameter with board_.length in function?
        //check move is legal: place is free?
        if (board_[y_][x_] != 0) {
            System.out.println("someone's already here: x=" + x_ + ", y=" + y_); //don't need this in the long-term
            return true;
        }
        //setup final proposed board for checking
        int[][] initialProposedBoard = new int[height_][height_];
        for (int i = 0; i < height_; ++i) {
            for (int j = 0; j < height_; ++j) {
                initialProposedBoard[i][j] = board_[i][j];
            }
        }
        if (CPT == white_) initialProposedBoard[y_][x_] = 2; //need str.equals() here and elsewhere?
        else initialProposedBoard[y_][x_] = 1;
        int[][] finalProposedBoard = updateBoard(initialProposedBoard, y_, x_, height_, CPT, white_);
        //check move is legal: there is at least 1 liberty around the new piece after captures accounted for
        if (getLiberties(y_, x_, height_, finalProposedBoard).length == 0) return true;
        //check move is legal: non-suicidal?
        //TODO
        //check move is legal: not a repeat of previous game board?
        boolean Same = true;
        for (int i = 0; i < height_; ++i) {
            for (int j = 0; j < height_; ++j) {
                if (finalProposedBoard[i][j] != previousBoard_[i][j]) Same = false;
            }
        }
        return Same;
    }
    
    public static int[][] updateBoard(int[][] board_, int y_, int x_, int height, String CPT, String white_) { //updates board as a result of a player CPT placing a piece at (x_, y_)
        int[] adjacentCoordinates = getAdjacentCoordinates(y_, x_, height);
        for (int i = 0; i < adjacentCoordinates.length-1; i += 2) {
            if (board_[adjacentCoordinates[i]][adjacentCoordinates[i+1]] == 2 && CPT != white_) {
                visited.clear();
                boolean capturing = willBeCaptured(board_, adjacentCoordinates[i], adjacentCoordinates[i+1], height, 2);
                if (capturing) {
                    while (visited.size() != 0) {
                        List<Integer> point = visited.iterator().next();
                        visited.remove(point);
                        int temp_y = point.get(0);
                        int temp_x = point.get(1);
                        board_[temp_y][temp_x] = 0;
                    }
                }
            }
            else if (board_[adjacentCoordinates[i]][adjacentCoordinates[i+1]] == 1 && CPT == white_) {
                visited.clear();
                boolean capturing = willBeCaptured(board_, adjacentCoordinates[i], adjacentCoordinates[i+1], height, 1);
                if (capturing) {
                    while (visited.size() != 0) {
                        List<Integer> point = visited.iterator().next();
                        visited.remove(point);
                        int temp_y = point.get(0);
                        int temp_x = point.get(1);
                        board_[temp_y][temp_x] = 0;
                    }
                }
            }
        }
        return board_;
    }
    
    //need to empty the hashset before each use...?
    public static boolean willBeCaptured(int[][] board_, int y_, int x_, int height, int colour) {
        visited.add(Arrays.asList(y_, x_));
        //int colour = board_[y][x];
        if (getLiberties(y_, x_, height, board_).length != 0) return false;
        int[] adjs = getAdjacentCoordinates(y_, x_, height);
        for (int i = 0; i < adjs.length-1; i += 2) {
            if (board_[adjs[i]][adjs[i+1]] == colour && !(visited.contains(Arrays.asList(i, i+1)))) {
                return willBeCaptured(board_, adjs[i], adjs[i+1], height, colour);
            }
        }
        return true;
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
        else { //y_ == height_-1
            int[] t = new int[6];
            t[0] = y-1; t[1] = x;
            t[2] = y;   t[3] = x+1;
            t[4] = y;   t[5] = x-1;
            return t;
        }
    }
    
    //function gets the coordinates of all existing liberties around an input coordinate pair
    //returns them as consecutive (y, x) pairs, e.g. first two elements in int[] being 3, 2 means y=3, x=2
    public static int[] getLiberties(int y, int x, int height, int[][] board_) {
        int[] adjacents = getAdjacentCoordinates(y, x, height);
        int libertyCount = 0;
        for (int i = 0; i < adjacents.length-1; i += 2) {
            if (board_[adjacents[i]][adjacents[i+1]] == 0) ++libertyCount;
        }
        int[] liberties = new int[2*libertyCount];
        int libertyIndex = 0;
        for (int i = 0; i < adjacents.length-1; i += 2) {
            if (board_[adjacents[i]][adjacents[i+1]] == 0) {
                liberties[libertyIndex] = adjacents[i];
                ++libertyIndex;
                liberties[libertyIndex] = adjacents[i+1];
                ++libertyIndex;
            }
        }
        return liberties;
    }
}