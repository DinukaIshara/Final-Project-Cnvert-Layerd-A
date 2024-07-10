package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.*;
import lk.ijse.chama.entity.ItemSupplierDetail;
import lk.ijse.chama.entity.Transport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {
    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException ;

    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException ;

    String generateOrderID() throws SQLException, ClassNotFoundException ;

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException;

    public ArrayList<ItemSupplierDetailDTO> getItemSupplierDetaiAll() throws SQLException, ClassNotFoundException ;

    List<String> getlocation() throws SQLException,ClassNotFoundException;

    public boolean purchaseOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetails) throws Exception;
    List<String> getItemName() throws SQLException ,ClassNotFoundException;
    List<String> getCustomerTel() throws SQLException, ClassNotFoundException;
    Transport searchByLocation(String id) throws SQLException, ClassNotFoundException;
    double getNetTot(String oId) throws SQLException, ClassNotFoundException;
    String getLastOrderId() throws SQLException,ClassNotFoundException;

}
