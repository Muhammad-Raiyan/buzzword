package data;

import app.JFLAGApplication;
import components.JFLAGDataComponent;
import components.JFLAGFileComponent;
import sun.security.util.Password;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Created by ishmam on 10/30/2016.
 */
public class ProfileManager implements JFLAGFileComponent{
    private HashMap<String, UserData> UserList;
    private UserData user;

    private enum JsonTag {
        USERNAME,
        PASSWORD,
        FILENAME
    }

    public ProfileManager(JFLAGApplication app){

    }

    public void addUser(UserData newUser){

    }

    private void init(File file){

    }

    private void loadToList(){

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
