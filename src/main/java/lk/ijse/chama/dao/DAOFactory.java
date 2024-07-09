package lk.ijse.chama.dao;

import lk.ijse.chama.bo.custom.impl.UserBOImpl;
import lk.ijse.chama.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getDAOFactory() {
        return daoFactory == null ? daoFactory= new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USERDAO,CUSTOMERDAO,EMPLOYEEDAO,ITEMDAO,ITEMSUPPLIERDAO,ORDERDAO,ORDERDETAILSDAO,TRANSPORTDAO,SUPPLIERDAO,REPAIRDAO,QUERY;
    }

    public SuperDAO getDAO(DAOTypes daoTypes){

        switch (daoTypes){
            case USERDAO:
                return new UserDAOImpl();

            case ITEMDAO:
                return  new ItemDAOImpl();

            case EMPLOYEEDAO:
                return new EmployeeDAOImpl();

            case ORDERDAO:
                return  new OrderDAOImpl();

            case CUSTOMERDAO:
                return  new CustomerDAOImpl();

            case ORDERDETAILSDAO:
                return new OrderDetailDAOImpl();

            case ITEMSUPPLIERDAO:
                return new ItemSupplierDAOImpl();

            case TRANSPORTDAO:
                return new TransportDAOImpl();

            case SUPPLIERDAO:
                return new SupplierDAOImpl();

            case REPAIRDAO:
                return new RepairDAOImpl();

            case QUERY:
                return new QueryDAOImpl();

            default:
                return null;
        }
    }
}
