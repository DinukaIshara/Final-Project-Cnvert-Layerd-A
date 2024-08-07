package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.entity.Custom;
import lk.ijse.chama.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    boolean save(List<OrderDetail> odList) throws SQLException , ClassNotFoundException;
    List<Custom> getMostSellItem() throws SQLException,ClassNotFoundException ;
}
