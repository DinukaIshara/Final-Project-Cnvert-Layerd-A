package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order> {
    List<String> getAllDate() throws SQLException,ClassNotFoundException;
    String getLastOrderId() throws SQLException,ClassNotFoundException;
}
