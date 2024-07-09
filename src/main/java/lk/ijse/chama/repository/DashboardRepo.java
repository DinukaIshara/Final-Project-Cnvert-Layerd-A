package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.DailyOrders;
import lk.ijse.chama.entity.tm.MostSellItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardRepo {
    public static List<MostSellItemTm> getMostSellItem() throws SQLException {
        String sql = "SELECT item_id,COUNT(order_id),SUM(qty) FROM order_detail GROUP BY item_id ORDER BY SUM(qty) DESC LIMIT 5;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<MostSellItemTm> sellItem = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            int count = resultSet.getInt(2);
            int sumQty = resultSet.getInt(3);

            MostSellItemTm mostSellItem = new MostSellItemTm(id, count, sumQty);
            sellItem.add(mostSellItem);
        }
        return sellItem;
    }

    public static DailyOrders orderDaily(Date date) throws SQLException {
        String sql = "SELECT o.order_date,COUNT(DISTINCT o.order_id),SUM(od.qty) FROM orders o JOIN order_detail od ON o.order_id = od.order_id WHERE o.order_date = ? GROUP BY o.order_date ORDER BY o.order_date";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, date);

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            Date date1 = resultSet.getDate(1);
            int count = resultSet.getInt(2);
            int totQty = resultSet.getInt(3);

            return new DailyOrders(date1, count, totQty);
        }
        return null;
    }

    public static double getLastMonthIncome() throws SQLException {
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
}
