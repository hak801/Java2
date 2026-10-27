package ai1027;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LAB_RandomDice extends JFrame {

    private final JPanel gridPanel;
    private final Random random = new Random();

    // 주사위 숫자별 색상
    private final Color[] colors = {
            Color.RED,                  // 1
            new Color(101, 189, 69),    // 2
            new Color(40, 96, 168),     // 3
            Color.YELLOW,               // 4
            new Color(168, 90, 159),    // 5
            new Color(255, 181, 46)     // 6
    };

    // 가로와 세로 칸 수
    private final int rows = random.nextInt(10) + 1;
    private final int cols = random.nextInt(10) + 1;

    public LAB_RandomDice() {
        setTitle("주사위 숫자 채우기");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // 제목
        JLabel title = new JLabel(
                "주사위 숫자 채우기",
                SwingConstants.CENTER
        );
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // 격자 영역
        gridPanel = new JPanel();
        add(gridPanel, BorderLayout.CENTER);

        // 주사위 버튼
        JButton diceButton = new JButton("주사위 던지기");
        diceButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        diceButton.addActionListener(e -> fillDice());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(diceButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 처음 실행할 때 숫자 표시
        fillDice();

        setVisible(true);
    }

    // 격자에 주사위 숫자 채우기
    private void fillDice() {
        gridPanel.removeAll();

        // GridLayout으로 행과 열 설정
        gridPanel.setLayout(
                new GridLayout(rows, cols, 2, 2)
        );

        for (int i = 0; i < rows * cols; i++) {
            int number = random.nextInt(6) + 1;

            JLabel cell = new JLabel(
                    String.valueOf(number),
                    SwingConstants.CENTER
            );

            cell.setOpaque(true);
            cell.setBackground(colors[number - 1]);
            cell.setForeground(Color.BLACK);
            cell.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            cell.setBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 1)
            );

            gridPanel.add(cell);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LAB_RandomDice::new);
    }
}
