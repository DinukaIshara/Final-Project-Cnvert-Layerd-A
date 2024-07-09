package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.OrderDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Order> orderList = new ArrayList<>();

        while (resultSet.next()) {
            String orderId = resultSet.getString(1);
            String custId = resultSet.getString(2);
            String trId = resultSet.getString(3);
            Date date = resultSet.getDate(4);
            String payment = resultSet.getString(5);

            Order order = new Order(orderId, custId, trId, (java.sql.Date) date, payment);
            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public boolean save(Order order) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?, ? ,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        System.out.println(order);
        pstm.setString(1, order.getOrderId());
        pstm.setString(2, order.getCustomerId());
        pstm.setString(3, order.getTrId());
        pstm.setDate(4, order.getDate());
        pstm.setString(5, order.getPayment());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID(String currentId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT order_id FROM orders ORDER BY CAST(SUBSTRING(order_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if (rst.next()) {
            String id = rst.getString("order_id");
            String[] split = id.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getAllDate() throws SQLException, ClassNotFoundException {
        String sql = "SELECT order_date FROM orders GROUP BY order_date";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> dateList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            dateList.add(id);
        }
        return dateList;
    }

    @Override
    public String getLastOrderId() throws SQLException,ClassNotFoundException {
        String sql = "SELECT order_id FROM orders ORDER BY CAST(SUBSTRING(order_id, 2) AS UNSIGNED) DESC LIMIT 1;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }
}
