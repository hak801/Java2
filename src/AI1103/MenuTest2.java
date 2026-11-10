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

public class MenuTest2 extends JFrame {
    JTextArea textArea = new JTextArea();
    JFileChooser fileChooser = new JFileChooser();
    JFrame frame;

    // 툴바 아이콘 파일 이름 (toolbar 폴더 안의 PNG 파일)
    // [수정] "save" 아이콘을 저장 버튼으로 사용 → 첨부한 이미지를 toolbar/save.png 로 저장
    String[] btnIconFileNames = {"new", "open", "close", "save", "Magenta", "Blue", "White"};
    String[] btnToolTips = {"새 파일 (Ctrl+N)", "열기 (Ctrl+O)", "닫기",
            "저장 (Ctrl+S) / Shift+클릭: 다른 이름으로 저장", "배경 Magenta", "배경 Blue", "배경 White"};

    // [수정] 버튼 개수를 아이콘 개수와 맞춤 (6 → 7). 기존에는 마지막 아이콘이 빠지고 동작이 한 칸씩 밀려 있었음
    JButton[] btns = new JButton[btnIconFileNames.length];

    File currentFile = null; // 현재 열려 있거나 저장된 파일 (없으면 null)

    public MenuTest2() {
        frame = this;
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(scrollPane);

        JToolBar toolbar = new JToolBar();
        add("North", toolbar);

        for (int i = 0; i < btns.length; i++) {
            btns[i] = new JButton(resizeIcon("toolbar/" + btnIconFileNames[i] + ".png", 48, 48));
            btns[i].setToolTipText(btnToolTips[i]);
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
        JMenuItem newMenuItem = new JMenuItem("New");        // textArea의 문자열을 초기화
        JMenuItem openMenuItem = new JMenuItem("Open");      // JFileChooser로 파일 열기
        JMenuItem saveMenuItem = new JMenuItem("Save");      // 현재 파일에 저장 (없으면 Save As)
        JMenuItem saveAsMenuItem = new JMenuItem("Save As"); // 다른 이름으로 저장
        JMenuItem closeMenuItem = new JMenuItem("Close");    // System.exit

        // [추가] 메뉴에도 저장 아이콘 표시
        saveMenuItem.setIcon(resizeIcon("toolbar/save.png", 16, 16));
        saveAsMenuItem.setIcon(resizeIcon("toolbar/save.png", 16, 16));

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
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        JMenuItem selectAllMenuItem = new JMenuItem("Select All");

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

    // [수정] 아이콘 순서(new, open, close, save, Magenta, Blue, White)와 동작을 일치시킴
    ActionListener btnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton eBtn = (JButton) e.getSource();

            if (eBtn == btns[0]) {          // new
                newFile();
            } else if (eBtn == btns[1]) {   // open
                openFile();
            } else if (eBtn == btns[2]) {   // close
                System.exit(0);
            } else if (eBtn == btns[3]) {   // save (첨부한 저장 아이콘)
                // Shift를 누른 채 클릭하면 다른 이름으로 저장, 그냥 클릭하면 저장
                if ((e.getModifiers() & ActionEvent.SHIFT_MASK) != 0) {
                    saveFileAs();
                } else {
                    saveFile();
                }
            } else if (eBtn == btns[4]) {   // Magenta
                textArea.setBackground(Color.MAGENTA);
            } else if (eBtn == btns[5]) {   // Blue
                textArea.setBackground(Color.BLUE);
            } else if (eBtn == btns[6]) {   // White
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

    // Edit 메뉴 항목(잘라내기/복사/붙여넣기/전체 선택) 처리
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

    // Save: 열려 있는 파일이 있으면 그 파일에 덮어쓰고, 없으면 Save As 실행
    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
        } else {
            writeFile(currentFile);
        }
    }

    // Save As: 저장 대화상자로 파일을 골라 저장
    private void saveFileAs() {
        if (currentFile != null) {
            fileChooser.setSelectedFile(currentFile); // 현재 파일 이름을 기본값으로
        }
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

    // textArea의 내용을 파일에 기록
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
        StringBuilder totalLines = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalLines.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalLines.toString();
    }

    public static void main(String[] args) {
        new MenuTest2(); // [수정] MenuTest → MenuTest2
    }
}