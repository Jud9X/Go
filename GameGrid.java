import javafx.beans.value.ChangeListener;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class GameGrid extends GridPane {
    
    private Tile[][] grid;
    
    public GameGrid(int k) {
        grid = new Tile[k][k];
        makeGrid(k);
    }
    
    private void makeGrid(int k) {
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < k; ++j) {
                if (i == 0 && j == 0) {
                    grid[i][j] = new Tile("top left corner");
                    add(grid[i][j], j, i);
                }
                else if (i == 0 && j == k-1) {
                    grid[i][j] = new Tile("top right corner");
                    add(grid[i][j], j, i);
                }
                else if (i == k-1 && j == 0) {
                    grid[i][j] = new Tile("bottom left corner");
                    add(grid[i][j], j, i);
                }
                else if (i == k-1 && j == k-1) {
                    grid[i][j] = new Tile("bottom right corner");
                    add(grid[i][j], j, i);
                }
                else if (i == 0) {
                    grid[i][j] = new Tile("top edge");
                    add(grid[i][j], j, i);
                }
                else if (i == k-1) {
                    grid[i][j] = new Tile("bottom edge");
                    add(grid[i][j], j, i);
                }
                else if (j == 0) {
                    grid[i][j] = new Tile("left edge");
                    add(grid[i][j], j, i);
                }
                else if (j == k-1) {
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
    
    public Tile[][] getGrid() {
        return grid;
    }
    
    public void updateGrid() {
        for (int i = 0; i < GameContainer.getG().getBoard().length; ++i) {
            for (int j = 0; j < GameContainer.getG().getBoard().length; ++j) {
                if (GameContainer.getG().getBoard()[i][j] == 1) {
                    if (getGrid()[i][j].getHasWhite()) {
                        getGrid()[i][j].removeWhite();
                    }
                    getGrid()[i][j].addBlack();
                }
                else if (GameContainer.getG().getBoard()[i][j] == 2) {
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
    
    public void updateGrid(Score score) {
        for (int i = 0; i < score.getEndingBoard().length; ++i) {
            for (int j = 0; j < score.getEndingBoard().length; ++j) {
                if (score.getEndingBoard()[i][j] == 1) {
                    if (getGrid()[i][j].getHasWhite()) {
                        getGrid()[i][j].removeWhite();
                    }
                    getGrid()[i][j].addBlack();
                }
                else if (score.getEndingBoard()[i][j] == 2) {
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