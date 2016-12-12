package data;

import app.JFLAGApplication;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.JFLAGDataComponent;
import components.JFLAGFileComponent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by ishmam on 10/30/2016.
 * @author ishmam
 */
public class ProfileManager implements JFLAGFileComponent{
    private HashMap<String, UserData> userList;
    private UserData user;
    private Path allProfilePath;

    private final static String USERNAME = "userName", PASSWORD = "password";

    public ProfileManager(JFLAGApplication app){
        userList = new HashMap<>();
        allProfilePath = Paths.get("JFLAG\\resources\\profiledata\\users.json");
        try {
            loadData(allProfilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(UserData newUser){
        userList.put(newUser.getUserName(), newUser);
        //System.out.println(newUser);   //TODO : Remove
    }

    public UserData getUser(String userName){
        return userList.get(userName);
    }

    /*private void init(File file){

    }

    public boolean login(String user, String pass){
        return true;
    }*/

    @Override
    public void saveData(Path target) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        try(OutputStream out = new FileOutputStream(target.toFile())){
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
    public void loadData(Path target) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(Files.newInputStream(target));
        jsonParser.nextToken();
        UserData data = new UserData();
        while (!jsonParser.isClosed()) {
            JsonToken token = jsonParser.nextToken();

            if (JsonToken.FIELD_NAME.equals(token)) {
                switch (jsonParser.getCurrentName()) {
                    case USERNAME:
                        data = new UserData();
                        jsonParser.nextToken();
                        data.setUserName(jsonParser.getValueAsString());
                        break;
                    case PASSWORD:
                        jsonParser.nextToken();
                        data.setPassword(jsonParser.getValueAsString());
                        addUser(data);
                        break;
                }
            }
        }
    }

    @Override
    public void exportData(JFLAGDataComponent data, Path filePath) throws IOException {

    }

    public boolean validate(String userName, String password){
        password = UserData.encryptPass(password);
        return userList.containsKey(userName) && userList.get(userName).getPassword().equals(password);
    }
}
