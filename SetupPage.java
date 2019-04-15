import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;//fix
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class SetupPage extends VBox {
    
    Label label1, label2, label3, label4, label5, label6, label7; //make all these private and do getters...
    TextField username1, fname1, lname1, username2, fname2, lname2;
    ToggleGroup gridSizes;
    RadioButton r1, r2;
    Button startGame;
    
    public SetupPage() {
        label1 = new Label("Enter P1 username:");
        label2 = new Label("Enter P1 first name:");
        label3 = new Label("Enter P1 last name:");
        label4 = new Label("Enter P2 username:");
        label5 = new Label("Enter P2 first name:");
        label6 = new Label("Enter P2 last name:");
        label7 = new Label("Enter grid size:");
        username1 = new TextField();
        fname1 = new TextField();
        lname1 = new TextField();
        username2 = new TextField();
        fname2 = new TextField();
        lname2 = new TextField();
        gridSizes = new ToggleGroup();
        r1 = new RadioButton("9");
        r2 = new RadioButton("13");
        r1.setToggleGroup(gridSizes);
        r2.setToggleGroup(gridSizes);
        r2.setSelected(true);
        startGame = new Button("Start game");
        getChildren().addAll(label1, username1, label2, fname1, label3, lname1, label4, username2, label5, fname2, label6, lname2, label7, r1, r2, startGame);
        
        startGame.setOnAction(e -> {
            Player p1 = new Player(username1.getText(), fname1.getText(), lname1.getText());
            Player p2 = new Player(username2.getText(), fname2.getText(), lname2.getText());
            //g = new GameState(Integer.parseInt(((RadioButton)gridSizes.getSelectedToggle()).getText()), p1, p2);
            //grid = new GameGrid(Integer.parseInt(((RadioButton)gridSizes.getSelectedToggle()).getText()));
            //grid.setAlignment(Pos.CENTER);
            //grid.setMinSize(400, 400); //edit and fix the bad ratioing that has appeared
            //grid.setMaxSize(600, 600);
        });
    }
}