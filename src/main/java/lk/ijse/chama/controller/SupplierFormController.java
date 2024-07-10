package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.bo.BOFactory;
import lk.ijse.chama.bo.custom.SupplierBO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.dto.SupplierDTO;
import lk.ijse.chama.util.validation.Regex;
import lk.ijse.chama.view.tdm.tm.SupplierTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SupplierFormController {

    @FXML
    private TextField txtSearchSupplier;

    @FXML
    private TableColumn<?, ?> colCompanyName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colPersonName;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> coltel;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLoacation;

    @FXML
    private TextField txtPersonName;

    @FXML
    private TextField txtSupId;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.SUPPLIERBO);

    public void initialize() {
        setCellValueFactory();
        loadAllSuppliers();
        supplierCompanyName();
        getCurrentId();
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colPersonName.setCellValueFactory(new PropertyValueFactory<>("personName"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllSuppliers() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDTO> supplierList = supplierBO.getAllSuppliers();
            for (SupplierDTO supplier : supplierList) {
                SupplierTm tm = new SupplierTm(
                        supplier.getSupId(),
                        supplier.getCompanyName(),
                        supplier.getPersonName(),
                        supplier.getTel(),
                        supplier.getLocation(),
                        supplier.getEmail()
                );

                obList.add(tm);
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String supId = txtSupId.getText();
        String companyName = txtCompanyName.getText();
        String personName = txtPersonName.getText();
        String tel = txtContact.getText();
        String location = txtLoacation.getText();
        String email = txtEmail.getText();

        SupplierDTO suppler = new SupplierDTO(supId, companyName, personName, tel, location , email );

        try {
            if(isValidat()) {
                boolean isSaved = supplierBO.saveSupplier(suppler);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
                    clearFields();
                    initialize();
                }
            }else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update Supplier?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String supId = txtSupId.getText();
            String companyName = txtCompanyName.getText();
            String personName = txtPersonName.getText();
            String tel = txtContact.getText();
            String location = txtLoacation.getText();
            String email = txtEmail.getText();

            SupplierDTO suppler = new SupplierDTO(supId, companyName, personName, tel, location, email);

            try {
                if (isValidat()) {
                    boolean isSaved = supplierBO.updateSupplier(suppler);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "supplier update!").show();
                        clearFields();
                        initialize();
                    }
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void clearFields() {
        txtSupId.setText("");
        txtCompanyName.setText("");
        txtPersonName.setText("");
        txtContact.setText("");
        txtLoacation.setText("");
        txtEmail.setText("");
        txtSearchSupplier.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete Supplier?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String id = txtSupId.getText();

            try {
                boolean isDeleted = supplierBO.deleteSupplier(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
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

    private String getCurrentId() {
        String nextId = "";

        try {
            nextId = supplierBO.generateNewID();
            txtSupId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return nextId;
    }

    public void supplierCompanyName() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> nameList = supplierBO.getSupplierName();

            for (String name : nameList) {
                obList.add(name);
            }
            TextFields.bindAutoCompletion(txtSearchSupplier, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSearchSupplierOnAction(ActionEvent actionEvent){
        try {
            btnSearchSupplierOnAction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchSupplierOnAction()  throws SQLException,ClassNotFoundException {
        String name = txtSearchSupplier.getText();

        SupplierDTO supplier = supplierBO.searchByNameSupplier(name);
        if (supplier != null) {
            txtSupId.setText(supplier.getSupId());
            txtCompanyName.setText(supplier.getCompanyName());
            txtPersonName.setText(supplier.getPersonName());
            txtLoacation.setText(supplier.getLocation());
            txtEmail.setText(supplier.getEmail());
            txtContact.setText(supplier.getTel());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
    }

    public void txtSupIdOnAction(ActionEvent actionEvent) {
        txtCompanyName.requestFocus();
    }

    public void txtConpanyNameOnAction(ActionEvent actionEvent) {
        txtPersonName.requestFocus();
    }

    public void txtPersonNameOnAction(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void txtcontactNameOnAction(ActionEvent actionEvent) {
        txtLoacation.requestFocus();
    }

    // Validation Part -------------------------------------------------------------------------------------
    public void txtContactNoOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PHONENO,txtContact);
    }

    public void txtLocationOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    public void txtSupIdOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.SID,txtSupId);
    }

    public void txtEmailOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.EMAIL,txtEmail);
    }

    public boolean isValidat(){
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.SID,txtSupId))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.EMAIL,txtEmail))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PHONENO,txtContact))return false;

        return true;
    }

    public boolean isIdValidate(){
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.SID,txtSupId)) return false;

        return true;
    }

    // Supplier Report -----------------------------------------------------------------------------------------------------------
    @FXML
    void btnSupplierReportOnAction(ActionEvent event) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/SupplerItemReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        if(isIdValidate()) {
            data.put("supId", txtSupId.getText());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Supplier Id you entered is incorrect").show();
        }

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}
