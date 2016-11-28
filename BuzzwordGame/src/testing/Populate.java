package testing;

import trie.Trie;
import trie.TrieNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ishmam on 11/28/2016.
 *
 * @author ishmam
 */
public class Populate {

    Trie trie;
    ArrayList<String> list;

    public Populate() {
        trie = new Trie();
        list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("BuzzwordGame\\resources\\words\\science"))){
            String line;
            while( (line = br.readLine()) != null){
                trie.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateList();
        System.out.println(list);
    }

    private void generateList() {
        TrieNode tNode;
        char c;
        do {
            Random r = new Random();
            c = (char) (r.nextInt(26) + 'a');
            tNode = trie.searchNode(Character.toString(c));
        }while(tNode==null);
        list.add(""+c);
        fillNext((TrieNode) tNode);
    }

    public void fillNext(TrieNode root){
        if(root.children.size() == 0) return;
        ArrayList value = new ArrayList<>(root.children.values());
        Collections.shuffle(value);
        while(value.size()!=0) {
            list.add(String.valueOf((TrieNode) value.get(0)));
            fillNext((TrieNode) value.remove(0));
        }
    }

}
