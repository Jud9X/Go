import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class GameGrid extends GridPane {
    
    Tile[][] grid;
    
    public GameGrid(int k) {
        this.grid = new Tile[k][k];
        makeGrid(k);
    }
    
    private void makeGrid(int k) {
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < k; ++j) {
                if (i == 0 && j == 0) {
                    //stuff
                }
                else if (i == 0 && j == k-1) {
                    //stuff
                }
                else if (i == k-1 && j == 0) {
                    //stuff
                }
                else if (i == k-1 && j == k-1) {
                    //stuff
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
    
    //more stuff?
}