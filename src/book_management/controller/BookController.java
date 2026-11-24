package book_management.controller;

import book_management.domain.Book;
import book_management.repository.BookRepository;
import book_management.view.BookInsertPan;
import book_management.view.BookSearchPan;
import book_management.view.BookUpdatePan;
import book_management.view.center.SetFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BookController extends JFrame {
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    BookRepository repository;
    ArrayList<Book> bookList;
    JComboBox<String> combo;
    BookSearchPan searchPan;
    BookInsertPan insertPan;
    BookUpdatePan updatePan;
    JTable updateTable;

    public BookController() {
//        도서정보 검색
        searchPan = new BookSearchPan();
        combo = searchPan.getCombo();
        repository = new BookRepository();
        bookList = repository.select(searchPan.getSearchWord(), combo.getSelectedIndex());
        searchPan.setBookList(bookList);
        searchPan.initView();

//        도서 삽입(추가)
        insertPan = new BookInsertPan();
        insertPan.setBookList(bookList);
        insertPan.initView();

        JButton btnSearch = searchPan.getBtnSearch();
        btnSearch.addActionListener(btnSearchListener);

        JButton btnInsert = insertPan.getBtnInsert();
        btnInsert.addActionListener(btnInsertListener);

//        도서 수정 및 삭제
        updatePan = new BookUpdatePan();
        updatePan.setBookList(bookList);
        updatePan.initView();

        updateTable = updatePan.getTable();
        updateTable.addMouseListener(updateTableListener);
        JButton btnUpdate = updatePan.getBtnUpdate();
        btnUpdate.addActionListener(btnUpdateListener);

//        JTabbedPane에 JPanel들을 추가
        tabbedPane.add("도서 검색", searchPan);
        tabbedPane.add("도서 추가", insertPan);
        tabbedPane.add("수정 및 삭제", updatePan);
        tabbedPane.addMouseListener(tabbedPaneListener);

        add(tabbedPane);

        SetFrame.setFrame(this, "도서관리 시스템", 700, 600);
    }

    ActionListener btnSearchListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            bookList = repository.select(searchPan.getSearchWord(), combo.getSelectedIndex());
            searchPan.setBookList(bookList);
            searchPan.putSearchResult();
        }
    };

    ActionListener btnInsertListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Book book = insertPan.getBook();
            repository.insert(book);
            ArrayList<Book> insertBookList = new ArrayList<Book>();
            insertBookList = repository.select("", 0);
            insertPan.setBookList(insertBookList);
            insertPan.putSearchResult();
        }
    };

    // 수정 테이블: 한 번 클릭하면 도서 정보를 입력칸에 표시, 더블클릭하면 해당 도서 삭제
    MouseAdapter updateTableListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int rowIndex = updateTable.getSelectedRow();
            if (rowIndex == -1) {
                return;
            }

            if (e.getClickCount() == 1) {
                updatePan.setTextField(rowIndex);
            } else if (e.getClickCount() == 2) {
                String isbn = updateTable.getValueAt(rowIndex, 0) + "";
                int answer = JOptionPane.showConfirmDialog(updatePan,
                        "도서번호 " + isbn + " 도서를 삭제하시겠습니까?", "도서 삭제", JOptionPane.YES_NO_OPTION);
                if (answer != JOptionPane.YES_OPTION) {
                    return;
                }

                repository.delete(isbn);
                ArrayList<Book> deleteBookList = new ArrayList<Book>();
                deleteBookList = repository.select("", 0);
                updatePan.setBookList(deleteBookList);
                updatePan.putSearchResult();
                updatePan.clearInput();
            }
        }
    };

    ActionListener btnUpdateListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Book book = updatePan.getBook();
            repository.update(book);
            ArrayList<Book> updateBookList = new ArrayList<Book>();
            updateBookList = repository.select("", 0);
            updatePan.setBookList(updateBookList);
            updatePan.putSearchResult();
        }
    };

    // 탭을 클릭하면 DB에서 도서 목록을 다시 조회해서 선택한 탭의 테이블을 최신 정보로 갱신
    MouseAdapter tabbedPaneListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int tabIndex = tabbedPane.getSelectedIndex();
            switch (tabIndex) {
                case 0: // 도서 검색: 현재 검색어/검색 조건 그대로 다시 조회
                    bookList = repository.select(searchPan.getSearchWord(), combo.getSelectedIndex());
                    searchPan.setBookList(bookList);
                    searchPan.putSearchResult();
                    break;
                case 1: // 도서 추가
                    insertPan.setBookList(repository.select("", 0));
                    insertPan.putSearchResult();
                    break;
                case 2: // 수정 및 삭제
                    updatePan.setBookList(repository.select("", 0));
                    updatePan.putSearchResult();
                    break;
            }
        }
    };

    public static void main(String[] args) {
        new BookController();
    }
}
