package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dto.CustomerDTO;
import lk.ijse.chama.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
    String generateNewID() throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;
    CustomerDTO searchByCustomerId(String tel) throws SQLException, ClassNotFoundException;
    List<String> getCustomerTel() throws SQLException, ClassNotFoundException;
}
