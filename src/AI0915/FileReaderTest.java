package AI0915;

import java.io.*;

public class FileReaderTest {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader((new FileReader("D:/fileIOTest/myData1.txt")));

            String line = "";

            line = br.readLine();
            System.out.println(line);

            line = br.readLine();
            System.out.println(line);

            line = br.readLine();
            System.out.println(line);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}