package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.bo.BOFactory;
import lk.ijse.chama.bo.custom.CustomerBO;
import lk.ijse.chama.bo.custom.RepairBO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.CustomerDTO;
import lk.ijse.chama.dto.RepairDTO;
import lk.ijse.chama.entity.*;
import lk.ijse.chama.entity.tm.RepairTm;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.entity.Repair;
import lk.ijse.chama.util.validation.Regex;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class RepairFormController {

    public TextField txtSearchRepair;
    @FXML
    private TextField txtCustomerTel;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colReceiveDate;

    @FXML
    private TableColumn<?, ?> colRepairId;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colReturncolDescriptionedDescription;

    @FXML
    private Label lblCustomerName;

    @FXML
    private TableView<Repair> tblReturnedRepair;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemName;

    @FXML
    private DatePicker txtReceiveDate;

    @FXML
    private TextField txtRepairId;

    @FXML
    private DatePicker txtReturnDate;

    RepairBO repairBO = (RepairBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.REPAIRBO);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMERBO);

    public void initialize() {
        getCustomerTel();
        loadAllRepair();
        setCellValueFactory();
        getRepairId();
        getCurrentId();
    }

    private void setCellValueFactory() {
        colRepairId.setCellValueFactory(new PropertyValueFactory<>("repId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colReturncolDescriptionedDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colReceiveDate.setCellValueFactory(new PropertyValueFactory<>("reciveDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("reternDate"));
    }

    private void loadAllRepair() {
        ObservableList<Repair> obList = FXCollections.observableArrayList();

        try {
            List<RepairDTO> repairList = repairBO.getAllRepair();//RepairRepo.getAll();
            for (RepairDTO repair : repairList) {
                RepairTm tm = new RepairTm(
                        repair.getRepairId(),
                        repair.getItemName(),
                        repair.getDescription(),
                        repair.getCustId(),
                        repair.getCost(),
                        repair.getReciveDate(),
                        repair.getReturnDate()
                );

                obList.add(tm);
            }

            tblReturnedRepair.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String repairId = txtRepairId.getText();
        LocalDate reciveDate = txtReceiveDate.getValue();
        LocalDate returnDate = txtReturnDate.getValue();
        double cost = Double.parseDouble(txtCost.getText());
        String description = txtDescription.getText();
        String custId = lblCustomerId.getText();
        String itemName = txtItemName.getText();
        System.out.println(lblCustomerId.getText());

        var repair = new RepairDTO(repairId, reciveDate, returnDate, cost, description, custId, itemName);

        try {
            if(isValidate()) {
                boolean isPlaced = repairBO.saveRepair(repair);//RepairRepo.save(repair);
                if (isPlaced) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Save Repair!").show();
                    clearFields();
                    initialize();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Repair Save Unsuccessfully!").show();
                }
            }else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update Repair?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String repairId = txtRepairId.getText();
            LocalDate reciveDate = txtReceiveDate.getValue();
            LocalDate returnDate = txtReturnDate.getValue();
            double cost = Double.parseDouble(txtCost.getText());
            String description = txtDescription.getText();
            String custId = lblCustomerId.getText();
            String itemName = txtItemName.getText();

            var repair = new RepairDTO(repairId, reciveDate, returnDate, cost, description, custId, itemName);

            try {
                if (isValidate()) {
                    boolean isPlaced = repairBO.updateRepair(repair);//RepairRepo.update(repair);
                    if (isPlaced) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Update Repair!").show();
                        initialize();
                        clearFields();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Repair Update Unsuccessfully!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete Repair?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String id = txtRepairId.getText();

            try {
                boolean isDeleted = repairBO.deleteRepair(id); //RepairRepo.delete(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Repair deleted!").show();
                    clearFields();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtCost.setText("");
        txtRepairId.setText(getCurrentId());
        txtDescription.setText("");
        txtItemName.setText("");
        txtReceiveDate.setValue(null);
        txtReturnDate.setValue(null);
        txtCustomerTel.setText("");
        lblCustomerId.setText("");
        lblCustomerName.setText("");
        txtSearchRepair.setText("");

    }

    private String getCurrentId() {
        String nextId = "";

        try {
            String currentId = "";//repairBO.getRepairLastId();//RepairRepo.getLastId();

            nextId = repairBO.generateNewID();//generateNextId(currentId);
            txtRepairId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return nextId;
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane customerRootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        customerRootNode.getChildren().clear();
        customerRootNode.getChildren().add(customerRootNode);
    }

    private void getRepairId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = repairBO.getRepairId();//RepairRepo.getId();

            for (String id : idList) {
                obList.add(id);
            }
            TextFields.bindAutoCompletion(txtSearchRepair, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerTel() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = customerBO.getCustomerTel();//CustomerRepo.getTel();

            for(String tel : telList) {
                obList.add(tel);
            }
            TextFields.bindAutoCompletion(txtCustomerTel,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtCustomerTelOnAction(ActionEvent actionEvent) {
        String tel = txtCustomerTel.getText();
        try {
            CustomerDTO customer = customerBO.searchCustomer(tel);//CustomerRepo.searchByTel(tel);

            lblCustomerName.setText(customer.getCName());
            lblCustomerId.setText(customer.getCustId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        txtItemName.requestFocus();
    }

    public void txtSearchRepairOnAction(ActionEvent actionEvent) {
        try {
            btnSearchRepairOnAction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchRepairOnAction() throws SQLException, ClassNotFoundException {
        String id = txtSearchRepair.getText();

        RepairDTO rep = repairBO.searchRepair(id);//RepairRepo.searchById(String.valueOf(id));
        if (rep != null) {
            txtRepairId.setText(rep.getRepairId());
            lblCustomerId.setText(rep.getCustId());
            txtItemName.setText(rep.getItemName());
            Customer cust = CustomerRepo.searchById(rep.getCustId());
            if (cust != null) {
                txtCustomerTel.setText(cust.getContactNo());
                lblCustomerName.setText(cust.getCName());
            }
            txtReceiveDate.setValue(rep.getReciveDate());
            txtReturnDate.setValue(rep.getReturnDate());
            txtDescription.setText(rep.getDescription());
            txtCost.setText(String.valueOf(rep.getCost()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
    }

    @FXML
    void dpReceiveDateOnAction(ActionEvent event) {
        txtReturnDate.requestFocus();
    }

    @FXML
    void dpReturnDateOnAction(ActionEvent event) {
        txtDescription.requestFocus();
    }

    public void txtRidOnAction(ActionEvent actionEvent) {
        txtCustomerTel.requestFocus();
    }

    public void txtItemNameOnAction(ActionEvent actionEvent) {
        txtReceiveDate.requestFocus();
    }

    public void txtDescriptionOnAction(ActionEvent actionEvent) {
        txtCost.requestFocus();
    }

    public void txtCostOnAction(ActionEvent actionEvent) {

    }

    public void txtRepairIdOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.RPID,txtRepairId);
    }

    public void txtCustomerTellOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PHONENO,txtCustomerTel);
    }

    public void txtCostOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PRICE,txtCost);
    }

    public boolean isValidate(){
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.RPID,txtRepairId))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PHONENO,txtCustomerTel))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PRICE,txtCost))return false;

        return true;
    }

    public boolean isIdValidate(){
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.RPID,txtRepairId)) return false;

        return true;
    }

    public void btnBillOnAction(ActionEvent actionEvent) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/RepairReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        if(isIdValidate()) {
            data.put("repId", txtRepairId.getText());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Repair Id you entered is incorrect").show();
        }

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}
