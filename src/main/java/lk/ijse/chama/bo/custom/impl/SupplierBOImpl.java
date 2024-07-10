package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.SupplierBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.SupplierDAO;
import lk.ijse.chama.dto.SupplierDTO;
import lk.ijse.chama.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SUPPLIERDAO);

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers = supplierDAO.getAll();
        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        for (Supplier supplier: suppliers){
            supplierDTOS.add(new SupplierDTO(supplier.getSupId(),supplier.getCompanyName(),supplier.getPersonName(),supplier.getTel(),supplier.getLocation(),supplier.getEmail()));
        }
        return supplierDTOS;
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(supplier.getSupId(),supplier.getCompanyName(),supplier.getPersonName(),supplier.getTel(),supplier.getLocation(),supplier.getEmail()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplier.getSupId(),supplier.getCompanyName(),supplier.getPersonName(),supplier.getTel(),supplier.getLocation(),supplier.getEmail()));
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewID();
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public SupplierDTO searchByIdSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.search(id);
        SupplierDTO supplierDTO = new SupplierDTO(supplier.getSupId(),supplier.getCompanyName(),supplier.getPersonName(),supplier.getTel(),supplier.getLocation(),supplier.getEmail());
        return supplierDTO;
    }

    @Override
    public SupplierDTO searchByNameSupplier(String name) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.searchByName(name);
        SupplierDTO supplierDTO = new SupplierDTO(supplier.getSupId(),supplier.getCompanyName(),supplier.getPersonName(),supplier.getTel(),supplier.getLocation(),supplier.getEmail());
        return supplierDTO;
    }

    @Override
    public List<String> getSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getId();
    }

    @Override
    public List<String> getSupplierName() throws SQLException, ClassNotFoundException {
        return supplierDAO.getName();
    }
}
