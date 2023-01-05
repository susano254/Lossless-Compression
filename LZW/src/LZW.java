//Name: Mahmoud Salah
//ID: 20200722
import java.util.ArrayList;
import java.util.HashMap;

public class LZW implements Compressor{
    String stream;
    StringBuilder match;
    char nextChar;
    int code;
    HashMap<String, Integer> tableCompress;
    HashMap<Integer, String> tableDeCompress;
    ArrayList<Integer> outputCodes;


    LZW(String stream, ArrayList outputCodes){
        this.stream = stream;
        this.outputCodes = outputCodes;
    }

    @Override
    public void compress() {
        int matchCode;
        tableCompress = new HashMap<>();
        code = 128;

        for(int i = 0; i < stream.length(); i += match.length()) {
            matchCode = findLongestMatch(stream, i);
            outputCodes.add(matchCode);
        }
    }

    @Override
    public String decompress() {
        tableDeCompress = new HashMap<>();
        match = new StringBuilder();
        code = 128;
        String current = "", prev = "";

        for(int i = 0; i < outputCodes.size(); i++){
            //search the table for the code of the given string
            current  = searchTableDeCompress(outputCodes.get(i));
            //if not found apply prev+prev[0]
            if(current == null) current = prev + prev.charAt(0);
            //if first iteration or prev is empty no need to add one char
            if(!prev.isEmpty()) tableDeCompress.put(code++, prev + current.charAt(0));
            //update prev
            prev = current;
            //update match
            match.append(current);
        }
        return match.toString();
    }

    private int findLongestMatch(String stream, int index){
        int matchCode = 0;
        match = new StringBuilder();

        for(int i = index; i < stream.length(); i++){
            //init next char for the algorithm
            nextChar = stream.charAt(i);
            //search the table for the ascii or the match code
            int tempCode = searchTableCompress(match.toString() + nextChar);
            //if found update the match
            if(tempCode != -1){
                matchCode = tempCode;
                match.append(nextChar);
            }
            //else we finished
            else {
                tableCompress.put(match.toString() + nextChar, code++);
                return matchCode;
            }

        }

        return matchCode;
    }

    private int searchTableCompress(String s) {
        //if one char return its ascii
        if(s.length() == 1){
            return Integer.valueOf(s.charAt(0));
        }
        //else search the tableCompressMap for its  code
        else {
            if(tableCompress.get(s) != null) return tableCompress.get(s);
            return -1;
        }
    }

    private String searchTableDeCompress(int c) {
        //if one char return its ascii
        if(c < 128){
            return String.valueOf((char) c);
        }
        //else search the tableCompressMap for its  code
        else {
            if(tableDeCompress.get(c) != null) return tableDeCompress.get(c);
        }
        return null;
    }
}
