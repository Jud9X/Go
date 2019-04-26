import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassCastException;
import java.time.temporal.ChronoUnit;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Holds the stage, all of the scenes and almost all of the GUI controls.
 * This is a large class partly because of the constraint that it is only allowed to contain one method.
 * @author Oliver
 * @version 2.5
 * */
public class Main extends Application {
    
    public final String GRID_SIZE_OPTION_1 = "9";
    public final String GRID_SIZE_OPTION_2 = "13";
    private Scene gameSetupPage;
    private Scene gameScene;
    private Scene dashScene;
    private Scene loginScene;
    private Scene createUserScene;
    private ArrayList<User> loggedIn;
    private BooleanProperty authenticated;
    private VBox news;
    private Label leaderboardCurData;
    
    public static void main(final String[] args) {
        launch(args);
    }
        
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Play Go!");
        
        VBox setupPage = new VBox();
        setupPage.setPadding(new Insets(10,10,10,10));
        setupPage.setSpacing(10);
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
        BooleanProperty makeDash = new SimpleBooleanProperty(false);
        BooleanProperty setupTime = new SimpleBooleanProperty(false);
        setupTime.addListener((observable, oV0, nV0) -> {
            if (nV0) {
                Label setupIntroInfo = new Label("Set up a new game below");
                Label curUser = new Label("Your username: " + loggedIn.get(0).getUsername());
                setupPage.setMargin(curUser, new Insets(10, 0, 0, 0));
                Label opponentInfo = new Label("Players you can play against are listed in the drop-down list below. If the list is empty, you first need to create a new user.");
                opponentInfo.setWrapText(true);
                ChoiceBox<String> userDropDownList = new ChoiceBox<>();
                for (Administrator a:adminList) {
                    if (!a.getUsername().equals(loggedIn.get(0).getUsername())) {
                        userDropDownList.getItems().addAll(a.getUsername());
                    }
                }
                for (Player p:playerList) {
                    if (!p.getUsername().equals(loggedIn.get(0).getUsername())) {
                        userDropDownList.getItems().addAll(p.getUsername());
                    }
                }
                Label chooseGridSize = new Label("Select grid size:");
                setupPage.setMargin(chooseGridSize, new Insets(20, 0, 0, 0));
                ToggleGroup gridSizes = new ToggleGroup();
                RadioButton r1 = new RadioButton(GRID_SIZE_OPTION_1);
                RadioButton r2 = new RadioButton(GRID_SIZE_OPTION_2);
                r1.setToggleGroup(gridSizes);
                r2.setToggleGroup(gridSizes);
                r2.setSelected(true);
                Button startGame = new Button("Start game");
                setupPage.setMargin(startGame, new Insets(20, 0, 0, 0));
                setupPage.getChildren().addAll(setupIntroInfo, curUser, opponentInfo, userDropDownList, chooseGridSize, r1, r2, startGame);
                startGame.setOnAction(e -> {
                    User p1 = loggedIn.get(0);
                    //code on next line adapted from https://stackoverflow.com/questions/17526608/how-to-find-an-object-in-an-arraylist-by-property
                    User p2 = adminList.stream().filter(user -> userDropDownList.getValue().equals(user.getUsername())).findFirst().orElse(null); //make sure that a value in userdropdownlist is selected first
                    if (p2 == null) {
                        p2 = playerList.stream().filter(user -> userDropDownList.getValue().equals(user.getUsername())).findFirst().orElse(null);
                    }
                    GameContainer.setG(Integer.parseInt(((RadioButton)gridSizes.getSelectedToggle()).getText()), p1, p2);
                    GameContainer.setGrid(Integer.parseInt(((RadioButton)gridSizes.getSelectedToggle()).getText()));
                    GameContainer.getGrid().setAlignment(Pos.CENTER);
                    GameContainer.getGrid().setMinSize(400, 400);
                    GameContainer.getGrid().setMaxSize(600, 600);
                    //layout of page 2
                    BorderPane layout2 = new BorderPane();
                    layout2.setCenter(GameContainer.getGrid());
                    VBox gameInfo = new VBox();
                    gameInfo.setPadding(new Insets(10,10,10,10));
                    gameInfo.setSpacing(10);
                    TextFlow firstPlayer = new TextFlow();
                    Text black1 = new Text("Black: ");
                    black1.setStyle("-fx-font-weight: bold");
                    Text black2 = new Text(GameContainer.getG().getBlack());
                    firstPlayer.getChildren().addAll(black1, black2);
                    TextFlow secondPlayer = new TextFlow();
                    Text white1 = new Text("White: ");
                    white1.setStyle("-fx-font-weight: bold");
                    Text white2 = new Text(GameContainer.getG().getWhite());
                    secondPlayer.getChildren().addAll(white1, white2);
                    Label tn = new Label("Turns played: ");
                    tn.setStyle("-fx-font-weight: bold");
                    gameInfo.setMargin(tn, new Insets(20, 0, 0, 0));
                    Label tnLive = new Label();
                    tnLive.textProperty().bind(GameContainer.getG().getTurnNoP());
                    Label capsB = new Label("Captures by black: ");
                    capsB.setStyle("-fx-font-weight: bold");
                    gameInfo.setMargin(capsB, new Insets(10, 0, 0, 0));
                    Label capsBLive = new Label();
                    capsBLive.textProperty().bind(GameContainer.getG().getCapsBP());
                    Label capsW = new Label("Captures by white: ");
                    capsW.setStyle("-fx-font-weight: bold");
                    gameInfo.setMargin(capsW, new Insets(10, 0, 0, 0));
                    Label capsWLive = new Label();
                    capsWLive.textProperty().bind(GameContainer.getG().getCapsWP());
                    Label pc = new Label("Pass count: ");
                    pc.setStyle("-fx-font-weight: bold");
                    gameInfo.setMargin(pc, new Insets(10, 0, 0, 0));
                    Label pcLive = new Label();
                    pcLive.textProperty().bind(GameContainer.getG().getPassCountP());
                    Label CPT = new Label("Current player's turn: ");
                    CPT.setStyle("-fx-font-weight: bold");
                    gameInfo.setMargin(CPT, new Insets(10, 0, 0, 0));
                    Label CPTLive = new Label();
                    CPTLive.textProperty().bind(GameContainer.getG().getCurrentPlayerTurnP());
                    gameInfo.getChildren().addAll(firstPlayer, secondPlayer, tn, tnLive, capsB, capsBLive, capsW, capsWLive, pc, pcLive, CPT, CPTLive);
                    layout2.setRight(gameInfo);
                    VBox gameControl = new VBox();
                    gameControl.setPadding(new Insets(10,10,10,10));
                    gameControl.setSpacing(10);
                    Label passInfo = new Label("Click the button below to pass your turn:");
                    passInfo.setMaxWidth(100);
                    passInfo.setWrapText(true);
                    Button pass = new Button("Pass");
                    pass.setOnAction(e2 -> GameContainer.getG().pass());
                    Label undoInfo = new Label("Click the button below to undo the previous move (unless that move was passed):");
                    undoInfo.setMaxWidth(100);
                    undoInfo.setWrapText(true);
                    gameControl.setMargin(undoInfo, new Insets(20, 0, 0, 0));
                    Button undo = new Button("Undo");
                    undo.setDisable(true);
                    undo.setOnAction(e3 -> {
                        GameContainer.getG().undoLastMove();
                        GameContainer.getGrid().updateGrid();
                    });
                    GameContainer.getG().getUndoStateP().addListener((o, oV, nV) -> {
                        if (nV == true) {
                            undo.setDisable(true);
                        }
                        else {
                            undo.setDisable(false);
                        }
                    });
                    Label undoMarkInfo = new Label("Click the button below to undo the previous dead stone marking:");
                    undoMarkInfo.setMaxWidth(100);
                    undoMarkInfo.setWrapText(true);
                    undoMarkInfo.setVisible(false);
                    gameControl.setMargin(undoMarkInfo, new Insets(20, 0, 0, 0));
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
                        if (nV.equals("" + GameContainer.getG().PASS_LIMIT)) {
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
                        if (nV == true) {
                            GameContainer.getS().getUndoP().addListener((o2, oV2, nV2) -> {
                                if (nV2 == true) {
                                    undoMark.setDisable(true);
                                }
                                else {
                                    undoMark.setDisable(false);
                                }
                            });
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
                        InformationBox.display("Game Complete", "Final scores:\n" 
                            + GameContainer.getG().getBlack() + ": " 
                            + GameContainer.getS().getFinalScores()[0] + "\n" 
                            + GameContainer.getG().getWhite() + ": " 
                            + GameContainer.getS().getFinalScores()[1] + "\nWinner: " 
                            + GameContainer.getS().getWinnerName() 
                            + "\nNote that if the scores are the same then white "
                            + "is declared winner due to starting second.");
                        primaryStage.setScene(gameSetupPage);
                        primaryStage.centerOnScreen();
                        ArrayList<User> tempAllUsers = new ArrayList<>();
                        for (Administrator a:adminList) {
                            tempAllUsers.add(a);
                        }
                        for (Player p:playerList) {
                            tempAllUsers.add(p);
                        }
                        for (int i = 0; i < tempAllUsers.size() - 1; ++i) {
                            for (int j = 0; j < tempAllUsers.size() - i - 1; ++j) {
                                if (tempAllUsers.get(j).getWinRate() > tempAllUsers.get(j + 1).getWinRate()) {
                                    User t = tempAllUsers.get(j);
                                    tempAllUsers.set(j, tempAllUsers.get(j + 1));
                                    tempAllUsers.set(j + 1, t);
                                }
                            }
                        }
                        for (int i = tempAllUsers.size(); i > 0; --i) {
                            tempAllUsers.get(i - 1).setLeaderboardPosition(tempAllUsers.size() - i + 1);
                        }
                        makeDash.set(false);
                    });
                    gameControl.getChildren().addAll(passInfo, pass, undoInfo, undo, instructions, done, undoMarkInfo, undoMark);
                    layout2.setLeft(gameControl);
                    gameScene = new Scene(layout2, 862, 672);
                    primaryStage.setScene(gameScene);
                    primaryStage.centerOnScreen();
                });
            }
        });
        
        //user dashboard
        makeDash.addListener((observable, oV0, nV0) -> {
            if (nV0) {
                BorderPane dashboard = new BorderPane();
                VBox topPanel = new VBox();
                topPanel.setPadding(new Insets(10,10,10,10));
                topPanel.setSpacing(10);
                HBox secondRow = new HBox();
                secondRow.setPadding(new Insets(1,1,1,1));
                secondRow.setSpacing(10);
                Label dashIntro = new Label("Welcome to the user dashboard!");
                dashIntro.setStyle("-fx-font-weight: bold");
                Label lastLogin = new Label("Previous login: ");
                Label lastLoginDate;
                if (loggedIn.get(0).getPreviousLoginTime() == null) {
                    lastLoginDate = new Label("This is your first time logging in");
                }
                else {
                    lastLoginDate = new Label(loggedIn.get(0).getPreviousLoginTime().toString());
                }
                secondRow.getChildren().addAll(lastLogin, lastLoginDate);
                HBox thirdRow = new HBox();
                thirdRow.setPadding(new Insets(1,1,1,1));
                thirdRow.setSpacing(10);
                Button leaderboardButton = new Button("Show leaderboard");
                leaderboardButton.setOnAction(e -> {
                    VBox leaderboardLayout = new VBox();
                    leaderboardLayout.setPadding(new Insets(10,10,10,10));
                    leaderboardLayout.setSpacing(10);
                    ObservableList<User> leaderboardRecords = FXCollections.observableArrayList();
                    for (Administrator a:adminList) {
                        leaderboardRecords.add(a);
                    }
                    for (Player p:playerList) {
                        leaderboardRecords.add(p);
                    }
                    TableView<User> leaderboardTable = new TableView<>();
                    TableColumn<User, Integer> leaderboardPositionCol = new TableColumn<>("Rank");
                    leaderboardPositionCol.setMinWidth(100);
                    leaderboardPositionCol.setCellValueFactory(new PropertyValueFactory<>("leaderboardPosition"));
                    leaderboardPositionCol.setSortType(TableColumn.SortType.ASCENDING);
                    TableColumn<User, String> usernameCol = new TableColumn<>("Username");
                    usernameCol.setMinWidth(100);
                    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
                    TableColumn<User, Integer> gameCountCol = new TableColumn<>("Games Played");
                    gameCountCol.setMinWidth(100);
                    gameCountCol.setCellValueFactory(new PropertyValueFactory<>("gameCount"));
                    TableColumn<User, Integer> winRateCol = new TableColumn<>("Win Rate (%)");
                    winRateCol.setMinWidth(100);
                    winRateCol.setCellValueFactory(new PropertyValueFactory<>("winRate"));
                    leaderboardTable.setItems(leaderboardRecords);
                    leaderboardTable.getColumns().add(leaderboardPositionCol);
                    leaderboardTable.getColumns().add(usernameCol);
                    leaderboardTable.getColumns().add(gameCountCol);
                    leaderboardTable.getColumns().add(winRateCol);
                    leaderboardTable.getSortOrder().add(leaderboardPositionCol);
                    Button leaveLeaderboard = new Button("Return to dashboard");
                    leaveLeaderboard.setOnAction(e2 -> {
                        primaryStage.setScene(dashScene);
                    });
                    leaderboardLayout.getChildren().addAll(leaderboardTable, leaveLeaderboard);
                    Scene leaderboardScene = new Scene(leaderboardLayout, 600, 600);
                    primaryStage.setScene(leaderboardScene);
                });
                Button gameSetupButton = new Button("Setup new game");
                gameSetupButton.setOnAction(e -> {
                    setupTime.set(true);
                    primaryStage.setScene(gameSetupPage);
                });
                Button logout = new Button("Logout");
                logout.setOnAction(e -> {
                    loggedIn.get(0).setPreviousLeaderboardPosition(loggedIn.get(0).getLeaderboardPosition());
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
                thirdRow.getChildren().addAll(leaderboardButton, gameSetupButton, logout);
                thirdRow.setAlignment(Pos.CENTER);
                HBox adminRow = new HBox();
                adminRow.setPadding(new Insets(5,5,5,5));
                adminRow.setSpacing(10);
                Label adminOptionInfo = new Label("Administrator option: ");
                Button createUser = new Button("Create new user...");
                createUser.setOnAction(e -> {
                    primaryStage.setScene(createUserScene);
                    setupTime.set(false);
                    setupPage.getChildren().clear();
                });
                adminRow.getChildren().addAll(adminOptionInfo, createUser);
                adminRow.setAlignment(Pos.CENTER);
                if (loggedIn.get(0).getClass().getName().equals("Player")) {
                    topPanel.getChildren().addAll(dashIntro, secondRow, thirdRow);
                }
                else {
                    topPanel.getChildren().addAll(dashIntro, secondRow, thirdRow, adminRow);
                }
                VBox information = new VBox();
                information.setPadding(new Insets(10,10,10,10));
                information.setSpacing(10);
                Label usernameLabel = new Label("Username:");
                Label usernameData = new Label(loggedIn.get(0).getUsername());
                Label firstName = new Label("First name:");
                Label firstNameData = new Label(loggedIn.get(0).getFname());
                Label secondName = new Label("Second name:");
                Label secondNameData = new Label(loggedIn.get(0).getLname());
                Label winRate = new Label("Win rate (%):");
                Label winRateData = new Label("" + loggedIn.get(0).getWinRate());
                Label profilePic = new Label("Profile picture:");
                ImageView iv;
                if (loggedIn.get(0).getProfileImg() == 1) {
                    Image profileImage = new Image("icons8-anonymous-mask-64.png");
                    iv = new ImageView();
                    iv.setImage(profileImage);
                }
                else if (loggedIn.get(0).getProfileImg() == 2) {
                    Image profileImage = new Image("icons8-cat-profile-64.png");
                    iv = new ImageView();
                    iv.setImage(profileImage);
                }
                else if (loggedIn.get(0).getProfileImg() == 3) {
                    Image profileImage = new Image("icons8-contacts-64.png");
                    iv = new ImageView();
                    iv.setImage(profileImage);
                }
                else if (loggedIn.get(0).getProfileImg() == 4) {
                    Image profileImage = new Image("icons8-male-user-64.png");
                    iv = new ImageView();
                    iv.setImage(profileImage);
                }
                else if (loggedIn.get(0).getProfileImg() == 5) {
                    Image profileImage = new Image("icons8-person-female-64.png");
                    iv = new ImageView();
                    iv.setImage(profileImage);
                }
                else if (loggedIn.get(0).getProfileImg() == 6) {
                    Image profileImage = new Image("icons8-user-64.png");
                    iv = new ImageView();
                    iv.setImage(profileImage);
                }
                else {
                    iv = new ImageView();
                    iv.setImage(null);
                }
                Button choosePic = new Button("Choose picture");
                choosePic.setOnAction(e -> {
                    VBox choosePicLayout = new VBox();
                    choosePicLayout.setPadding(new Insets(10,10,10,10));
                    choosePicLayout.setSpacing(10);
                    Label chooseAPic = new Label("Please select a profile picture from the list below:");
                    chooseAPic.setWrapText(true);
                    ToggleGroup profilePictures = new ToggleGroup();
                    RadioButton r0 = new RadioButton("None");
                    RadioButton r1 = new RadioButton("Anonymous");
                    RadioButton r2 = new RadioButton("Cat");
                    RadioButton r3 = new RadioButton("Contacts");
                    RadioButton r4 = new RadioButton("Male");
                    RadioButton r5 = new RadioButton("Female");
                    RadioButton r6 = new RadioButton("User");
                    r0.setToggleGroup(profilePictures);
                    r1.setToggleGroup(profilePictures);
                    r2.setToggleGroup(profilePictures);
                    r3.setToggleGroup(profilePictures);
                    r4.setToggleGroup(profilePictures);
                    r5.setToggleGroup(profilePictures);
                    r6.setToggleGroup(profilePictures);
                    r0.setSelected(true);
                    Button doneChoosing = new Button("Finish and return");
                    doneChoosing.setOnAction(e2 -> {
                        if (r1.isSelected()) {
                            loggedIn.get(0).setProfileImg(1);
                        }
                        else if (r2.isSelected()) {
                            loggedIn.get(0).setProfileImg(2);
                        }
                        else if (r3.isSelected()) {
                            loggedIn.get(0).setProfileImg(3);
                        }
                        else if (r4.isSelected()) {
                            loggedIn.get(0).setProfileImg(4);
                        }
                        else if (r5.isSelected()) {
                            loggedIn.get(0).setProfileImg(5);
                        }
                        else if (r6.isSelected()) {
                            loggedIn.get(0).setProfileImg(6);
                        }
                        else {
                            loggedIn.get(0).setProfileImg(0);
                        }
                        makeDash.set(false);
                        makeDash.set(true);
                        primaryStage.setScene(dashScene);
                    });
                    choosePicLayout.getChildren().addAll(chooseAPic, r0, r1, r2, r3, r4, r5, r6, doneChoosing);
                    Scene picScene = new Scene(choosePicLayout, 600, 600);
                    primaryStage.setScene(picScene);
                });
                information.getChildren().addAll(usernameLabel, usernameData, firstName, firstNameData, secondName, secondNameData, winRate, winRateData, profilePic, iv, choosePic);
                news = new VBox();
                news.setPadding(new Insets(10,10,10,10));
                news.setSpacing(10);
                news.setMaxWidth(150);
                Label leaderboardPrev = new Label("Leaderboard position on last logout:");
                leaderboardPrev.setWrapText(true);
                Label leaderboardPrevData;
                if (loggedIn.get(0).getPreviousLoginTime() == null) {
                    leaderboardPrevData = new Label("This is your first login");
                }
                else {
                    leaderboardPrevData = new Label("" + loggedIn.get(0).getPreviousLeaderboardPosition());
                }
                Label leaderboardCur = new Label("Current leaderboard position:");
                leaderboardCur.setWrapText(true);
                if (loggedIn.get(0).getLeaderboardPosition() == 0) {
                    leaderboardCurData = new Label("" + (adminList.size() + playerList.size()));
                }
                else {
                    leaderboardCurData = new Label("" + loggedIn.get(0).getLeaderboardPosition());
                }
                Label newUsers = new Label("Click the drop down list to see all new users added since last login:");
                newUsers.setWrapText(true);
                ChoiceBox<String> newUserDropDownList = new ChoiceBox<>();
                if (loggedIn.get(0).getPreviousLoginTime() != null) {
                    for (Administrator a:adminList) {
                        if (a.getJoinDate().until(ZonedDateTime.now(), ChronoUnit.SECONDS) < loggedIn.get(0).getPreviousLoginTime().until(ZonedDateTime.now(), ChronoUnit.SECONDS)) {
                            newUserDropDownList.getItems().addAll(a.getUsername());
                        }
                    }
                    for (Player p:playerList) {
                        if (p.getJoinDate().until(ZonedDateTime.now(), ChronoUnit.SECONDS) < loggedIn.get(0).getPreviousLoginTime().until(ZonedDateTime.now(), ChronoUnit.SECONDS)) {
                            newUserDropDownList.getItems().addAll(p.getUsername());
                        }
                    }
                }
                Label gamesSince = new Label("Click the button for list of games completed since last login.");
                gamesSince.setWrapText(true);
                Button gamesSinceButton = new Button("Games Completed");
                gamesSinceButton.setOnAction(e -> {
                    VBox gamesSinceLayout = new VBox();
                    gamesSinceLayout.setPadding(new Insets(10,10,10,10));
                    gamesSinceLayout.setSpacing(10);
                    ObservableList<GameRecord> newGameRecords = FXCollections.observableArrayList();
                    if (loggedIn.get(0).getPreviousLoginTime() != null) {
                        for (GameRecord game:GameContainer.getGamesPlayed()) {
                            if (game.getDateCompleted().until(ZonedDateTime.now(), ChronoUnit.SECONDS) < loggedIn.get(0).getPreviousLoginTime().until(ZonedDateTime.now(), ChronoUnit.SECONDS)) {
                                newGameRecords.add(game);
                            }
                        }
                    }
                    TableView<GameRecord> gamesSinceTable = new TableView<>();
                    TableColumn<GameRecord, String> dateCol = new TableColumn<>("Date Completed");
                    dateCol.setMinWidth(200);
                    dateCol.setCellValueFactory(new PropertyValueFactory<>("dateCompleted"));
                    TableColumn<GameRecord, String> winnerCol = new TableColumn<>("Winner");
                    winnerCol.setMinWidth(100);
                    winnerCol.setCellValueFactory(new PropertyValueFactory<>("winnerUsername"));
                    TableColumn<GameRecord, String> loserCol = new TableColumn<>("Loser");
                    loserCol.setMinWidth(100);
                    loserCol.setCellValueFactory(new PropertyValueFactory<>("loserUsername"));
                    dateCol.setSortType(TableColumn.SortType.DESCENDING);
                    gamesSinceTable.setItems(newGameRecords);
                    gamesSinceTable.getColumns().add(dateCol);
                    gamesSinceTable.getColumns().add(winnerCol);
                    gamesSinceTable.getColumns().add(loserCol);
                    gamesSinceTable.getSortOrder().add(dateCol);
                    Button leaveGamesSince = new Button("Return to dashboard");
                    leaveGamesSince.setOnAction(e2 -> {
                        primaryStage.setScene(dashScene);
                    });
                    gamesSinceLayout.getChildren().addAll(gamesSinceTable, leaveGamesSince);
                    Scene newGamesScene = new Scene(gamesSinceLayout, 600, 600);
                    primaryStage.setScene(newGamesScene);
                });
                news.getChildren().addAll(leaderboardPrev, leaderboardPrevData, leaderboardCur, leaderboardCurData, newUsers, newUserDropDownList, gamesSince, gamesSinceButton);
                VBox allMyGamesLayout = new VBox();
                allMyGamesLayout.setPadding(new Insets(10,10,10,10));
                allMyGamesLayout.setSpacing(10);
                Label gamesHistoryInfo = new Label("Below is a list of all games you have played:");
                gamesHistoryInfo.setWrapText(true);
                ObservableList<GameRecord> myGameRecords = FXCollections.observableArrayList();
                for (GameRecord game:GameContainer.getGamesPlayed()) {
                    if (game.getWinner().getUsername().equals(loggedIn.get(0).getUsername()) || game.getLoser().getUsername().equals(loggedIn.get(0).getUsername())) {
                        myGameRecords.add(game);
                    }
                }
                TableView<GameRecord> myHistoryTable = new TableView<>();
                TableColumn<GameRecord, String> dateCol = new TableColumn<>("Date Completed");
                dateCol.setMinWidth(110);
                dateCol.setCellValueFactory(new PropertyValueFactory<>("dateCompleted"));
                TableColumn<GameRecord, String> winnerCol = new TableColumn<>("Winner");
                winnerCol.setMinWidth(100);
                winnerCol.setCellValueFactory(new PropertyValueFactory<>("winnerUsername"));
                TableColumn<GameRecord, String> loserCol = new TableColumn<>("Loser");
                loserCol.setMinWidth(100);
                loserCol.setCellValueFactory(new PropertyValueFactory<>("loserUsername"));
                dateCol.setSortType(TableColumn.SortType.DESCENDING);
                myHistoryTable.setItems(myGameRecords);
                myHistoryTable.getColumns().add(dateCol);
                myHistoryTable.getColumns().add(winnerCol);
                myHistoryTable.getColumns().add(loserCol);
                myHistoryTable.getSortOrder().add(dateCol);
                allMyGamesLayout.getChildren().addAll(gamesHistoryInfo, myHistoryTable);
                dashboard.setTop(topPanel);
                dashboard.setLeft(information);
                dashboard.setRight(news);
                dashboard.setCenter(allMyGamesLayout);
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
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(200);
        Button loginButton = new Button("Login");
        loginButton.requestFocus();
        authenticated = new SimpleBooleanProperty(false);
        loggedIn = new ArrayList<>();
        loginButton.setOnAction(e -> {
            for (Administrator a:adminList) {
                if (username.getText().equals(a.getUsername()) && password.getText().equals(a.getPassword())) {
                    a.setThisLoginTime(ZonedDateTime.now());
                    loggedIn.add(a);
                    authenticated.set(true);
                    ArrayList<User> tempAllUsers = new ArrayList<>();
                    for (Administrator ad:adminList) {
                        tempAllUsers.add(ad);
                    }
                    for (Player pl:playerList) {
                        tempAllUsers.add(pl);
                    }
                    for (int i = 0; i < tempAllUsers.size() - 1; ++i) {
                        for (int j = 0; j < tempAllUsers.size() - i - 1; ++j) {
                            if (tempAllUsers.get(j).getWinRate() > tempAllUsers.get(j + 1).getWinRate()) {
                                User t = tempAllUsers.get(j);
                                tempAllUsers.set(j, tempAllUsers.get(j + 1));
                                tempAllUsers.set(j + 1, t);
                            }
                        }
                    }
                    for (int i = tempAllUsers.size(); i > 0; --i) {
                        tempAllUsers.get(i - 1).setLeaderboardPosition(tempAllUsers.size() - i + 1);
                    }
                    makeDash.set(false);
                    makeDash.set(true);
                    primaryStage.setScene(dashScene);
                    password.clear();
                    username.clear();
                }
            }
            for (Player p:playerList) {
                if (username.getText().equals(p.getUsername()) && password.getText().equals(p.getPassword())) {
                    p.setThisLoginTime(ZonedDateTime.now());
                    loggedIn.add(p);
                    makeDash.set(true);
                    authenticated.set(true);
                    ArrayList<User> tempAllUsers = new ArrayList<>();
                    for (Administrator a:adminList) {
                        tempAllUsers.add(a);
                    }
                    for (Player pl:playerList) {
                        tempAllUsers.add(pl);
                    }
                    for (int i = 0; i < tempAllUsers.size() - 1; ++i) {
                        for (int j = 0; j < tempAllUsers.size() - i - 1; ++j) {
                            if (tempAllUsers.get(j).getWinRate() > tempAllUsers.get(j + 1).getWinRate()) {
                                User t = tempAllUsers.get(j);
                                tempAllUsers.set(j, tempAllUsers.get(j + 1));
                                tempAllUsers.set(j + 1, t);
                            }
                        }
                    }
                    for (int i = tempAllUsers.size(); i > 0; --i) {
                        tempAllUsers.get(i - 1).setLeaderboardPosition(tempAllUsers.size() - i + 1);
                    }
                    makeDash.set(false);
                    makeDash.set(true);
                    primaryStage.setScene(dashScene);
                    password.clear();
                    username.clear();
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
        backToDash.setOnAction(e -> {
            makeDash.set(true);
            primaryStage.setScene(dashScene);
        });
        bottomRow.getChildren().addAll(backToDash);
        BorderPane layout1 = new BorderPane();
        layout1.setCenter(setupPage);
        layout1.setBottom(bottomRow);
        gameSetupPage = new Scene(layout1, 600, 600);
        
        //create users page
        VBox createUserLayout = new VBox();
        createUserLayout.setPadding(new Insets(10,10,10,10));
        createUserLayout.setSpacing(10);
        Label createNewUserInfo = new Label("Create a new user using the form below:");
        ToggleGroup userTypes = new ToggleGroup();
        RadioButton np = new RadioButton("Normal Player");
        RadioButton admin = new RadioButton("Administrator");
        np.setToggleGroup(userTypes);
        admin.setToggleGroup(userTypes);
        np.setSelected(true);
        Label chooseUsername = new Label("Type username below:");
        TextField newUsername = new TextField(); //restrict this to have no spaces
        newUsername.setMaxWidth(200);
        Label choosePass = new Label("Type password below:");
        TextField newPass = new TextField();
        newPass.setMaxWidth(200);
        Label chooseFName = new Label("Type first name below:");
        TextField newFName = new TextField();
        newFName.setMaxWidth(200);
        Label chooseLName = new Label("Type last name below:");
        TextField newLName = new TextField();
        newLName.setMaxWidth(200);
        Label chooseAdminID = new Label("Type new admin ID below:");
        TextField newAdminID = new TextField(); //enforce this to be an integer
        newAdminID.setMaxWidth(200);
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
        BooleanProperty usernameNotTaken = new SimpleBooleanProperty(true);
        Button createUserButton = new Button("Create user");
        createUserButton.setOnAction(e -> { //check all fields first?
            if (admin.isSelected()) {
                notTaken.set(true);
                usernameNotTaken.set(true);
                for (Administrator a:adminList) {
                    if (newAdminID.getText().equals("" + a.getAdminID())) {
                        notTaken.set(false);
                        InformationBox.display("Admin ID already taken", "The chosen admin ID is already taken, please choose another.");
                    }
                    if (newUsername.getText().equals("" + a.getUsername())) {
                        usernameNotTaken.set(false);
                        InformationBox.display("Username already taken", "The chosen username is already taken, please choose another.");
                    }
                }
                for (Player p:playerList) {
                    if (newUsername.getText().equals("" + p.getUsername())) {
                        usernameNotTaken.set(false);
                        InformationBox.display("Username already taken", "The chosen username is already taken, please choose another.");
                    }
                }
                if (notTaken.getValue() && usernameNotTaken.getValue()) {
                    Administrator newAdmin = new Administrator(newUsername.getText(), newPass.getText(), newFName.getText(), newLName.getText(), Integer.parseInt(newAdminID.getText()));
                    adminList.add(newAdmin);
                    InformationBox.display("Created new administrator", "New administrator successfully created");
                    ArrayList<User> tempAllUsers = new ArrayList<>();
                    for (Administrator a:adminList) {
                        tempAllUsers.add(a);
                    }
                    for (Player p:playerList) {
                        tempAllUsers.add(p);
                    }
                    for (int i = 0; i < tempAllUsers.size() - 1; ++i) {
                        for (int j = 0; j < tempAllUsers.size() - i - 1; ++j) {
                            if (tempAllUsers.get(j).getWinRate() > tempAllUsers.get(j + 1).getWinRate()) {
                                User t = tempAllUsers.get(j);
                                tempAllUsers.set(j, tempAllUsers.get(j + 1));
                                tempAllUsers.set(j + 1, t);
                            }
                        }
                    }
                    for (int i = tempAllUsers.size(); i > 0; --i) {
                        tempAllUsers.get(i - 1).setLeaderboardPosition(tempAllUsers.size() - i + 1);
                    }
                    makeDash.set(false);
                }
            }
            else {
                usernameNotTaken.set(true);
                for (Administrator a:adminList) {
                    if (newUsername.getText().equals(a.getUsername())) {
                        usernameNotTaken.set(false);
                        InformationBox.display("Username already taken", "The chosen username is already taken, please choose another.");
                    }
                }
                for (Player p:playerList) {
                    if (newUsername.getText().equals(p.getUsername())) {
                        usernameNotTaken.set(false);
                        InformationBox.display("Username already taken", "The chosen username is already taken, please choose another.");
                    }
                }
                if (usernameNotTaken.getValue()) {
                    Player newPlayer = new Player(newUsername.getText(), newPass.getText(), newFName.getText(), newLName.getText());
                    playerList.add(newPlayer);
                    InformationBox.display("Created new player", "New player successfully created");
                    ArrayList<User> tempAllUsers = new ArrayList<>();
                    for (Administrator a:adminList) {
                        tempAllUsers.add(a);
                    }
                    for (Player p:playerList) {
                        tempAllUsers.add(p);
                    }
                    for (int i = 0; i < tempAllUsers.size() - 1; ++i) {
                        for (int j = 0; j < tempAllUsers.size() - i - 1; ++j) {
                            if (tempAllUsers.get(j).getWinRate() > tempAllUsers.get(j + 1).getWinRate()) {
                                User t = tempAllUsers.get(j);
                                tempAllUsers.set(j, tempAllUsers.get(j + 1));
                                tempAllUsers.set(j + 1, t);
                            }
                        }
                    }
                    for (int i = tempAllUsers.size(); i > 0; --i) {
                        tempAllUsers.get(i - 1).setLeaderboardPosition(tempAllUsers.size() - i + 1);
                    }
                    makeDash.set(false);
                }
            }
        });
        Button leaveCreateUser = new Button("Return to user dashboard");
        createUserLayout.setMargin(leaveCreateUser, new Insets(20, 0, 0, 0));
        leaveCreateUser.setOnAction(e -> {
            makeDash.set(true);
            primaryStage.setScene(dashScene);
            newUsername.clear();
            newPass.clear();
            newFName.clear();
            newLName.clear();
            newAdminID.clear();
        });
        createUserLayout.getChildren().addAll(createNewUserInfo, np, admin, chooseUsername, newUsername, choosePass, newPass, chooseFName, newFName, chooseLName, newLName, chooseAdminID, newAdminID, createUserButton, leaveCreateUser);
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