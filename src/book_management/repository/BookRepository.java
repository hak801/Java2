package book_management.repository;

import book_management.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRepository {

    public ArrayList<Book> select(String searchWord, int selectedIndex) {
        Connection con = JDBCConnecter.getConnection();
        String[] columnName = {"name", "publish_name", "author"};
        ArrayList<Book> bookList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (con == null) {
            return bookList;
        }
        if (selectedIndex < 0 || selectedIndex >= columnName.length) {
            selectedIndex = 0;
        }

        String sql = "select isbn, name, author, price, inventory, category_name, publish_name " +
                "from book b, category c, publish p " +
                "where b.category_id = c.category_id and b.publish_id = p.publish_id " +
                "and " + columnName[selectedIndex] + " like ? " +
                "order by isbn";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchWord + "%");
            rs = pstmt.executeQuery();
            Book book = null;
            while (rs.next()) {
                book = new Book();
                book.setIsbn(rs.getString("isbn"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getInt("price"));
                book.setInventory(rs.getInt("inventory"));
                book.setCategory_name(rs.getString("category_name"));
                book.setPublish_name(rs.getString("publish_name"));
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return bookList;
    }

    // 도서 추가: 카테고리명/출판사명으로 각각의 id를 찾아서 book 테이블에 insert
    // 반환값: 추가된 행 수 (실패 시 0)
    public int insert(Book book) {
        Connection con = JDBCConnecter.getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        if (con == null) {
            return result;
        }

        String sql = "insert into book (isbn, name, author, price, inventory, category_id, publish_id) " +
                "values (?, ?, ?, ?, ?, " +
                "(select category_id from category where category_name = ?), " +
                "(select publish_id from publish where publish_name = ?))";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getName());
            pstmt.setString(3, book.getAuthor());
            pstmt.setInt(4, book.getPrice());
            pstmt.setInt(5, book.getInventory());
            pstmt.setString(6, book.getCategory_name());
            pstmt.setString(7, book.getPublish_name());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("도서 추가 실패: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    // 도서 수정: 도서번호(isbn)로 찾아서 나머지 정보를 변경
    // 반환값: 변경된 행 수 (실패 시 0)
    public int update(Book book) {
        Connection con = JDBCConnecter.getConnection();
        String sql = "update book set name=?, author=?, price=?, inventory=?, category_id=?, publish_id=? where isbn=?";
        PreparedStatement pstmt = null;
        int result = 0;

        if (con == null) {
            return result;
        }

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getPrice());
            pstmt.setInt(4, book.getInventory());

//            출판사명 id 정수값으로 변경
            int publish_id = 0;
            switch (book.getPublish_name()) {
                case "한빛아카데미":
                    publish_id = 1;
                    break;
                case "생명출판사":
                    publish_id = 2;
                    break;
                case "민음사":
                    publish_id = 3;
                    break;
                case "해냄출판사":
                    publish_id = 4;
                    break;
            }
//            카테고리 id 정수값으로 변경
            int category_id = 0;
            switch (book.getCategory_name()) {
                case "IT도서":
                    category_id = 10;
                    break;
                case "소설":
                    category_id = 20;
                    break;
                case "수필":
                    category_id = 30;
                    break;
                case "시":
                    category_id = 40;
                    break;
            }

            pstmt.setInt(5, category_id);
            pstmt.setInt(6, publish_id);
            pstmt.setString(7, book.getIsbn());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    // 도서 삭제: 도서번호(isbn)로 찾아서 book 테이블에서 delete
    // 반환값: 삭제된 행 수 (실패 시 0)
    public int delete(String isbn) {
        Connection con = JDBCConnecter.getConnection();
        String sql = "delete from book where isbn=?";
        PreparedStatement pstmt = null;
        int result = 0;

        if (con == null) {
            return result;
        }

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, isbn);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}
