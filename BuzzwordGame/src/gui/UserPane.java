package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import state.ButtonState;
import ui.JFLAGScene;

/**
 * Created by mrislam on 11/17/2016.
 */
public class UserPane extends JFLAGScene {

    BorderPane primaryPane;
    private Button logout;
    private VBox data;
    private VBox bottomPane;

    public UserPane() {
        layout();
    }

    @Override
    public void layout() {
        primaryPane = new BorderPane();

        data = new VBox();
        data.setMinHeight(500);

        logout = new Button("Logout");
        bottomPane = new VBox();
        bottomPane.getChildren().add(logout);
        bottomPane.setAlignment(Pos.CENTER);
        primaryPane.setCenter(data);
        primaryPane.setBottom(logout);
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

    }

    @Override
    public void initializeStyle() {

    }

    @Override
    public void init(ButtonState buttonState) {

    }
}
