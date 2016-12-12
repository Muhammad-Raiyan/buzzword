package testing;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ishmam on 11/28/2016.
 *
 * @author ishmam
 */
public class WordTest {
    public static void main(String[] args) {
        HashSet<String> names = new HashSet<>();
        try(BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ishmam\\Documents\\Programming\\CSE219\\BuzzwordProject\\BuzzwordGame\\src\\testing\\name.txt"))){
            String line;
            while( (line = br.readLine()) != null){
                    String temp[] = line.split("\\s+");
                    for(String s : temp){
                        if(s.length()>=3 && s.chars().allMatch(Character::isLetter))names.add(s);
                    }
                //if(line.length() >= 3 && line.length()<=7)names.add(line);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

        List<String> names2 = names.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        Collections.sort(names2);

        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\ishmam\\Documents\\Programming\\CSE219\\BuzzwordProject\\BuzzwordGame\\src\\testing\\names2.txt")))){
            names2.forEach(node ->{
                try {
                    bw.write(node.toString() + "\n");
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}
