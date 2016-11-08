package gamedata;

import app.JFLAGApplication;
import components.JFLAGDataComponent;
import data.ProfileManager;
import propertymanager.PropertyManager;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordDataFile extends ProfileManager {

    public BuzzwordDataFile(JFLAGApplication app) {
        super(app);

    }

    @Override
    public void saveData(JFLAGDataComponent data, Path filePath) throws IOException {

    }

    @Override
    public void loadData(JFLAGDataComponent data, Path filePath) throws IOException {

    }
}
