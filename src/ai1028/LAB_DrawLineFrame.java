package ai1028;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LAB_DrawLineFrame extends JFrame {
    // 현재 그리는 선의 시작점(x1, y1)과 끝점(x2, y2)
    private int x1, y1, x2, y2;

    // 지금까지 그린 모든 선을 저장하는 목록 (각 선 = {x1, y1, x2, y2})
    private ArrayList<int[]> lines = new ArrayList<>();

    public LAB_DrawLineFrame() {
        setTitle("선 그리기");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new MyPanel());
        setVisible(true);
    }

    // 선을 그릴 패널
    class MyPanel extends JPanel {
        public MyPanel() {
            setBackground(Color.YELLOW); // 노란 배경

            addMouseListener(new MouseAdapter() {
                // 마우스를 누른 지점 = 선의 시작점
                @Override
                public void mousePressed(MouseEvent e) {
                    x1 = e.getX();
                    y1 = e.getY();
                }

                // 마우스를 뗀 지점 = 선의 끝점
                @Override
                public void mouseReleased(MouseEvent e) {
                    x2 = e.getX();
                    y2 = e.getY();
                    lines.add(new int[]{x1, y1, x2, y2}); // 완성된 선을 목록에 추가
                    repaint(); // 화면 다시 그리기 요청
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // 배경을 먼저 그림
            g.setColor(Color.RED);

            // 저장된 모든 선을 다시 그림
            for (int[] line : lines) {
                g.drawLine(line[0], line[1], line[2], line[3]);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LAB_DrawLineFrame());
    }
}