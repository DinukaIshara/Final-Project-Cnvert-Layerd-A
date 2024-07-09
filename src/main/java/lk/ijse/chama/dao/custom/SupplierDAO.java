package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier> {
    Supplier searchByName(String name) throws SQLException,ClassNotFoundException;
    List<String> getId() throws SQLException,ClassNotFoundException ;
    List<String> getName() throws SQLException,ClassNotFoundException ;
}
