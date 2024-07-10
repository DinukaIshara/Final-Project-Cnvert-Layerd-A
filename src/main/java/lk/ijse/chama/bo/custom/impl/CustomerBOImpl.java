package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.CustomerBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.CustomerDAO;
import lk.ijse.chama.dto.CustomerDTO;
import lk.ijse.chama.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);
    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTO = new ArrayList<>();

        for (Customer customer: customers){
            customerDTO.add(new CustomerDTO(customer.getCustId(), customer.getCName(), customer.getCAddress(), customer.getCNIC(), customer.getContactNo(), customer.getCEmail()));
        }
        return customerDTO;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customer.getCustId(), customer.getCName(), customer.getCAddress(), customer.getCNIC(), customer.getContactNo(), customer.getCEmail()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customer.getCustId(), customer.getCName(), customer.getCAddress(), customer.getCNIC(), customer.getContactNo(), customer.getCEmail()));
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchCustomer(String tel) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(tel);
        CustomerDTO customerDTO = new CustomerDTO(customer.getCustId(), customer.getCName(), customer.getCAddress(), customer.getCNIC(), customer.getContactNo(), customer.getCEmail());
        return customerDTO;
    }

    @Override
    public CustomerDTO searchByCustomerId(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.searchById(id);
        CustomerDTO customerDTO = new CustomerDTO(customer.getCustId(), customer.getCName(), customer.getCAddress(), customer.getCNIC(), customer.getContactNo(), customer.getCEmail());
        return customerDTO;
    }

    @Override
    public List<String> getCustomerTel() throws SQLException, ClassNotFoundException {
        return customerDAO.getTel();
    }
}
