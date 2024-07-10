package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.UserDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Custom;
import lk.ijse.chama.entity.DailyOrders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean saveUser(String userName, String password) throws SQLException,ClassNotFoundException {
        boolean result = SQLUtill.execute("INSERT INTO users VALUES(?, ?)",userName,password);
        return result;
    }
    @Override
    public String Checkcredential(String username) throws SQLException,ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT user_name,password from users where user_name=?",username);
        if (resultSet.next()) {
            String dbpw = resultSet.getString("password");
            return dbpw;
        }
        return null;
    }


}
