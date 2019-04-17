import javafx.geometry.Pos;
import javafx.scene.control.*;//fix
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;

public class Tile extends Pane {
    
    private boolean hasBlack; //make all these private, add getters if necessary
    private boolean hasWhite;
    private Line line1;
    private Line line2;
    private Circle circle1;
    private Circle circle2;
    private List<Node> nodes = new ArrayList<>();
    
    private void handleClick1() {
        if (GameContainer.getG().getCurrentPlayerTurn() == GameContainer.getG().getBlack()) {
            GameContainer.getG().placePiece(GameContainer.getGrid().getRowIndex(this), GameContainer.getGrid().getColumnIndex(this));
            if (GameContainer.getG().getCurrentPlayerTurn() != GameContainer.getG().getBlack()) {
                for (int i = 0; i < GameContainer.getG().getBoard().length; ++i) {
                    for (int j = 0; j < GameContainer.getG().getBoard().length; ++j) {
                        if (GameContainer.getG().getBoard()[i][j] == 1) {
                            if (GameContainer.getGrid().getGrid()[i][j].getHasWhite()) {
                                GameContainer.getGrid().getGrid()[i][j].removeWhite();
                            }
                            GameContainer.getGrid().getGrid()[i][j].addBlack();
                        }
                        else if (GameContainer.getG().getBoard()[i][j] == 2) {
                            if (GameContainer.getGrid().getGrid()[i][j].getHasBlack()) {
                                GameContainer.getGrid().getGrid()[i][j].removeBlack();
                            }
                            GameContainer.getGrid().getGrid()[i][j].addWhite();
                        }
                        else {
                            if (GameContainer.getGrid().getGrid()[i][j].getHasBlack()) {
                                GameContainer.getGrid().getGrid()[i][j].removeBlack();
                            }
                            if (GameContainer.getGrid().getGrid()[i][j].getHasWhite()) {
                                GameContainer.getGrid().getGrid()[i][j].removeWhite();
                            }
                        }
                    }
                }
            }
        }
        else { //it's white's turn
            GameContainer.getG().placePiece(GameContainer.getGrid().getRowIndex(this), GameContainer.getGrid().getColumnIndex(this));
            if (GameContainer.getG().getCurrentPlayerTurn() != GameContainer.getG().getWhite()) {
                for (int i = 0; i < GameContainer.getG().getBoard().length; ++i) {
                    for (int j = 0; j < GameContainer.getG().getBoard().length; ++j) {
                        if (GameContainer.getG().getBoard()[i][j] == 1) {
                            if (GameContainer.getGrid().getGrid()[i][j].getHasWhite()) {
                                GameContainer.getGrid().getGrid()[i][j].removeWhite();
                            }
                            GameContainer.getGrid().getGrid()[i][j].addBlack();
                        }
                        else if (GameContainer.getG().getBoard()[i][j] == 2) {
                            if (GameContainer.getGrid().getGrid()[i][j].getHasBlack()) {
                                GameContainer.getGrid().getGrid()[i][j].removeBlack();
                            }
                            GameContainer.getGrid().getGrid()[i][j].addWhite();
                        }
                        else {
                            if (GameContainer.getGrid().getGrid()[i][j].getHasBlack()) {
                                GameContainer.getGrid().getGrid()[i][j].removeBlack();
                            }
                            if (GameContainer.getGrid().getGrid()[i][j].getHasWhite()) {
                                GameContainer.getGrid().getGrid()[i][j].removeWhite();
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void handleClick2() {
        //include confirmation before each removal?
        int row = GameContainer.getGrid().getRowIndex(this);
        int col = GameContainer.getGrid().getColumnIndex(this);
        if (GameContainer.getG().getBoard()[row][col] == 1) {
            if (GameContainer.getGrid().getGrid()[row][col].getHasBlack()) {
                GameContainer.getGrid().getGrid()[row][col].removeBlack();
                GameContainer.getS().markDeadStone(row, col, 1);
            }
        }
        else if (GameContainer.getG().getBoard()[row][col] == 2) {
            if (GameContainer.getGrid().getGrid()[row][col].getHasWhite()) {
                GameContainer.getGrid().getGrid()[row][col].removeWhite();
                GameContainer.getS().markDeadStone(row, col, 2);
            }
        }
    }
    
    public Tile() {
        this.hasBlack = false;
        this.hasWhite = false;
        setStyle("-fx-border-color : beige");
        this.setPrefSize(50, 50);
        line1 = new Line(this.getWidth()/2, this.getHeight(), this.getWidth()/2, 0);
        line1.startXProperty().bind(this.widthProperty().divide(2)); //remove all these properties if they're not needed
        line1.endXProperty().bind(this.widthProperty().divide(2));
        line1.startYProperty().bind(this.heightProperty());
        //horizontal line
        line2 = new Line(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
        line2.startYProperty().bind(this.heightProperty().divide(2));
        line2.endXProperty().bind(this.widthProperty());
        line2.endYProperty().bind(this.heightProperty().divide(2));
        line1.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
        line2.setLayoutY(this.getHeight()/2 - line1.getLayoutBounds().getMinY());
        getChildren().addAll(line1, line2);
        nodes.addAll(getChildren());
        this.setOnMouseClicked(e -> {
            if (GameContainer.getG().getPassCount() != 2) handleClick1();
            else if (!GameContainer.getG().isFinished()) handleClick2();
        });
    }
    
    public Tile(String position) { //make this constructor shorter? by taking out line1 and line2 setup and making calls to that
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
            line1.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
            line2.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
            line2.setLayoutY(this.getHeight()/2 - line1.getLayoutBounds().getMinY());
            getChildren().addAll(line1, line2);
            nodes.addAll(getChildren());
            this.setOnMouseClicked(e -> {
                if (GameContainer.getG().getPassCount() != 2) handleClick1();
                else handleClick2();
            });
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
            line1.setLayoutX(this.getWidth()/2 - line1.getLayoutBounds().getMinX());
            line2.setLayoutX(0 - line1.getLayoutBounds().getMinX());
            line2.setLayoutY(this.getHeight()/2 - line1.getLayoutBounds().getMinY());
            getChildren().addAll(line1, line2);
            nodes.addAll(getChildren());
            this.setOnMouseClicked(e -> {
                if (GameContainer.getG().getPassCount() != 2) handleClick1();
                else handleClick2();
            });
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
            line1.relocate(this.getWidth()/2, this.getHeight()/2);
            line2.setLayoutY(this.getHeight()/2 - line1.getLayoutBounds().getMinY());
            getChildren().addAll(line1, line2);
            nodes.addAll(getChildren());
            this.setOnMouseClicked(e -> {
                if (GameContainer.getG().getPassCount() != 2) handleClick1();
                else handleClick2();
            });
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
            line1.relocate(this.getWidth()/2, this.getHeight()/2);
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
            getChildren().addAll(line1, line2);
            nodes.addAll(getChildren());
            this.setOnMouseClicked(e -> {
                if (GameContainer.getG().getPassCount() != 2) handleClick1();
                else handleClick2();
            });
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
            line1.relocate(this.getWidth()/2, this.getHeight());
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
            getChildren().addAll(line1, line2);
            nodes.addAll(getChildren());
            this.setOnMouseClicked(e -> {
                if (GameContainer.getG().getPassCount() != 2) handleClick1();
                else handleClick2();
            });
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
            line1.relocate(this.getWidth()/2, this.getHeight());
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
            getChildren().addAll(line1, line2);
            nodes.addAll(getChildren());
            this.setOnMouseClicked(e -> {
                if (GameContainer.getG().getPassCount() != 2) handleClick1();
                else handleClick2();
            });
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
            line1.relocate(this.getWidth()/2, this.getHeight());
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
            getChildren().addAll(line1, line2);
            nodes.addAll(getChildren());
            this.setOnMouseClicked(e -> {
                if (GameContainer.getG().getPassCount() != 2) handleClick1();
                else handleClick2();
            });
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
            line1.relocate(this.getWidth()/2, this.getHeight());
            line2.relocate(this.getWidth()/2, this.getHeight()/2);
            getChildren().addAll(line1, line2);
            nodes.addAll(getChildren());
            this.setOnMouseClicked(e -> {
                if (GameContainer.getG().getPassCount() != 2) handleClick1();
                else handleClick2();
            });
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
        circle1 = new Circle(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2);
        circle1.centerXProperty().bind(this.widthProperty().divide(2));
        circle1.centerYProperty().bind(this.heightProperty().divide(2));
        circle1.radiusProperty().bind(this.widthProperty().divide(2));
        circle1.setFill(Color.BLACK);
        this.getChildren().addAll(circle1);
    }
    
    public void addWhite() {
        hasWhite = true;
        circle2 = new Circle(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2);
        circle2.centerXProperty().bind(this.widthProperty().divide(2));
        circle2.centerYProperty().bind(this.heightProperty().divide(2));
        circle2.radiusProperty().bind(this.widthProperty().divide(2));
        circle2.setFill(Color.WHITE);
        this.getChildren().add(circle2);
    }
    
    public void removeBlack() {
        hasBlack = false;
        //have to do work-around here as I think there's a javafx bug that stops .remove(circle1) working here
        this.getChildren().clear();
        for (Node n : nodes) this.getChildren().addAll(n);
    }
    
    public void removeWhite() {
        hasWhite = false;
        //have to do work-around here as I think there's a javafx bug that stops .remove(2) working here
        this.getChildren().clear();
        for (Node n : nodes) this.getChildren().addAll(n);
    }
}