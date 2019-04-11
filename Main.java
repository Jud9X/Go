//import java.util.Arrays;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import java.util.Scanner;

public class Main extends Application {
    
    Button button1;
    Scene scene1, scene2;
    static SetupPage setupPage;
    
    public static void main(final String[] args) {
        launch(args);
    }
        
        @Override
        public void start(Stage primaryStage) throws Exception {
            primaryStage.setTitle("Play Go!");
            
            Menu fileMenu = new Menu("_File");
            Menu editMenu = new Menu("_Edit");
            Menu otherMenu = new Menu("_Other");
            
            MenuItem newFile = new MenuItem("_New...");
            newFile.setOnAction(e -> System.out.println("testing, new file"));
            fileMenu.getItems().add(newFile);
            MenuItem openFile = new MenuItem("_Open...");
            newFile.setOnAction(e -> System.out.println("testing, open file"));
            fileMenu.getItems().add(openFile);
            MenuItem somethingElse = new MenuItem("Do something else...");
            newFile.setOnAction(e -> System.out.println("testing, something else"));
            fileMenu.getItems().add(somethingElse);
            fileMenu.getItems().add(new SeparatorMenuItem());
            MenuItem exit = new MenuItem("Exit");
            newFile.setOnAction(e -> System.out.println("exits"));
            fileMenu.getItems().add(exit);
            
            MenuItem copy = new MenuItem("_Copy");
            copy.setOnAction(e -> System.out.println("copied"));
            editMenu.getItems().add(copy);
            MenuItem somethingElse2 = new MenuItem("Do another thing");
            somethingElse2.setOnAction(e -> System.out.println("the other thing"));
            editMenu.getItems().add(somethingElse2);
            
            MenuItem otherThing = new MenuItem("Other thing");
            otherThing.setOnAction(e -> System.out.println("some other thing is done"));
            otherMenu.getItems().add(otherThing);
            CheckMenuItem binaryOption = new CheckMenuItem("Option that can be on or off");
            binaryOption.setSelected(true);
            binaryOption.setOnAction(e -> {
                if (binaryOption.isSelected()) System.out.println("option is selected");
                else System.out.println("Option is turned off");
            });
            otherMenu.getItems().add(binaryOption);
            
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().addAll(fileMenu, editMenu, otherMenu);
            
            setupPage = new SetupPage();
            
            Label label1 = new Label("This is page 1");
            button1 = new Button("Go to page 2");
            button1.setOnAction(e -> {
                Label label2 = new Label(setupPage.username1.getText());
                //layout of page 2
                BorderPane layout2 = new BorderPane();
                layout2.setCenter(setupPage.grid);
                layout2.setBottom(label2);
                VBox gameInfo = new VBox();
                Label black = new Label(setupPage.g.getBlack());
                Label white = new Label(setupPage.g.getWhite());
                Label pc = new Label(""+setupPage.g.getPassCount());
                Label tn = new Label(""+setupPage.g.getTurnNo());
                Label CPT = new Label(setupPage.g.getCurrentPlayerTurn());
                Label capsB = new Label(""+setupPage.g.getCaptures()[0]);
                Label capsW = new Label(""+setupPage.g.getCaptures()[1]);
                gameInfo.getChildren().addAll(black, white, pc, tn, CPT, capsB, capsW);
                layout2.setRight(gameInfo);
                VBox gameControl = new VBox();
                Button pass = new Button("Pass");
                pass.setOnAction(e2 -> setupPage.g.pass());
                gameControl.getChildren().addAll(pass);
                layout2.setLeft(gameControl);
                scene2 = new Scene(layout2, 702, 702);
                primaryStage.setScene(scene2);
            });
            
            /*
            //layout of page 1
            BorderPane layout1 = new BorderPane();
            layout1.setCenter(button1);
            layout1.setTop(menuBar);
            */
            
            //layout of page 1
            BorderPane layout1 = new BorderPane();
            layout1.setCenter(setupPage);
            layout1.setTop(menuBar);
            layout1.setBottom(button1);
            
            Button button2 = new Button("Go back to page 1");
            button2.setOnAction(e -> primaryStage.setScene(scene1));
            
            /*
            //layout of page 2
            VBox layout2 = new VBox(20);
            layout2.getChildren().addAll(button2);
            */

            //GameGrid grid = new GameGrid(Integer.parseInt(setupPage.gridSize.getText()));
            //GameGrid grid = new GameGrid(13);//13 needs to come from somewhere
            //grid.setAlignment(Pos.CENTER);
            
            //GridPane gpane = new GridPane();
            //gpane.getChildren().addAll(button2);
            
            
            
            /*
            //while (!SetupPage.g.isFinished()) {
            System.out.println("This player's turn: " + SetupPage.g.getCurrentPlayerTurn());
            if (point.equals("pass")) {
                g.pass();
                System.out.println("Number of consecutive passes: " + g.getPassCount());
            }
            else {
                String[] coords = point.split(" ");
                g.placePiece(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            }
            //}
            System.out.println("game is over");
            */


            /*
            //layout of page 2
            BorderPane layout2 = new BorderPane();
            layout2.setCenter(setupPage.grid);
            layout2.setBottom(label2);
            */
            
            scene1 = new Scene(layout1, 400, 400);
            primaryStage.setScene(scene1);
            primaryStage.show();
            
            //scene2 = new Scene(layout2, 702, 702);
        
        }
        
        /*
        Player p1 = new Player("ohart", "Oliver", "Hart");
        Player p2 = new Player("fred", "Fred", "West");
        GameState g = new GameState(4, p1, p2);
        Scanner userInput = new Scanner(System.in);
        while (!g.isFinished()) {
            System.out.println("This player's turn: " + g.getCurrentPlayerTurn());
            System.out.println(Arrays.deepToString(g.getBoard()));
            System.out.println("Type coordinates to place your piece in the form 'y x' without quotes, starting from top left as 0,0 or 'pass' w/o quotes");
            String point = userInput.nextLine();
            if (point.equals("pass")) {
                g.pass();
                System.out.println("Number of consecutive passes: " + g.getPassCount());
            }
            else {
                String[] coords = point.split(" ");
                g.placePiece(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            }
            System.out.println(Arrays.deepToString(g.getBoard()));
        }
        userInput.close();
        System.out.println("game is over");
    }*/
}