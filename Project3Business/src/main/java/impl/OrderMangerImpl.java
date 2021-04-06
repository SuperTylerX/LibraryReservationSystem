package impl;

import dao.OrderDao;
import interfacedef.OrderManager;
import pojo.Order;

import java.util.ArrayList;

public class OrderMangerImpl implements OrderManager {
    private static final OrderManager OrderMangerImpl = new OrderMangerImpl();

    public static OrderManager getInstance() {
        return OrderMangerImpl;
    }

    private OrderMangerImpl() {
    }


    @Override
    public int createOrder(long pickupDate, int bookId, int userId) {
        OrderDao orderDao = new OrderDao();
        return orderDao.createOrder("PROCESSING", userId, bookId, pickupDate);
    }

    @Override
    public boolean changeOrder(int orderId, String status) {
        OrderDao orderDao = new OrderDao();
        return orderDao.changeStatus(status, orderId);
    }

    @Override
    public boolean changeOrderByUser(int orderId,int userId) {
        OrderDao orderDao = new OrderDao();
        return orderDao.changeStatusByUser(orderId,userId);
    }

    @Override
    public boolean changePickupDate(int orderId, long pickupDate) {
        OrderDao orderDao = new OrderDao();
        return orderDao.changePickupDate(orderId, pickupDate);
    }

    @Override
    public boolean changePickupDateByUser(int orderId, long pickupDate, int userId) {
        OrderDao orderDao = new OrderDao();
        return orderDao.changePickupDateByUser(orderId, pickupDate, userId);
    }

    @Override
    public ArrayList<Order> getMyOrder(int userId) {
        ArrayList<Order> allOrders = new ArrayList<>();
        OrderDao orderDao = new OrderDao();
        allOrders = orderDao.getUserOrders(userId);
        return allOrders;
    }

    @Override
    public ArrayList<Order> getAllOrder() {
        ArrayList<Order> allOrders = new ArrayList<>();
        OrderDao orderDao = new OrderDao();
        allOrders = orderDao.getAllOrders();
        return allOrders;
    }

}
