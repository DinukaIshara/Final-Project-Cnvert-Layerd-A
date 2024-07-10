package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;
import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.EmployeeDTO;
import lk.ijse.chama.entity.Employee;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;
    public boolean saveEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException;
    public boolean updateEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException;
    public String generateNewID() throws SQLException, ClassNotFoundException ;
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    public EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException;
    public List<String> getEmployeeId() throws SQLException, ClassNotFoundException;
}
