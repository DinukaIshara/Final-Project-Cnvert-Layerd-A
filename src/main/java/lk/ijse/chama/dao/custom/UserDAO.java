package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.dao.SuperDAO;
import lk.ijse.chama.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO extends SuperDAO {

    boolean saveUser(String userName, String password) throws SQLException;
    String Checkcredential(String username) throws SQLException,ClassNotFoundException;
}
