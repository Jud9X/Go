import javafx.beans.value.ChangeListener;
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
                    grid[i][j] = new Tile("top left corner");
                    /*ChangeListener<Number> updater = (o, oV, nV) -> {
                        double newWidth = getWidth() / k;
                        double newHeight = getHeight() / k;
                        getChildren().forEach(n -> {
                            Tile p = (Tile) n;
                            p.setPrefSize(newWidth, newHeight);
                        });
                    };
                    widthProperty().addListener(updater);
                    heightProperty().addListener(updater);*/
                    //grid[i][j].prefWidthProperty().bind(widthProperty());
                    //grid[i][j].prefHeightProperty().bind(heightProperty());
                    add(grid[i][j], j, i);
                }
                else if (i == 0 && j == k-1) {
                    grid[i][j] = new Tile("top right corner");
                    //grid[i][j].prefWidthProperty().bind(widthProperty());
                    //grid[i][j].prefHeightProperty().bind(heightProperty());
                    add(grid[i][j], j, i);
                }
                else if (i == k-1 && j == 0) {
                    grid[i][j] = new Tile("bottom left corner");
                    //grid[i][j].prefWidthProperty().bind(widthProperty());
                    //grid[i][j].prefHeightProperty().bind(heightProperty());
                    add(grid[i][j], j, i);
                }
                else if (i == k-1 && j == k-1) {
                    grid[i][j] = new Tile("bottom right corner");
                    //grid[i][j].prefWidthProperty().bind(widthProperty());
                    //grid[i][j].prefHeightProperty().bind(heightProperty());
                    add(grid[i][j], j, i);
                }
                else if (i == 0) {
                    grid[i][j] = new Tile("top edge");
                    //grid[i][j].prefWidthProperty().bind(widthProperty());
                    //grid[i][j].prefHeightProperty().bind(heightProperty());
                    add(grid[i][j], j, i);
                }
                else if (i == k-1) {
                    grid[i][j] = new Tile("bottom edge");
                    //grid[i][j].prefWidthProperty().bind(widthProperty());
                    //grid[i][j].prefHeightProperty().bind(heightProperty());
                    add(grid[i][j], j, i);
                }
                else if (j == 0) {
                    grid[i][j] = new Tile("left edge");
                    //grid[i][j].prefWidthProperty().bind(widthProperty());
                    //grid[i][j].prefHeightProperty().bind(heightProperty());
                    add(grid[i][j], j, i);
                }
                else if (j == k-1) {
                    grid[i][j] = new Tile("right edge");
                    //grid[i][j].prefWidthProperty().bind(widthProperty());
                    //grid[i][j].prefHeightProperty().bind(heightProperty());
                    add(grid[i][j], j, i);
                }
                else { //interior case
                    grid[i][j] = new Tile();
                    //grid[i][j].prefWidthProperty().bind(widthProperty());
                    //grid[i][j].prefHeightProperty().bind(heightProperty());
                    add(grid[i][j], j, i);
                }
            }
        }
    }
    
    public Tile[][] getGrid() {
        return grid;
    }
    
    //methods for when the grid is clicked on a particular player's turn?
}