package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.CustomerDAO;
import lk.ijse.chama.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM customer");

        ArrayList<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String nic = resultSet.getString(4);
            String tel = resultSet.getString(5);
            String email = resultSet.getString(6);

            Customer customer = new Customer(id, name, address, nic, tel, email);
            cusList.add(customer);
        }
        return cusList;
    }

    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("INSERT INTO customer VALUES(?, ?, ?, ?, ?, ?)", customer.getCustId() ,customer.getCName(), customer.getCAddress(), customer.getCNIC(), customer.getContactNo(), customer.getCEmail());
        return result;
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("UPDATE customer SET c_name = ?, c_address = ?, c_nic = ?, contact_no = ?, email = ? WHERE cust_id = ?",customer.getCName(), customer.getCAddress(), customer.getCNIC(), customer.getContactNo(), customer.getCEmail(), customer.getCustId());
        return result;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT cust_id FROM customer ORDER BY CAST(SUBSTRING(cust_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if (rst.next()) {
            String id = rst.getString(1);
            String[] split = id.split("C");
            int idNum = Integer.parseInt(split[1]);

            if(idNum >= 1){
                return "C" + 0 + 0 + ++idNum;
            }else if(idNum >= 9){
                return "C" + 0 + ++idNum;
            } else if(idNum >= 99){
                return "C" + ++idNum;
            }
        }
        return "C001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        boolean result =SQLUtill.execute("DELETE FROM customer WHERE cust_id = ?",id);
        return result;
    }

    @Override
    public Customer search(String tel) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM customer WHERE contact_no = ?",tel);
        if (resultSet.next()) {
            String cId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String nic = resultSet.getString(4);
            String cTel = resultSet.getString(5);
            String email = resultSet.getString(6);

            Customer customer = new Customer(cId, name, address, nic, cTel,email);

            return customer;
        }

        return null;
    }

    @Override
    public Customer searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM customer WHERE cust_id = ?",id);
        if (resultSet.next()) {
            String cId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String nic = resultSet.getString(4);
            String cTel = resultSet.getString(5);
            String email = resultSet.getString(6);

            Customer customer = new Customer(cId, name, address, nic, cTel,email);

            return customer;
        }

        return null;
    }

    @Override
    public List<String> getTel() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT contact_no FROM customer");
        List<String> idList = new ArrayList<>();

        while (rst.next()) {
            String id = rst.getString(1);
            idList.add(id);
        }
        return idList;

    }
}
