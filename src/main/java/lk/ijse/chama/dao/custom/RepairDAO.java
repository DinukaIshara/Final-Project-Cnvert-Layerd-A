package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.entity.Repair;

import java.sql.SQLException;
import java.util.List;

public interface RepairDAO extends CrudDAO<Repair>{
    List<String> getId() throws SQLException ,ClassNotFoundException;

    String getLastId() throws SQLException , ClassNotFoundException;

}
