package impl;

import dao.BookDAO;
import interfacedef.BookManager;
import pojo.Book;

import java.util.ArrayList;

public class BookManagerImpl implements BookManager {

    private static final BookManager bookManager = new BookManagerImpl();

    public static BookManager getInstance() {
        return bookManager;
    }

    private final BookDAO bookDAO = new BookDAO();

    @Override
    public boolean addBook(Book book) {
        return bookDAO.createBook(book);
    }

    @Override
    public boolean deleteBook(int bookId) {
        return bookDAO.deleteBook(bookId);
    }

    @Override
    public ArrayList<Book> getBooks(int pageNum) {
        return bookDAO.getBooksByPageNum(pageNum);
    }

    @Override
    public ArrayList<Book> getBooksByTitle(String title, int pageNum) {
        return bookDAO.getBooksByTitle(title, pageNum);
    }

    @Override
    public Book getBookById(int bookId) {
        // Need to Access Google API
        return null;
    }

    @Override
    public boolean updateBook(Book book) {
        return bookDAO.updateBook(book);
    }
}
