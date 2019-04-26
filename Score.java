import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Completes all scoring after a game is finished, including handling the marking of dead stones.
 * @author Oliver
 * @version 2.3
 * */
public class Score {
    private Set<List<Integer>> possibleTerritory;
    private Set<List<Integer>> confirmedTerritory;
    private boolean touchesBlack;
    private boolean touchesWhite;
    private boolean isTerritory;
    private int[][] endingBoard;
    private int[] deads;
    private int deadsIndex;
    private int removeBlackCount;
    private int removeWhiteCount;
    private int[] territorySums;
    private int[] finalScores;
    private int[] previousDead;
    private BooleanProperty undoP;
    private StringProperty dbP;
    private StringProperty dwP;
    private String winnerName;
    private String loserName;
    
    public Score(int[][] board) {
        possibleTerritory = new HashSet<List<Integer>>();
        confirmedTerritory = new HashSet<List<Integer>>();
        touchesBlack = false;
        touchesWhite = false;
        isTerritory = true;
        endingBoard = board;
        removeBlackCount = 0;
        removeWhiteCount = 0;
        previousDead = new int[3]; //will contain y, x and colourvalue
        undoP = new SimpleBooleanProperty(true);
        dbP = new SimpleStringProperty("0");
        dwP = new SimpleStringProperty("0");
    }
    
    public int[][] getEndingBoard() {
        return endingBoard;
    }
    
    public BooleanProperty getUndoP() {
        return undoP;
    }
    
    public int[] getFinalScores() {
        return finalScores;
    }
    
    public StringProperty getDbP() {
        return dbP;
    }
    
    public StringProperty getDwP() {
        return dwP;
    }
    
    public String getWinnerName() {
        return winnerName;
    }
    
    public String getLoserName() {
        return loserName;
    }
    
    public void markDeadStone(int y, int x, int oldValue) {
        undoP.set(false);
        endingBoard[y][x] = 0;
        if (oldValue == GameContainer.getG().BLACK) {
            ++removeBlackCount;
            dbP.set("" + removeBlackCount);
        }
        else {
            ++removeWhiteCount;
            dwP.set("" + removeWhiteCount);
        }
        previousDead[0] = y;
        previousDead[1] = x;
        previousDead[2] = oldValue;
    }
    
    public void undoMarkDeadStone() {
        undoP.set(true);
        if (previousDead[2] == GameContainer.getG().BLACK) {
            --removeBlackCount;
        }
        else {
            --removeWhiteCount;
        }
        dbP.set("" + removeBlackCount);
        dwP.set("" + removeWhiteCount);
        endingBoard[previousDead[0]][previousDead[1]] = previousDead[2];
    }
    
    public void calculateFinalScores() { //returns int[] where array[0] is black's territory and array[1] is white's
        territorySums = new int[2];
        for (int i = 0; i < endingBoard.length; ++i) {
            for (int j = 0; j < endingBoard.length; ++j) {
                if (endingBoard[i][j] == 0 && !(confirmedTerritory.contains(Arrays.asList(i, j)))) {
                    possibleTerritory.clear();
                    touchesBlack = false;
                    touchesWhite = false;
                    isTerritory = true;
                    if (isTerritory(endingBoard, i, j)) {
                        confirmedTerritory.addAll(possibleTerritory);
                        if (touchesBlack) {
                            territorySums[0] += possibleTerritory.size();
                        }
                        else if (touchesWhite) {
                            territorySums[1] += possibleTerritory.size();
                        }
                    }
                }
            }
        }
        finalScores = new int[2]; //finalScores[0] is black's score and [1] is white's score
        finalScores[0] += territorySums[0] - GameContainer.getG().getCaptures()[1] - removeBlackCount;
        finalScores[1] += territorySums[1] - GameContainer.getG().getCaptures()[0] - removeWhiteCount;
        //the logic of the 2 lines below means that if they get the same score, white is declared winner (as they start second)
        winnerName = (finalScores[0] > finalScores[1]) ? GameContainer.getG().getBlack() : GameContainer.getG().getWhite();
        loserName = (finalScores[0] <= finalScores[1]) ? GameContainer.getG().getBlack() : GameContainer.getG().getWhite();
        User winner = (GameContainer.getG().getPlayer1().getUsername().equals(winnerName)) ? GameContainer.getG().getPlayer1() : GameContainer.getG().getPlayer2();
        User loser = (GameContainer.getG().getPlayer1().getUsername().equals(loserName)) ? GameContainer.getG().getPlayer1() : GameContainer.getG().getPlayer2();
        winner.incrementWinCount();
        winner.incrementGameCount();
        loser.incrementGameCount();
        GameRecord result = new GameRecord(ZonedDateTime.now(), winner, loser);
        GameContainer.getGamesPlayed().add(result);
    }
    
    private boolean isTerritory(int[][] board, int y, int x) {
        possibleTerritory.add(Arrays.asList(y, x));
        int[] adjs = GameLogic.getAdjacentCoordinates(y, x, board.length);
        for (int i = 0; i < adjs.length - 1; i += 2) {
            if (board[adjs[i]][adjs[i + 1]] == GameContainer.getG().BLACK) {
                touchesBlack = true;
            }
            else if (board[adjs[i]][adjs[i + 1]] == GameContainer.getG().WHITE) {
                touchesWhite = true;
            }
        }
        if (touchesBlack && touchesWhite) {
            return false;
        }
        for (int i = 0; i < adjs.length - 1; i += 2) {
            if (board[adjs[i]][adjs[i + 1]] == 0 && !(possibleTerritory.contains(Arrays.asList(adjs[i], adjs[i + 1])))) {
                isTerritory = isTerritory(board, adjs[i], adjs[i + 1]);
            }
        }
        return isTerritory;
    }
}