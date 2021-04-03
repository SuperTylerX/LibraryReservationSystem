package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import impl.OrderMangerImpl;
import interfacedef.OrderManager;
import pojo.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("order")
public class OrderRest {
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders(@HeaderParam("userId") String strUserId, @HeaderParam("role") String role) {
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
        responseJson.put("code", 200);
        responseJson.put("orders", allOrders);
        return Response.ok()
                .entity(responseJson.toJSONString())
                .build();
    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserOrder(@HeaderParam("userId") String strUserId, @HeaderParam("role") String role) {
        JSONObject responseJson = new JSONObject();
        if (!role.equals("admin")) {
            responseJson.put("code", 401);
            responseJson.put("message", "Permission Denied");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }
        int userId = Integer.parseInt(strUserId);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOrderStatus(@HeaderParam("userId") String strUserId, @HeaderParam("role") String role, @FormParam("status") String status, @FormParam("orderId") int orderId) {
        JSONObject responseJson = new JSONObject();
        if (!role.equals("admin")) {
            responseJson.put("code", 401);
            responseJson.put("message", "Permission Denied");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }
        int userId = Integer.parseInt(strUserId);
        OrderManager orderManager = OrderMangerImpl.getInstance();
        boolean change = orderManager.changeOrder(orderId, status);
        if (change) {
            responseJson.put("code", 200);
            responseJson.put("message", "Order status changed!");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        } else {
            responseJson.put("code", 400);
            responseJson.put("message", "Order status change failed");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }

    }

    @PUT
    @Path("modifyDate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOrderDate(@HeaderParam("userId") String strUserId, @HeaderParam("role") String role, @FormParam("pickupDate") long pickupDate, @FormParam("orderId") int orderId) {
        JSONObject responseJson = new JSONObject();
        if (!role.equals("admin")) {
            responseJson.put("code", 401);
            responseJson.put("message", "Permission Denied");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }
        int userId = Integer.parseInt(strUserId);
        OrderManager orderManager = OrderMangerImpl.getInstance();
        boolean change = orderManager.changePickupDate(orderId, pickupDate);
        if (change) {
            responseJson.put("code", 200);
            responseJson.put("message", "Order Pickup Date changed!");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        } else {
            responseJson.put("code", 400);
            responseJson.put("message", "Order Pickup Date change failed");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }
    }

    @POST
    @Path("createOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@HeaderParam("userId") String strUserId, @HeaderParam("role") String role, @FormParam("pickupDate") long pickupDate, @FormParam("bookId") int bookId) {
        JSONObject responseJson = new JSONObject();
        if (!role.equals("admin")) {
            responseJson.put("code", 401);
            responseJson.put("message", "Permission Denied");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }
        int userId = Integer.parseInt(strUserId);
        OrderManager orderManager = OrderMangerImpl.getInstance();
        boolean create = orderManager.createOrder(pickupDate, bookId, userId);
        if (create) {
            responseJson.put("code", 200);
            responseJson.put("message", "Order created");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        } else {
            responseJson.put("code", 400);
            responseJson.put("message", "Create Order failed");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }
    }
}
