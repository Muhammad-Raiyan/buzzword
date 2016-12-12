package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import state.ButtonState;


/**
 * Created by ishmam on 10/30/2016.
 */
public class ProfileDialogSingleton extends JFLAGScene{

    private static ProfileDialogSingleton singleton;

    private static Stage primaryStage;
    private Scene primaryScene;
    private GridPane grid;
    private static Button create, login, cancel;
    private static HBox buttonHolder;
    private TextField userName;
    private PasswordField password;
    private String selection;

    private ProfileDialogSingleton(){
        layout();
        initializeHandlers();
    }

    public static ProfileDialogSingleton getProfileCreator(){
        if(singleton == null)
            singleton = new ProfileDialogSingleton();
        return singleton;
    }

    public void init(Stage owner) {
        primaryScene = new Scene(grid);
        primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(primaryScene);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(owner);
        initializeStyle();

    }

    public void initializeHandlers() {
        create.setOnAction(event ->{
            AppGUI.buttonState = ButtonState.SIGNUP;
            setChanged();
            notifyObservers();
            selection = "create";
        });
        login.setOnAction(event -> {
            selection = "login";
            primaryStage.close();
        });
        cancel.setOnAction(event ->{
            selection = "cancel";
            primaryStage.close();
        });
    }

    public static void changeToLogin(){
        buttonHolder.getChildren().set(0, ProfileDialogSingleton.login);
    }

    public void changeToSignup(){
        buttonHolder.getChildren().set(0, ProfileDialogSingleton.create);
    }

    public void initializeStyle() {
        primaryScene.getStylesheets().add("css/DialogStyle.css");
    }

    @Override
    public void init(ButtonState buttonState) {

    }

    public void layout() {
        grid = new GridPane();
        userName = new TextField();
        userName.setPromptText("username");
        password = new PasswordField();
        password.setPromptText("At least 5 character");

        grid.add(new Label("Username: "), 0, 0);
        grid.add(userName, 1, 0);
        grid.add(new Label("Password: "), 0, 1);
        grid.add(password, 1, 1);


        create = new Button("Sign Up");
        login = new Button("Login");
        cancel = new Button("Cancel");

        buttonHolder = new HBox(create, cancel);
        buttonHolder.setAlignment(Pos.CENTER);
        grid.add(buttonHolder, 1, 2);
    }

    public void disableUserNameEntry(String name){
        userName.setText(name);
        userName.setDisable(true);
    }

    public Scene getScene() {
        return primaryScene;
    }

    public Stage getStage() {
        return primaryStage;
    }

    public void show(){
        primaryStage.showAndWait();
    }

    public void close(){
        userName.clear();
        password.clear();
        if(userName.isDisable()) userName.setDisable(false);
        primaryStage.close();
    }

    public String getUserName() {
        return userName.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
