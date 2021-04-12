package impl;

import dao.OrderDao;
import dao.UserDao;
import interfacedef.OrderManager;
import pojo.Order;
import pojo.User;
import tools.mail.SMTP;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderMangerImpl implements OrderManager {
    private static final OrderManager OrderMangerImpl = new OrderMangerImpl();
    OrderDao orderDao = new OrderDao();
    UserDao userDao = new UserDao();

    public static OrderManager getInstance() {
        return OrderMangerImpl;
    }

    private OrderMangerImpl() {
    }


    @Override
    public int createOrder(long pickupDate, int bookId, int userId) {
        return orderDao.createOrder("PROCESSING", userId, bookId, pickupDate);
    }

    @Override
    public boolean changeOrderStatus(int orderId, String status) {
        return orderDao.changeStatus(status, orderId);
    }

    @Override
    public boolean changeOrderByUser(int orderId, int userId) {
        return orderDao.changeStatusByUser(orderId, userId);
    }

    @Override
    public boolean changePickupDate(int orderId, long pickupDate) {
        return orderDao.changePickupDate(orderId, pickupDate);
    }

    @Override
    public boolean changePickupDateByUser(int orderId, long pickupDate, int userId) {
        return orderDao.changePickupDateByUser(orderId, pickupDate, userId);
    }

    @Override
    public ArrayList<Order> getMyOrder(int userId) {
        return orderDao.getUserOrders(userId);
    }

    @Override
    public ArrayList<Order> getAllOrder() {
        return orderDao.getAllOrders();
    }

    @Override
    public boolean sendReadyEmail(int orderId) {
        Order order = orderDao.getOrdersById(orderId);
        User user = userDao.getUserById(order.getUserId());

        String title = "[Library] Your Reserved Book is Ready";
        String body = "Hello " + user.getUsername() + ",\r\n"
                + "Your reserved book \"" + order.getTitle() + "\" is ready. Please pick it on "
                + new SimpleDateFormat("yyyy-MM-dd").format(new Date(order.getPickupDate())) + ".";

        SMTP.sendMail(user.getEmail(), title, body);
        return true;
    }
}
