package lk.ijse.chama.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class SidepanelformController {

    public AnchorPane rootNode;
    public AnchorPane childRootNode;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblUserName;

    public void initialize(){
        setDate();
        try {
            loadDashboardForm();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setDate() {
        LocalDate nowDate = LocalDate.now();
        lblDate.setText(String.valueOf(nowDate));
    }

    private void loadDashboardForm() throws IOException {
        AnchorPane dashRootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(dashRootNode);
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane dashRootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(dashRootNode);
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane customerRootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(customerRootNode);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane placeOrRootNode = FXMLLoader.load(this.getClass().getResource("/view/placeOrderForm.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(placeOrRootNode);
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane itemRootNode = FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(itemRootNode);

    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane employeeRootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(employeeRootNode);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane supRootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(supRootNode);
    }

    public void btnTransportOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane transRootNode = FXMLLoader.load(this.getClass().getResource("/view/transport_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(transRootNode);
    }

    public void btnRepairOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane repairRootNode = FXMLLoader.load(this.getClass().getResource("/view/repair_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(repairRootNode);
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane reportRootNode = FXMLLoader.load(this.getClass().getResource("/view/report_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(reportRootNode);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);


        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    public void setUserName(String userName){
        lblUserName.setText(userName);
    }

}
