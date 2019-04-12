import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
//import javafx.scene.Node;
//import javafx.geometry.Point2D;

public class Tile extends Pane {
    
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
        line1.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
        line2.setLayoutY(this.getHeight()/2 - line1.getLayoutBounds().getMinY());
        getChildren().addAll(line1, line2);
        this.setOnMouseClicked(e -> {
            if (Main.setupPage.g.getCurrentPlayerTurn() == Main.setupPage.g.getBlack()) {
                Main.setupPage.g.placePiece(Main.setupPage.grid.getRowIndex(this), Main.setupPage.grid.getColumnIndex(this));
                if (Main.setupPage.g.getCurrentPlayerTurn() != Main.setupPage.g.getBlack()) {
                    for (int i = 0; i < Main.setupPage.g.getBoard().length; ++i) {
                        for (int j = 0; j < Main.setupPage.g.getBoard().length; ++j) {
                            if (Main.setupPage.g.getBoard()[i][j] == 1) {
                                if (Main.setupPage.grid.getGrid()[i][j].getHasWhite()) {
                                    Main.setupPage.grid.getGrid()[i][j].removeWhite();
                                }
                                Main.setupPage.grid.getGrid()[i][j].addBlack();
                            }
                            else if (Main.setupPage.g.getBoard()[i][j] == 2) {
                                if (Main.setupPage.grid.getGrid()[i][j].getHasBlack()) {
                                    Main.setupPage.grid.getGrid()[i][j].removeBlack();
                                }
                                Main.setupPage.grid.getGrid()[i][j].addWhite();
                            }
                            else {
                                if (Main.setupPage.grid.getGrid()[i][j].getHasBlack()) {
                                    Main.setupPage.grid.getGrid()[i][j].removeBlack();
                                }
                                if (Main.setupPage.grid.getGrid()[i][j].getHasWhite()) {
                                    Main.setupPage.grid.getGrid()[i][j].removeWhite();
                                }
                            }
                        }
                    }
                }
            }
            else { //it's white's turn
                Main.setupPage.g.placePiece(Main.setupPage.grid.getRowIndex(this), Main.setupPage.grid.getColumnIndex(this));
                if (Main.setupPage.g.getCurrentPlayerTurn() != Main.setupPage.g.getWhite()) {
                    for (int i = 0; i < Main.setupPage.g.getBoard().length; ++i) {
                        for (int j = 0; j < Main.setupPage.g.getBoard().length; ++j) {
                            if (Main.setupPage.g.getBoard()[i][j] == 1) {
                                if (Main.setupPage.grid.getGrid()[i][j].getHasWhite()) {
                                    Main.setupPage.grid.getGrid()[i][j].removeWhite();
                                }
                                Main.setupPage.grid.getGrid()[i][j].addBlack();
                            }
                            else if (Main.setupPage.g.getBoard()[i][j] == 2) {
                                if (Main.setupPage.grid.getGrid()[i][j].getHasBlack()) {
                                    Main.setupPage.grid.getGrid()[i][j].removeBlack();
                                }
                                Main.setupPage.grid.getGrid()[i][j].addWhite();
                            }
                            else {
                                if (Main.setupPage.grid.getGrid()[i][j].getHasBlack()) {
                                    Main.setupPage.grid.getGrid()[i][j].removeBlack();
                                }
                                if (Main.setupPage.grid.getGrid()[i][j].getHasWhite()) {
                                    Main.setupPage.grid.getGrid()[i][j].removeWhite();
                                }
                            }
                        }
                    }
                }
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
            //setAlignment(line2, Pos.CENTER_RIGHT);
            line1.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
            //line1.layoutYProperty().bind(this.heightProperty());
            line2.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
            line2.setLayoutY(this.getHeight()/2 - line1.getLayoutBounds().getMinY());
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
            //setAlignment(line2, Pos.CENTER_LEFT);
            line1.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
            //line1.layoutYProperty().bind(this.heightProperty());
            line2.setLayoutX(0 - line1.getLayoutBounds().getMinX());
            line2.setLayoutY(this.getHeight()/2 - line1.getLayoutBounds().getMinY());
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("top edge")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, this.getHeight());
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line1.endYProperty().bind(this.heightProperty());
            line2 = new Line(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty());
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            //setAlignment(line1, Pos.BOTTOM_CENTER);
            //line1.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
            //line1.setLayoutY(this.getHeight() - line1.getLayoutBounds().getMinY());
            line1.relocate(this.getWidth()/2, this.getHeight()/2);
            //line1.layoutYProperty().bind(this.heightProperty());
            //line2.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
            line2.setLayoutY(this.getHeight()/2 - line1.getLayoutBounds().getMinY());
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
            //setAlignment(line1, Pos.TOP_CENTER);
            line1.relocate(this.getWidth()/2, this.getHeight()/2);
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("top left corner")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, this.getHeight());
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line1.endYProperty().bind(this.heightProperty());
            line2 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
            line2.startXProperty().bind(this.widthProperty().divide(2));
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty());
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            //setAlignment(line1, Pos.BOTTOM_CENTER);
            //setAlignment(line2, Pos.CENTER_RIGHT);
            line1.relocate(this.getWidth()/2, this.getHeight());
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("top right corner")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, this.getHeight());
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line1.endYProperty().bind(this.heightProperty());
            line2 = new Line(0, this.getHeight()/2, this.getWidth()/2, this.getHeight()/2);
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty().divide(2));
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            //setAlignment(line1, Pos.BOTTOM_CENTER);
            //setAlignment(line2, Pos.CENTER_LEFT);
            line1.relocate(this.getWidth()/2, this.getHeight());
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
            getChildren().addAll(line1, line2);
        }
        else if (position.equals("bottom left corner")) {
            line1 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, 0);
            line1.startXProperty().bind(this.widthProperty().divide(2));
            line1.endXProperty().bind(this.widthProperty().divide(2));
            line1.startYProperty().bind(this.heightProperty().divide(2));
            line2 = new Line(this.getWidth()/2, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
            line2.startXProperty().bind(this.widthProperty().divide(2));
            line2.startYProperty().bind(this.heightProperty().divide(2));
            line2.endXProperty().bind(this.widthProperty());
            line2.endYProperty().bind(this.heightProperty().divide(2));
            //line1.setStroke(Color.BLACK);
            //line1.setStrokeWidth(1.5);
            //setAlignment(line1, Pos.TOP_CENTER);
            //setAlignment(line2, Pos.CENTER_RIGHT);
            line1.relocate(this.getWidth()/2, this.getHeight());
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
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
            //setAlignment(line1, Pos.TOP_CENTER);
            //setAlignment(line2, Pos.CENTER_LEFT);
            line1.relocate(this.getWidth()/2, this.getHeight());
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
            getChildren().addAll(line1, line2);
        }
    }
    
    public boolean getHasBlack() {
        return hasBlack;
    }
    
    public boolean getHasWhite() {
        return hasWhite;
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
        getChildren().remove(2);
    }
    
    public void removeWhite() {
        hasWhite = false;
        getChildren().remove(2);
    }
}