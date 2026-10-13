package ai1013;

import center.CenterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboBoxImageTest extends JFrame {
    JLabel lbl = new JLabel();
    public ComboBoxImageTest() {
        setTitle("문자열 입력 컴포넌트");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        String[] items = {"강아지", "고양이", "토끼", "미어캣", "코알라"};
        JComboBox comboBox = new JComboBox(items);
        comboBox.addItemListener(comboListener);

        add("North", comboBox);
        add("Center", lbl);


        int w = 500, h = 500;
        int[] location = CenterFrame.getLocation(w, h);
        setBounds(location[0], location[1], w, h);// setLocation(x, y) + setSize(w, h)
        setVisible(true);
    }

    ItemListener comboListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            ImageIcon icon = null;
            String selectedItem = (String) e.getItem();
            switch (selectedItem) {
                case "강아지":
                    icon = new ImageIcon("imgs/dog22.jpg");
                    break;
                case "고양이":
                    icon = new ImageIcon("imgs/cat.png");
                    break;
                case "토끼":
                    icon = new ImageIcon("imgs/rabbit.png");
                    break;
                case "미어캣":
                    icon = new ImageIcon("imgs/meerkat.png");
                    break;
                case "코알라":
                    icon = new ImageIcon("imgs/koala.png");
                    break;
                default:
                    icon = new ImageIcon("imgs/dog_icon.png");
                    break;
            }

            lbl.setIcon(icon);
        }
    };

    public static void main(String[] args) {
        new ComboBoxImageTest();
    }
}