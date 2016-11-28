package gamecontroller;

import app.JFLAGApplication;
import gui.Workspace;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by MuhammadRaiyanul on 11/22/2016.
 */
public class BuzzwordObserver implements Observer {

    private JFLAGApplication application;
    private BuzzwordController fileController;
    private Workspace workspace;

    public BuzzwordObserver(JFLAGApplication application){
        this.application = application;

        workspace = (Workspace) application.getWorkspaceComponent();
    }

    @Override
    public void update(Observable o, Object arg) {
        String ID = workspace.gameState.toString();
        fileController = (BuzzwordController) application.getGameController();
        switch (ID){
            case "START" :
                fileController.handleStartRequest();
                break;
            default:
                System.out.println("hello from buzzword");
        }
    }
}
