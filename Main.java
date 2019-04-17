import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassCastException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    //Button startGame;
    Scene gameSetupPage, gameScene, dashScene, loginScene, createUserScene;
    static SetupPage setupPage;
    private ArrayList<User> loggedIn;
    private BooleanProperty authenticated;
    
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
        
        //setupPage = new SetupPage();
        VBox setupPage = new VBox();
        ArrayList<Administrator> adminList = new ArrayList<>();
        ArrayList<Player> playerList = new ArrayList<>();
        if (!(new File("userdata").isFile())) {
            Administrator defaultAdmin = new Administrator("admin", "password", "Default", "Administrator", 1);
            try {
                FileOutputStream fos = new FileOutputStream("userdata");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(defaultAdmin);
                oos.close();
                fos.close();
            }
            catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        boolean EoF = false;
        if (new File("userdata").isFile()) {
            try {
                FileInputStream fis = new FileInputStream("userdata");
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (!EoF) {
                    try {
                        User userLoaded = (User) ois.readObject();
                        try {
                            Administrator a = (Administrator) userLoaded;
                            adminList.add(a);
                        }
                        catch (ClassCastException cc) {
                            Player p = (Player) userLoaded;
                            playerList.add(p);
                        }
                    }
                    catch (EOFException eof) {
                        EoF = true;
                    }
                }
                ois.close();
                fis.close();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            for (Administrator a:adminList) {
                System.out.println(a.toString()); //just for testing
            }
            for (Player p:playerList) {
                System.out.println(p.toString()); //just for testing
            }
        }
        EoF = false;
        if (new File("gamedata").isFile()) {
            try {
                FileInputStream fis2 = new FileInputStream("gamedata");
                ObjectInputStream ois2 = new ObjectInputStream(fis2);
                while (!EoF) {
                    try {
                        GameRecord loaded = (GameRecord) ois2.readObject();
                        GameContainer.getGamesPlayed().add(loaded);
                    }
                    catch (EOFException eof) {
                        EoF = true;
                    }
                }
                ois2.close();
                fis2.close();
            }
            catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            for (GameRecord game:GameContainer.getGamesPlayed()) {
                System.out.println(game.getDateCompleted().toString()); //just for testing
            }
        }
        BooleanProperty setupTime = new SimpleBooleanProperty(false);
        setupTime.addListener((observable, oV0, nV0) -> {
            if (nV0) {
                Label curUser = new Label("Your username: " + loggedIn.get(0).getUsername());
                Label opponentInfo = new Label("Players you can play against are listed in the drop-down list below. If the list is empty, you first need to create a new user.");
                opponentInfo.setWrapText(true);
                ChoiceBox<String> userDropDownList = new ChoiceBox<>();
                for (Administrator a:adminList) {
                    if (!a.getUsername().equals(loggedIn.get(0).getUsername())) userDropDownList.getItems().addAll(a.getUsername());
                }
                for (Player p:playerList) {
                    if (!p.getUsername().equals(loggedIn.get(0).getUsername())) userDropDownList.getItems().addAll(p.getUsername());
                }
                Label label7 = new Label("Select grid size:");
                ToggleGroup gridSizes = new ToggleGroup();
                RadioButton r1 = new RadioButton("9");
                RadioButton r2 = new RadioButton("13");
                r1.setToggleGroup(gridSizes);
                r2.setToggleGroup(gridSizes);
                r2.setSelected(true);
                Button startGame = new Button("Start game");
                setupPage.getChildren().addAll(curUser, opponentInfo, userDropDownList, label7, r1, r2, startGame);
                startGame.setOnAction(e -> { //tidy up this huge button click?
                    User p1 = loggedIn.get(0);
                    //code on next line adapted from https://stackoverflow.com/questions/17526608/how-to-find-an-object-in-an-arraylist-by-property
                    User p2 = adminList.stream().filter(user -> userDropDownList.getValue().equals(user.getUsername())).findFirst().orElse(null);
                    if (p2 == null) p2 = playerList.stream().filter(user -> userDropDownList.getValue().equals(user.getUsername())).findFirst().orElse(null);
                    GameContainer.setG(Integer.parseInt(((RadioButton)gridSizes.getSelectedToggle()).getText()), p1, p2);
                    GameContainer.setGrid(Integer.parseInt(((RadioButton)gridSizes.getSelectedToggle()).getText()));
                    GameContainer.getGrid().setAlignment(Pos.CENTER);
                    GameContainer.getGrid().setMinSize(400, 400); //edit and fix the bad ratioing that has appeared
                    GameContainer.getGrid().setMaxSize(600, 600);
                    //layout of page 2
                    BorderPane layout2 = new BorderPane();
                    layout2.setCenter(GameContainer.getGrid());
                    VBox gameInfo = new VBox();
                    Label black = new Label("Black: " + GameContainer.getG().getBlack());
                    Label white = new Label("White: " + GameContainer.getG().getWhite());
                    Label tn = new Label("Turns played: ");
                    Label tnLive = new Label();
                    tnLive.textProperty().bind(GameContainer.getG().getTurnNoP());
                    Label capsB = new Label("Captures by black: ");
                    Label capsBLive = new Label();
                    capsBLive.textProperty().bind(GameContainer.getG().getCapsBP());
                    Label capsW = new Label("Captures by white: ");
                    Label capsWLive = new Label();
                    capsWLive.textProperty().bind(GameContainer.getG().getCapsWP());
                    Label pc = new Label("Pass count: ");
                    Label pcLive = new Label();
                    pcLive.textProperty().bind(GameContainer.getG().getPassCountP());
                    Label CPT = new Label("Current player's turn: ");
                    Label CPTLive = new Label();
                    CPTLive.textProperty().bind(GameContainer.getG().getCurrentPlayerTurnP());
                    gameInfo.getChildren().addAll(black, white, tn, tnLive, capsB, capsBLive, capsW, capsWLive, pc, pcLive, CPT, CPTLive);
                    layout2.setRight(gameInfo);
                    VBox gameControl = new VBox();
                    Label passInfo = new Label("Click the button below to pass your turn:");
                    passInfo.setMaxWidth(100);
                    passInfo.setWrapText(true);
                    Button pass = new Button("Pass");
                    pass.setOnAction(e2 -> GameContainer.getG().pass());
                    Label undoInfo = new Label("Click the button below to undo the previous move (unless that move was passed):");
                    undoInfo.setMaxWidth(100);
                    undoInfo.setWrapText(true);
                    Button undo = new Button("Undo");
                    undo.setDisable(true);
                    undo.setOnAction(e3 -> {
                        GameContainer.getG().undoLastMove();
                        GameContainer.getGrid().updateGrid();
                    });
                    GameContainer.getG().getUndoStateP().addListener((o, oV, nV) -> {
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
                        GameContainer.getS().undoMarkDeadStone();
                        GameContainer.getGrid().updateGrid(GameContainer.getS());
                    });
                    Button done = new Button("Finished marking");
                    done.setVisible(false);
                    Label instructions = new Label("Game is over. Click on stones to mark them as dead, then click 'Finished marking' when done.");
                    instructions.setMaxWidth(100);
                    instructions.setWrapText(true);
                    instructions.setVisible(false);
                    GameContainer.getG().getPassCountP().addListener((o, oV, nV) -> {
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
                    GameContainer.getG().getReady().addListener((o, oV, nV) -> {
                        if (nV) {
                            GameContainer.getS().getUndoP().addListener((o2, oV2, nV2) -> {
                                if (nV2 == true) undoMark.setDisable(true);
                                else undoMark.setDisable(false);
                            });
                        }
                        if (true) { //sort this out
                            Label deadBlacks = new Label("Dead black stones:");
                            Label deadBlacksLive = new Label();
                            deadBlacksLive.textProperty().bind(GameContainer.getS().getDbP());
                            Label deadWhites = new Label("Dead white stones:");
                            Label deadWhitesLive = new Label();
                            deadWhitesLive.textProperty().bind(GameContainer.getS().getDwP());
                            gameInfo.getChildren().addAll(deadBlacks, deadBlacksLive, deadWhites, deadWhitesLive);
                        }
                    });
                    done.setOnAction(e5 -> {
                        instructions.setVisible(false);
                        done.setVisible(false);
                        undoMarkInfo.setVisible(false);
                        undoMark.setVisible(false);
                        GameContainer.getG().setFinished();
                        GameContainer.getS().calculateFinalScores();
                        InformationBox.display("Game Complete", "Final scores:\n" + GameContainer.getG().getBlack() + ": " 
                                                   + GameContainer.getS().getFinalScores()[0] + "\n" + GameContainer.getG().getWhite() 
                                                   + ": " + GameContainer.getS().getFinalScores()[1]); //add new win%s?
                        primaryStage.setScene(gameSetupPage);
                        primaryStage.centerOnScreen();
                    });
                    gameControl.getChildren().addAll(passInfo, pass, undoInfo, undo, instructions, done, undoMarkInfo, undoMark);
                    layout2.setLeft(gameControl);
                    gameScene = new Scene(layout2, 802, 702); //fix these random screen size values, maybe using: https://stackoverflow.com/questions/38216268/how-to-listen-resize-event-of-stage-in-javafx
                    primaryStage.setScene(gameScene);
                    primaryStage.centerOnScreen();
                });
            }
        });
        
        //user dashboard (outsource this to a new class?)
        BooleanProperty makeDash = new SimpleBooleanProperty(false);
        makeDash.addListener((observable, oV0, nV0) -> {
            if (nV0) {
                BorderPane dashboard = new BorderPane();
                VBox topPanel = new VBox();
                HBox topRow = new HBox();
                Label dashIntro = new Label("Welcome to the user dashboard!");
                Label lastLogin = new Label("Previous login:");
                Label lastLoginDate = new Label(loggedIn.get(0).getPreviousLastLoginTime().toString());
                topRow.getChildren().addAll(dashIntro, lastLogin, lastLoginDate);
                HBox secondRow = new HBox();
                Button leaderboardButton = new Button("Show leaderboard");
                //add on action
                Button gameSetupButton = new Button("Setup new game");
                gameSetupButton.setOnAction(e -> {
                    setupTime.set(true);
                    primaryStage.setScene(gameSetupPage);
                });
                Button logout = new Button("Logout");
                logout.setOnAction(e -> {
                    authenticated.set(false);
                    setupTime.set(false);
                    makeDash.set(false);
                    loggedIn.clear();
                    setupPage.getChildren().clear();
                    try {
                        FileOutputStream fos2 = new FileOutputStream("userdata");
                        ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
                        for (Administrator a:adminList) {
                            try {
                                oos2.writeObject(a);
                            }
                            catch  (IOException ioe) {
                                System.out.println(ioe.getMessage());
                            }
                        }
                        for (Player p:playerList) {
                            try {
                                oos2.writeObject(p);
                            }
                            catch  (IOException ioe) {
                                System.out.println(ioe.getMessage());
                            }
                        }
                        oos2.close();
                        fos2.close();
                    }
                    catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }
                    try {
                        FileOutputStream fos3 = new FileOutputStream("gamedata");
                        ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
                        for (GameRecord game:GameContainer.getGamesPlayed()) {
                            try {
                                oos3.writeObject(game);
                            }
                            catch (IOException ioe) {
                                System.out.println(ioe.getMessage());
                            }
                        }
                        oos3.close();
                        fos3.close();
                    }
                    catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }
                    primaryStage.setScene(loginScene);
                });
                secondRow.getChildren().addAll(leaderboardButton, gameSetupButton, logout);
                HBox adminRow = new HBox();
                Button createUser = new Button("Create new user...");
                createUser.setOnAction(e -> primaryStage.setScene(createUserScene));
                adminRow.getChildren().addAll(createUser);
                if (loggedIn.get(0).getClass().getName().equals("Player")) {
                    topPanel.getChildren().addAll(topRow, secondRow);
                }
                else topPanel.getChildren().addAll(topRow, secondRow, adminRow);
                VBox information = new VBox();
                Label usernameLabel = new Label("Username:");
                Label usernameData = new Label(loggedIn.get(0).getUsername());
                Label firstName = new Label("First name:");
                Label firstNameData = new Label(loggedIn.get(0).getFname());
                Label secondName = new Label("Second name:");
                Label secondNameData = new Label(loggedIn.get(0).getLname());
                Label winRate = new Label("Win rate (%):");
                Label winRateData = new Label(""+(int)loggedIn.get(0).getWinRate());
                Label profilePic = new Label("Profile picture:");
                //somehow put the profile picture here
                information.getChildren().addAll(usernameLabel, usernameData, firstName, firstNameData, secondName, secondNameData, winRate, winRateData, profilePic); //add the profile pic obj here
                VBox news = new VBox();
                Label leaderboardPrev = new Label("Leaderboard position on last logout:");
                Label leaderboardPrevData = new Label(); //get old leaderboard position (from file presumably)
                Label leaderboardCur = new Label("Current leaderboard position:");
                Label leaderboardCurData = new Label(); //get leaderboard position
                Label newPlayers = new Label("New players since last logout:");
                Label newPlayersData = new Label(); //get new players
                Label gamesSince = new Label("Click for list of games completed since last login");
                gamesSince.setWrapText(true);
                Button gamesSinceButton = new Button("Games Completed"); //make this link to a tableview of games completed since last login
                gamesSinceButton.setOnAction(e -> {
                    //implement this
                });
                news.getChildren().addAll(leaderboardPrev, leaderboardPrevData, leaderboardCur, leaderboardCurData, newPlayers, newPlayersData, gamesSince, gamesSinceButton);
                ObservableList<GameRecord> gameRecords = FXCollections.observableArrayList();
                for (GameRecord game:GameContainer.getGamesPlayed()) {
                    if (game.getWinner().getUsername().equals(loggedIn.get(0).getUsername()) || game.getLoser().getUsername().equals(loggedIn.get(0).getUsername())) {
                        gameRecords.add(game);
                    }
                }
                TableView<GameRecord> myHistoryTable = new TableView<>();
                TableColumn<GameRecord, String> dateCol = new TableColumn<>("Date Completed");
                dateCol.setMinWidth(100);
                dateCol.setCellValueFactory(new PropertyValueFactory<>("dateCompleted"));
                TableColumn<GameRecord, String> winnerCol = new TableColumn<>("Winner");
                winnerCol.setMinWidth(100);
                winnerCol.setCellValueFactory(new PropertyValueFactory<>("winner"));
                TableColumn<GameRecord, String> loserCol = new TableColumn<>("Loser");
                loserCol.setMinWidth(100);
                loserCol.setCellValueFactory(new PropertyValueFactory<>("loser"));
                myHistoryTable.setItems(gameRecords);
                myHistoryTable.getColumns().add(dateCol);
                myHistoryTable.getColumns().add(winnerCol);
                myHistoryTable.getColumns().add(loserCol);
                dashboard.setTop(topPanel);
                dashboard.setLeft(information);
                dashboard.setRight(news);
                dashboard.setCenter(myHistoryTable);
                dashScene = new Scene(dashboard, 600, 600);
            }
        });
        
        //login page
        VBox login = new VBox(10);
        login.setAlignment(Pos.CENTER);
        Label welcome = new Label("Welcome to the Go app, please type your username and password in the fields below then click 'Login'.");
        welcome.setWrapText(true);
        welcome.setMaxWidth(300);
        TextField username = new TextField(); //limit the input to this and all other text fields...
        username.setPromptText("Username");
        username.setMaxWidth(200);
        TextField password = new TextField();
        password.setPromptText("Password");
        password.setMaxWidth(200); //hide the password text
        Button loginButton = new Button("Login");
        loginButton.requestFocus();
        authenticated = new SimpleBooleanProperty(false);
        loggedIn = new ArrayList<>();
        loginButton.setOnAction(e -> {
            for (Administrator a:adminList) {
                if (username.getText().equals(a.getUsername()) && password.getText().equals(a.getPassword())) {
                    a.setLastLoginTime(ZonedDateTime.now());
                    loggedIn.add(a);
                    makeDash.set(true);
                    authenticated.set(true);
                    primaryStage.setScene(dashScene);
                }
            }
            for (Player p:playerList) {
                if (username.getText().equals(p.getUsername()) && password.getText().equals(p.getPassword())) {
                    p.setLastLoginTime(ZonedDateTime.now());
                    loggedIn.add(p);
                    makeDash.set(true);
                    authenticated.set(true);
                    primaryStage.setScene(dashScene);
                }
            }
            if (!authenticated.getValue()) {
                InformationBox.display("Invalid login details", "The username and password combination was not recognised.");
            }
        });
        login.getChildren().addAll(welcome, username, password, loginButton);
        loginScene = new Scene(login, 600, 600);
        
        //game setup page
        HBox bottomRow = new HBox();
        bottomRow.setPadding(new Insets(10,10,10,10));
        bottomRow.setSpacing(10);
        Button backToDash = new Button("Return to dashboard");
        backToDash.setOnAction(e -> primaryStage.setScene(dashScene));
        bottomRow.getChildren().addAll(backToDash);
        BorderPane layout1 = new BorderPane();
        layout1.setCenter(setupPage);
        layout1.setTop(menuBar);
        layout1.setBottom(bottomRow);
        gameSetupPage = new Scene(layout1, 600, 600);
        
        //create users page
        VBox createUserLayout = new VBox();
        ToggleGroup userTypes = new ToggleGroup();
        RadioButton np = new RadioButton("Normal Player");
        RadioButton admin = new RadioButton("Administrator");
        np.setToggleGroup(userTypes);
        admin.setToggleGroup(userTypes);
        np.setSelected(true);
        Label chooseUsername = new Label("Type username below:");
        TextField newUsername = new TextField(); //set max size and restrict this to have no spaces
        Label choosePass = new Label("Type password below:");
        TextField newPass = new TextField();
        Label chooseFName = new Label("Type first name below:");
        TextField newFName = new TextField();
        Label chooseLName = new Label("Type last name below:");
        TextField newLName = new TextField();
        Label chooseAdminID = new Label("Type new admin ID below:");
        TextField newAdminID = new TextField();
        chooseAdminID.setVisible(false);
        newAdminID.setVisible(false);
        admin.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
            if (isSelected) {
                chooseAdminID.setVisible(true);
                newAdminID.setVisible(true);
            }
            else {
                chooseAdminID.setVisible(false);
                newAdminID.setVisible(false);
            }
        });
        BooleanProperty notTaken = new SimpleBooleanProperty(true);
        Button createUserButton = new Button("Create user");
        createUserButton.setOnAction(e -> {
            if (admin.isSelected()) {
                notTaken.set(true);
                for (Administrator a:adminList) {
                    if (newAdminID.getText().equals(""+a.getAdminID())) {
                        notTaken.set(false);
                        InformationBox.display("Admin ID already taken", "The chosen admin ID is already taken, please choose another.");
                    }
                }
                if (notTaken.getValue()) {
                    Administrator newAdmin = new Administrator(newUsername.getText(), newPass.getText(), newFName.getText(), newLName.getText(), Integer.parseInt(newAdminID.getText()));
                    adminList.add(newAdmin);
                    InformationBox.display("Created new administrator", "New administrator successfully created");
                }
            }
            else {
                notTaken.set(true);
                for (Administrator a:adminList) {
                    if (newUsername.getText().equals(a.getUsername())) {
                        notTaken.set(false);
                        InformationBox.display("Username already taken", "The chosen username is already taken, please choose another.");
                    }
                }
                for (Player p:playerList) {
                    if (newUsername.getText().equals(p.getUsername())) {
                        notTaken.set(false);
                        InformationBox.display("Username already taken", "The chosen username is already taken, please choose another.");
                    }
                }
                if (notTaken.getValue()) {
                    Player newPlayer = new Player(newUsername.getText(), newPass.getText(), newFName.getText(), newLName.getText());
                    playerList.add(newPlayer);
                    InformationBox.display("Created new player", "New player successfully created");
                }
            }
        });
        Button leaveCreateUser = new Button("Return to user dashboard");
        leaveCreateUser.setOnAction(e -> primaryStage.setScene(dashScene));
        createUserLayout.getChildren().addAll(np, admin, chooseUsername, newUsername, choosePass, newPass, chooseFName, newFName, chooseLName, newLName, chooseAdminID, newAdminID, createUserButton, leaveCreateUser);
        createUserScene = new Scene(createUserLayout, 600, 600);
        
        primaryStage.setOnCloseRequest(e -> {
            try {
                FileOutputStream fos2 = new FileOutputStream("userdata");
                ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
                for (Administrator a:adminList) {
                    try {
                        oos2.writeObject(a);
                    }
                    catch  (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }
                }
                for (Player p:playerList) {
                    try {
                        oos2.writeObject(p);
                    }
                    catch  (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }
                }
                oos2.close();
                fos2.close();
            }
            catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            try {
                FileOutputStream fos3 = new FileOutputStream("gamedata");
                ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
                for (GameRecord game:GameContainer.getGamesPlayed()) {
                    try {
                        oos3.writeObject(game);
                    }
                    catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }
                }
                oos3.close();
                fos3.close();
            }
            catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            primaryStage.close();
        });
        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}