package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.dao.SuperDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Custom;
import lk.ijse.chama.entity.DailyOrders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public interface UserDAO extends SuperDAO {

    boolean saveUser(String userName, String password) throws SQLException,ClassNotFoundException;
    String Checkcredential(String username) throws SQLException,ClassNotFoundException;

}
