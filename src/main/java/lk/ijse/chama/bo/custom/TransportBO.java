package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.LocationDTO;
import lk.ijse.chama.dto.TransportDTO;
import lk.ijse.chama.entity.Location;
import lk.ijse.chama.entity.Transport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TransportBO extends SuperBO {
    boolean saveTransport(TransportDTO transport) throws SQLException,ClassNotFoundException;

    boolean updateTransport(TransportDTO transport) throws SQLException,ClassNotFoundException ;

    boolean deleteTransport(String id) throws SQLException,ClassNotFoundException;

    String generateNewID(String currentId) throws SQLException, ClassNotFoundException;

    List<TransportDTO> getAllTransport() throws SQLException, ClassNotFoundException ;

    Transport searchByLocation(String loc) throws SQLException,ClassNotFoundException;

    List<String> getlocation() throws SQLException,ClassNotFoundException;

    LocationDTO searchByPath(String name) throws SQLException, ClassNotFoundException;

    List<String> getPlace() throws SQLException,ClassNotFoundException;

}
