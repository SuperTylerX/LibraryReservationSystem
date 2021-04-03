package interfacedef;

import pojo.Book;

import java.util.ArrayList;

public interface BookManager {

    boolean addBook(Book book);

    boolean deleteBook(int bookId);

    ArrayList<Book> getBooks(int pageNum);

    ArrayList<Book> getBooksByTitle(String title, int pageNum);

    Book getBookById(int bookId);

    boolean updateBook(Book book);

}
