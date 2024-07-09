package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.custom.QueryDAO;
import lk.ijse.chama.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public double getNetTot(String oId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(od.qty * od.unit_price) AS net_total FROM orders o JOIN order_detail od ON o.order_id = od.order_id WHERE o.order_id = ? GROUP BY o.order_id;";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        System.out.println(oId);
        pstm.setString(1, oId);

        ResultSet resultSet = pstm.executeQuery();
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
    public ArrayList<String> getBarChart() throws SQLException {
        String sql = "SELECT\n" +
                "    DATE_FORMAT(MIN(o.order_date), '%Y-%m-%d') AS WeekStartDate,\n" +
                "    DATE_FORMAT(MAX(o.order_date), '%Y-%m-%d') AS WeekEndDate,\n" +
                "    COUNT(DISTINCT o.order_id) AS WeeklyOrders,\n" +
                "    SUM(od.qty * od.unit_price) AS TotalAmount\n" +
                "FROM\n" +
                "    orders o\n" +
                "JOIN \n" +
                "    order_detail od ON o.order_id = od.order_id\n" +
                "WHERE\n" +
                "    o.order_date BETWEEN (SELECT MIN(order_date) FROM orders) AND (SELECT MAX(order_date) FROM orders)\n" +
                "GROUP BY\n" +
                "    YEARWEEK(o.order_date, 1)\n" +
                "ORDER BY\n" +
                "    WeekStartDate;";

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet rst  = stm.executeQuery();

        ArrayList<String> date = new ArrayList<>();

        while (true) {
            if (!rst.next()) break;

            date .add(rst.getString(2));
        }
        return date;
    }
}
