package trie;

import java.util.HashMap;

/**
 * Created by ishmam on 11/28/2016.
 *
 * @author ishmam
 */

public class TrieNode {
    char c;
    public HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    public boolean isLeaf;

    public TrieNode() {}

    public TrieNode(char c){
        this.c = c;
    }

    @Override
    public String toString() {
        return ""+c;
    }

    public String getData(){
        return ""+c;
    }
}
