package ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import state.ButtonState;

/**
 * Created by ishmam on 10/30/2016.
 */
public class InitialSceneSingleton extends JFLAGScene{

    public static InitialSceneSingleton initialSceneSingleton = null;
    private static Scene scene;
    private BorderPane pane;
    private Button createProfile, login, help, exit, logout;
    private ButtonState buttonState;
    private Pane rightBar;

    private InitialSceneSingleton(){
        layout();
        initializeHandlers();
        initializeStyle();
    }

    public static InitialSceneSingleton getInitialSceneSingleton(){
        if(initialSceneSingleton == null)
            initialSceneSingleton = new InitialSceneSingleton();
        return initialSceneSingleton;
    }

    @Override
    public void layout() {
        pane = new BorderPane();
        pane.setPrefSize(800, 600);

        VBox leftBar = new VBox(10);

        leftBar.setId("left-bar");
        Region filler = new Region();
        filler.setPrefSize(leftBar.getPrefWidth(), 120);

        createProfile = new Button("Create New Profile");

        login = new Button("Sign In");

        help = new Button("Help");

        logout = new Button("Logout");

        leftBar.getChildren().addAll(filler, createProfile, login, help);

        rightBar = new VBox();
        rightBar.setId("right-bar");

        Region r = new Region();
        r.setPrefHeight(150);
        rightBar.getChildren().addAll(createTopPane(), r, new VBox());

        pane.setCenter(rightBar);
        pane.setLeft(leftBar);
        scene = new Scene(pane);
    }

    public  BorderPane createTopPane(){
        BorderPane topPane = new BorderPane();
        Label title = new Label("!! Buzzword !!");
        exit = new Button("X");
        exit.setOnAction(event -> {
            AppGUI.buttonState = ButtonState.EXIT;
            setChanged();
            notifyObservers();
        });
        topPane.setRight(exit);
        topPane.setCenter(title);
        return topPane;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    public void setRightPane(Pane pane){
        rightBar.getChildren().set(2, pane);
    }

    @Override
    public void initializeHandlers() {
        createProfile.setOnAction(event -> {
            AppGUI.buttonState = ButtonState.CREATE;
            setChanged();
            notifyObservers();
        });
        login.setOnAction(event -> {
            AppGUI.buttonState = ButtonState.SIGNIN;
            setChanged();
            notifyObservers();
        });
        help.setOnAction(event -> {
            HelpPane helpPane = new HelpPane();
            setRightPane(helpPane.getPane());
        });
        logout.setOnAction(event -> {
            AppGUI.buttonState = ButtonState.LOGOUT;
            setChanged();
            notifyObservers();
        });
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN),
                () -> createProfile.fire());
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN),
                () -> login.fire());
    }

    public void initializeStyle() {
        scene.getStylesheets().add("css/SceneStyle.css");
    }

    @Override
    public void init(ButtonState buttonState) {
        this.buttonState = buttonState;
    }

    public Button getLogoutButton(){
        return logout;
    }


}
