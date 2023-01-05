import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Tag> tags = new ArrayList<>();
        String str = "ABAABABAABBBBBBBBBBBBAA";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a stream or leave blank to use default: ");
        //read stream
        String stream = scanner.nextLine();
        //if empty just use the default slide value
        if(stream.isEmpty()) stream = str;

        //compress using lz77
        Compressor compressor = new Compressor(stream, tags);
        compressor.lz77(12);
        //output all the tags found
        for(int i = 0; i < tags.size(); i++) System.out.println(tags.get(i));


        //make sure it returns the same string again
        System.out.println(compressor.lz77Decompress());
    }
}