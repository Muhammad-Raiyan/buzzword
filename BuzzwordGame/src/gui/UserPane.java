package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import state.ButtonState;
import ui.InitialSceneSingleton;
import ui.JFLAGScene;

/**
 * Created by mrislam on 11/17/2016.
 */
public class UserPane extends JFLAGScene {

    BorderPane primaryPane;
    private Button logout;
    private VBox data;
    private HBox bottomPane;

    public UserPane() {
        layout();
    }

    @Override
    public void layout() {
        primaryPane = new BorderPane();

        data = new VBox();
        data.setMinHeight(500);

        logout = InitialSceneSingleton.getInitialSceneSingleton().getLogoutButton();
        bottomPane = new HBox();

        Region space = new Region();
        space.setPrefWidth(100);
        bottomPane.getChildren().addAll(space, logout);
        primaryPane.setCenter(logout);
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
