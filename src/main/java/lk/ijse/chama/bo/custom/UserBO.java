package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserBO extends SuperBO {
    boolean saveUser(String userName, String password) throws SQLException ;
    String Checkcredential(String username) throws SQLException,ClassNotFoundException;
}
