package data;

import app.JFLAGApplication;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.SourceTree;
import components.JFLAGDataComponent;
import components.JFLAGFileComponent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ishmam on 10/30/2016.
 */
public class ProfileManager implements JFLAGFileComponent{
    private HashMap<String, UserData> userList;
    private UserData user;
    private Path allProfilePath;

    private final static String USERNAME = "userName", PASSWORD = "password", FILENAME = "jsonFile";

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
        System.out.println(newUser);
    }

    private void init(File file){

    }

    private void loadToList(){

    }

    public boolean login(String user, String pass){
        return true;
    }

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
                        jsonParser.nextToken();
                        data.setUserName(jsonParser.getValueAsString());
                        break;
                    case PASSWORD:
                        jsonParser.nextToken();
                        data.setPassword(jsonParser.getValueAsString());
                        break;
                    case FILENAME:
                        jsonParser.nextToken();
                        addUser(data);
                        break;
                }
            }
        }
    }

    @Override
    public void exportData(JFLAGDataComponent data, Path filePath) throws IOException {

    }
}
