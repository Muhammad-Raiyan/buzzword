package data;

import app.JFLAGApplication;
import components.JFLAGDataComponent;
import components.JFLAGFileComponent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Created by ishmam on 10/30/2016.
 */
public class ProfileManager implements JFLAGFileComponent{
    private static ProfileManager singleton = null;
    private HashMap<String, UserData> UserList;
    private UserData user;
    private UserBuilder userBuilder;
    private enum JsonTag {
        USERNAME
    }

    public ProfileManager(JFLAGApplication app){

    }

    private void init(File file){

    }

    private void loadToList(){

    }

    public void createProfile(String user, String pass){
        userBuilder = new UserBuilder(user);


    }

    public boolean login(String user, String pass){
        return true;
    }

    @Override
    public void saveData(JFLAGDataComponent data, Path filePath) throws IOException {

    }

    @Override
    public void loadData(JFLAGDataComponent data, Path filePath) throws IOException {

    }

    @Override
    public void exportData(JFLAGDataComponent data, Path filePath) throws IOException {

    }
}
