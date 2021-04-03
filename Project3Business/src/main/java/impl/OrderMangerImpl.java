package impl;

import dao.OrderDao;
import interfacedef.OrderManager;
import interfacedef.UserManager;
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
    public boolean createOrder(long pickupDate, int bookId, int userId) {
        OrderDao orderDao=new OrderDao();
        boolean create=orderDao.createOrder("PROCESSING",userId,bookId,pickupDate);
        return create;
    }

    @Override
    public boolean changeOrder(int orderId, String status) {
        OrderDao orderDao=new OrderDao();
        boolean change=orderDao.changeStatus(status,orderId);
        return change;
    }

    @Override
    public boolean changePickupDate(int orderId, long pickupDate) {
        OrderDao orderDao=new OrderDao();
        boolean change=orderDao.changePickupDate(orderId,pickupDate);
        return change;
    }

    @Override
    public ArrayList<Order> getMyOrder(int userId) {
        ArrayList<Order>allOrders=new ArrayList<>();
        OrderDao orderDao=new OrderDao();
        allOrders=orderDao.getUserOrders(userId);
        return allOrders;
    }

    @Override
    public ArrayList<Order> getAllOrder() {
        ArrayList<Order>allOrders=new ArrayList<>();
        OrderDao orderDao=new OrderDao();
        allOrders=orderDao.getAllOrders();
        return allOrders;
    }

}
