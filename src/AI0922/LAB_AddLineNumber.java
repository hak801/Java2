package AI0922;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LAB_AddLineNumber {
    public static void main(String[] args) {
        String fillName = "data.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            int lineNo = 1;

            while ((line = reader.readLine()) !=null) {
                System.out.println(lineNo + " : " + line);
                lineNo++;
            }
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다.: " + e.getMessage());
        }
    }
}
