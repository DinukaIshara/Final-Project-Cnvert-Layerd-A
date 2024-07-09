package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.custom.OrderDetailDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.OrderDetail;
import lk.ijse.chama.entity.tm.MostSellItemTm;

import java.sql.PreparedStatement;
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
        String sql = "INSERT INTO order_detail VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, od.getOrderId());
        pstm.setString(2, od.getItemCode());
        pstm.setInt(3, od.getQty());
        pstm.setDouble(4, od.getUnitPrice());

        return pstm.executeUpdate() > 0;    //false ->  |
    }

    @Override
    public boolean save(List<OrderDetail> odList) throws SQLException,ClassNotFoundException {
        for (OrderDetail od : odList) {
            boolean isSaved = save(od);
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<MostSellItemTm> getMostSellItem() throws SQLException {
        String sql = "SELECT item_id,COUNT(order_id),SUM(qty) FROM order_detail GROUP BY item_id ORDER BY SUM(qty) DESC LIMIT 5;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<MostSellItemTm> sellItem = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            int count = resultSet.getInt(2);
            int sumQty = resultSet.getInt(3);

            MostSellItemTm mostSellItem = new MostSellItemTm(id, count, sumQty);
            sellItem.add(mostSellItem);
        }
        return sellItem;
    }

    @Override
    public boolean update(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID(String currentId) throws SQLException, ClassNotFoundException {
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
