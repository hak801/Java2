package book_management.view;

import book_management.domain.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class BookInsertPan extends JPanel {
    JTable table;
    DefaultTableModel model;
    ArrayList<Book> bookList;
    Book book;
    String[] tableHeader = {"도서번호", "도서명", "저자명", "출판사", "가격", "재고량", "카테고리"};
    // 입력 패널 라벨 순서: 텍스트필드 5개(0~4) 다음에 출판사(5), 카테고리(6) 콤보박스
    String[] header = {"도서번호", "도서명", "저자명", "가격", "재고량", "출판사", "카테고리"};
    String[] categoryNames = {"IT도서", "소설", "수필", "시"};
    String[] publishNames = {"한빛아카데미", "생명출판사", "민음사", "해냄출판사"};
    JLabel[] lbls = new JLabel[tableHeader.length];
    JTextField[] tfs = new JTextField[tableHeader.length - 2];
    JComboBox<String> comboCategory, comboPublish;
    JButton btnInsert = new JButton("도서추가");
    JPanel panS = new JPanel(new GridLayout(4, 4, 20, 2));

    public BookInsertPan() {
        bookList = new ArrayList<Book>();
        setLayout(new BorderLayout());

        comboCategory = new JComboBox<String>(categoryNames);
        comboPublish = new JComboBox<String>(publishNames);

        for (int i = 0; i < header.length; i++) {
            lbls[i] = new JLabel(header[i], JLabel.RIGHT);
            panS.add(lbls[i]);
            if (i < header.length - 2) {
                tfs[i] = new JTextField();
                panS.add(tfs[i]);
            } else if (i == 5) {
                panS.add(comboPublish);
            } else if (i == 6) {
                panS.add(comboCategory);
            }
        }
        panS.add(new JLabel());
        panS.add(btnInsert);
    }

    public void initView() {
        model = new DefaultTableModel(tableHeader, bookList.size()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        TableColumnModel tcm = table.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(50);
        tcm.getColumn(1).setPreferredWidth(200);
        tcm.getColumn(2).setPreferredWidth(150);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(50);
        tcm.getColumn(5).setPreferredWidth(50);
        tcm.getColumn(6).setPreferredWidth(100);

        JScrollPane scroll = new JScrollPane(table);
        putSearchResult();

        add("Center", scroll);
        add("South", panS);
    }

    public void putSearchResult() {
        model.setRowCount(bookList.size());
        Book book = null;

        for (int i = 0; i < bookList.size(); i++) {
            book = bookList.get(i);
            model.setValueAt(book.getIsbn(), i, 0);
            model.setValueAt(book.getName(), i, 1);
            model.setValueAt(book.getAuthor(), i, 2);
            model.setValueAt(book.getPublish_name(), i, 3);
            model.setValueAt(book.getPrice(), i, 4);
            model.setValueAt(book.getInventory(), i, 5);
            model.setValueAt(book.getCategory_name(), i, 6);
        }
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public JButton getBtnInsert() {
        return btnInsert;
    }

    // 입력칸 값으로 Book 객체 생성
    // tfs: 0=도서번호, 1=도서명, 2=저자명, 3=가격, 4=재고량
    // 가격/재고량이 숫자가 아니면 NumberFormatException 발생 (컨트롤러에서 처리)
    public Book getBook() {
        book = new Book();
        book.setIsbn(tfs[0].getText());
        book.setName(tfs[1].getText());
        book.setAuthor(tfs[2].getText());
        book.setPrice(Integer.parseInt(tfs[3].getText()));
        book.setInventory(Integer.parseInt(tfs[4].getText()));
        // getSelectedItem()은 Object를 반환하므로 String으로 형변환 필요
        book.setPublish_name((String) comboPublish.getSelectedItem());
        book.setCategory_name((String) comboCategory.getSelectedItem());
        return book;
    }

    public void clearInput() {
        for (JTextField tf : tfs) {
            tf.setText("");
        }
        comboPublish.setSelectedIndex(0);
        comboCategory.setSelectedIndex(0);
    }
}
