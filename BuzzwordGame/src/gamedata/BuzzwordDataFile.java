package gamedata;

import app.JFLAGApplication;
import components.JFLAGDataComponent;
import data.ProfileManager;
import propertymanager.PropertyManager;

import java.io.File;
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

        if(!filePath.toFile().exists()){
            File f = new File(filePath.toString());
            f.createNewFile();
        }
    }
}
