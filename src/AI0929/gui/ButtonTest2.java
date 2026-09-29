package AI0929.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonTest2 extends JFrame {
    public ButtonTest2() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSizeDim = toolkit.getScreenSize();

        int w = 500;
        int h = 200;

//        int sw = screenSizeDim.width; //정가운데로 표현되는 x좌표값
//        int sh = screenSizeDim.height; //정가운데로 표현되는 y좌표값
//        Dimension locationDim = CenterFrame.getLocation(w, h);
        int[] location = CenterFrame.getLocation(w, h);
        int x = location[0];
        int y = location[1];



        setLayout(new FlowLayout());
        setTitle("Button 컴포넌트");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JButton btn = new JButton("메세지 대화상자 보이기");
        add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "대화상자를 선택하셨네요.");
            }
        });

        setSize(w,h);
        setLocation(x, y);
        setVisible(true);

        setSize(500, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ButtonTest2();
    }
}