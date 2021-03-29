package interfacedef;

import pojo.Order;

import java.util.ArrayList;

public interface OrderManager {

    public boolean createOrder(Order order);

    public boolean changeOrder(int orderId, String status);

    public boolean changePickupDate(int orderId, long pickupDate);

    public ArrayList<Order> getMyOrder(int userId, int pageNum);

    public int getMyOrderTotalNumber(int userId);

    public ArrayList<Order> getAllOrder(String type, int pageNum);

    public int getAllOrderTotalNumber(String type, int pageNum);
}

