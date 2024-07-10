package lk.ijse.chama.bo.custom;

import lk.ijse.chama.bo.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface ReportBO extends SuperBO {
    List<String> getCustomerTel() throws SQLException, ClassNotFoundException;
    List<String> getItemDate() throws SQLException, ClassNotFoundException;
}
