import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
//import javafx.scene.Node;
//import javafx.geometry.Point2D;

public class Tile extends StackPane {
    
    //int k;
    boolean hasBlack;
    boolean hasWhite;
    
    public Tile() {
        this.hasBlack = false;
        this.hasWhite = false;
        setStyle("-fx-border-color : beige");
        this.setPrefSize(50, 50);
        Line line1 = new Line(this.getWidth()/2, this.getHeight(), this.getWidth()/2, 0);
        line1.startXProperty().bind(this.widthProperty().divide(2));
        line1.endXProperty().bind(this.widthProperty().divide(2));
        line1.startYProperty().bind(this.heightProperty());
        Line line2 = new Line(this.getWidth(), this.getHeight()/2, 0, this.getHeight()/2);
        line2.startXProperty().bind(this.widthProperty());
        line2.startYProperty().bind(this.heightProperty().divide(2));
        line2.endYProperty().bind(this.heightProperty().divide(2));
        //line1.setStroke(Color.BLACK);
        //line1.setStrokeWidth(1.5);
        getChildren().addAll(line1, line2);
    }

    //more stuff
}