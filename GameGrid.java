import javafx.beans.value.ChangeListener;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * Grid that holds all of the Tiles that make up the game board.
 * @author Oliver
 * @version 1.5
 * */
public class GameGrid extends GridPane {
    
    private Tile[][] grid;
    
    /**
     * Makes a new GameGrid grid to display.
     * @param k The size of the grid given as one length.
     * */
    public GameGrid(int k) {
        grid = new Tile[k][k];
        makeGrid(k);
    }
    
    /**
     * Makes a new grid according to the Tile required.
     * @param k The size of the grid given as one length.
     * */
    private void makeGrid(int k) {
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < k; ++j) {
                if (i == 0 && j == 0) {
                    grid[i][j] = new Tile("top left corner");
                    add(grid[i][j], j, i);
                }
                else if (i == 0 && j == k - 1) {
                    grid[i][j] = new Tile("top right corner");
                    add(grid[i][j], j, i);
                }
                else if (i == k - 1 && j == 0) {
                    grid[i][j] = new Tile("bottom left corner");
                    add(grid[i][j], j, i);
                }
                else if (i == k - 1 && j == k - 1) {
                    grid[i][j] = new Tile("bottom right corner");
                    add(grid[i][j], j, i);
                }
                else if (i == 0) {
                    grid[i][j] = new Tile("top edge");
                    add(grid[i][j], j, i);
                }
                else if (i == k - 1) {
                    grid[i][j] = new Tile("bottom edge");
                    add(grid[i][j], j, i);
                }
                else if (j == 0) {
                    grid[i][j] = new Tile("left edge");
                    add(grid[i][j], j, i);
                }
                else if (j == k - 1) {
                    grid[i][j] = new Tile("right edge");
                    add(grid[i][j], j, i);
                }
                else { //interior case
                    grid[i][j] = new Tile();
                    add(grid[i][j], j, i);
                }
            }
        }
    }
    
    /**
     * Gets the new grid.
     * @return Tile[][] The grid as a 2D array of Tile objects.
     * */
    public Tile[][] getGrid() {
        return grid;
    }
    
    /**
     * Updates the grid. For use during gameplay when a piece is placed.
     * */
    public void updateGrid() {
        for (int i = 0; i < GameContainer.getG().getBoard().length; ++i) {
            for (int j = 0; j < GameContainer.getG().getBoard().length; ++j) {
                if (GameContainer.getG().getBoard()[i][j] == GameContainer.getG().BLACK) {
                    if (getGrid()[i][j].getHasWhite()) {
                        getGrid()[i][j].removeWhite();
                    }
                    getGrid()[i][j].addBlack();
                }
                else if (GameContainer.getG().getBoard()[i][j] == GameContainer.getG().WHITE) {
                    if (getGrid()[i][j].getHasBlack()) {
                        getGrid()[i][j].removeBlack();
                    }
                    getGrid()[i][j].addWhite();
                }
                else {
                    if (getGrid()[i][j].getHasBlack()) {
                        getGrid()[i][j].removeBlack();
                    }
                    if (getGrid()[i][j].getHasWhite()) {
                        getGrid()[i][j].removeWhite();
                    }
                }
            }
        }
    }
    
    /**
     * Updates the grid. For use during the removal of dead stones during scoring.
     * @param score The current Score object.
     * */
    public void updateGrid(Score score) {
        for (int i = 0; i < score.getEndingBoard().length; ++i) {
            for (int j = 0; j < score.getEndingBoard().length; ++j) {
                if (score.getEndingBoard()[i][j] == GameContainer.getG().BLACK) {
                    if (getGrid()[i][j].getHasWhite()) {
                        getGrid()[i][j].removeWhite();
                    }
                    getGrid()[i][j].addBlack();
                }
                else if (score.getEndingBoard()[i][j] == GameContainer.getG().WHITE) {
                    if (getGrid()[i][j].getHasBlack()) {
                        getGrid()[i][j].removeBlack();
                    }
                    getGrid()[i][j].addWhite();
                }
                else {
                    if (getGrid()[i][j].getHasBlack()) {
                        getGrid()[i][j].removeBlack();
                    }
                    if (getGrid()[i][j].getHasWhite()) {
                        getGrid()[i][j].removeWhite();
                    }
                }
            }
        }
    }
}