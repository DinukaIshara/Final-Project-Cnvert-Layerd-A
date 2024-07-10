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
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM transport");//pstm.executeQuery();

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
        boolean result = SQLUtill.execute("INSERT INTO transport VALUES(?, ?, ?, ?, ?)",transport.getTrId(),transport.getVehicalNo(),transport.getDriverName(),transport.getLocation(),transport.getCost());
        return result;
    }

    @Override
    public boolean update(Transport transport) throws SQLException, ClassNotFoundException {
        boolean result = SQLUtill.execute("UPDATE transport SET vehicle_no = ?, driver_name = ?, location = ?, transport_cost = ? WHERE tr_id = ?",transport.getVehicalNo(),transport.getDriverName(),transport.getLocation(),transport.getCost(),transport.getTrId());
        return result;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT tr_id FROM transport ORDER BY CAST(SUBSTRING(tr_id, 2) AS UNSIGNED) DESC LIMIT 1");//pstm.executeQuery();
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
        boolean result = SQLUtill.execute("DELETE FROM transport WHERE tr_id = ?",id);
        return result;
    }

    @Override
    public Transport search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM transport WHERE location = ?",id);//pstm.executeQuery();
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
    public List<String> getlocation() throws SQLException,ClassNotFoundException {
        List<String> locationList = new ArrayList<>();

        ResultSet resultSet = SQLUtill.execute("SELECT location FROM transport");//pstm.executeQuery();
        while (resultSet.next()) {
            String location = resultSet.getString(1);
            locationList.add(location);
        }
        return locationList;
    }

    @Override
    public Location searchByPath(String name) throws SQLException,ClassNotFoundException {
        ResultSet resultSet = SQLUtill.execute("SELECT * FROM location WHERE place = ?",name);//pstm.executeQuery();
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
    public List<String> getPlace() throws SQLException,ClassNotFoundException {
        List<String> placeList = new ArrayList<>();

        ResultSet resultSet = SQLUtill.execute("SELECT place FROM location");//pstm.executeQuery();
        while (resultSet.next()) {
            String place = resultSet.getString(1);
            placeList.add(place);
        }
        return placeList;
    }
}
