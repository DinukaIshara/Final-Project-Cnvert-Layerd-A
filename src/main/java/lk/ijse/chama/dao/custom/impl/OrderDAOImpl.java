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

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM orders");//pstm.executeQuery();

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
        boolean result = SQLUtill.execute("INSERT INTO orders VALUES(?, ?, ?, ? ,?)",order.getOrderId(),order.getCustomerId(),order.getTrId(),order.getDate(),order.getPayment());
        return result;
    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
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

        List<String> dateList = new ArrayList<>();

        ResultSet resultSet = SQLUtill.execute("SELECT order_date FROM orders GROUP BY order_date");//pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            dateList.add(id);
        }
        return dateList;
    }

    @Override
    public String getLastOrderId() throws SQLException,ClassNotFoundException {

        ResultSet resultSet = SQLUtill.execute("SELECT order_id FROM orders ORDER BY CAST(SUBSTRING(order_id, 2) AS UNSIGNED) DESC LIMIT 1;");//pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }
}
