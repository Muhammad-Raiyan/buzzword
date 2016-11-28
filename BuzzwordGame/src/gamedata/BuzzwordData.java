package gamedata;

import app.JFLAGApplication;
import components.JFLAGDataComponent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordData implements JFLAGDataComponent {

    JFLAGApplication app;
    private Double progPerc;
    private String currentMode;
    private int currentLevel;
    private ArrayList<Integer> scores;
    private HashMap<String, ArrayList<Integer>> progress;

    public BuzzwordData(JFLAGApplication app){
        this.app = app;
        currentLevel = 1;
        currentMode = "Dictionary";
        init();
    }

    private void init() {
        progress = new HashMap<>();
        progress.put("Dictionary", new ArrayList<Integer>());
        progress.get("Dictionary").add(0);
        progress.put("Places", new ArrayList<Integer>());
        progress.get("Places").add(0);
        progress.put("Science", new ArrayList<Integer>());
        progress.get("Science").add(0);
        progress.put("Famous", new ArrayList<Integer>());
        progress.get("Famous").add(0);
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public HashMap<String, ArrayList<Integer>> getProgress() {
        return progress;
    }

    @Override
    public void reset() {

    }
}
