package lk.ijse.chama.dao.custom;

import lk.ijse.chama.dao.CrudDAO;
import lk.ijse.chama.entity.OrderDetail;
import lk.ijse.chama.entity.tm.MostSellItemTm;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    boolean save(List<OrderDetail> odList) throws SQLException , ClassNotFoundException;
    List<MostSellItemTm> getMostSellItem() throws SQLException,ClassNotFoundException ;
}
