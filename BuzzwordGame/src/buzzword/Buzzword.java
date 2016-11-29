package buzzword;

import app.JFLAGApplication;
import components.*;
import controller.FileController;
import gamecontroller.BuzzwordController;
import gamedata.BuzzwordData;
import gamedata.BuzzwordDataFile;
import gamesettings.BuzzwordSettings;
import gui.Workspace;

/**
 * Created by ishmam on 10/29/2016.
 */
public class Buzzword extends JFLAGApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public JFLAGComponentsBuilder makeAppBuilderHook() {
        return new JFLAGComponentsBuilder() {
            @Override
            public JFLAGDataComponent buildDataComponent() throws Exception {
                return new BuzzwordData(Buzzword.this);
            }

            @Override
            public JFLAGFileComponent buildFileComponent() throws Exception {
                return new BuzzwordDataFile(Buzzword.this);
            }

            @Override
            public FileController buildGameController() throws Exception {
                return new BuzzwordController(Buzzword.this);
            }

            @Override
            public JFLAGSettingsComponent buildSettingComponent() throws Exception {
                return new BuzzwordSettings(Buzzword.this);
            }

            @Override
            public JFLAGWorkspaceComponent buildWorkspaceComponent() throws Exception {
                return new Workspace(Buzzword.this);
            }
        };
    }

   /* @Override
    public void run() {
        synchronized (this){
            System.out.println("Hello from run");
            BuzzwordData data = new BuzzwordData(Buzzword.this);
            load();
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }*/

    public void load(){

        //System.out.println("loaded");
    }
}
