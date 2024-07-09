package lk.ijse.chama.bo.custom.impl;

import lk.ijse.chama.bo.custom.PlaceItemBO;
import lk.ijse.chama.dao.DAOFactory;
import lk.ijse.chama.dao.custom.ItemDAO;
import lk.ijse.chama.dao.custom.ItemSupplierDetailDAO;
import lk.ijse.chama.dao.custom.SupplierDAO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.ItemDTO;
import lk.ijse.chama.dto.ItemSupplierDetailDTO;
import lk.ijse.chama.dto.SupplierDTO;
import lk.ijse.chama.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceItemBOImpl implements PlaceItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);
    ItemSupplierDetailDAO itemSupplierDetailDAO = (ItemSupplierDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEMSUPPLIERDAO);

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SUPPLIERDAO);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item item: items){
            itemDTOS.add(new ItemDTO(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath()));
        }
        return itemDTOS;
    }

    @Override
    public boolean saveItem(ItemDTO item) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath()));
    }

    @Override
    public boolean updateItem(ItemDTO item) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath()));
    }

    @Override
    public ItemDTO searchItemByName(String name) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.searchByName(name);
        ItemDTO itemDTO = new ItemDTO(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath());
        return itemDTO;
    }

    @Override
    public boolean updateItemQ(List<OrderDetail> isList) throws SQLException, ClassNotFoundException {
        return itemDAO.updateQ(isList);
    }

    @Override
    public boolean updateItemQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return itemDAO.updateQty(itemCode,qty);
    }

    @Override
    public String generateNewID(String currentId) throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID(currentId);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public ItemDTO searchItemById(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id);
        ItemDTO itemDTO = new ItemDTO(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath());

        return itemDTO;
    }

    @Override
    public List<String> getItemDate() throws SQLException, ClassNotFoundException {
        return itemDAO.getDate();
    }

    @Override
    public List<String> getItemName() throws SQLException, ClassNotFoundException {
        return itemDAO.getName();
    }

    @Override
    public ArrayList<ItemSupplierDetailDTO> getAllItemSupplierDetail() throws SQLException, ClassNotFoundException {
        ArrayList<ItemSupplierDetail> itemSupplierss = itemSupplierDetailDAO.getAll();
        ArrayList<ItemSupplierDetailDTO> itemSupplierDetailDTOS = new ArrayList<>();

        for (ItemSupplierDetail itemSupplierDetail: itemSupplierss){
            itemSupplierDetailDTOS.add(new ItemSupplierDetailDTO(itemSupplierDetail.getItemId(),itemSupplierDetail.getSupId(),itemSupplierDetail.getQty(),itemSupplierDetail.getUnitPrice()));
        }
        return itemSupplierDetailDTOS;
    }

    @Override
    public ItemSupplierDetailDTO searchItemSupplierDetail(String id) throws SQLException, ClassNotFoundException {
        ItemSupplierDetail itemSupplierDetail = itemSupplierDetailDAO.search(id);
        ItemSupplierDetailDTO itemSupplierDetailDTO = new ItemSupplierDetailDTO(itemSupplierDetail.getItemId(),itemSupplierDetail.getSupId(),itemSupplierDetail.getQty(),itemSupplierDetail.getUnitPrice());
        return itemSupplierDetailDTO;
    }

    @Override
    public boolean saveItemSupplierDetail(ItemSupplierDetailDTO is) throws SQLException, ClassNotFoundException {
        return itemSupplierDetailDAO.save(new ItemSupplierDetail(is.getItemId(),is.getSupId(),is.getQty(),is.getUnitPrice()));
    }

    @Override
    public boolean updateItemSupplierDetail(ItemSupplierDetailDTO is) throws SQLException, ClassNotFoundException {
        return itemSupplierDetailDAO.update(new ItemSupplierDetail(is.getItemId(),is.getSupId(),is.getQty(),is.getUnitPrice()));
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers = supplierDAO.getAll();
        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        for (Supplier supplier: suppliers){
            supplierDTOS.add(new SupplierDTO(supplier.getSupId(),supplier.getCompanyName(),supplier.getPersonName(),supplier.getTel(),supplier.getLocation(),supplier.getEmail()));
        }
        return supplierDTOS;
    }

    @Override
    public List<String> getSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getId();
    }

    @Override
    public SupplierDTO searchByIdSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.search(id);
        SupplierDTO supplierDTO = new SupplierDTO(supplier.getSupId(),supplier.getCompanyName(),supplier.getPersonName(),supplier.getTel(),supplier.getLocation(),supplier.getEmail());
        return supplierDTO;
    }

    @Override
    public boolean palceItem(ItemDTO item,ItemSupplierDetailDTO itemSupplierDetailDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        Item item1 = new Item(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath());
        ItemSupplierDetail itemSupplierDetail = new ItemSupplierDetail(itemSupplierDetailDTO.getItemId(),itemSupplierDetailDTO.getSupId(),itemSupplierDetailDTO.getQty(),itemSupplierDetailDTO.getUnitPrice());

        try {
            boolean isItemSave = itemDAO.save(item1);//BrandNewItemRepo.save(bni.getItem());

            if (isItemSave) {
                boolean isItemSupplierDetailSaved = itemSupplierDetailDAO.save(itemSupplierDetail);//ItemSupplierDetailRepo.save(bni.getItemSupplier());

                if (isItemSupplierDetailSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean updateItem(ItemDTO item,ItemSupplierDetailDTO itemSupplierDetailDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        Item item1 = new Item(item.getItemId(),item.getName(),item.getCategory(),item.getBrand(),item.getStockDate(),item.getDescription(),item.getWarranty(),item.getType(),item.getPath());
        ItemSupplierDetail itemSupplierDetail = new ItemSupplierDetail(itemSupplierDetailDTO.getItemId(),itemSupplierDetailDTO.getSupId(),itemSupplierDetailDTO.getQty(),itemSupplierDetailDTO.getUnitPrice());


        try {
            boolean isItemUpdate = itemDAO.update(item1);//BrandNewItemRepo.update(si.getItem());

            if (isItemUpdate) {
                boolean isItemSupplierDetailUpdate = itemSupplierDetailDAO.update(itemSupplierDetail);//ItemSupplierDetailRepo.update(si.getItemSupplier());

                if (isItemSupplierDetailUpdate) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
