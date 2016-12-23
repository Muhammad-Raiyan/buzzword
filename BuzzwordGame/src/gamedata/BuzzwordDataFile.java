package gamedata;

import app.JFLAGApplication;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.JFLAGDataComponent;
import data.ProfileManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordDataFile extends ProfileManager {
    JFLAGApplication app;
    public BuzzwordDataFile(JFLAGApplication app) {
        super(app);
        this.app = app;
    }

    public void saveProgress(BuzzwordData data, Path filePath) throws IOException {
        BuzzwordData gameData = data;
        ObjectMapper mapper = new ObjectMapper();
        try (OutputStream out = new FileOutputStream(filePath.toFile())){
            JsonGenerator generator = mapper.getFactory().createGenerator(out);
            generator.setPrettyPrinter(new DefaultPrettyPrinter());
            //generator.writeStartObject();
            mapper.writeValue(filePath.toFile(), gameData);
            /*generator.writeEndObject();
            generator.close();*/
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadGameData(JFLAGDataComponent data, Path filePath) throws IOException {

        try {
            if(!filePath.toFile().exists()){
                filePath.toFile().getParentFile().mkdirs();
                filePath.toFile().createNewFile();
                initFile(filePath);
            }
            else {
                ObjectMapper mapper = new ObjectMapper();
                data = mapper.readValue(filePath.toFile(), BuzzwordData.class);
                app.setData(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initFile(Path target){
        ObjectMapper mapper = new ObjectMapper();
        BuzzwordData data = (BuzzwordData) app.getDataComponent();
        HashMap<String, ArrayList<Integer>> progress = data.getProgress();
        try {
            mapper.writeValue(target.toFile(), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
