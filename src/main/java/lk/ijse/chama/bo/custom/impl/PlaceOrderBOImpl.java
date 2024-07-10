package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.PlaceOrderBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.*;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.*;
import lk.ijse.chama.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);
    ItemSupplierDetailDAO itemSupplierDetailDAO = (ItemSupplierDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEMSUPPLIERDAO);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);
    OrderDetailDAO orderDetailsDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILSDAO);
    TransportDAO transportDAO = (TransportDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.TRANSPORTDAO);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        CustomerDTO customerDTO = new CustomerDTO(customer.getCustId(), customer.getCName(), customer.getCAddress(), customer.getCNIC(), customer.getContactNo(), customer.getCEmail());
        return customerDTO;
    }

    @Override
    public ItemDTO searchItem(String con) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.searchByName(con);
        ItemDTO itemDTO = new ItemDTO(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath());
        return itemDTO;
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTO = new ArrayList<>();

        for (Customer customer: customers){
            customerDTO.add(new CustomerDTO(customer.getCustId(), customer.getCName(), customer.getCAddress(), customer.getCNIC(), customer.getContactNo(), customer.getCEmail()));
        }
        return customerDTO;
    }

    @Override
    public List<String> getlocation() throws SQLException,ClassNotFoundException {
        return transportDAO.getlocation();
    }

    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item item: items){
            itemDTOS.add(new ItemDTO(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath()));
        }
        return itemDTOS;
    }

    @Override
    public ArrayList<ItemSupplierDetailDTO> getItemSupplierDetaiAll() throws SQLException, ClassNotFoundException {
        ArrayList<ItemSupplierDetail> itemSupplierss = itemSupplierDetailDAO.getAll();
        ArrayList<ItemSupplierDetailDTO> itemSupplierDetailDTOS = new ArrayList<>();

        for (ItemSupplierDetail itemSupplierDetail: itemSupplierss){
            itemSupplierDetailDTOS.add(new ItemSupplierDetailDTO(itemSupplierDetail.getItemId(),itemSupplierDetail.getSupId(),itemSupplierDetail.getQty(),itemSupplierDetail.getUnitPrice()));
        }
        return itemSupplierDetailDTOS;
    }

    @Override
    public ItemSupplierDetailDTO searchItemSuppliers(String id) throws SQLException, ClassNotFoundException {
        ItemSupplierDetail itemSupplierss = itemSupplierDetailDAO.search(id);
        ItemSupplierDetailDTO itemSupplierDetailDTO = new ItemSupplierDetailDTO(itemSupplierss.getItemId(),itemSupplierss.getSupId(),itemSupplierss.getQty(),itemSupplierss.getUnitPrice());

        return itemSupplierDetailDTO;
    }

    @Override
    public boolean purchaseOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetails) throws Exception {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            Order order = new Order(orderDTO.getOrderId(),orderDTO.getCustomerId(),orderDTO.getTrId(),orderDTO.getDate(),orderDTO.getPayment());
            ArrayList<OrderDetail> orderDetailEn = new ArrayList<>();

            System.out.println(order);

            for (OrderDetailDTO order1: orderDetails){
                orderDetailEn.add(new OrderDetail(order1.getOrderId(),order1.getItemCode(),order1.getQty(),order1.getUnitPrice()));
            }

            boolean isOrderSaved = orderDAO.save(order);

            if (isOrderSaved) {



                boolean isQtyUpdated = itemDAO.updateQ(orderDetailEn);

                if (isQtyUpdated) {
                    boolean isOrderDetailSaved = orderDetailsDAO.save(orderDetailEn);

                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }

            connection.rollback();
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }

    @Override
    public List<String> getItemName() throws SQLException, ClassNotFoundException {
        return itemDAO.getName();
    }

    @Override
    public List<String> getCustomerTel() throws SQLException, ClassNotFoundException {
        return customerDAO.getTel();

    }

    @Override
    public Transport searchByLocation(String loc) throws SQLException,ClassNotFoundException {
        return transportDAO.search(loc);
    }

    @Override
    public double getNetTot(String oId) throws SQLException, ClassNotFoundException {
        return queryDAO.getNetTot(oId);
    }

    @Override
    public String getLastOrderId() throws SQLException,ClassNotFoundException {
        return orderDAO.getLastOrderId();
    }
}
