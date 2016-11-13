package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Created by ishmam on 10/30/2016.
 */
public class ProfileDialogSingleton extends JFLAGScene {

    private static ProfileDialogSingleton singleton = null;

    private static Stage primaryStage;
    private Scene primaryScene;
    private GridPane grid;
    private static Button create, login, cancel;
    private static HBox buttonHolder;

    private ProfileDialogSingleton(){


    }

    public static ProfileDialogSingleton getProfileCreator(){
        return singleton == null? new ProfileDialogSingleton() : singleton;
    }

    public void init(Stage owner) {

        grid = new GridPane();
        TextField userName = new TextField();
        userName.setPromptText("username");
        PasswordField password = new PasswordField();
        password.setPromptText("At least 5 character");

        grid.add(new Label("Username: "), 0, 0);
        grid.add(userName, 1, 0);
        grid.add(new Label("Password: "), 0, 1);
        grid.add(password, 1, 1);


        create = new Button("Sign Up");
        //create.setVisible(false);
        login = new Button("Login");
        //login.setVisible(false);
        cancel = new Button("Cancel");

        buttonHolder = new HBox(create, cancel);
        buttonHolder.setAlignment(Pos.CENTER);
        grid.add(buttonHolder, 1, 2);

        primaryScene = new Scene(grid);
        primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(primaryScene);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(owner);
        initializeHandlers();
        initializeStyle();
    }

    @Override
    public void initializeHandlers() {
        create.setOnAction(event -> primaryStage.close());
        login.setOnAction(event -> primaryStage.close());
        cancel.setOnAction(event -> primaryStage.close());
    }

    public static void changeToLogin(){
        buttonHolder.getChildren().set(0, ProfileDialogSingleton.login);
    }

    public void changeToSignup(){
        buttonHolder.getChildren().set(0, ProfileDialogSingleton.create);
    }

    @Override
    public void initializeStyle() {
        primaryScene.getStylesheets().add("ui/DialogStyle.css");
    }

    @Override
    public void layout() {

    }

    @Override
    public Scene getScene() {
        return primaryScene;
    }

    public Stage getStage() {
        return primaryStage;
    }

    public void show(){
        primaryStage.showAndWait();
    }

}
