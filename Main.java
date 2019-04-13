//import java.util.Arrays;
import javafx.application.Application;
//import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
                Label black = new Label("Black: " + setupPage.g.getBlack());
                Label white = new Label("White: " + setupPage.g.getWhite());
                Label tn = new Label("Turns played: ");
                Label tnLive = new Label();
                tnLive.textProperty().bind(setupPage.g.getTurnNoP());
                Label capsB = new Label("Captures by black: ");
                Label capsBLive = new Label();
                capsBLive.textProperty().bind(setupPage.g.getCapsBP());
                Label capsW = new Label("Captures by white: ");
                Label capsWLive = new Label();
                capsWLive.textProperty().bind(setupPage.g.getCapsWP());
                Label pc = new Label("Pass count: ");
                Label pcLive = new Label();
                pcLive.textProperty().bind(setupPage.g.getPassCountP());
                Label CPT = new Label("Current player's turn: ");
                Label CPTLive = new Label();
                CPTLive.textProperty().bind(setupPage.g.getCurrentPlayerTurnP());
                gameInfo.getChildren().addAll(black, white, tn, tnLive, capsB, capsBLive, capsW, capsWLive, pc, pcLive, CPT, CPTLive);
                layout2.setRight(gameInfo);
                VBox gameControl = new VBox();
                Label passInfo = new Label("Click the button below to pass your turn:");
                passInfo.setMaxWidth(100);
                passInfo.setWrapText(true);
                Button pass = new Button("Pass");
                pass.setOnAction(e2 -> setupPage.g.pass());
                Label undoInfo = new Label("Click the button below to undo the previous move (unless that move was passed):");
                undoInfo.setMaxWidth(100);
                undoInfo.setWrapText(true);
                Button undo = new Button("Undo");
                undo.setDisable(true);
                undo.setOnAction(e3 -> {
                    setupPage.g.undoLastMove();
                    setupPage.grid.updateGrid();
                });
                setupPage.g.getUndoStateP().addListener((o, oV, nV) -> {
                    if (nV == true) undo.setDisable(true);
                    else undo.setDisable(false);
                });
                Label undoMarkInfo = new Label("Click the button below to undo the previous dead stone marking:");
                undoMarkInfo.setMaxWidth(100);
                undoMarkInfo.setWrapText(true);
                undoMarkInfo.setVisible(false);
                Button undoMark = new Button("Undo mark");
                undoMark.setDisable(true);
                undoMark.setVisible(false);
                undoMark.setOnAction(e4 -> {
                    setupPage.g.s.undoMarkDeadStone();
                    setupPage.grid.updateGrid(setupPage.g.s);
                });
                Button done = new Button("Finished marking");
                done.setVisible(false);
                Label instructions = new Label("Game is over. Click on stones to mark them as dead, then click 'Finished marking' when done.");
                instructions.setMaxWidth(100);
                instructions.setWrapText(true);
                instructions.setVisible(false);
                setupPage.g.getPassCountP().addListener((o, oV, nV) -> {
                    if (nV.equals("2")) {
                        passInfo.setVisible(false);
                        pass.setVisible(false);
                        undoInfo.setVisible(false);
                        undo.setVisible(false);
                        instructions.setVisible(true);
                        undoMarkInfo.setVisible(true);
                        undoMark.setVisible(true);
                        done.setVisible(true);
                    }
                });
                setupPage.g.getReady().addListener((o, oV, nV) -> {
                    if (nV) {
                        setupPage.g.s.getUndoP().addListener((o2, oV2, nV2) -> {
                            if (nV2 == true) undoMark.setDisable(true);
                            else undoMark.setDisable(false);
                        });
                    }
                });
                done.setOnAction(e5 -> {
                    instructions.setVisible(false);
                    done.setVisible(false);
                    undoMarkInfo.setVisible(false);
                    undoMark.setVisible(false);
                    setupPage.g.setFinished();
                    setupPage.g.s.calculateFinalScores();
                    InformationBox.display("Game Complete", "Final scores:\n" + setupPage.g.getBlack() + ": " 
                                               + setupPage.g.s.getFinalScores()[0] + "\n" + setupPage.g.getWhite() 
                                               + ": " + setupPage.g.s.getFinalScores()[1]); //add new win%s?
                    primaryStage.setScene(scene1);
                    //hide button after finished? block all further action?
                });
                gameControl.getChildren().addAll(passInfo, pass, undoInfo, undo, instructions, done, undoMarkInfo, undoMark);
                layout2.setLeft(gameControl);
                scene2 = new Scene(layout2, 802, 702); //fix these random screen size values, maybe using: https://stackoverflow.com/questions/38216268/how-to-listen-resize-event-of-stage-in-javafx
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