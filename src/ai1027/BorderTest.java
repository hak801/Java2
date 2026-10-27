package ai1027;

import center.CenterFrame2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BorderTest extends JFrame {
    public BorderTest() {
        super("BorderLayout 연습");
//        BorderLayout에 수평, 수직 간격을 10px로 설정하시오
        setLayout(new BorderLayout(10, 10));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        JFrame에 5개의 영역에 버튼을 추가하시오
//        1. 버튼객체 생성
//        2. JFrame의 각 위치에 추가
//        각 버튼을 클릭하면 버튼의 라벨 문자열이 선택되었다는 메세지 창을 띄우시오

        JButton btn1 = new JButton("apple");
        JButton btn2 = new JButton("banana");
        JButton btn3 = new JButton("orange");
        JButton btn4 = new JButton("watermelon");
        JButton btn5 = new JButton("dragonfruit");

        add(btn1, BorderLayout.NORTH);
        add(btn2, BorderLayout.SOUTH);
        add(btn3, BorderLayout.EAST);
        add(btn4, BorderLayout.WEST);
        add(btn5, BorderLayout.CENTER);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clicked = (JButton) e.getSource();
                JOptionPane.showMessageDialog(BorderTest.this,
                        clicked.getText() + "가(이) 선택되었습니다.");
            }
        };

        btn1.addActionListener(listener);
        btn2.addActionListener(listener);
        btn3.addActionListener(listener);
        btn4.addActionListener(listener);
        btn5.addActionListener(listener);


        int w = 250, h = 250;

        Point point = CenterFrame2.getLocation(w, h);
        setBounds(point.x, point.y, w, h);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BorderTest();
    }
}
