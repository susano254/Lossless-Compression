import java.util.ArrayList;

public class Compressor {
    String stream;
    final int DEFAULT_MAX = 12;
    int windowMaxSize;
    StringBuffer searchBuffer;
    StringBuilder match;
    char nextChar;
    ArrayList<Tag> tags;

    public Compressor(String stream, ArrayList tags){
        this.stream = stream;
        this.tags = tags;
        this.tags.clear();
        searchBuffer = new StringBuffer();
    }

    public void lz77(){
        lz77(DEFAULT_MAX);
    }
    public void lz77(int windowMaxSize){
        this.windowMaxSize = windowMaxSize;
        for(int i = 0; i < stream.length(); i += match.length() + 1) {
            //find the longest match return a tag
            Tag tag = findLongestMatch(stream, i, searchBuffer);
            //add the tag
            tags.add(tag);
            //modify buffer ( add new matched and trim if necessary )
            searchBuffer.append(match.toString() + nextChar);
            if(searchBuffer.length() > windowMaxSize) {
                searchBuffer = searchBuffer.delete(0, searchBuffer.length() - windowMaxSize);
            }
        }
        //repeat
    }
    public String lz77Decompress(){
        StringBuilder stream = new StringBuilder();

        for(int i = 0; i < tags.size(); i++){
            Tag tag = tags.get(i);
            if(tag.offset != 0){
                int start = stream.length() - tag.offset;
                int end = start + tag.length;
                stream.append(stream.substring(start, end));
            }
            stream.append(tag.nextChar);
        }

        return stream.toString();
    }

    private Tag findLongestMatch(String stream, int index, StringBuffer searchBuffer) {
        match = new StringBuilder();
        int tempIndex;
        int matchIndex = searchBuffer.length();
        int i;

        for(i = index; i < index + windowMaxSize && i < stream.length(); i++) {
            nextChar = stream.charAt(i);
            tempIndex = searchBuffer.lastIndexOf(match.toString() + nextChar);

            if(tempIndex != -1){
                matchIndex = tempIndex;
                match.append(nextChar);
            }
            else {
                break;
            }
        }
        if(i >= stream.length() ) nextChar = ' ';
        int offset = searchBuffer.length() - matchIndex;
        int length = match.length();
        return new Tag(offset, length, nextChar);
    }

    public void lz78(String stream){

    }
    public void lzw(String stream){

    }
}
