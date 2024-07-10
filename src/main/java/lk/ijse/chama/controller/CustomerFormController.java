package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.util.QrGenerateor;
import lk.ijse.chama.bo.BOFactory;
import lk.ijse.chama.bo.custom.CustomerBO;
import lk.ijse.chama.dto.CustomerDTO;
import lk.ijse.chama.util.validation.Regex;
import lk.ijse.chama.view.tdm.tm.CustomerTm;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerFormController {

    @FXML
    private TextField txtId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TextField txtSearchCustomers;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMERBO);


    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
        getCustomerTel();
        getCurrentId();

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cAddress"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("cNIC"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("cEmail"));
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDTO> customerList = customerBO.getAllCustomers();
            for (CustomerDTO customer : customerList) {
                CustomerTm tm = new CustomerTm(
                        customer.getCustId(),
                        customer.getCName(),
                        customer.getCAddress(),
                        customer.getCNIC(),
                        customer.getContactNo(),
                        customer.getCEmail()
                );

                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String contact = txtTel.getText();
        String email = txtEmail.getText();

        CustomerDTO customer = new CustomerDTO(id, name, address, nic, contact , email );

        try {
            if(isValidate()) {
                boolean isSaved = customerBO.saveCustomer(customer);
                if (isSaved) {
                    QrGenerateor.setData(contact, email, 1);
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    clearFields();
                    initialize();
                }
            }else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) { // Update Customer
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update Customer?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String nic = txtNIC.getText();
            String contact = txtTel.getText();
            String email = txtEmail.getText();

            CustomerDTO customer = new CustomerDTO(id, name, address, nic, contact, email); // Set Customer Data

            try {
                if (isValidate()) { // Add Validation
                    System.out.println(customer);
                    boolean isUpdated = customerBO.updateCustomer(customer);

                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                        clearFields();
                        initialize();
                    }
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction() { // Delete Customers
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete Customer?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String id = txtId.getText();

            try {
                boolean isDeleted = customerBO.deleteCustomer(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                    clearFields();
                    initialize();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtTel.setText("");
        txtEmail.setText("");
        txtSearchCustomers.setText("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private String getCurrentId() {
        String nextId = "";

        try {

            nextId = customerBO.generateNewID();
            txtId.setText(nextId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return nextId;
    }

    @FXML
    void txtSearchCustomersOnAction(ActionEvent event) throws SQLException {
        btnSearchCustomersOnAction();
    }

    private void getCustomerTel() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = customerBO.getCustomerTel();

            for (String tel : telList) {
                obList.add(tel);
            }

            TextFields.bindAutoCompletion(txtSearchCustomers, obList); // Set Data List in Text Field

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchCustomersOnAction() /*throws SQLException*/ { // Search Customers
        try {
            String tel = txtSearchCustomers.getText();

            CustomerDTO customer = customerBO.searchCustomer(tel);
            ;
            if (customer != null) {
                txtId.setText(customer.getCustId());
                txtName.setText(customer.getCName());
                txtNIC.setText(customer.getCNIC());
                txtAddress.setText(customer.getCAddress());
                txtEmail.setText(customer.getCEmail());
                txtTel.setText(customer.getContactNo());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Focus Actions -------------------------------------------------------
    @FXML
    void nameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void addressOnAction(ActionEvent event) {
        txtNIC.requestFocus();
    }

    @FXML
    void nicOnAction(ActionEvent event) {
        txtTel.requestFocus();
    }

    @FXML
    void telOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        txtName.requestFocus();
    }


    // Validation---------------------------------------------------------------------------------------------------------------------------------
    public void txtCustIdOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.CID,txtId);
    }

    public void txtNicOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.NIC,txtNIC);
    }

    public void txtTelOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PHONENO,txtTel);
    }

    public void txtAddressOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.ADDRESS,txtAddress);
    }

    public void txtEmailOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.EMAIL,txtEmail);
    }

    public boolean isValidate(){
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.CID,txtId)) return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.NIC,txtNIC)) return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.ADDRESS,txtAddress))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PHONENO,txtTel)) return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.EMAIL,txtEmail)) return false;

        return true;
    }

}
