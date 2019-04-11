import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
//import javafx.scene.Node;
//import javafx.geometry.Point2D;

public class Tile extends StackPane {
    
    boolean hasBlack;
    boolean hasWhite;
    Line line1;
    Line line2;
    
    public Tile() {
        this.hasBlack = false;
        this.hasWhite = false;
        setStyle("-fx-border-color : beige");
        this.setPrefSize(50, 50);
        //vertical line
        line1 = new Line(this.getWidth()/2, this.getHeight(), this.getWidth()/2, 0);
        line1.startXProperty().bind(this.widthProperty().divide(2));
        line1.endXProperty().bind(this.widthProperty().divide(2));
        line1.startYProperty().bind(this.heightProperty());
        //horizontal line
        line2 = new Line(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
        line2.startYProperty().bind(this.heightProperty().divide(2));
        line2.endXProperty().bind(this.widthProperty());
        line2.endYProperty().bind(this.heightProperty().divide(2));
        //line1.setStroke(Color.BLACK);
        //line1.setStrokeWidth(1.5);
        getChildren().addAll(line1, line2);
        this.setOnMouseClicked(e -> {
            if (Main.setupPage.g.getCurrentPlayerTurn() == Main.setupPage.g.getBlack()) {
                this.addBlack();
            }
            else {
                this.addWhite();
            }
        });
    }
    
    public Tile(String position) {
        this.hasBlack = false;
        this.hasWhite = false;
        setStyle("-fx-border-color : beige");
        this.setPrefSize(50, 50);
        if (position.equals("left edge")) {
            line1 = new Line(this.getWidth()/2, this.getHeight(), this.getWidth()/2, 0);
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty());
            line2 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
            line2.startXProperty().bind(this.widthProperty().divide(2));
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty());
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            setAlignment(line2, Pos.CENTER_RIGHT);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("right edge")) {
            line1 = new Line(this.getWidth()/2, this.getHeight(), this.getWidth()/2, 0);
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty());
            line2 = new Line(0, this.getHeight()/2, this.getWidth()/2, this.getHeight()/2);
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty().divide(2));
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            setAlignment(line2, Pos.CENTER_LEFT);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("top edge")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, 0);
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line2 = new Line(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty());
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            setAlignment(line1, Pos.BOTTOM_CENTER);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("bottom edge")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, 0);
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line2 = new Line(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty());
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            setAlignment(line1, Pos.TOP_CENTER);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("top left corner")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, 0);
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line2 = new Line(0, this.getHeight()/2, this.getWidth()/2, this.getHeight()/2);
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty().divide(2));
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            setAlignment(line1, Pos.BOTTOM_CENTER);
            setAlignment(line2, Pos.CENTER_RIGHT);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("top right corner")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, 0);
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line2 = new Line(0, this.getHeight()/2, this.getWidth()/2, this.getHeight()/2);
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty().divide(2));
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            setAlignment(line1, Pos.BOTTOM_CENTER);
            setAlignment(line2, Pos.CENTER_LEFT);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("bottom left corner")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, 0);
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line2 = new Line(0, this.getHeight()/2, this.getWidth()/2, this.getHeight()/2);
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty().divide(2));
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            setAlignment(line1, Pos.TOP_CENTER);
            setAlignment(line2, Pos.CENTER_RIGHT);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("bottom right corner")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, 0);
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line2 = new Line(0, this.getHeight()/2, this.getWidth()/2, this.getHeight()/2);
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty().divide(2));
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            setAlignment(line1, Pos.TOP_CENTER);
            setAlignment(line2, Pos.CENTER_LEFT);
            getChildren().addAll(line1, line2);
        }
    }

    public void addBlack() {
        hasBlack = true;
        Circle circle = new Circle(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2);
        circle.centerXProperty().bind(this.widthProperty().divide(2));
        circle.centerYProperty().bind(this.heightProperty().divide(2));
        circle.radiusProperty().bind(this.widthProperty().divide(2));
        circle.setFill(Color.BLACK);
        getChildren().add(circle);
    }
    
    public void addWhite() {
        hasWhite = true;
        Circle circle = new Circle(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2);
        circle.centerXProperty().bind(this.widthProperty().divide(2));
        circle.centerYProperty().bind(this.heightProperty().divide(2));
        circle.radiusProperty().bind(this.widthProperty().divide(2));
        circle.setFill(Color.WHITE);
        getChildren().add(circle);
    }
    
    public void removeBlack() {
        hasBlack = false;
        getChildren().remove(lookup(".circle"));
    }
    
    public void removeWhite() {
        hasWhite = false;
        getChildren().remove(lookup(".circle"));
    }
}