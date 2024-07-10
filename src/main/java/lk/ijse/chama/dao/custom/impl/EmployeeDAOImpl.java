package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.EmployeeDAO;
import lk.ijse.chama.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM employee");

        ArrayList<Employee> empList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String nic = resultSet.getString(4);
            String position = resultSet.getString(5);
            String contact = resultSet.getString(6);
            java.sql.Date dob = resultSet.getDate(7);
            Date dateRegistration = resultSet.getDate(8);
            String email = resultSet.getString(9);
            double salary = resultSet.getDouble(10);
            String path = resultSet.getString(11);

            Employee employee = new Employee(id, name, address, nic, position, contact , dob, dateRegistration, email, salary, path);
            empList.add(employee);
        }
        return empList;
    }

    @Override
    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",employee.getEmpId(),employee.getEmpName(),employee.getEmpAddress(), employee.getEmpNic(), employee.getPosition(), employee.getEmpTel(), employee.getDob(),employee.getDateRegister(), employee.getEmpEmail(), employee.getSalary(), employee.getPath());
        return result;
    }

    @Override
    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("UPDATE employee SET name = ?, address = ?, nic = ?, position = ?, contact = ?, dob = ?, enroll_date = ?, email = ?, basic_salary = ?, path = ? WHERE emp_id = ?",employee.getEmpName(),employee.getEmpAddress(), employee.getEmpNic(), employee.getPosition(), employee.getEmpTel(), employee.getDob(),employee.getDateRegister(), employee.getEmpEmail(), employee.getSalary(), employee.getPath(),employee.getEmpId());
        return result;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT emp_id FROM employee ORDER BY CAST(SUBSTRING(emp_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if (rst.next()) {
            String id = rst.getString("emp_id");
            String[] split = id.split("E");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);

            if(idNum >= 1){
                return "E" + 0 + 0 + ++idNum;
            }else if(idNum >= 9){
                return "E" + 0 + ++idNum;
            } else if(idNum >= 99){
                return "E" + ++idNum;
            }
        }
        return "E001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        boolean result =SQLUtill.execute("DELETE FROM employee WHERE emp_id = ?",id);
        return result;
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM employee WHERE emp_id = ?",id);
        rst.next();
        return new Employee(id + "", rst.getString("name"), rst.getString("address"),rst.getString("nic"),rst.getString("position"),rst.getString("contact"), rst.getDate("dob"), rst.getDate("enroll_date"), rst.getNString("email"), rst.getDouble("basic_salary"), rst.getNString("path"));

    }

    @Override
    public List<String> getId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT emp_id FROM employee");
        List<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;

    }
}
