package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import state.ButtonState;
import ui.InitialSceneSingleton;
import ui.JFLAGScene;
import ui.ProfileDialogSingleton;

/**
 * Created by mrislam on 11/17/2016.
 */
public class UserPane extends JFLAGScene {

    BorderPane primaryPane;
    private Button logout, changePassword;
    private VBox data;
    private VBox bottomPane;
    private Workspace workspace;

    public UserPane(Workspace workspace) {
        this.workspace = workspace;
        layout();
        initializeHandlers();
    }

    @Override
    public void layout() {
        primaryPane = new BorderPane();
        primaryPane.setPadding(new Insets(50));

        Label userName = new Label("User Name: " + workspace.getUserName());

        changePassword = new Button("Change Password");

        logout = InitialSceneSingleton.getInitialSceneSingleton().getLogoutButton();
        logout.setAlignment(Pos.CENTER);


        Region space = new Region();
        space.setPrefHeight(100);

        data = new VBox();
        data.setAlignment(Pos.CENTER);
        data.getChildren().addAll(userName, changePassword, space, logout);



        primaryPane.setCenter(data);
    }

    public BorderPane getPrimaryPane() {
        return primaryPane;
    }

    @Override
    public Scene getScene() {
        return null;
    }

    @Override
    public void initializeHandlers() {
        changePassword.setOnAction(event -> {
            ProfileDialogSingleton profileDialogSingleton = ProfileDialogSingleton.getProfileCreator();
            profileDialogSingleton.changeToSignup();
            profileDialogSingleton.disableUserNameEntry(workspace.getUserName());
            profileDialogSingleton.show();
        });
    }

    @Override
    public void initializeStyle() {

    }

    @Override
    public void init(ButtonState buttonState) {

    }
}
