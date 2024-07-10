package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.ReportBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.CustomerDAO;
import lk.ijse.chama.dao.custom.ItemDAO;

import java.sql.SQLException;
import java.util.List;

public class ReportBOImpl implements ReportBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);
    @Override
    public List<String> getCustomerTel() throws SQLException, ClassNotFoundException {
        return customerDAO.getTel();
    }

    @Override
    public List<String> getItemDate() throws SQLException, ClassNotFoundException {
        return itemDAO.getDate();
    }
}
