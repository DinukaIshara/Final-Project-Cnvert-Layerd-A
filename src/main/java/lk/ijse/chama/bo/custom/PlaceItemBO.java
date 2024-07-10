package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.ItemDTO;
import lk.ijse.chama.dto.ItemSupplierDetailDTO;
import lk.ijse.chama.dto.SupplierDTO;
import lk.ijse.chama.entity.Item;
import lk.ijse.chama.entity.ItemSupplierDetail;
import lk.ijse.chama.entity.OrderDetail;
import lk.ijse.chama.repository.BrandNewItemRepo;
import lk.ijse.chama.repository.ItemSupplierDetailRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PlaceItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
    boolean saveItem(ItemDTO item) throws SQLException, ClassNotFoundException ;
    boolean updateItem(ItemDTO item) throws SQLException, ClassNotFoundException;
    ItemDTO searchItemByName(String name) throws SQLException,ClassNotFoundException;
    boolean updateItemQ(List<OrderDetail> isList) throws SQLException,ClassNotFoundException;
    boolean updateItemQty(String itemCode, int qty) throws SQLException,ClassNotFoundException;
    String generateNewID() throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    ItemDTO searchItemById(String id) throws SQLException, ClassNotFoundException;
    List<String> getItemDate() throws SQLException,ClassNotFoundException;
    List<String> getItemName() throws SQLException,ClassNotFoundException;
    ArrayList<ItemSupplierDetailDTO> getAllItemSupplierDetail() throws SQLException, ClassNotFoundException;
    ItemSupplierDetailDTO searchItemSupplierDetail(String id) throws SQLException, ClassNotFoundException ;
    public boolean saveItemSupplierDetail(ItemSupplierDetailDTO is) throws SQLException, ClassNotFoundException;
    public boolean updateItemSupplierDetail(ItemSupplierDetailDTO is) throws SQLException, ClassNotFoundException;
    ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException;
    SupplierDTO searchByIdSupplier(String id) throws SQLException, ClassNotFoundException;
    List<String> getSupplierId() throws SQLException, ClassNotFoundException;
    boolean palceItem(ItemDTO item,ItemSupplierDetailDTO itemSupplierDetailDTO) throws SQLException ,ClassNotFoundException;
    boolean updateItem(ItemDTO item,ItemSupplierDetailDTO itemSupplierDetailDTO) throws SQLException,ClassNotFoundException;
}
