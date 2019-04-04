public class GameState {
    private int height;
    private int width;
    private int[][] board; //e.g. 9x9 2D array
    private String white;
    private String black;
    private User player1;
    private User player2;
    public int passCount; //make private later(?)
    private int turnNo;
    public String currentPlayerTurn; //make private later(?)
    
    public GameState(int k, User player1_, User player2_) { //player1 is player 1 user object
        height = k;
        width = k;
        board = new int[k][k]; //0 will be empty, 1 will be black and 2 will be white
        player1 = player1_;
        player2 = player2_;
        if (player1.getWinRate() > player2.getWinRate()) {
            white = player1.getUsername();
            black = player2.getUsername();
        }
        else if (player2.getWinRate() > player1.getWinRate()) {
            white = player2.getUsername();
            black = player1.getUsername();
        }
        else {
            white = player1.getUsername();
            black = player2.getUsername(); // can randomise this later
        }
        passCount = 0;
        turnNo = 0;
        currentPlayerTurn = black; //or directly enter username
    }
    
    //y is the first index, x is the second index, starting from (0,0) in the top left corner to (k-1, k-1)
    public void placePiece(int y, int x) {
        if (board[y][x] != 0) {
            System.out.println("someone's already here: x=" + x + ", y=" + y);
            return;
        }
        passCount = 0;
        if (currentPlayerTurn == white) board[y][x] = 2; //2 is white
        else board[y][x] = 1; //1 is black
        //TODO: auto-remove pieces if necessary as a result of the move here
        ++turnNo;
        if (turnNo % 2 == 0) currentPlayerTurn = black;
        else currentPlayerTurn = white;
        return;
    }
    
    public int[][] showBoard() {
        return board;
    }
    
    public void pass() {
        ++passCount;
        if (passCount == 3) {
            //TODO: end game somehow and score it and declare winner
            System.out.println("3 consecutive passes so game ends");
        }
        ++turnNo;
        if (turnNo % 2 == 0) currentPlayerTurn = black;
        else currentPlayerTurn = white;
        return;
    }
    
}