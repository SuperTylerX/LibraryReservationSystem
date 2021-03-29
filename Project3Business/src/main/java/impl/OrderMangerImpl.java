package impl;

import interfacedef.OrderManager;
import pojo.Order;

import java.util.ArrayList;

public class OrderMangerImpl implements OrderManager {
    @Override
    public boolean createOrder(Order order) {
        return false;
    }

    @Override
    public boolean changeOrder(int orderId, String status) {
        return false;
    }

    @Override
    public boolean changePickupDate(int orderId, long pickupDate) {
        return false;
    }

    @Override
    public ArrayList<Order> getMyOrder(int userId, int pageNum) {
        return null;
    }

    @Override
    public int getMyOrderTotalNumber(int userId) {
        return 0;
    }

    @Override
    public ArrayList<Order> getAllOrder(String type, int pageNum) {
        return null;
    }

    @Override
    public int getAllOrderTotalNumber(String type, int pageNum) {
        return 0;
    }
}
