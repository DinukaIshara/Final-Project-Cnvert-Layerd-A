package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Item;
import lk.ijse.chama.entity.OrderDetail;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BrandNewItemRepo {

    public static boolean save(Item bi) throws SQLException {
        System.out.println("item : " + bi);
        String sql = "INSERT INTO item VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, bi.getItemId());
        pstm.setString(2, bi.getName());
        pstm.setString(3, bi.getCategory());
        pstm.setString(4, bi.getBrand());
        pstm.setDate(5, Date.valueOf(bi.getStockDate()));
        pstm.setString(6, bi.getDescription());
        pstm.setString(7, bi.getWarranty());
        pstm.setString(8, bi.getType());
        pstm.setString(9, bi.getPath());

        return pstm.executeUpdate() > 0;
    }

    public static List<Item> getAll() {
        String sql = "SELECT * FROM item";
        List<Item> itemList;

        PreparedStatement pstm = null;
        try {
            pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            itemList = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String category = resultSet.getString(3);
                String brand = resultSet.getString(4);
                LocalDate date = resultSet.getDate(5).toLocalDate();
                String warranty = resultSet.getString(6);
                String description = resultSet.getString(7);
                String type = resultSet.getString(8);
                String path = resultSet.getString(9);

                Item item = new Item(id, name, category, brand, date, warranty, description, type, path);
                itemList.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return itemList;
    }

    public static List<String> getDate() throws SQLException {
        String sql = "SELECT date FROM item";

        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> dateList = new ArrayList<>();
        while (resultSet.next()) {
            dateList.add(resultSet.getString(1));
        }
        return dateList;
    }

    public static List<String> getName() throws SQLException {
        String sql = "SELECT name FROM item";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> nameList = new ArrayList<>();
        while (resultSet.next()) {
            nameList.add(resultSet.getString(1));
        }
        return nameList;
    }

    public static Item searchById(String code) throws SQLException {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, code);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)

            );
        }
        return null;
    }

    public static Item searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM item WHERE name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, name);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)

            );
        }
        return null;
    }

    public static boolean update(List<OrderDetail> isList) throws SQLException {
        for (OrderDetail is : isList) {
            System.out.println(is.getItemCode() + "qtyUpdate Item - " + is.getQty());
            boolean isUpdateQty = updateQty(is.getItemCode(), is.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String itemCode, int qty) throws SQLException {
        String sql = "UPDATE item_supplier_detail SET qty = qty - ? WHERE item_id = ?";
        System.out.println("update Item QTY");
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, itemCode);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE item SET name = ?, category = ?, brand = ?, date = ?, description = ?, warranty = ?, type = ?, path = ? WHERE item_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, item.getName());
        pstm.setObject(2, item.getCategory());
        pstm.setObject(3, item.getBrand());
        pstm.setObject(4, item.getStockDate());
        pstm.setObject(5, item.getDescription());
        pstm.setObject(6, item.getWarranty());
        pstm.setObject(7, item.getType());
        pstm.setObject(8, item.getPath());
        pstm.setObject(9, item.getItemId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM item WHERE item_id = ?";

        System.out.println(id);

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static String getLastId() throws SQLException {
        String sql = "SELECT item_id FROM item ORDER BY CAST(SUBSTRING(item_id, 2) AS UNSIGNED) DESC LIMIT 1;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String Id = resultSet.getString(1);
            return Id;
        }
        return null;
    }
}
