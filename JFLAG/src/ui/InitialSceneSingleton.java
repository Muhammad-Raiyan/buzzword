package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import state.ButtonState;

/**
 * Created by ishmam on 10/30/2016.
 */
public class InitialSceneSingleton extends JFLAGScene{

    public static InitialSceneSingleton initialSceneSingleton = null;
    private static Scene scene;
    private BorderPane pane;
    private Button createProfile, login, help;
    private ButtonState buttonState;
    private VBox rightBar;

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

        login = new Button("Login");

        help = new Button("Help");

        leftBar.getChildren().addAll(filler, createProfile, login, help);

        rightBar = new VBox();
        rightBar.setId("right-bar");
        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER);
        Label title = new Label("!! Buzzword !!");
        topPane.getChildren().add(title);
        Region r = new Region();
        r.setPrefHeight(150);
        rightBar.getChildren().addAll(topPane, r);

        pane.setCenter(rightBar);
        pane.setLeft(leftBar);
        scene = new Scene(pane);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    public void setRightPane(Pane pane){
        rightBar.getChildren().add(pane);
    }

    @Override
    public void initializeHandlers() {
        ProfileDialogSingleton s = ProfileDialogSingleton.getProfileCreator();
        createProfile.setOnAction(event -> {
            AppGUI.buttonState = ButtonState.CREATE;
            setChanged();
            notifyObservers();
        });
        login.setOnAction(event -> {
            AppGUI.buttonState = ButtonState.LOGIN;
            setChanged();
            notifyObservers();
        });
        help.setOnAction(event -> System.out.println("Help"));
    }

    public void initializeStyle() {
        scene.getStylesheets().add("css/SceneStyle.css");
    }

    @Override
    public void init(ButtonState buttonState) {
        this.buttonState = buttonState;
    }

}
