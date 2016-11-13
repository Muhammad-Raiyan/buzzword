package ui;

import javafx.geometry.Insets;
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
public class ProfileCreatorSingleton extends JFLAGScene {

    private static ProfileCreatorSingleton singleton = null;

    private static Stage primaryStage;
    private Scene primaryScene;
    private GridPane grid;
    private Button create, login, cancel;

    private ProfileCreatorSingleton(){


    }

    public static ProfileCreatorSingleton getProfileCreator(){
        return singleton == null? new ProfileCreatorSingleton() : singleton;
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
        create.setOnAction(event -> primaryStage.close());
        create.setVisible(false);
        login = new Button("Login");
        login.setOnAction(event -> primaryStage.close());
        //login.setVisible(false);
        cancel = new Button("Cancel");
        cancel.setOnAction(event -> primaryStage.close());
        HBox buttonHolder = new HBox(login, create, cancel);
        buttonHolder.setAlignment(Pos.CENTER);
        grid.add(buttonHolder, 1, 2);

        primaryScene = new Scene(grid);
        primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(primaryScene);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(owner);
        initializeStyle();
    }

    @Override
    public void initializeHandlers() {

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
