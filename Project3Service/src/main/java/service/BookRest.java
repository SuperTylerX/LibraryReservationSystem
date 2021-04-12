package service;

import com.alibaba.fastjson.JSONObject;
import impl.BookManagerImpl;
import interfacedef.BookManager;
import pojo.Book;
import pojo.User;

import javax.ws.rs.*;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("book")
public class BookRest {


    private static final BookManager bookManager = BookManagerImpl.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@HeaderParam("Authorization") String token, @QueryParam("pageNum") int pageNum) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }

        JSONObject responseJson = new JSONObject();

        if (user != null) {
            ArrayList<Book> books = bookManager.getBooks(pageNum);
            if (books == null || books.isEmpty()) {
                responseJson.put("code", 500);
            } else {
                JSONObject data = new JSONObject();
                int num = bookManager.getBooksNumber();
                data.put("total", num);
                data.put("books", books);
                responseJson.put("code", 200);
                responseJson.put("data", data);
            }
        } else {
            responseJson.put("code", 401);
            responseJson.put("message", "Unauthorized user");
        }

        return Response.ok().entity(responseJson.toJSONString()).build();
    }


    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByTitle(@HeaderParam("Authorization") String token, @QueryParam("title") String title, @QueryParam("pageNum") int pageNum) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }

        JSONObject responseJson = new JSONObject();

        if (user != null) {
            ArrayList<Book> books = bookManager.getBooksByTitle(title, pageNum);
            if (books == null) {
                responseJson.put("code", 500);
            } else {
                JSONObject data = new JSONObject();
                int num = bookManager.getBooksNumberByTitle(title);
                data.put("total", num);
                data.put("books", books);
                responseJson.put("code", 200);
                responseJson.put("data", data);
            }
        } else {
            responseJson.put("code", 401);
            responseJson.put("message", "Unauthorized user");
        }

        return Response.ok().entity(responseJson.toJSONString()).build();
    }


    @GET
    @Path("detail")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@HeaderParam("Authorization") String token, @QueryParam("bookId") int bookId) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }

        JSONObject responseJson = new JSONObject();

        if (user != null) {
            return Response.ok().entity(bookManager.getBookById(bookId)).build();
        } else {
            responseJson.put("code", 401);
            responseJson.put("message", "Unauthorized user");
            return Response.ok().entity(responseJson.toJSONString()).build();
        }
    }


    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(@HeaderParam("Authorization") String token, Book book) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }

        JSONObject responseJson = new JSONObject();

        if (user != null) {
            String role = user.getRole();

            if (role.equals("admin")) {
                boolean flag = bookManager.addBook(book);
                if (flag) {
                    responseJson.put("code", 200);
                } else {
                    responseJson.put("code", 500);
                }
            } else {
                responseJson.put("code", 401);
                responseJson.put("message", "Unauthorized user");
            }
        } else {
            responseJson.put("code", 401);
            responseJson.put("message", "Unauthorized user");
        }

        return Response.ok().entity(responseJson.toJSONString()).build();
    }


    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@HeaderParam("Authorization") String token, Book book) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }
        JSONObject responseJson = new JSONObject();

        if (user != null) {
            String role = user.getRole();

            if (role.equals("admin")) {
                boolean flag = bookManager.updateBook(book);
                if (flag) {
                    responseJson.put("code", 200);
                } else {
                    responseJson.put("code", 500);
                }
            } else {
                responseJson.put("code", 401);
                responseJson.put("message", "Unauthorized user");
            }
        } else {
            responseJson.put("code", 401);
            responseJson.put("message", "Unauthorized user");
        }

        return Response.ok().entity(responseJson.toJSONString()).build();

    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@HeaderParam("Authorization") String token, @QueryParam("bookId") int bookId) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }
        JSONObject responseJson = new JSONObject();

        if (user != null) {
            String role = user.getRole();

            if (role.equals("admin")) {
                boolean flag = bookManager.deleteBook(bookId);
                if (flag) {
                    responseJson.put("code", 200);
                } else {
                    responseJson.put("code", 500);
                }
            } else {
                responseJson.put("code", 401);
                responseJson.put("message", "Unauthorized user");
            }
        } else {
            responseJson.put("code", 401);
            responseJson.put("message", "Unauthorized user");
        }

        return Response.ok().entity(responseJson.toJSONString()).build();
    }


}
