//Name: Mahmoud Salah
//ID: 20200722

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String strDefault = "ABAABABBAABAABAAAABABBBBBBBBPP";
        ArrayList<Integer> codes = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a stream or leave blank to use default: ");
        //read stream
        String stream = scanner.nextLine();
        //if empty just use the default slide value
        if(stream.isEmpty()) stream = strDefault;

        Compressor lzw = new LZW(stream, codes);
        lzw.compress();
        System.out.println(codes);
        System.out.println(lzw.decompress());
        System.out.println(stream.equals(lzw.decompress()));
    }
}