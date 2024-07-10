package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Location;
import lk.ijse.chama.entity.Transport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TransportDAO extends CrudDAO<Transport> {
    List<String> getlocation() throws SQLException,ClassNotFoundException ;
    Location searchByPath(String name) throws SQLException, ClassNotFoundException;
    List<String> getPlace() throws SQLException, ClassNotFoundException;
}
