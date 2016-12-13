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
    private int levelScore;
    private String currentMode;
    private int currentLevel;
    private ArrayList<Integer> scores;
    private HashMap<String, ArrayList<Integer>> progress;
    //private ArrayList<String> letterList;
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

        /*trie = new Trie();
        try(BufferedReader br = new BufferedReader(new FileReader("BuzzwordGame\\resources\\words\\science.txt"))){
            String line;
            while( (line = br.readLine()) != null){
                trie.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //generateList();*/
    }

    public void generateList() {
        /*letterList = new ArrayList<>();
        TrieNode t;
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');
        while(((t = trie.searchNode(Character.toString(c)))==null)){
            r = new Random();
            c = (char) (r.nextInt(26) + 'a');
            t = trie.searchNode(Character.toString(c));
        }*/
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

    public ArrayList<Integer> getUnlockedLevels(String mode){
        switch(mode){
            case "Dictionary":
                return progress.get("Dictionary");
            case "Famous":
                return progress.get("Famous");
            case "Places":
                return progress.get("Places");
            case "Science":
                return progress.get("Science");
            default:
                return null;
        }
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

    public void setLevelScore(int levelScore, int level, String mode) {
        this.levelScore = levelScore;
        this.currentMode = mode;
        this.currentLevel = level;
        scores = progress.get(currentMode);
        scores.set(level-1, levelScore);
        if(scores.get(scores.size()-1)!=0)scores.add(0);
    }
}
