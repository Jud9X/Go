public class GameLogic {
    public static boolean moveIsIllegal(int[][] previousBoard_, int[][] board_, int y_, int x_, int height_, String CPT, String white_) {
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
        if (CPT == white_) initialProposedBoard[y_][x_] = 2;
        else initialProposedBoard[y_][x_] = 1;
        int[][] finalProposedBoard = updateBoard(initialProposedBoard, y_, x_);
        //check move is legal: there is at least 1 liberty around the new piece after captures accounted for
        boolean atLeastOneLiberty = false;
        //1. interior point case
        if (x_ > 0 && x_ < height_-1 && y_ > 0 && y_ < height_-1) {
            if (finalProposedBoard[y_][x_+1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_][x_-1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_+1][x_] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_-1][x_] == 0) atLeastOneLiberty = true;
        }
        //2. corner point cases
        else if (x_ == 0 && y_ == 0) {
            if (finalProposedBoard[y_][x_+1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_+1][x_] == 0) atLeastOneLiberty = true;
        }
        else if (x_ == 0 && y_ == height_-1) {
            if (finalProposedBoard[y_][x_+1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_-1][x_] == 0) atLeastOneLiberty = true;
        }
        else if (x_ == height_-1 && y_ == 0) {
            if (finalProposedBoard[y_][x_-1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_+1][x_] == 0) atLeastOneLiberty = true;
        }
        else if (x_ == height_-1 && y_ == height_-1) {
            if (finalProposedBoard[y_][x_-1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_-1][x_] == 0) atLeastOneLiberty = true;
        }
        //3. edge point cases
        else if (x_ == 0) {
            if (finalProposedBoard[y_][x_+1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_+1][x_] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_-1][x_] == 0) atLeastOneLiberty = true;
        }
        else if (x_ == height_-1) {
            if (finalProposedBoard[y_][x_-1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_+1][x_] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_-1][x_] == 0) atLeastOneLiberty = true;
        }
        else if (y_ == 0) {
            if (finalProposedBoard[y_][x_+1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_][x_-1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_+1][x_] == 0) atLeastOneLiberty = true;
        }
        else { //y_ == height_-1
            if (finalProposedBoard[y_][x_+1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_][x_-1] == 0) atLeastOneLiberty = true;
            if (finalProposedBoard[y_-1][x_] == 0) atLeastOneLiberty = true;
        }
        if (!atLeastOneLiberty) return true;
        //check move is legal: non-suicidal?
        //TODO
        //check move is legal: not a repeat of previous game board?
        boolean Same = true;
        for (int i = 0; i < height_; ++i) {
            for (int j = 0; j < height_; ++j) {
                if (finalProposedBoard[i][j] != previousBoard_[i][j]) Same = false;
            }
        }
        return Same; //this is becoming a very long method...
    }
    
    public static int[][] updateBoard(int[][] board_, int y_, int x_) { //updates board as a result of a player placing a piece at (x_, y_)
        //TODO: implement main game logic
        return board_; //just for testing
    }
}