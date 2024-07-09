package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Item;
import lk.ijse.chama.entity.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {
    Item searchByName(String name) throws SQLException ,ClassNotFoundException;
    boolean updateQ(List<OrderDetail> isList) throws SQLException,ClassNotFoundException;
    boolean updateQty(String itemCode, int qty) throws SQLException,ClassNotFoundException;
    List<String> getDate() throws SQLException,ClassNotFoundException;
    List<String> getName() throws SQLException,ClassNotFoundException;
}
