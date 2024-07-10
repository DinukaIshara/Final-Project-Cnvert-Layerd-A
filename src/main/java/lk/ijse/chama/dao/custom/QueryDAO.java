package lk.ijse.chama.dao.custom;

import javafx.scene.chart.XYChart;
import lk.ijse.chama.dao.SuperDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Custom;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface QueryDAO extends SuperDAO {
    double getNetTot(String oId) throws SQLException,ClassNotFoundException;
    double getLastMonthIncome() throws SQLException,ClassNotFoundException;
    ArrayList<Custom> getBarChart() throws SQLException,ClassNotFoundException;
    Custom orderDaily(Date date) throws SQLException,ClassNotFoundException;
}
