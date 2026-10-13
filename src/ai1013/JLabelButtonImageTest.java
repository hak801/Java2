package ai1013;

import center.CenterFrame;

import javax.swing.*;
import java.awt.*;

public class JLabelButtonImageTest extends JFrame {
    public JLabelButtonImageTest() {
        setTitle("문자열 입력 컴포넌트");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JTextField textField = new JTextField("", 20);

        JTextArea area = new JTextArea("", 5, 20);

        JPasswordField passwordField = new JPasswordField(20);

        add(textField);
        add(area);
        add(passwordField);

        int w = 250, h = 250;
        int[] location = CenterFrame.getLocation(w, h);
        setBounds(location[0], location[1], w, h);// setLocation(x, y) + setSize(w, h)
        setVisible(true);
    }

    public static void main(String[] args) {
        new JLabelButtonImageTest();
    }
}