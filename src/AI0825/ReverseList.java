package AI0825;

import java.util.Arrays;
import java.util.Collections;

public class ReverseList {
    public static void main(String[] args) {
        String[] NewJeans = {"다니엘", "하니", "민지", "혜인", "해린"};
        System.out.println("원본:" + Arrays.toString(NewJeans));
        Collections.reverse(Arrays.asList(NewJeans));
        System.out.println("반전:" + Arrays.toString(NewJeans));
    }
}
