package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.RepairDAO;
import lk.ijse.chama.dao.custom.SupplierDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM supplier");

        ArrayList<Supplier> supList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String companyName = resultSet.getString(2);
            String personName = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String location = resultSet.getString(5);
            String email = resultSet.getString(6);

            Supplier supplier = new Supplier(id, companyName, personName, tel, location, email);
            supList.add(supplier);
        }
        return supList;
    }

    @Override
    public boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("INSERT INTO supplier VALUES(?, ?, ?, ?, ?, ?)",supplier.getSupId(),supplier.getCompanyName(),supplier.getPersonName(),supplier.getTel(),supplier.getLocation(),supplier.getEmail());
        return result;
    }

    @Override
    public boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("UPDATE supplier SET company_name = ?,person_name = ?, contact_no = ?, location = ?, email = ? WHERE sup_id = ?",supplier.getCompanyName(),supplier.getPersonName(),supplier.getTel(),supplier.getLocation(),supplier.getEmail(),supplier.getSupId());
        return result;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT sup_id FROM supplier ORDER BY CAST(SUBSTRING(sup_id, 2) AS UNSIGNED) DESC LIMIT 1");//pstm.executeQuery();
        if(resultSet.next()) {
            String Id = resultSet.getString(1);
            String[] split = Id.split("S");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);

            if(idNum >= 1){
                return "S" + 0 + 0 + ++idNum;
            }else if(idNum >= 9){
                return "S" + 0 + ++idNum;
            } else if(idNum >= 99){
                return "S" + ++idNum;
            }
        }
        return "S001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("DELETE FROM supplier WHERE sup_id = ?",id);
        return result;
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM supplier WHERE sup_id = ?",id);//pstm.executeQuery();
        if (resultSet.next()) {
            String sup_id = resultSet.getString(1);
            String comName = resultSet.getString(2);
            String person = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String location =resultSet.getString(5);
            String email = resultSet.getString(6);

            return new Supplier(sup_id, comName, person, contact, location, email);
        }

        return null;
    }

    @Override
    public Supplier searchByName(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM supplier WHERE company_name = ?",name);//pstm.executeQuery();
        if (resultSet.next()) {
            String sup_id = resultSet.getString(1);
            String comName = resultSet.getString(2);
            String person = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String location =resultSet.getString(5);
            String email = resultSet.getString(6);

            return new Supplier(sup_id, comName, person, contact, location, email);
        }

        return null;

    }

    @Override
    public List<String> getId() throws SQLException, ClassNotFoundException {
        List<String> idList = new ArrayList<>();

        ResultSet resultSet = SQLUtill.execute("SELECT sup_id FROM supplier");//pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    @Override
    public List<String> getName() throws SQLException, ClassNotFoundException {
        List<String> nameList = new ArrayList<>();

        ResultSet resultSet = SQLUtill.execute("SELECT company_name FROM supplier");//pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            nameList.add(id);
        }
        return nameList;
    }
}
