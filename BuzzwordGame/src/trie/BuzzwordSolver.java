package trie;

import gamedata.Populate;

import java.util.*;

/**
 * Created by ishmam on 12/10/2016.
 *
 * @author ishmam
 */
public class BuzzwordSolver {

    private Trie trie;
    private HashSet<String> solution;
    private String bboard[][];
    private ArrayList sortedSolution;

    public BuzzwordSolver() {
        solution = new HashSet<>();
        bboard = new String[4][4];
    }

    public void solve(Populate populate){
        this.trie = populate.getTrie();
        setupBoard(populate.getMap());
        findWords();
        sortedSolution = new ArrayList(solution);
        Collections.sort(sortedSolution);
    }

    private void setupBoard(HashMap<Integer, String> map) {
        int[] arr = new int[] {
                0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15
        };

        int pos = 0;
        for (int r = 0; r<4; r++){
            for(int c = 0; c<4; c++){
                String temp = map.get(arr[pos++]);
                bboard[r][c] = ((temp));
            }
        }
    }

    private void findWords() {
        boolean visited[][] = new boolean[4][4];

        String prefix = "";
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                recFindWord(bboard, visited, i, j, prefix);
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void recFindWord(String[][] bboard, boolean[][] visited, int i, int j, String prefix) {
        visited[i][j] = true;
        prefix += bboard[i][j];

        if(trie.search(prefix)) {
            solution.add(prefix);
        }

        for (int row = i-1; row <=i+1 && row < 4; row++){
            for (int col = j-1; col <= j+1 && col < 4; col++){
                if(row >=0 && col >= 0 && !visited[row][col])recFindWord(bboard, visited, row, col, prefix);
            }
        }

        prefix = prefix.substring(0, prefix.length()-1);
        visited[i][j] = false;
    }

    public ArrayList getSolution() {
        return sortedSolution;
    }
}
