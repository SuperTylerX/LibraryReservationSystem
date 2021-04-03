package service;

import com.alibaba.fastjson.JSONObject;
import impl.BookManagerImpl;
import interfacedef.BookManager;
import pojo.Book;

import javax.ws.rs.*;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;

@Path("book")
public class BookRest {


    private static final BookManager bookManager = BookManagerImpl.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Book> getBooks(@QueryParam("pageNum") int pageNum) {
        return bookManager.getBooks(pageNum);
    }


    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Book> getBooksByTitle(@QueryParam("title") String title, @QueryParam("pageNum") int pageNum) {
            return bookManager.getBooksByTitle(title, pageNum);
    }


    @GET
    @Path("detail")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBookById(@QueryParam("bookId") int bookId){
        return bookManager.getBookById(bookId);
    }


    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addBook(Book book) {
        JSONObject response = new JSONObject();
        boolean flag = bookManager.addBook(book);
        if (flag) {
            response.put("status", 200);
        } else {
            response.put("status", 500);
        }
        return response.toString();
    }


    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateBook(Book book) {
        JSONObject response = new JSONObject();
        boolean flag = bookManager.updateBook(book);
        if (flag) {
            response.put("status", 200);
        } else {
            response.put("status", 500);
        }
        return response.toString();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteBook(@QueryParam("bookId") int bookId) {
        JSONObject response = new JSONObject();
        boolean flag = bookManager.deleteBook(bookId);
        if (flag) {
            response.put("status", 200);
        } else {
            response.put("status", 500);
        }
        return response.toString();
    }


}
