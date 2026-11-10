package AI1103;

import center.SetFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MenuTest extends JFrame {
    JTextArea textArea = new JTextArea();
    JFileChooser fileChooser = new JFileChooser();
    JFrame frame;
    String[] btnIconFileNames = {"new", "open", "close", "Magenta", "Blue", "White"};
    JButton[] btns = new JButton[6];
    File currentFile = null; // 현재 열려 있거나 저장된 파일 (없으면 null)

    public MenuTest() {
        frame = this;
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(scrollPane);

        JToolBar toolbar = new JToolBar();
        add("North", toolbar);

        for (int i = 0; i < btns.length; i++) {
            btns[i] = new JButton(resizeIcon("toolbar/" + btnIconFileNames[i] + ".png", 48, 48));
            btns[i].addActionListener(btnListener);
            toolbar.add(btns[i]);
        }

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // ===== File 메뉴 =====
        JMenuItem newMenuItem = new JMenuItem("New");       // textArea의 문자열을 초기화
        JMenuItem openMenuItem = new JMenuItem("Open");     // JFileChooser로 파일 열기
        JMenuItem saveMenuItem = new JMenuItem("Save");     // [추가] 현재 파일에 저장 (없으면 Save As)
        JMenuItem saveAsMenuItem = new JMenuItem("Save As"); // [추가] 다른 이름으로 저장
        JMenuItem closeMenuItem = new JMenuItem("Close");   // System.exit

        // 단축키 지정
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(closeMenuItem);

        newMenuItem.addActionListener(fileListener);
        openMenuItem.addActionListener(fileListener);
        saveMenuItem.addActionListener(fileListener);
        saveAsMenuItem.addActionListener(fileListener);
        closeMenuItem.addActionListener(fileListener);

        // ===== Edit 메뉴 =====
        JMenuItem cutMenuItem = new JMenuItem("Cut");             // [추가] 잘라내기
        JMenuItem copyMenuItem = new JMenuItem("Copy");           // [추가] 복사
        JMenuItem pasteMenuItem = new JMenuItem("Paste");         // [추가] 붙여넣기
        JMenuItem selectAllMenuItem = new JMenuItem("Select All"); // [추가] 전체 선택

        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(selectAllMenuItem);
        editMenu.addSeparator();

        cutMenuItem.addActionListener(editListener);
        copyMenuItem.addActionListener(editListener);
        pasteMenuItem.addActionListener(editListener);
        selectAllMenuItem.addActionListener(editListener);

        JMenuItem whiteMenuItem = new JMenuItem("White");
        JMenuItem blueMenuItem = new JMenuItem("Blue");
        JMenuItem magentaMenuItem = new JMenuItem("Magenta");
        editMenu.add(whiteMenuItem);
        editMenu.add(blueMenuItem);
        editMenu.add(magentaMenuItem);

        whiteMenuItem.addActionListener(backgroundListener);
        blueMenuItem.addActionListener(backgroundListener);
        magentaMenuItem.addActionListener(backgroundListener);

        SetFrame.setFrame(this, "메뉴만들기", 500, 500);
    }

    ActionListener btnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton eBtn = (JButton) e.getSource();

            if (eBtn == btns[0]) {
                newFile();
            } else if (eBtn == btns[1]) {
                openFile();
            } else if (eBtn == btns[2]) {
                System.exit(0);
            } else if (eBtn == btns[3]) {
                textArea.setBackground(Color.MAGENTA);
            } else if (eBtn == btns[4]) {
                textArea.setBackground(Color.BLUE);
            } else if (eBtn == btns[5]) {
                textArea.setBackground(Color.WHITE);
            }
        }
    };

    ActionListener fileListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "New":
                    newFile();
                    break;
                case "Open":
                    openFile();
                    break;
                case "Save":
                    saveFile();
                    break;
                case "Save As":
                    saveFileAs();
                    break;
                case "Close":
                    System.exit(0);
                    break;
            }
        }
    };

    // [추가] Edit 메뉴 항목(잘라내기/복사/붙여넣기/전체 선택) 처리
    ActionListener editListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Cut":
                    textArea.cut();
                    break;
                case "Copy":
                    textArea.copy();
                    break;
                case "Paste":
                    textArea.paste();
                    break;
                case "Select All":
                    textArea.selectAll();
                    break;
            }
            textArea.requestFocus();
        }
    };

    ActionListener backgroundListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = null;
            switch (e.getActionCommand()) {
                case "White":
                    color = Color.WHITE;
                    break;
                case "Blue":
                    color = Color.BLUE;
                    break;
                case "Magenta":
                    color = Color.MAGENTA;
                    break;
            }
            textArea.setBackground(color);
        }
    };

    private void newFile() {
        textArea.setText("");
        currentFile = null;
        setTitle("메뉴만들기");
    }

    private void openFile() {
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            textArea.setText(readFile(selectedFile));
            textArea.setCaretPosition(0);
            currentFile = selectedFile;
            setTitle("메뉴만들기 - " + selectedFile.getName());
        }
    }

    // [추가] Save: 열려 있는 파일이 있으면 그 파일에 덮어쓰고, 없으면 Save As 실행
    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
        } else {
            writeFile(currentFile);
        }
    }

    // [추가] Save As: 저장 대화상자로 파일을 골라 저장
    private void saveFileAs() {
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.exists()) {
                int answer = JOptionPane.showConfirmDialog(frame,
                        selectedFile.getName() + " 파일이 이미 있습니다. 덮어쓰시겠습니까?",
                        "다른 이름으로 저장", JOptionPane.YES_NO_OPTION);
                if (answer != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            if (writeFile(selectedFile)) {
                currentFile = selectedFile;
                setTitle("메뉴만들기 - " + selectedFile.getName());
            }
        }
    }

    // [추가] textArea의 내용을 파일에 기록
    private boolean writeFile(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(textArea.getText());
            JOptionPane.showMessageDialog(frame, file.getName() + " 파일이 저장되었습니다.");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "저장 중 오류가 발생했습니다: " + e.getMessage(),
                    "오류", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        Image image = new ImageIcon(path).getImage();
        Image resized = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    public String readFile(File selectedFile) {
        String totalLines = "";
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalLines += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalLines;
    }

    public static void main(String[] args) {
        new MenuTest();
    }
}
