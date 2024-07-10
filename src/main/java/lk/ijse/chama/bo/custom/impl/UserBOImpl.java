package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.UserBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USERDAO);

    @Override
    public boolean saveUser(String userName, String password) throws SQLException,ClassNotFoundException {
        return userDAO.saveUser(userName,password);
    }

    @Override
    public String Checkcredential(String username) throws SQLException,ClassNotFoundException {
        return userDAO.Checkcredential(username);
    }
}
