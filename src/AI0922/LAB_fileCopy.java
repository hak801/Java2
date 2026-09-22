package AI0922;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LAB_fileCopy {
    public static void main(String[] args) {
        String src = "myData1.txt";
        String dest = "newFile.txt";

        try (FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dest)) {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }

            System.out.println("--- " + src + "가" + dest + "로 복사되었습니다. ---");
        } catch (IOException e) {
            System.out.println("파일 복사 중 오류가 발생하였습니다.: " + e.getMessage());
        }
    }
}
