package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.chama.bo.BOFactory;
import lk.ijse.chama.bo.custom.ReportBO;
import lk.ijse.chama.util.QrReader;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.QrResult;
import lk.ijse.chama.util.validation.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportFormController {

    @FXML
    private TextField txtSearchItemStockDate;
    @FXML
    private TextField txtSearchCustomerTel;

    ReportBO reportBO = (ReportBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.REPORTBO);

    public ReportFormController(){
        qrResultModel = new QrResult();
    }

    public void initialize(){
        getCustomerTel();
        getItemDate();
    }

    @FXML
    void btnCustomerReportOnAction() throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/CustomerReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        if(isValidateNum()) {
            data.put("custTel", txtSearchCustomerTel.getText());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
        }


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

        clearTel();
    }

    @FXML
    void btnStockOnAction() throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/supplierDateVise.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        if(isValidateDate()) {
            data.put("date", txtSearchItemStockDate.getText());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
        }

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

        clearDate();

    }

    public void btnProfitOnAction(ActionEvent actionEvent) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/Monthly_Profit.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    public void clearTel(){
        txtSearchCustomerTel.setText("");
    }

    public void clearDate(){
        txtSearchItemStockDate.setText("");
    }

    private void getCustomerTel() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = reportBO.getCustomerTel();

            for(String tel : telList) {
                obList.add(tel);
            }
            TextFields.bindAutoCompletion(txtSearchCustomerTel,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void getItemDate() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> dateList = reportBO.getItemDate();

            for (String date : dateList) {
                obList.add(date);
            }
            TextFields.bindAutoCompletion(txtSearchItemStockDate,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtCustTelOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PHONENO,txtSearchCustomerTel);
    }

    public void txtStockDateOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.DATE,txtSearchItemStockDate);
    }

    public boolean isValidateNum(){
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PHONENO,txtSearchCustomerTel))return false;

        return true;
    }

    public boolean isValidateDate(){
        if(!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.DATE,txtSearchItemStockDate))return false;

        return true;
    }

    private QrReader qr;
    private QrResult qrResultModel;

    public void btnScanOnAction(ActionEvent actionEvent) {
        qr = new QrReader(qrResultModel);
        new Thread(() -> {
            while (qrResultModel.getResult() == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            txtSearchCustomerTel.setText(qrResultModel.getResult());
        }).start();

        txtSearchCustomerTel.requestFocus();
    }
}
