package impl;

import interfacedef.BookManager;
import pojo.Book;

import java.util.ArrayList;

public class BookManagerImpl implements BookManager {
    private static final BookManager bookManagerImpl = new BookManagerImpl();

    public static BookManager getInstance() {
        return bookManagerImpl;
    }

    private BookManagerImpl() {

    }
    @Override
    public boolean addBook(Book book) {
        return false;
    }

    @Override
    public boolean deleteBook(int bookId) {
        return false;
    }

    @Override
    public ArrayList<Book> getBooks(int pageNum) {
        return null;
    }

    @Override
    public ArrayList<Book> getBooksByTitle(String title,int pageNum) {
        return null;
    }

    @Override
    public Book getBookById(int bookId) {
        // Need to Access Google API
        return null;
    }

    @Override
    public boolean updateBook(Book book) {
        return false;
    }
}
