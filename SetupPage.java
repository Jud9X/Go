import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SetupPage extends VBox {
    
    Label label1, label2, label3, label4, label5, label6, label7;
    TextField username1, fname1, lname1, username2, fname2, lname2, gridSize;
    
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
        getChildren().addAll(label1, username1);
    }
    
}