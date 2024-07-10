package lk.ijse.chama.bo.custom;

import javafx.scene.chart.XYChart;
import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dto.CustomDTO;
import lk.ijse.chama.dto.CustomerDTO;
import lk.ijse.chama.dto.ItemDTO;
import lk.ijse.chama.dto.OrderDTO;
import lk.ijse.chama.entity.Custom;
import lk.ijse.chama.entity.tm.MostSellItemTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface DashboardBO extends SuperBO {
    List<CustomDTO> getMostSellItem() throws SQLException ,ClassNotFoundException;
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;

    XYChart.Series getBarChart() throws SQLException,ClassNotFoundException;

    ItemDTO searchItemById(String id) throws SQLException, ClassNotFoundException;
    List<String> getAllDate() throws SQLException, ClassNotFoundException;
    double getLastMonthIncome() throws SQLException,ClassNotFoundException;
    CustomDTO orderDaily(Date date) throws SQLException,ClassNotFoundException;
}
