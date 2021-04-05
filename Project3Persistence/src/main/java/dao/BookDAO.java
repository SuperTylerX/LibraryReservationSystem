package dao;

import config.AppConfig;
import pojo.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {

    public boolean createBook(Book book) {
        Connection connection = DBConnection.getConnection();
        String query = "INSERT INTO book (book_google_id, book_isbn, book_title, book_publisher, book_author, book_stock ) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getGoogleId());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getTitle());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getAuthor());
            ps.setInt(6, book.getStock());
            int i = ps.executeUpdate();
            if (i == 1) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setBookId(generatedKeys.getInt(1));
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean deleteBook(int bookId) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE from book where book_id = ? ;";
        try {
            PreparedStatement ps1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, bookId);
            int i = ps1.executeUpdate();
            return i == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Book> getBooksByPageNum(int pageNum) {
        ArrayList<Book> books = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM book ORDER BY book_id LIMIT ?,? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, (pageNum - 1) * AppConfig.ROWS_PAGE);
            ps.setInt(2, AppConfig.ROWS_PAGE);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setGoogleId(rs.getString("book_google_id"));
                book.setIsbn(rs.getString("book_isbn"));
                book.setTitle(rs.getString("book_title"));
                book.setPublisher(rs.getString("book_publisher"));
                book.setAuthor(rs.getString("book_author"));
                book.setStock(rs.getInt("book_stock"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Book> getBooksByTitle(String title, int pageNum) {
        ArrayList<Book> books = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM book WHERE book_title LIKE ? ORDER BY book_title LIMIT ?,?";
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (title == null || title.isEmpty()) {
                ps.setString(1, "%");
            } else {
                ps.setString(1, "%" + title + "%");
            }
            ps.setInt(2, (pageNum - 1) * AppConfig.ROWS_PAGE);
            ps.setInt(3, AppConfig.ROWS_PAGE);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setGoogleId(rs.getString("book_google_id"));
                book.setIsbn(rs.getString("book_isbn"));
                book.setTitle(rs.getString("book_title"));
                book.setPublisher(rs.getString("book_publisher"));
                book.setAuthor(rs.getString("book_author"));
                book.setStock(rs.getInt("book_stock"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateBook(Book book) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE book SET book_google_id=?, book_isbn=?, book_title=?, book_publisher=?, book_author=?, book_stock=? WHERE book_id=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getGoogleId());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getTitle());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getAuthor());
            ps.setInt(6, book.getStock());
            ps.setInt(7, book.getBookId());
            int i = ps.executeUpdate();
            if (i == 1) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    return generatedKeys.next();
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Book getBookById(int bookId) {

        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM book WHERE book_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bookId);

            ResultSet rs = ps.executeQuery();
            Book book = new Book();
            if (rs.next()) {
                book.setGoogleId(rs.getString("book_google_id"));
                book.setIsbn(rs.getString("book_isbn"));
                book.setTitle(rs.getString("book_title"));
                book.setPublisher(rs.getString("book_publisher"));
                book.setAuthor(rs.getString("book_author"));
                book.setStock(rs.getInt("book_stock"));
            }
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
