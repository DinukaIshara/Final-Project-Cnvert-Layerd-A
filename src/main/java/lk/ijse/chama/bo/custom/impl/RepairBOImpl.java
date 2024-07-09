package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.RepairBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.RepairDAO;
import lk.ijse.chama.dto.RepairDTO;
import lk.ijse.chama.entity.Repair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairBOImpl implements RepairBO {
    RepairDAO repairDAO = (RepairDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.REPAIRDAO);

    @Override
    public ArrayList<RepairDTO> getAllRepair() throws SQLException, ClassNotFoundException {
        ArrayList<Repair> repairs = repairDAO.getAll();
        ArrayList<RepairDTO> repairDTOS = new ArrayList<>();

        for (Repair repair: repairs){
            repairDTOS.add(new RepairDTO(repair.getRepairId(), repair.getReciveDate(),repair.getReturnDate(),repair.getCost(),repair.getDescription(),repair.getCustId(),repair.getItemName()));
        }
        return repairDTOS;
    }

    @Override
    public boolean saveRepair(RepairDTO repair) throws SQLException, ClassNotFoundException {
        return repairDAO.save(new Repair(repair.getRepairId(), repair.getReciveDate(),repair.getReturnDate(),repair.getCost(),repair.getDescription(),repair.getCustId(),repair.getItemName()));
    }

    @Override
    public boolean updateRepair(RepairDTO repair) throws SQLException, ClassNotFoundException {
        return repairDAO.update(new Repair(repair.getRepairId(), repair.getReciveDate(),repair.getReturnDate(),repair.getCost(),repair.getDescription(),repair.getCustId(),repair.getItemName()));
    }

    @Override
    public boolean deleteRepair(String id) throws SQLException, ClassNotFoundException {
        return repairDAO.delete(id);
    }

    @Override
    public boolean existRepair(String id) throws SQLException, ClassNotFoundException {
        return repairDAO.exist(id);
    }

    @Override
    public String generateNewID(String currentId) throws SQLException, ClassNotFoundException {
        return repairDAO.generateNewID(currentId);
    }

    @Override
    public RepairDTO searchRepair(String id) throws SQLException, ClassNotFoundException {
        Repair repair = repairDAO.search(id);
        RepairDTO repairDTO = new RepairDTO(repair.getRepairId(), repair.getReciveDate(),repair.getReturnDate(),repair.getCost(),repair.getDescription(),repair.getCustId(),repair.getItemName());
        return repairDTO;
    }

    @Override
    public List<String> getRepairId() throws SQLException, ClassNotFoundException {
        return repairDAO.getId();
    }

    @Override
    public String getRepairLastId() throws SQLException, ClassNotFoundException {
        return repairDAO.getLastId();
    }
}
