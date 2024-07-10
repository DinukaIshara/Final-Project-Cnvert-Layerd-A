package lk.ijse.chama.bo.custom.impl;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import lk.ijse.chama.bo.custom.DashboardBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.*;
import lk.ijse.chama.dto.CustomDTO;
import lk.ijse.chama.dto.CustomerDTO;
import lk.ijse.chama.dto.ItemDTO;
import lk.ijse.chama.dto.OrderDTO;
import lk.ijse.chama.entity.Custom;
import lk.ijse.chama.entity.Customer;
import lk.ijse.chama.entity.Item;
import lk.ijse.chama.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardBOImpl implements DashboardBO {
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);

    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILSDAO);

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);

    @Override
    public List<CustomDTO> getMostSellItem() throws SQLException, ClassNotFoundException {
        List<Custom> custom = orderDetailDAO.getMostSellItem();
        List<CustomDTO> customDTOS = new ArrayList<>();

        for (Custom custom1: custom){
            customDTOS.add(new CustomDTO(custom1.getItemId(),custom1.getOrderCount(),custom1.getSumQty()));
        }
        return customDTOS;
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
    public XYChart.Series getBarChart() throws SQLException, ClassNotFoundException {
        XYChart.Series series1 = new XYChart.Series();  // represent a series of data points on the chart.
        series1.setName("Chama Computers");
        List<Custom> dailyRevenueList = new ArrayList<>();/*DashboardRepo.getDateCount();*/
        try {
            List<Custom> customEntities = queryDAO.getBarChart();
            for (Custom customEntity:customEntities) {
                dailyRevenueList.add(new Custom(customEntity.getDate(),customEntity.getCount()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        for (Custom dailyRevenue: dailyRevenueList) {
            series1.getData().add(new XYChart.Data<>(dailyRevenue.getDate(),dailyRevenue.getCount()));  //xy chart class eke thiyana static innerclass ekak
        }
        return series1;
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

    @Override
    public CustomDTO orderDaily(Date date) throws SQLException,ClassNotFoundException {
        Custom custom = queryDAO.orderDaily(date);
        CustomDTO customDTO = new CustomDTO(custom.getDate(),custom.getCount(),custom.getSumQty());
        return customDTO;
    }
}
