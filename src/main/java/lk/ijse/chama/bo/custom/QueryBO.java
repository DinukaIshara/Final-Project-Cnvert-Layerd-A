package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryBO extends SuperBO {
    public double getNetTot(String oId) throws SQLException, ClassNotFoundException;
}
