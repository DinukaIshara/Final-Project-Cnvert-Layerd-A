package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.RepairDTO;
import lk.ijse.chama.entity.Repair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface RepairBO extends SuperBO {
    ArrayList<RepairDTO> getAllRepair() throws SQLException, ClassNotFoundException;
    boolean saveRepair(RepairDTO repair) throws SQLException, ClassNotFoundException;
    boolean updateRepair(RepairDTO repair) throws SQLException, ClassNotFoundException;
    boolean deleteRepair(String id) throws SQLException, ClassNotFoundException;
    boolean existRepair(String id) throws SQLException, ClassNotFoundException;
    String generateNewID(String currentId) throws SQLException, ClassNotFoundException;
    RepairDTO searchRepair(String id) throws SQLException, ClassNotFoundException;
    List<String> getRepairId() throws SQLException, ClassNotFoundException;
    String getRepairLastId() throws SQLException, ClassNotFoundException;
}
