package data;

import app.JFLAGApplication;
import components.JFLAGDataComponent;

/**
 * Created by ishmam on 10/29/2016.
 */
public class UserData implements JFLAGDataComponent{
    private String userName;
    private String password;
    private String jsonFile;
    private JFLAGApplication app;

    public UserData(JFLAGApplication app){

    }

    public void createUser(String userName, String password){

    }

    private String encryptPass(){
        return null;
    }

    private String generateJsonFileName(){
        return null;
    }

    public boolean checkUser(String user){
        return true;
    }

    public  boolean checkPass(String password){
        return true;
    }
    @Override
    public void reset() {

    }
}
