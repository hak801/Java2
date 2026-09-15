package AI0915;

import java.util.Arrays;
import java.util.Random;

public class LottoGenerator {

    static int lottoNumber() {
        Random random = new Random();

        int number = random.nextInt(45) + 1;

        return number;
    }

    public static void main(String[] args) {
        int[] lottoArray = {};

        int number = 0;

        System.out.println("로또 추첨을 시~~~~~~~~~~작 하겠습니다!");

        my_loop:
        while (true) {
            number = lottoNumber();

            for (int num: lottoArray) {
                if (number == num) {
                    continue my_loop;
                }
            }

            lottoArray = Arrays.copyOf(lottoArray, lottoArray.length + 1);

            lottoArray[lottoArray.length -1] = number;

            if (lottoArray.length == 6)
                break;

        }

        System.out.println("이번 주 로또 번호: ");

        System.out.println(Arrays.toString(lottoArray));

    }
}
