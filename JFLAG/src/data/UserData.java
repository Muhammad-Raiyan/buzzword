package data;

import app.JFLAGApplication;
import components.JFLAGDataComponent;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ishmam on 10/29/2016.
 */
public class UserData implements JFLAGDataComponent{
    private String userName;
    private String password;
    private Path jsonFile;
    private JFLAGApplication app;

    public UserData(){

    }

    public void createUser(String userName, String password){
        this.userName = userName;
        this.password = encryptPass(password);
        jsonFile = generateJsonFileName(userName);
    }

    private String encryptPass(String input){
        return input;
    }

    private Path generateJsonFileName(String input){
        Path target = Paths.get("JFLAG\\resources\\profiledata\\" + input + ".json").toAbsolutePath();
        return target;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Path getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(Path jsonFile) {
        this.jsonFile = jsonFile;
    }
}
