package gamedata;

import app.JFLAGApplication;
import components.JFLAGDataComponent;
import data.UserData;

import java.util.HashMap;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordData implements JFLAGDataComponent {

    private Double progPerc;
    private String currentMode, currentLevel;
    private HashMap<String, Double> scores;
    private HashMap<String, HashMap<String, Integer>> progress;

    public BuzzwordData(JFLAGApplication app){

    }
    @Override
    public void reset() {

    }
}
