package data;

import app.JFLAGApplication;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.JFLAGDataComponent;
import components.JFLAGFileComponent;
import jdk.nashorn.internal.parser.JSONParser;
import sun.security.util.Password;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by ishmam on 10/30/2016.
 */
public class ProfileManager implements JFLAGFileComponent{
    private HashMap<String, UserData> userList;
    private UserData user;
    private Path allProfile;

    private final static String USERNAME = "USERNAME", PASSWORD = "PASSWORD", FILENAME = "FILENAME";

    public ProfileManager(JFLAGApplication app){
        userList = new HashMap<>();
        allProfile = Paths.get("JFLAG\\resources\\profiledata\\users.json").toAbsolutePath();
    }

    public void addUser(UserData newUser){
        userList.put(newUser.getUserName(), newUser);
    }

    private void init(File file){

    }

    private void loadToList(){

    }

    public boolean login(String user, String pass){
        return true;
    }

    @Override
    public void saveData(JFLAGDataComponent data, Path to) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        try(OutputStream out = new FileOutputStream(to.toFile())){
            JsonGenerator generator = mapper.getFactory().createGenerator(out);
            generator.setPrettyPrinter(new DefaultPrettyPrinter());
            generator.writeStartObject();
            userList.forEach((s, userData) -> {
                try {
                    generator.writeObjectField(s,userData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            generator.writeEndObject();
            generator.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadData(JFLAGDataComponent data, Path filePath) throws IOException {

    }

    @Override
    public void exportData(JFLAGDataComponent data, Path filePath) throws IOException {

    }
}
