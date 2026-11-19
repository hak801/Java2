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
}
