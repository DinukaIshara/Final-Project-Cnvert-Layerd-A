package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.custom.ItemSupplierDetailDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.ItemSupplierDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemSupplierDAOImpl implements ItemSupplierDetailDAO {
    @Override
    public ArrayList<ItemSupplierDetail> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item_supplier_detail";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<ItemSupplierDetail> itemSupplierList = new ArrayList<>();

        while (resultSet.next()) {
            String itemId = resultSet.getString(1);
            String supId = resultSet.getString(2);
            int handOnQty = resultSet.getInt(3);
            double unitPrice = resultSet.getDouble(4);

            ItemSupplierDetail itemSupplier = new ItemSupplierDetail(itemId,supId,handOnQty,unitPrice);
            itemSupplierList.add(itemSupplier);
        }
        return itemSupplierList;
    }

    @Override
    public boolean save(ItemSupplierDetail is) throws SQLException, ClassNotFoundException {
        System.out.println("start Item supper detail");
        String sql = "INSERT INTO item_supplier_detail VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, is.getItemId());
        pstm.setString(2, is.getSupId());
        pstm.setInt(3, is.getQty());
        pstm.setDouble(4, is.getUnitPrice());
        System.out.println("end Item supper detail");

        return pstm.executeUpdate() > 0;    //false ->  |
    }

    @Override
    public boolean update(ItemSupplierDetail is) throws SQLException, ClassNotFoundException {
        System.out.println("start Item supper detail");
        String sql = "UPDATE item_supplier_detail SET sup_id = ?, qty = ?, unit_price = ? WHERE item_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, is.getSupId());
        pstm.setInt(2, is.getQty());
        pstm.setDouble(3, is.getUnitPrice());
        pstm.setString(4, is.getItemId());
        System.out.println("end Item supper detail");

        return pstm.executeUpdate() > 0;    //false ->  |
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
    public ItemSupplierDetail search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item_supplier_detail WHERE item_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        System.out.println(id);
        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new ItemSupplierDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }
}
