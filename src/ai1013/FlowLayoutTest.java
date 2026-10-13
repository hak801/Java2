package ai1013;

import center.CenterFrame2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlowLayoutTest extends JFrame {
    public FlowLayoutTest() {
        setTitle("FlowLayout 연습");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        ArrayList<JButton> list = new ArrayList<JButton>(5);

        // 버튼마다 바뀔 색
        Color[] colors = {Color.PINK, Color.YELLOW, Color.CYAN, Color.GREEN, Color.ORANGE};

        for (int i = 0; i < 5; i++) {
            JButton btn = new JButton("버튼" + (i + 1));
            Color original = btn.getBackground(); // 원래 색 저장
            Color changed = colors[i];            // 바꿀 색

            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (btn.getBackground().equals(original)) {
                        btn.setBackground(changed);   // 원래 색이면 새 색으로
                    } else {
                        btn.setBackground(original);  // 바뀐 색이면 원래대로
                    }
                }
            });

            list.add(btn);
            add(btn);
        }

        int w = 250, h = 250;
        Point point = CenterFrame2.getLocation(w, h);
        setBounds(point.x, point.y, w, h);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FlowLayoutTest();
    }
}