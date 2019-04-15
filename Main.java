import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;//fix
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;//
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    
    //Button startGame;
    Scene gameSetupPage, gameScene, dashScene, loginScene, createUserScene;
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
        
        //setupPage = new SetupPage();
        VBox setupPage = new VBox();
        Label label1 = new Label("Enter P1 username:");
        Label label2 = new Label("Enter P1 first name:");
        Label label3 = new Label("Enter P1 last name:");
        Label label4 = new Label("Enter P2 username:");
        Label label5 = new Label("Enter P2 first name:");
        Label label6 = new Label("Enter P2 last name:");
        Label label7 = new Label("Enter grid size:");
        TextField username1 = new TextField();
        TextField fname1 = new TextField();
        TextField lname1 = new TextField();
        TextField username2 = new TextField();
        TextField fname2 = new TextField();
        TextField lname2 = new TextField();
        ToggleGroup gridSizes = new ToggleGroup();
        RadioButton r1 = new RadioButton("9");
        RadioButton r2 = new RadioButton("13");
        r1.setToggleGroup(gridSizes);
        r2.setToggleGroup(gridSizes);
        r2.setSelected(true);
        Button startGame = new Button("Start game");
        setupPage.getChildren().addAll(label1, username1, label2, fname1, label3, lname1, label4, username2, label5, fname2, label6, lname2, label7, r1, r2, startGame);
        startGame.setOnAction(e -> { //tidy up this huge button click?
            Player p1 = new Player(username1.getText(), fname1.getText(), lname1.getText());
            Player p2 = new Player(username2.getText(), fname2.getText(), lname2.getText());
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
        
        //user dashboard (including admin features if necessary) (outsource this to a new class?)
        BorderPane dashboard = new BorderPane();
        VBox topPanel = new VBox();
        HBox topRow = new HBox();
        Label dashIntro = new Label("Welcome to the user dashboard!");
        Label lastLogin = new Label("Last login:");
        Label lastLoginDate = new Label(); //get date
        topRow.getChildren().addAll(dashIntro, lastLogin, lastLoginDate);
        HBox secondRow = new HBox();
        Button leaderboardButton = new Button("Show leaderboard");
        //add on action
        Button gameSetupButton = new Button("Setup new game");
        gameSetupButton.setOnAction(e -> primaryStage.setScene(gameSetupPage));
        Button logout = new Button("Logout");
        logout.setOnAction(e -> primaryStage.setScene(loginScene));
        secondRow.getChildren().addAll(leaderboardButton, gameSetupButton, logout);
        HBox adminRow = new HBox();
        Button createUser = new Button("Create new user...");
        createUser.setOnAction(e -> primaryStage.setScene(createUserScene));
        adminRow.getChildren().addAll(createUser);
        topPanel.getChildren().addAll(topRow, secondRow, adminRow); //restrict adminRow to only appear for administrators somehow
        VBox information = new VBox();
        Label usernameLabel = new Label("Username:");
        Label usernameData = new Label(); //get username
        Label firstName = new Label("First name:");
        Label firstNameData = new Label(); //get first name
        Label secondName = new Label("Second name:");
        Label secondNameData = new Label(); //get second name
        Label winRate = new Label("Win rate (%):");
        Label winRateData = new Label(); //get winrate
        Label profilePic = new Label("Profile picture:");
        //somehow put the profile picture here
        information.getChildren().addAll(usernameLabel, usernameData, firstName, firstNameData, secondName, secondNameData, winRate, winRateData, profilePic); //all the profile pic obj here
        VBox news = new VBox();
        Label leaderboardPrev = new Label("Leaderboard position on last logout:");
        Label leaderboardPrevData = new Label(); //get old leaderboard position (from file presumably)
        Label leaderboardCur = new Label("Current leaderboard position:");
        Label leaderboardCurData = new Label(); //get leaderboard position
        Label newPlayers = new Label("New players since last logout:");
        Label newPlayersData = new Label(); //get new players
        Label gamesSince = new Label("Click for list of games completed since last login");
        Button gamesSinceButton = new Button("Games Completed"); //make this link to a tableview of games completed since last login
        news.getChildren().addAll(leaderboardPrev, leaderboardPrevData, leaderboardCur, leaderboardCurData, newPlayers, newPlayersData, gamesSince, gamesSinceButton);
        TableView historyTable = new TableView();
        //implement this table
        dashboard.setTop(topPanel);
        dashboard.setLeft(information);
        dashboard.setRight(news);
        dashboard.setCenter(historyTable);
        Scene dashScene = new Scene(dashboard, 600, 600);
        
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
        password.setMaxWidth(200);
        Button loginButton = new Button("Login");
        loginButton.requestFocus();
        loginButton.setOnAction(e -> {
            primaryStage.setScene(dashScene);
        });
        login.getChildren().addAll(welcome, username, password, loginButton);
        loginScene = new Scene(login, 600, 600);
        
        //game setup page (change this to provide a list of user opponents)
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
        TextField newUsername = new TextField(); //set max size
        Label chooseFName = new Label("Type first name below:");
        TextField newFName = new TextField();
        Label chooseLName = new Label("Type last name below:");
        TextField newLName = new TextField();
        Label chooseAdminID = new Label("Type new admin ID below:");
        TextField newAdminID = new TextField(); //prevent this from being a duplicate of other admin IDs
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
        Button createUserButton = new Button("Create user");
        createUserButton.setOnAction(e -> {
            //more stuff
            primaryStage.setScene(dashScene);
        });
        createUserLayout.getChildren().addAll(np, admin, chooseUsername,  newUsername, chooseFName, newFName, chooseLName, newLName, chooseAdminID, newAdminID, createUserButton);
        createUserScene = new Scene(createUserLayout, 600, 600);
        
        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}