package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.QueryBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.QueryDAO;

import java.sql.SQLException;

public class QueryBOImpl implements QueryBO {

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    @Override
    public double getNetTot(String oId) throws SQLException, ClassNotFoundException {
        return queryDAO.getNetTot(oId);
    }
}
