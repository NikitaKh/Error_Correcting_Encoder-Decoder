package correcter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException{
        Message letter = new Message("send.txt");
        letter.sending();
    }
}

class Message {

    byte[] content;
    int[][] binaryContent;
    int maxBit;

    Message(String s) throws IOException {
        content = Files.readAllBytes(Paths.get(s));
        maxBit = 8;
        binaryContent = new int[content.length][maxBit];
    }

    void sending() throws IOException {
        dexToBinary();
        curseARandomBit();
        binaryToDex();
        WriteContentInFile();
    }

    void dexToBinary() {
        for (int i = 0; i < content.length; i++) {
            for (int j = 0, elem = content[i]; j < maxBit; j++, elem /= 2) {
                binaryContent[i][maxBit - 1 - j] = elem % 2;
            }
        }
    }

    void curseARandomBit() {
        Random rand = new Random();
        for (int i = 0; i < content.length; i++) {
            binaryContent[i][rand.nextInt(maxBit)] ^= 1;
        }
    }

    void binaryToDex() {
        for (int i = 0; i < content.length; i++) {
            content[i] = 0;
            for (int j = 0; j < maxBit; j++) {
                content[i] += (byte) ((binaryContent[i][maxBit - 1 - j] & 1) * Math.pow(2,j));
            }
        }
    }

    void WriteContentInFile() throws IOException {
        OutputStream outputStream = new FileOutputStream("received.txt");
        outputStream.write(content);
        outputStream.close();
    }
}
