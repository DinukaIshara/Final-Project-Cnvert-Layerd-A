package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    List<String> getTel() throws SQLException, ClassNotFoundException;
    Customer searchById(String tel) throws SQLException, ClassNotFoundException;
}
