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
    private int[][] previousBoard;
    private int[] captures; //captures[0] is number captured by black, captures[1] is by white
    public boolean finished; //make private later(?)
    
    public GameState(int k, User player1_, User player2_) { //player1_ is a user object
        height = k;
        width = k;
        board = new int[k][k]; //0 will mean empty, 1 will mean black and 2 will mean white
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
            black = player2.getUsername(); //can randomise this later
        }
        passCount = 0;
        turnNo = 0;
        currentPlayerTurn = black; //or directly enter username
        previousBoard = new int[k][k];
        captures = new int[2];
        finished = false;
    }
    
    //y is the first index, x is the second index, starting from (0,0) in the top left corner to (k-1, k-1)
    public void placePiece(int y, int x) {
        if (GameLogic.moveIsIllegal(previousBoard, board, y, x, height, currentPlayerTurn, white)) return;
        passCount = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < height; ++j) {
                previousBoard[i][j] = board[i][j];
            }
        }
        if (currentPlayerTurn == white) board[y][x] = 2; //2 is white
        else board[y][x] = 1; //1 is black
        int currentPlayerColour = board[y][x]; //could use this in the following functions to make them simpler?
        int otherPlayerColour = (currentPlayerColour == 1) ? 2 : 1;
        board = GameLogic.updateBoard(board, y, x, height, currentPlayerTurn, white);
        int otherPlayerCountPrevious = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < height; ++j) {
                if (previousBoard[i][j] == otherPlayerColour) ++otherPlayerCountPrevious;
            }
        }
        int otherPlayerCountNew = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < height; ++j) {
                if (board[i][j] == otherPlayerColour) ++otherPlayerCountNew;
            }
        }
        captures[currentPlayerColour-1] += otherPlayerCountPrevious - otherPlayerCountNew;
        System.out.println("Captured by black: " + captures[0]);
        System.out.println("Captured by white: " + captures[1]);
        ++turnNo;
        if (turnNo % 2 == 0) currentPlayerTurn = black;
        else currentPlayerTurn = white;
        return;
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public int[] getCaptures() {
        return captures;
    }
    
    public void pass() {
        ++passCount;
        if (passCount == 3) {
            System.out.println("3 consecutive passes so game ends");
            int[] deadStoneCoordinates = Score.markDeadStones(board);
            //for (int i = 0; i < deadStoneCoordinates.length; ++i) System.out.println(deadStoneCoordinates[i]);
            int[][] finalBoard = Score.removeDeadStones(board, deadStoneCoordinates);
            int[] territory = Score.calculateTerritory(finalBoard);
            int[] scores = Score.calculateFinalScores(territory, captures, deadStoneCoordinates[deadStoneCoordinates.length-2], deadStoneCoordinates[deadStoneCoordinates.length-1]);
            System.out.println("Black's final score: " + scores[0]);
            System.out.println("White's final score: " + scores[1]);
            board = finalBoard;
            finished = true;
        }
        ++turnNo;
        if (turnNo % 2 == 0) currentPlayerTurn = black;
        else currentPlayerTurn = white;
        return;
    }
    
}