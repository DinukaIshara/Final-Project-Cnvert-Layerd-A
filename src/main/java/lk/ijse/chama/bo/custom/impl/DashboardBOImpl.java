package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.DashboardBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.*;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.CustomerDTO;
import lk.ijse.chama.dto.ItemDTO;
import lk.ijse.chama.dto.OrderDTO;
import lk.ijse.chama.entity.Customer;
import lk.ijse.chama.entity.Item;
import lk.ijse.chama.entity.Order;
import lk.ijse.chama.entity.tm.MostSellItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardBOImpl implements DashboardBO {
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);

    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILSDAO);

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);

    @Override
    public List<MostSellItemTm> getMostSellItem() throws SQLException, ClassNotFoundException {
        return orderDetailDAO.getMostSellItem();
    }

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
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = orderDAO.getAll();
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order: orders){
            orderDTOS.add(new OrderDTO(order.getOrderId(),order.getCustomerId(),order.getTrId(),order.getDate(),order.getPayment()));
        }
        return orderDTOS;
    }

    @Override
    public ItemDTO searchItemById(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id);
        ItemDTO itemDTO = new ItemDTO(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath());

        return itemDTO;
    }

    @Override
    public List<String> getAllDate() throws SQLException, ClassNotFoundException {
        return orderDAO.getAllDate();
    }

    @Override
    public double getLastMonthIncome() throws SQLException,ClassNotFoundException {
        return queryDAO.getLastMonthIncome();
    }
}
