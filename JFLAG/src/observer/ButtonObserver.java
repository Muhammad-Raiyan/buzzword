package observer;

import app.JFLAGApplication;
import controller.FileController;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ishmam on 10/30/2016.
 */
public class ButtonObserver implements Observer {
    private JFLAGApplication app;
    private FileController fileController;

    public ButtonObserver(JFLAGApplication app){
        this.app = app;
    }
    @Override
    public void update(Observable o, Object arg) {
        fileController = app.getGameController();
        String ID = app.getGUI().buttonState.toString();
        switch (ID){
            case "SAVE" :
                try {
                    fileController.handleSaveRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "LOAD" :
                try {
                    fileController.handleLoadRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "NEW" :
                fileController.handleNewRequest(); break;
            case "EXIT" :
                fileController.handleExitRequest(); break;
        }

    }
}

