package impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.AppConfig;
import dao.BookDAO;
import interfacedef.BookManager;
import pojo.Book;
import tools.network.Http;

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

        // get google volumeId
        Book book = bookDAO.getBookById(bookId);
        String volumeId = book.getGoogleId();

        // Need to Access Google API
        JSONObject obj = JSON.parseObject(Http.sendGet(AppConfig.GOOGLE_API_PATH + volumeId));
        book.setDescription(obj.getJSONObject("volumeInfo").getString("description"));
        book.setPageCount(String.valueOf(obj.getJSONObject("volumeInfo").getIntValue("pageCount")));
        book.setCategories(obj.getJSONObject("volumeInfo").getJSONArray("categories").toString());
        book.setImageLink(obj.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("medium"));
        book.setLanguage(obj.getJSONObject("volumeInfo").getString("language"));
        book.setPublishedDate(obj.getJSONObject("volumeInfo").getString("publishedDate"));
        return book;
    }

    @Override
    public int getBooksNumber() {
        return bookDAO.getBooksNumber();
    }

    @Override
    public int getBooksNumberByTitle(String title) {
        return bookDAO.getBooksNumberByTitle(title);
    }

    @Override
    public boolean updateBook(Book book) {
        return bookDAO.updateBook(book);
    }
}
