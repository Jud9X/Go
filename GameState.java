import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class GameState {
    private int[][] board; //e.g. 9x9 2D array
    private String white;
    private String black;
    private User player1;
    private User player2;
    private int passCount;
    private StringProperty passCountP;
    private int turnNo;
    private StringProperty turnNoP;
    private String currentPlayerTurn;
    private StringProperty currentPlayerTurnP;
    private int[][] previousBoard;
    private int[] captures; //captures[0] is number captured by black, captures[1] is by white
    private StringProperty capsBP;
    private StringProperty capsWP;
    private boolean finished;
    Score s;
    
    public GameState(int k, User player1, User player2) { //k is board size (e.g. height), player1 is a user object
        board = new int[k][k]; //0 in the array will mean empty, 1 will mean black and 2 will mean white
        this.player1 = player1;
        this.player2 = player2;
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
        passCountP = new SimpleStringProperty(""+passCount);
        turnNoP = new SimpleStringProperty(""+turnNo);
        currentPlayerTurnP = new SimpleStringProperty(currentPlayerTurn);
        capsBP = new SimpleStringProperty("0");
        capsWP = new SimpleStringProperty("0");
    }
    
    //y is the first index, x is the second index, starting from (0,0) in the top left corner to (k-1, k-1)
    public void placePiece(int y, int x) {
        int currentPlayerColour = (currentPlayerTurn == white) ? 2 : 1;
        int otherPlayerColour = (currentPlayerColour == 1) ? 2 : 1;
        if (GameLogic.moveIsIllegal(previousBoard, board, y, x, currentPlayerColour)) return;
        passCount = 0;
        passCountP.set(""+passCount);
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                previousBoard[i][j] = board[i][j];
            }
        }
        if (currentPlayerTurn == white) board[y][x] = 2; //2 is white
        else board[y][x] = 1; //1 is black
        board = GameLogic.updateBoard(board, y, x, currentPlayerColour);
        int otherPlayerCountPrevious = 0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (previousBoard[i][j] == otherPlayerColour) ++otherPlayerCountPrevious;
            }
        }
        int otherPlayerCountNew = 0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (board[i][j] == otherPlayerColour) ++otherPlayerCountNew;
            }
        }
        captures[currentPlayerColour-1] += otherPlayerCountPrevious - otherPlayerCountNew;
        capsBP.set(""+captures[0]);
        capsWP.set(""+captures[1]);
        ++turnNo;
        turnNoP.set(""+turnNo);
        if (turnNo % 2 == 0) currentPlayerTurn = black;
        else currentPlayerTurn = white;
        currentPlayerTurnP.set(currentPlayerTurn);
        return;
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public int[] getCaptures() {
        return captures;
    }
    
    public int getPassCount() {
        return passCount;
    }
    
    public String getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
    public String getBlack() {
        return black;
    }
    
    public String getWhite() {
        return white;
    }
    
    public int getTurnNo() {
        return turnNo;
    }
    
    public StringProperty getPassCountP() {
        return passCountP;
    }
    
    public StringProperty getTurnNoP() {
        return turnNoP;
    }
    
    public StringProperty getCurrentPlayerTurnP() {
        return currentPlayerTurnP;
    }
    
    public StringProperty getCapsBP() {
        return capsBP;
    }
    
    public StringProperty getCapsWP() {
        return capsWP;
    }
    
    public void pass() {
        ++passCount;
        passCountP.set(""+passCount);
        if (passCount == 2) {
            //System.out.println("2 consecutive passes so game ends");
            //popup with dialogue box saying need to click dead stones
            currentPlayerTurn = "none";
            currentPlayerTurnP.set(currentPlayerTurn);
            s = new Score(board);
            /*int[] deadStoneCoordinates = s.markDeadStones();
            //for (int i = 0; i < deadStoneCoordinates.length; ++i) System.out.println(deadStoneCoordinates[i]);
            int[][] finalBoard = s.removeDeadStones(board, deadStoneCoordinates);
            int[] territory = s.calculateTerritory(finalBoard);
            int[] scores = s.calculateFinalScores(territory, captures, deadStoneCoordinates[deadStoneCoordinates.length-2], deadStoneCoordinates[deadStoneCoordinates.length-1]);
            System.out.println("Black's final score: " + scores[0]);
            System.out.println("White's final score: " + scores[1]);
            board = finalBoard;*/
            finished = true;
        }
        ++turnNo;
        turnNoP.set(""+turnNo);
        if (turnNo % 2 == 0) currentPlayerTurn = black;
        else currentPlayerTurn = white;
        currentPlayerTurnP.set(currentPlayerTurn);
        return;
    }
    
    public void forfeitAndQuit() {
        //stuff
        return;
    }
}