package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.TransportBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.TransportDAO;
import lk.ijse.chama.dto.LocationDTO;
import lk.ijse.chama.dto.TransportDTO;
import lk.ijse.chama.entity.Location;
import lk.ijse.chama.entity.Transport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportBOImpl implements TransportBO {

    TransportDAO transportDAO = (TransportDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.TRANSPORTDAO);
    @Override
    public boolean saveTransport(TransportDTO transport) throws SQLException,ClassNotFoundException {
        return transportDAO.save(new Transport(transport.getTrId(),transport.getVehicalNo(),transport.getDriverName(), transport.getLocation(),transport.getCost()));
    }

    @Override
    public boolean updateTransport(TransportDTO transport) throws SQLException,ClassNotFoundException {
        return transportDAO.update(new Transport(transport.getTrId(),transport.getVehicalNo(),transport.getDriverName(), transport.getLocation(),transport.getCost()));
    }

    @Override
    public boolean deleteTransport(String id) throws SQLException,ClassNotFoundException {
        return transportDAO.delete(id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return transportDAO.generateNewID();
    }

    @Override
    public List<TransportDTO> getAllTransport() throws SQLException, ClassNotFoundException {
        ArrayList<Transport> transports = transportDAO.getAll();
        ArrayList<TransportDTO> transportDTO = new ArrayList<>();

        for (Transport transport: transports){
            transportDTO.add(new TransportDTO(transport.getTrId(),transport.getVehicalNo(),transport.getDriverName(), transport.getLocation(),transport.getCost()));
        }
        return transportDTO;
    }

    @Override
    public Transport searchByLocation(String loc) throws SQLException,ClassNotFoundException {
        return transportDAO.search(loc);
    }

    @Override
    public List<String> getlocation() throws SQLException,ClassNotFoundException {
        return transportDAO.getlocation();
    }

    @Override
    public LocationDTO searchByPath(String name) throws SQLException, ClassNotFoundException {
        Location location = transportDAO.searchByPath(name);
        LocationDTO locationDTO = new LocationDTO(location.getPlace(), location.getLatitude(), location.getLongitude());
        return locationDTO;
    }

    @Override
    public List<String> getPlace() throws SQLException, ClassNotFoundException {
        return transportDAO.getPlace();
    }
}
