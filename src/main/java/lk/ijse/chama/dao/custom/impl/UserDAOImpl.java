package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.UserDAO;
import lk.ijse.chama.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean saveUser(String userName, String password) throws SQLException {
        String sql = "INSERT INTO users VALUES(?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userName);
        pstm.setObject(2, password);

        return pstm.executeUpdate() > 0;
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
