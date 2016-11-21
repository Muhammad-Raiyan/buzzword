package observer;

import app.JFLAGApplication;
import controller.AppFileController;
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
        fileController = (AppFileController)app.getGameController();
        String ID = app.getGUI().buttonState.toString();
        switch (ID){
            case "CREATE" :
                try {
                    fileController.handleCreateRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "SIGNIN" :
                try {
                    fileController.handleSignInRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "SIGNUP" :
                try {
                    fileController.handleSignUpRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "LOGOUT" :
                fileController.handleLogoutRequest();
                break;

            case "HELP" :
                try {
                    fileController.handleHelpRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "EXIT" :
                fileController.handleExitRequest(); break;
        }

    }
}

