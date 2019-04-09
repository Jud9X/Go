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
                grid[i][j] = new Tile();
                add(grid[i][j], j, i);
            }
        }
    }
}