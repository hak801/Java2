package AI0922;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LAB_SecureDecodeTest {

    static final int KEY = 47;

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("secure.txt"))) {

            String line;
            System.out.println("--- secure.txt 해독 결과 ---");

            while ((line = reader.readLine()) != null) {
                String decrypted = decrypt(line);
                System.out.println(decrypted);
            }

        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    static String decrypt(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            sb.append((char) (ch - KEY));
        }
        return sb.toString();
    }
}