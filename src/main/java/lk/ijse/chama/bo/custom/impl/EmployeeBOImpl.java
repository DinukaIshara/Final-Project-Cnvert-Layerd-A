package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.EmployeeBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.EmployeeDAO;
import lk.ijse.chama.dto.EmployeeDTO;
import lk.ijse.chama.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEEDAO);
    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();

        for (Employee employee: employees){
            employeeDTOS.add(new EmployeeDTO(employee.getEmpId(),employee.getEmpName(),employee.getEmpAddress(), employee.getEmpNic(), employee.getPosition(), employee.getEmpTel(), employee.getDob(),employee.getDateRegister(), employee.getEmpEmail(), employee.getSalary(), employee.getPath()));
        }
        return employeeDTOS;
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(employee.getEmpId(),employee.getEmpName(),employee.getEmpAddress(), employee.getEmpNic(), employee.getPosition(), employee.getEmpTel(), employee.getDob(),employee.getDateRegister(), employee.getEmpEmail(), employee.getSalary(), employee.getPath()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(employee.getEmpId(),employee.getEmpName(),employee.getEmpAddress(), employee.getEmpNic(), employee.getPosition(), employee.getEmpTel(), employee.getDob(),employee.getDateRegister(), employee.getEmpEmail(), employee.getSalary(), employee.getPath()));
    }

    @Override
    public String generateNewID(String currentId) throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID(currentId);
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(id);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getEmpId(),employee.getEmpName(),employee.getEmpAddress(), employee.getEmpNic(), employee.getPosition(), employee.getEmpTel(), employee.getDob(),employee.getDateRegister(), employee.getEmpEmail(), employee.getSalary(), employee.getPath());
        return employeeDTO;
    }

    @Override
    public List<String> getEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.getId();
    }
}
