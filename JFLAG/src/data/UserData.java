package data;

import app.JFLAGApplication;
import components.JFLAGDataComponent;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public UserData(String userName, String password, Path jsonFile) {
        this.userName = userName;
        this.password = password;
        this.jsonFile = jsonFile;
    }

    public static String encryptPass(String input){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            md.update(input.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte raw[] = md.digest();
        String hash = (new BASE64Encoder()).encode(raw);
        return hash;
    }

    private Path generateJsonFileName(String input){
        Path target = Paths.get("BuzzwordGame\\resources\\profiledata\\" + input + ".json").toAbsolutePath();
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
        this.jsonFile = generateJsonFileName(userName);
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

    public void setJsonFile(String jsonFile) {
        this.jsonFile = Paths.get(jsonFile).toAbsolutePath();
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", jsonFile=" + jsonFile +
                '}';
    }
}
