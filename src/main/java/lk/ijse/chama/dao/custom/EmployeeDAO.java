package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeDAO extends CrudDAO<Employee> {
    List<String> getId() throws SQLException, ClassNotFoundException;
}
