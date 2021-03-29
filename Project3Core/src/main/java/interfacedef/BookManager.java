package interfacedef;

import pojo.Book;

import java.util.ArrayList;

public interface BookManager {

    public boolean addBook(Book book);

    public boolean deleteBook(int bookId);

    public ArrayList<Book> getBooks(int pageNum);

    public ArrayList<Book> getBooksByTitle(String title, int pageNum);

    public Book getBookById(int bookId);

    public boolean updateBook(Book book);

}
