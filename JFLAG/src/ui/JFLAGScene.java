package ui;

import javafx.scene.Scene;
import state.ButtonState;

import java.util.Observable;

/**
 * Created by ishmam on 10/30/2016.
 */
public abstract class JFLAGScene extends Observable{

    public abstract void layout();
    public abstract Scene getScene();
    public abstract void initializeHandlers();
    public abstract void initializeStyle();
    public abstract void init(ButtonState buttonState);
}
