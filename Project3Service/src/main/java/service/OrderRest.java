package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import impl.OrderMangerImpl;
import impl.UserManagerImpl;
import interfacedef.OrderManager;
import interfacedef.UserManager;
import pojo.Order;
import pojo.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("order")
public class OrderRest {
    UserManager userManager = UserManagerImpl.getInstance();
    OrderManager orderManager = OrderMangerImpl.getInstance();

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders(@HeaderParam("Authorization") String token) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }
        int userId = user.getUserId();
        String role = user.getRole();

        JSONObject responseJson = new JSONObject();
        if (!role.equals("admin")) {
            responseJson.put("code", 401);
            responseJson.put("message", "Permission Denied");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }

        OrderManager orderManager = OrderMangerImpl.getInstance();
        ArrayList<Order> allOrders = orderManager.getAllOrder();
        System.out.println(allOrders);
        responseJson.put("code", 200);
        responseJson.put("orders", allOrders);
        return Response.ok()
                .entity(responseJson.toJSONString())
                .build();
    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserOrder(@HeaderParam("Authorization") String token) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }
        int userId = user.getUserId();
        JSONObject responseJson = new JSONObject();
        OrderManager orderManager = OrderMangerImpl.getInstance();
        ArrayList<Order> allOrders = orderManager.getMyOrder(userId);
        responseJson.put("code", 200);
        responseJson.put("orders", allOrders);
        return Response.ok()
                .entity(responseJson.toJSONString())
                .build();
    }

    @PUT
    @Path("modifyStatus")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOrderStatus(@HeaderParam("Authorization") String token, @FormParam("status") String status, @FormParam("orderId") int orderId) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }
        int userId = user.getUserId();
        String role = user.getRole();

        JSONObject responseJson = new JSONObject();
        boolean change;
        if (role.equals("admin")) {
            change = orderManager.changeOrder(orderId, status);
        } else {
            change = orderManager.changeOrderByUser(orderId, userId);
        }
        if (change) {
            responseJson.put("code", 200);
            responseJson.put("message", "Order status changed!");
        } else {
            responseJson.put("code", 400);
            responseJson.put("message", "Order status change failed");
        }
        return Response.ok()
                .entity(responseJson.toJSONString())
                .build();
    }

    @PUT
    @Path("modifyDate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOrderDate(@HeaderParam("Authorization") String token, @FormParam("pickupDate") long pickupDate, @FormParam("orderId") int orderId) {
        JSONObject responseJson = new JSONObject();
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }
        int userId = user.getUserId();
        String role = user.getRole();
        boolean change;
        if (role.equals("admin")) {
            change = orderManager.changePickupDate(orderId, pickupDate);
        } else {
            change = orderManager.changePickupDateByUser(orderId, pickupDate, userId);
        }
        if (change) {
            responseJson.put("code", 200);
            responseJson.put("message", "Order Pickup Date changed!");
        } else {
            responseJson.put("code", 400);
            responseJson.put("message", "Order Pickup Date change failed");
        }
        return Response.ok()
                .entity(responseJson.toJSONString())
                .build();
    }

    @POST
    @Path("createOrder")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@HeaderParam("Authorization") String token, @FormParam("pickupDate") long pickupDate, @FormParam("bookId") int bookId) {
        User user;
        try {
            user = TokenValidate.tokenValidate(token);
        } catch (PermissionException e) {
            return e.response;
        }
        int userId = user.getUserId();
        String role = user.getRole();
        JSONObject responseJson = new JSONObject();

        OrderManager orderManager = OrderMangerImpl.getInstance();
        System.out.println(pickupDate);
        System.out.println(bookId);
        System.out.println(userId);
        int orderId = orderManager.createOrder(pickupDate, bookId, userId);
        if (orderId != -1) {
            responseJson.put("code", 200);
            responseJson.put("message", "Order created");
            responseJson.put("orderId", orderId);
        } else {
            responseJson.put("code", 400);
            responseJson.put("message", "Create Order failed");
        }
        return Response.ok()
                .entity(responseJson.toJSONString())
                .build();
    }
}
