package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.ItemDAO;
import lk.ijse.chama.entity.Item;
import lk.ijse.chama.entity.OrderDetail;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemList;
        try {
            ResultSet resultSet = SQLUtill.execute("SELECT * FROM item");

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

    @Override
    public boolean save(Item bi) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("INSERT INTO item VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",bi.getItemId(),bi.getName(),bi.getCategory(),bi.getBrand(),bi.getStockDate(),bi.getDescription(),bi.getWarranty(),bi.getType(),bi.getPath());
        return result;
    }

    @Override
    public boolean update(Item bi) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("UPDATE item SET name = ?, category = ?, brand = ?, date = ?, description = ?, warranty = ?, type = ?, path = ? WHERE item_id = ?",bi.getName(),bi.getCategory(),bi.getBrand(),bi.getStockDate(),bi.getDescription(),bi.getWarranty(),bi.getType(),bi.getPath(),bi.getItemId());
        return result;
    }

    @Override
    public Item searchByName(String name) throws SQLException,ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM item WHERE name = ?",name);
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

    @Override
    public boolean updateQ(List<OrderDetail> isList) throws SQLException,ClassNotFoundException {
        for (OrderDetail is : isList) {
            System.out.println(is.getItemCode() + "  qtyUpdate Item - " + is.getQty());

            boolean isUpdateQty = updateQty(is.getItemCode(), is.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateQty(String itemCode, int qty) throws SQLException,ClassNotFoundException {
        boolean result = SQLUtill.execute("UPDATE item_supplier_detail SET qty = qty - ? WHERE item_id = ?",qty,itemCode);
        System.out.println(qty+"  "+itemCode + result);
        return result;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT item_id FROM item ORDER BY CAST(SUBSTRING(item_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if (rst.next()) {
            String id = rst.getString("item_id");
            String[] split = id.split("I");
            int idNum = Integer.parseInt(split[1]);

            if(idNum >= 0){
                return "I" + 00 + ++idNum;
            }else if(idNum >= 9){
                return "I" + 0 + ++idNum;
            } else if(idNum >= 99){
                return "I" + ++idNum;
            }
        }
        return "I001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        boolean result =SQLUtill.execute("DELETE FROM item WHERE item_id = ?",id);
        return result;
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM item WHERE item_id = ?",id);
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

    @Override
    public List<String> getDate() throws SQLException,ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT date FROM item");

        List<String> dateList = new ArrayList<>();
        while (resultSet.next()) {
            dateList.add(resultSet.getString(1));
        }
        return dateList;
    }

    @Override
    public List<String> getName() throws SQLException,ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT name FROM item");

        List<String> nameList = new ArrayList<>();
        while (resultSet.next()) {
            nameList.add(resultSet.getString(1));
        }
        return nameList;
    }
}
