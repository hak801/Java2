package AI0922;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageFileCopy {
    public static void main(String[] args) {
        try {
            FileInputStream fIn = new FileInputStream("dgs.jpg");
            FileOutputStream fOut = new FileOutputStream("dgsCopy.jpg");

            int data;
            while ((data = fIn.read()) != -1) {
                fOut.write((byte) data);
            }

            System.out.println("이미지 복사 완료");

            fIn.close();
            fOut.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
