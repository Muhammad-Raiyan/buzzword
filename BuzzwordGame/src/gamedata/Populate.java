package gamedata;

import trie.Trie;
import trie.TrieNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by ishmam on 11/28/2016.
 *
 * @author ishmam
 */
public class Populate {
    Trie trie;
    HashMap<Integer, String> map;
    static int place;
    String mode;

    public Populate(String mode) {
        this.mode = mode;
        trie = new Trie();
        map = new HashMap<>();
        place = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("BuzzwordGame\\resources\\words\\" + getFile(mode) + ".txt"))){
            String line;
            while( (line = br.readLine()) != null){
                if(line.length()>= 3 && line.length()<=7)trie.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateList();
        if(map.size()<16){
            for(int i=0; i<16; i++){
                if(map.get(i) == null){
                    Random r = new Random();
                    char c = (char) (r.nextInt(26) + 'a');
                    map.put(i, ""+c);
                }
            }
        }
        //System.out.println(map);
    }

    private String getFile(String mode) {
        switch(mode){
            case "Famous People":
                return "names";
            case "Famous":
                return "names";
            case "Dictionary Words":
                return "dictionary";
            case "Science":
                return "science";
            case "Places":
                return "dictionary";
        }
        return mode;
    }

    private void generateList() {
        TrieNode tNode;
        char c;
        do {
            Random r = new Random();
            c = (char) (r.nextInt(26) + 'a');
            tNode = trie.searchNode(Character.toString(c));
        }while(tNode==null);
        map.put(place, ""+c);
        place++;
        fillNext(tNode);
    }

    public void fillNext(TrieNode root){
        if(root.children.size() == 0) return;
        ArrayList value = new ArrayList<>(root.children.values());
        Collections.shuffle(value);
        while(value.size()!=0) {
            insert(String.valueOf(value.get(0)));
            fillNext((TrieNode) value.remove(0));
        }
    }

    public void insert(String letter){
        int[] arr = {0, 1,2,3,7,11,15, 4,5,6,10,14,8,9,13,12};
        if(place<16)map.put(arr[place], letter);
        place++;
    }

    public HashMap<Integer, String> getMap() {
        return map;
    }

    public Trie getTrie() {
        return trie;
    }
}
