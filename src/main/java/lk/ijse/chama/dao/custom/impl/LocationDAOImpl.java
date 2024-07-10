package lk.ijse.chama.dao.custom.impl;

import lk.ijse.chama.dao.custom.LocationDAO;
import lk.ijse.chama.entity.Location;

import java.sql.SQLException;
import java.util.ArrayList;

public class LocationDAOImpl implements LocationDAO {
    @Override
    public ArrayList<Location> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Location dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Location dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Location search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
