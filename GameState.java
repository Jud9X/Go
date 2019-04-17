import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Contains the current state of the game.
 * @author Oliver
 * @version 1.14
 * */
public class GameState {
    public final int BLACK = 1;
    public final int WHITE = 2;
    public final int PASS_LIMIT = 2;
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
    private int[][] previousBoard2;
    private int[] captures; //captures[0] is number captured by black, captures[1] is by white
    private StringProperty capsBP;
    private StringProperty capsWP;
    private boolean finished;
    private BooleanProperty undoStateP;
    private BooleanProperty ready;
    Score s; //make private
    
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
        previousBoard2 = new int[k][k];
        captures = new int[2];
        finished = false;
        passCountP = new SimpleStringProperty("" + passCount);
        turnNoP = new SimpleStringProperty("" + turnNo);
        currentPlayerTurnP = new SimpleStringProperty(currentPlayerTurn);
        capsBP = new SimpleStringProperty("0");
        capsWP = new SimpleStringProperty("0");
        undoStateP = new SimpleBooleanProperty(true);
        ready = new SimpleBooleanProperty(false);
    }
    
    //y is the first index, x is the second index, starting from (0,0) in the top left corner to (k-1, k-1)
    public void placePiece(int y, int x) {
        int currentPlayerColour = (currentPlayerTurn == white) ? WHITE : BLACK;
        int otherPlayerColour = (currentPlayerColour == BLACK) ? WHITE : BLACK;
        if (GameLogic.moveIsIllegal(previousBoard, board, y, x, currentPlayerColour)) {
            return;
        }
        passCount = 0;
        passCountP.set("" + passCount);
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                previousBoard2[i][j] = previousBoard[i][j];
            }
        }
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                previousBoard[i][j] = board[i][j];
            }
        }
        if (currentPlayerTurn == white) {
            board[y][x] = WHITE;
        }
        else {
            board[y][x] = BLACK;
        }
        board = GameLogic.updateBoard(board, y, x, currentPlayerColour);
        int otherPlayerCountPrevious = 0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (previousBoard[i][j] == otherPlayerColour) {
                    ++otherPlayerCountPrevious;
                }
            }
        }
        int otherPlayerCountNew = 0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (board[i][j] == otherPlayerColour) {
                    ++otherPlayerCountNew;
                }
            }
        }
        captures[currentPlayerColour - 1] += otherPlayerCountPrevious - otherPlayerCountNew;
        capsBP.set("" + captures[0]);
        capsWP.set("" + captures[1]);
        ++turnNo;
        turnNoP.set("" + turnNo);
        if (turnNo % 2 == 0) {
            currentPlayerTurn = black;
        }
        else {
            currentPlayerTurn = white;
        }
        currentPlayerTurnP.set(currentPlayerTurn);
        undoStateP.set(false);
        return;
    }
    
    public void undoLastMove() {
        if (passCount > 0) {
            return;
        }
        undoStateP.set(true);
        int newCurrentPlayerColour = (currentPlayerTurn == white) ? BLACK : WHITE;
        int newOtherPlayerColour = (newCurrentPlayerColour == GameContainer.getG().BLACK) ? WHITE : BLACK;
        int newOtherPlayerCountPrevious = 0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (previousBoard[i][j] == newOtherPlayerColour) {
                    ++newOtherPlayerCountPrevious;
                }
            }
        }
        int newOtherPlayerCountNew = 0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (board[i][j] == newOtherPlayerColour) {
                    ++newOtherPlayerCountNew;
                }
            }
        }
        captures[newCurrentPlayerColour - 1] -= newOtherPlayerCountPrevious - newOtherPlayerCountNew;
        capsBP.set("" + captures[0]);
        capsWP.set("" + captures[1]);
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                board[i][j] = previousBoard[i][j];
            }
        }
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                previousBoard[i][j] = previousBoard2[i][j];
            }
        }
        --turnNo;
        turnNoP.set("" + turnNo);
        if (turnNo % 2 == 0) {
            currentPlayerTurn = black;
        }
        else {
            currentPlayerTurn = white;
        }
        currentPlayerTurnP.set(currentPlayerTurn);
        return;
    }
    
    public User getPlayer1() {
        return player1;
    }
    
    public User getPlayer2() {
        return player2;
    }
    
    public BooleanProperty getUndoStateP() {
        return undoStateP;
    }
    
    public BooleanProperty getReady() {
        return ready;
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public int[] getCaptures() { //remove these if unused
        return captures;
    }
    
    public int getPassCount() {
        return passCount;
    }
    
    public String getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }
    
    public void setFinished() {
        finished = true;
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
        passCountP.set("" + passCount);
        if (passCount == PASS_LIMIT) {
            currentPlayerTurn = "none (game is over)";
            currentPlayerTurnP.set(currentPlayerTurn);
            GameContainer.setS(board);
            ready.set(true);
            return;
        }
        ++turnNo;
        turnNoP.set("" + turnNo);
        if (turnNo % 2 == 0) {
            currentPlayerTurn = black;
        }
        else {
            currentPlayerTurn = white;
        }
        currentPlayerTurnP.set(currentPlayerTurn);
        return;
    }
    
    public void forfeitAndQuit() {
        //stuff
        return;
    }
}