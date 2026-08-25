package AI0825;

import java.util.Random;

public class auddjs {
    public static void main(String[] args) {
        String[] auddjs = {"1. 삶이 있는 한 희망은 있다. - 키케로", "2. 산다는것 그것은 치열한 전투이다. -로망로랑", "3. 하루에 3시간을 걸으면 7년후에 지구를 한바퀴 돌 수 있다. -사무엘잭슨", "4. 언제나 현재에 집중할 수 있다면 행복할 것이다. -파울로 코엘료", "5. 진정으로 웃으려면 고통을 참아야하며, 나아가 고통을 즐길 줄 알아야 해 -찰리 채플린", "6. 직업에서 행복을 찾아라, 아니면 행복이 무엇인지 절대 모를 것이다. -엘버트 허버드", "7. 신은 용기있는자를 결코 버리지 않는다. -켄러", "8. 행복의 문이 하나 닫히면 다른 문이 열린다 그러나 우리는 종종 닫힌 문을 멍하니 바라보다가 우리를 향해 열린 문을 보지 못하게 된다. -헬렌켈러", "9. 피할 수 없으면 즐겨라 -로버트 엘리엇", "10. 단순하게 살아라. 현대인은 쓸데없는 절차와 일 때문에 얼마나 복잡한 삶을 살아가는가? -이드리스 샤흐"};

        Random random = new Random();
        int auddjsIndex = random.nextInt(auddjs.length);
        System.out.println("오늘의 명언:" + auddjs[auddjsIndex]);
    }
}
