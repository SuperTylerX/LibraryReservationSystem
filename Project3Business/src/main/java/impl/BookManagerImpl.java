package impl;


import config.AppConfig;
import dao.BookDAO;
import interfacedef.BookManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import pojo.Book;

import java.io.IOException;
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
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(AppConfig.GOOGLE_API_PATH + volumeId);
            CloseableHttpResponse response = client.execute(httpget);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            HttpEntity httpEntity = response.getEntity();
            JSONObject obj = new JSONObject(EntityUtils.toString(httpEntity));
            book.setDescription(obj.getJSONObject("volumeInfo").getString("description"));
            book.setPageCount(String.valueOf(obj.getJSONObject("volumeInfo").getInt("pageCount")));
            book.setCategories(obj.getJSONObject("volumeInfo").getJSONArray("categories").toString());
            book.setImageLink(obj.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("medium"));
            book.setLanguage(obj.getJSONObject("volumeInfo").getString("language"));

            return book;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateBook(Book book) {
        return bookDAO.updateBook(book);
    }
}
