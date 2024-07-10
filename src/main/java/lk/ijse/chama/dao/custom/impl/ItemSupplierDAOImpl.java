package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
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
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM item_supplier_detail");//pstm.executeQuery();

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
        boolean result  = SQLUtill.execute("INSERT INTO item_supplier_detail VALUES(?, ?, ?, ?)",is.getItemId(),is.getSupId(),is.getQty(),is.getUnitPrice());
        return result;
    }



    @Override
    public boolean update(ItemSupplierDetail is) throws SQLException, ClassNotFoundException {
        System.out.println("start Item supper detail");
        boolean result  = SQLUtill.execute("UPDATE item_supplier_detail SET sup_id = ?, qty = ?, unit_price = ? WHERE item_id = ?",is.getSupId(),is.getQty(),is.getUnitPrice(),is.getItemId());
        return result;
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
    public ItemSupplierDetail search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM item_supplier_detail WHERE item_id = ?",id);//pstm.executeQuery();
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
