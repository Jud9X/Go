import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class SetupPage extends VBox {
    
    Label label1, label2, label3, label4, label5, label6, label7; //make all these private and do getters...
    TextField username1, fname1, lname1, username2, fname2, lname2, gridSize;
    Button startGame;
    GameState g;
    GameGrid grid;
    
    public SetupPage() {
        this.label1 = new Label("Enter P1 username:");
        this.label2 = new Label("Enter P1 first name:");
        this.label3 = new Label("Enter P1 last name:");
        this.label4 = new Label("Enter P2 username:");
        this.label5 = new Label("Enter P2 first name:");
        this.label6 = new Label("Enter P2 last name:");
        this.label7 = new Label("Enter grid size:");
        this.username1 = new TextField();
        this.fname1 = new TextField();
        this.lname1 = new TextField();
        this.username2 = new TextField();
        this.fname2 = new TextField();
        this.lname2 = new TextField();
        this.gridSize = new TextField(); //this needs to be a radio button later
        this.startGame = new Button("Start game");
        getChildren().addAll(label1, username1, label2, fname1, label3, lname1, label4, username2, label5, fname2, label6, lname2, label7, gridSize, startGame);
        
        startGame.setOnAction(e -> {
            Player p1 = new Player(username1.getText(), fname1.getText(), lname1.getText());
            Player p2 = new Player(username2.getText(), fname2.getText(), lname2.getText());
            g = new GameState(Integer.parseInt(gridSize.getText()), p1, p2);
            grid = new GameGrid(Integer.parseInt(gridSize.getText()));
            grid.setAlignment(Pos.CENTER);
            grid.setMinSize(400, 400); //edit and fix the bad ratioing that has appeared
            grid.setMaxSize(600, 600);
        });
    }
}