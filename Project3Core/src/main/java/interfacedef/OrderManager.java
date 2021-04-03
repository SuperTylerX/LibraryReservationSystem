package interfacedef;

import pojo.Order;

import java.util.ArrayList;

public interface OrderManager {

    public boolean createOrder(long pickupDate,int bookId,int userId);

    public boolean changeOrder(int orderId, String status);

    public boolean changePickupDate(int orderId, long pickupDate);

    public ArrayList<Order> getMyOrder(int userId);

    public ArrayList<Order> getAllOrder();

}

