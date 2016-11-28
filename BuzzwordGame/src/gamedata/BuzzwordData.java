package gamedata;

import app.JFLAGApplication;
import components.JFLAGDataComponent;
import trie.Trie;
import trie.TrieNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordData implements JFLAGDataComponent {

    JFLAGApplication app;
    private Double progPerc;
    private Trie trie;
    private String currentMode;
    private int currentLevel;
    private ArrayList<Integer> scores;
    private HashMap<String, ArrayList<Integer>> progress;
    private ArrayList<String> letterList;
    public BuzzwordData() {
    }

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

        trie = new Trie();
        try(BufferedReader br = new BufferedReader(new FileReader("BuzzwordGame\\resources\\words\\science"))){
            String line;
            while( (line = br.readLine()) != null){
                trie.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateList();
    }

    public void generateList() {
        letterList = new ArrayList<>();
        TrieNode t;
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');
        while(((t = trie.searchNode(Character.toString(c)))==null)){
            r = new Random();
            c = (char) (r.nextInt(26) + 'a');
            t = trie.searchNode(Character.toString(c));
        }
        //t.children.clear();
        System.out.println(c);
        System.out.println(t.children.size());

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

    public void setProgress(HashMap<String, ArrayList<Integer>> progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "BuzzwordData{" +
                "currentMode='" + currentMode + '\'' +
                ", currentLevel=" + currentLevel +
                ", progress=" + progress +
                '}';
    }

    @Override
    public void reset() {

    }
}
