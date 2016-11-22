package gamedata;

import app.JFLAGApplication;
import components.JFLAGDataComponent;
import data.ProfileManager;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordDataFile extends ProfileManager {

    public BuzzwordDataFile(JFLAGApplication app) {
        super(app);

    }

    public void saveProgress(JFLAGDataComponent data, Path filePath) throws IOException {

    }

    public void loadGameData(JFLAGDataComponent data, Path filePath) throws IOException {

        try {
            if(!filePath.toFile().exists()){
                filePath.toFile().getParentFile().mkdirs();
                filePath.toFile().createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
