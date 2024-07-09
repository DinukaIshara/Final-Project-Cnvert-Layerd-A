package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.SuperDAO;
import lk.ijse.chama.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    double getNetTot(String oId) throws SQLException,ClassNotFoundException;
    double getLastMonthIncome() throws SQLException,ClassNotFoundException;

    public ArrayList<String> getBarChart() throws SQLException;
}
