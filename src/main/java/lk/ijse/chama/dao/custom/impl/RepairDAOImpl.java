package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.RepairDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Repair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepairDAOImpl implements RepairDAO  {

    @Override
    public ArrayList<Repair> getAll() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM repair";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);*/

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM repair");//pstm.executeQuery();

        ArrayList<Repair> repairList = new ArrayList<>();

        while (resultSet.next()) {
            String repairId = resultSet.getString(1);
            LocalDate reciveDate = resultSet.getDate(2).toLocalDate();
            LocalDate returnDate = resultSet.getDate(3).toLocalDate();
            double cost = resultSet.getDouble(4);
            String description = resultSet.getString(5);
            String custId = resultSet.getString(6);
            String itemName = resultSet.getString(7);

            Repair repair = new Repair(repairId, reciveDate, returnDate, cost, description, custId, itemName);
            repairList.add(repair);
        }
        return repairList;
    }

    @Override
    public boolean save(Repair repair) throws SQLException, ClassNotFoundException {
        /*String sql = "INSERT INTO repair VALUES(?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, repair.getRepairId());
        pstm.setObject(2, repair.getReciveDate());
        pstm.setObject(3, repair.getReturnDate());
        pstm.setObject(4, repair.getCost());
        pstm.setObject(5, repair.getDescription());
        pstm.setObject(6, repair.getCustId());
        pstm.setObject(7, repair.getItemName());

        return pstm.executeUpdate() > 0;*/
        boolean result = SQLUtill.execute("INSERT INTO repair VALUES(?, ?, ?, ?, ?, ?, ?)",repair.getRepairId(), repair.getReciveDate(),repair.getReturnDate(),repair.getCost(),repair.getDescription(),repair.getCustId(),repair.getItemName());
        return result;
    }

    @Override
    public boolean update(Repair repair) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE repair SET date_recive = ?, date_return = ?, repair_cost = ?, description = ?, cust_id = ?, itemName = ? WHERE rep_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, repair.getReciveDate());
        pstm.setObject(2, repair.getReturnDate());
        pstm.setObject(3, repair.getCost());
        pstm.setObject(4, repair.getDescription());
        pstm.setObject(5, repair.getCustId());
        pstm.setObject(6, repair.getItemName());
        pstm.setObject(7, repair.getRepairId());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID(String currentId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT rep_id FROM repair ORDER BY CAST(SUBSTRING(rep_id, 2) AS UNSIGNED) DESC LIMIT 1;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String Id = resultSet.getString(1);
            String[] split = Id.split("R");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);

            if(idNum >= 1){
                return "R" + 0 + 0 + ++idNum;
            }else if(idNum >= 9){
                return "R" + 0 + ++idNum;
            } else if(idNum >= 99){
                return "R" + ++idNum;
            }
        }
        return "R001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM repair WHERE rep_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public Repair search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM repair WHERE rep_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String rep_id = resultSet.getString(1);
            LocalDate recive = resultSet.getDate(2).toLocalDate();
            LocalDate retu = resultSet.getDate(3).toLocalDate();
            double cost = resultSet.getDouble(4);
            String description =resultSet.getString(5);
            String cust_id = resultSet.getString(6);
            String itemName = resultSet.getString(7);

            return new Repair(rep_id, recive, retu, cost, description, cust_id, itemName);
        }

        return null;
    }

    @Override
    public List<String> getId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT rep_id FROM repair";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT rep_id FROM repair ORDER BY CAST(SUBSTRING(rep_id, 2) AS UNSIGNED) DESC LIMIT 1;";

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
