package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.OrderDetailDAO;
import lk.ijse.chama.entity.Custom;
import lk.ijse.chama.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetail od) throws SQLException, ClassNotFoundException {
        System.out.println( "save - "+ od);
        boolean result = SQLUtill.execute("INSERT INTO order_detail VALUES(?, ?, ?, ?)",od.getOrderId(),od.getItemCode(),od.getQty(),od.getUnitPrice());
        return result;
    }

    @Override
    public boolean save(List<OrderDetail> odList) throws SQLException,ClassNotFoundException {
        for (OrderDetail od : odList) {
            System.out.println( "Save "+ od);
            boolean isSaved = save(od);
            System.out.println("is saved - "+isSaved);
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Custom> getMostSellItem() throws SQLException,ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT item_id,COUNT(order_id),SUM(qty) FROM order_detail GROUP BY item_id ORDER BY SUM(qty) DESC LIMIT 5");//pstm.executeQuery();

        List<Custom> sellItem = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            int count = resultSet.getInt(2);
            int sumQty = resultSet.getInt(3);

            Custom mostSellItem = new Custom(id, count, sumQty);
            sellItem.add(mostSellItem);
        }
        return sellItem;
    }

    @Override
    public boolean update(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
