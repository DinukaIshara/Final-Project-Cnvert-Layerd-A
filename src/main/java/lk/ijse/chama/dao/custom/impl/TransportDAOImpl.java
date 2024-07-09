package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.SQLUtill;
import lk.ijse.chama.dao.custom.TransportDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.Location;
import lk.ijse.chama.entity.Transport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportDAOImpl implements TransportDAO {
    @Override
    public ArrayList<Transport> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM transport";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Transport> transList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String vehicalNo = resultSet.getString(2);
            String driverName = resultSet.getString(3);
            String location = resultSet.getString(4);
            double cost = resultSet.getDouble(5);

            Transport transport = new Transport(id, vehicalNo, driverName,location, cost);
            transList.add(transport);
        }
        return transList;
    }

    @Override
    public boolean save(Transport transport) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO transport VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, transport.getTrId());
        pstm.setObject(2, transport.getVehicalNo());
        pstm.setObject(3, transport.getDriverName());
        pstm.setObject(4, transport.getLocation());
        pstm.setObject(5, transport.getCost());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(Transport transport) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE transport SET vehicle_no = ?, driver_name = ?, location = ?, transport_cost = ? WHERE tr_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, transport.getVehicalNo());
        pstm.setObject(2, transport.getDriverName());
        pstm.setObject(3, transport.getLocation());
        pstm.setObject(4, transport.getCost());
        pstm.setObject(5, transport.getTrId());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT tr_id FROM transport WHERE tr_id=?",id);
        return rst.next();
    }

    @Override
    public String generateNewID(String currentId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT tr_id FROM transport ORDER BY CAST(SUBSTRING(tr_id, 2) AS UNSIGNED) DESC LIMIT 1;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String Id = resultSet.getString(1);
            String[] split = Id.split("T");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);

            if(idNum >= 1){
                return "T" + 0 + 0 + ++idNum;
            }else if(idNum >= 9){
                return "T" + 0 + ++idNum;
            } else if(idNum >= 99){
                return "T" + ++idNum;
            }
        }
        return "T001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM transport WHERE tr_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public Transport search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM transport WHERE location = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String tr_id = resultSet.getString(1);
            String ve_no = resultSet.getString(2);
            String driver_name = resultSet.getString(3);
            String location = resultSet.getString(4);
            double transport_cost =resultSet.getDouble(5);

            return new Transport(tr_id, ve_no, driver_name, location, transport_cost);
        }

        return null;
    }

    @Override
    public List<String> getlocation() throws SQLException {
        String sql = "SELECT location FROM transport";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> locationList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String location = resultSet.getString(1);
            locationList.add(location);
        }
        return locationList;
    }

    @Override
    public Location searchByPath(String name) throws SQLException {
        String sql = "SELECT * FROM location WHERE place = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, name);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Location(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getDouble(3)

            );
        }
        return null;
    }

    @Override
    public List<String> getPlace() throws SQLException {
        String sql = "SELECT place FROM location";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> placeList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String place = resultSet.getString(1);
            placeList.add(place);
        }
        return placeList;
    }
}
