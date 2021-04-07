package dao;

import pojo.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class OrderDao {
    public ArrayList<Order> getAllOrders() {
        Connection connection = DBConnection.getConnection();
        ArrayList<Order> allOrders = new ArrayList<>();
        try {
            String query = "select * from orders";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setBookId(rs.getInt("order_book_id"));
                order.setUserId(rs.getInt("order_user_id"));
                order.setPickupDate(rs.getLong("order_pickup_date"));
                order.setCreatedDate(rs.getLong("order_created_time"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setOrderId(rs.getInt("order_id"));
                allOrders.add(order);
            }

            ps.close();
            return allOrders;
        } catch (Exception e) {
            e.printStackTrace();
            return allOrders;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Order> getUserOrders(int userId) {
        Connection connection = DBConnection.getConnection();
        ArrayList<Order> allOrders = new ArrayList<>();
        try {
            String query = "select * from orders where order_user_id=?";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setBookId(rs.getInt("order_book_id"));
                order.setUserId(rs.getInt("order_user_id"));
                order.setPickupDate(rs.getLong("order_pickup_date"));
                order.setCreatedDate(rs.getLong("order_created_time"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setOrderId(rs.getInt("order_id"));
                allOrders.add(order);
            }

            return allOrders;
        } catch (Exception e) {
            e.printStackTrace();
            return allOrders;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Order> getOrdersByStatus(String status) {
        Connection connection = DBConnection.getConnection();
        ArrayList<Order> allOrders = new ArrayList<>();
        try {
            String query = "select * from orders where order_status=?";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setBookId(rs.getInt("order_book_id"));
                order.setUserId(rs.getInt("order_user_id"));
                order.setPickupDate(rs.getLong("order_pickup_date"));
                order.setCreatedDate(rs.getLong("order_created_time"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setOrderId(rs.getInt("order_id"));
                allOrders.add(order);
            }
            return allOrders;
        } catch (Exception e) {
            e.printStackTrace();
            return allOrders;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean changeStatusByUser(int order_id, int userId) {
        Connection connection = DBConnection.getConnection();
        try {
            String query = "UPDATE orders SET order_status=? where order_id=? and order_user_id=?";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "CANCELED");
            ps.setInt(2, order_id);
            ps.setInt(3, userId);
            int rs = ps.executeUpdate();
            return rs == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean changeStatus(String status, int order_id) {
        Connection connection = DBConnection.getConnection();

        try {
            String query = "UPDATE orders SET order_status=? where order_id=?";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, status);
            ps.setInt(2, order_id);
            int rs = ps.executeUpdate();

            return rs == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean changePickupDate(int order_id, long pickup_date) {
        Connection connection = DBConnection.getConnection();
        try {
            String query = "UPDATE orders SET order_pickup_date=? where order_id=?";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, pickup_date);
            ps.setInt(2, order_id);
            int rs = ps.executeUpdate();

            return rs == 1;
        } catch (Exception e) {
            System.out.println("exp");
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean changePickupDateByUser(int order_id, long pickup_date, int userid) {
        Connection connection = DBConnection.getConnection();
        try {
            String query = "UPDATE orders SET order_pickup_date=? where( order_id=? and order_user_id=? );";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, pickup_date);
            ps.setInt(2, order_id);
            ps.setInt(3, userid);
            int rs = ps.executeUpdate();
            return rs == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int createOrder(String status, int userId, int bookId, long pickupDate) {
        Connection connection = DBConnection.getConnection();
        try {
            String query = "insert into orders(order_user_id, order_book_id, order_created_time, order_pickup_date,order_status) values (?,?,?,?,?); ";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setLong(3, new Date().getTime());
            ps.setLong(4, pickupDate);
            ps.setString(5, status);
            int rs = ps.executeUpdate();

            if (rs == 1) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        ps.close();
                        connection.close();
                        return -1;
                    }
                }
            } else {
                ps.close();
                connection.close();
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
