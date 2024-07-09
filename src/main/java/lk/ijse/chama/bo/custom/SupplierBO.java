package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.SupplierDTO;
import lk.ijse.chama.entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierBO extends SuperBO {
    ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException;
    boolean saveSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException;
    boolean updateSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException;
    String generateNewID(String currentId) throws SQLException, ClassNotFoundException ;
    boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException ;
    SupplierDTO searchByIdSupplier(String id) throws SQLException, ClassNotFoundException ;
    SupplierDTO searchByNameSupplier(String name) throws SQLException, ClassNotFoundException ;
    List<String> getSupplierId() throws SQLException, ClassNotFoundException;
    List<String> getSupplierName() throws SQLException, ClassNotFoundException;
}
