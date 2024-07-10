package lk.ijse.chama.dao.custom.impl;

import javafx.scene.chart.XYChart;
import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.QueryDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Custom;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public double getNetTot(String oId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT SUM(od.qty * od.unit_price) AS net_total FROM orders o JOIN order_detail od ON o.order_id = od.order_id WHERE o.order_id = ? GROUP BY o.order_id",oId);//pstm.executeQuery();
        if(resultSet.next()) {
            double netTot = resultSet.getDouble(1);
            System.out.println(netTot);
            return netTot;
        }
        return 0.0;
    }

    @Override
    public double getLastMonthIncome() throws SQLException,ClassNotFoundException {
        String sql = "WITH LastMonth AS (SELECT DATE_FORMAT(MAX(order_date), '%Y-%m') AS last_month FROM orders ),MonthlySalesRevenue AS (SELECT DATE_FORMAT(o.order_date, '%Y-%m') AS month,SUM(od.qty * od.unit_price) AS total_revenue FROM orders o JOIN order_detail od ON o.order_id = od.order_id GROUP BY DATE_FORMAT(o.order_date, '%Y-%m') ), MonthlyCosts AS (SELECT DATE_FORMAT(o.order_date, '%Y-%m') AS month, SUM(od.qty * isd.unit_price) AS total_cost FROM orders o JOIN order_detail od ON o.order_id = od.order_id JOIN item_supplier_detail isd ON od.item_id = isd.item_id GROUP BY DATE_FORMAT(o.order_date, '%Y-%m')) SELECT msr.total_revenue - COALESCE(mc.total_cost, 0) AS last_month_profit FROM MonthlySalesRevenue msr JOIN LastMonth lm ON msr.month = lm.last_month LEFT JOIN MonthlyCosts mc ON msr.month = mc.month";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            double profit = resultSet.getDouble(1);

            return profit;
        }
        return 0.0;
    }

    @Override
    public ArrayList<Custom> getBarChart() throws SQLException,ClassNotFoundException {
        ArrayList<Custom> dailyRevenueTmList = new ArrayList<>();

        ResultSet rst = SQLUtill.execute("SELECT\n" +
                "    DATE_FORMAT(o.order_date, '%Y-%m-%d') AS OrderDate,\n" +
                "    SUM(od.qty * od.unit_price) AS TotalAmount\n" +
                "FROM\n" +
                "    orders o\n" +
                "JOIN \n" +
                "    order_detail od ON o.order_id = od.order_id\n" +
                "WHERE\n" +
                "    o.order_date BETWEEN (SELECT MIN(order_date) FROM orders) AND (SELECT MAX(order_date) FROM orders)\n" +
                "GROUP BY\n" +
                "    DATE_FORMAT(o.order_date, '%Y-%m-%d')\n" +
                "ORDER BY\n" +
                "    OrderDate");

        while (rst.next()) {
            String date = rst.getString(1);
            int count = rst.getInt(2);

            Custom dailyRevenueTm = new Custom(date,count);
            dailyRevenueTmList.add(dailyRevenueTm);
        }
        return dailyRevenueTmList;

    }

    @Override
    public Custom orderDaily(Date date) throws SQLException,ClassNotFoundException {

        ResultSet resultSet = SQLUtill.execute("SELECT o.order_date,COUNT(DISTINCT o.order_id),SUM(od.qty) FROM orders o JOIN order_detail od ON o.order_id = od.order_id WHERE o.order_date = ? GROUP BY o.order_date ORDER BY o.order_date",date);//pstm.executeQuery();
        while (resultSet.next()) {
            String date1 = resultSet.getString(1);
            int count = resultSet.getInt(2);
            int totQty = resultSet.getInt(3);

            return new Custom(date1, count, totQty);
        }
        return null;
    }
}
