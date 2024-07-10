package lk.ijse.chama.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lk.ijse.chama.util.MyListener;
import lk.ijse.chama.bo.BOFactory;
import lk.ijse.chama.bo.custom.PlaceOrderBO;
import lk.ijse.chama.dto.*;
import lk.ijse.chama.util.QrReader;
import lk.ijse.chama.util.SendMail;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.entity.*;
import lk.ijse.chama.view.tdm.tm.CartTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PlaceOrderFormController {

    @FXML
    private AnchorPane addToCartItemRootNode;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblItemId;

    @FXML
    private Label lblCatagory;

    @FXML
    private Label lblHandOnQty;

    @FXML
    private ImageView imageCart;

    @FXML
    private TextField txtItemNameSearch;

    @FXML
    private JFXButton btnOrderReceipt;

    @FXML
    private TextField txtLocation;

    @FXML
    private Label lblTrId;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField txtCustomerTel;

    @FXML
    private JFXComboBox cmbPaymentMethod;

    @FXML
    private TableColumn colTotal;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCustName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderCode;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane menuRootNode;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CartTm> tblOrder;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblOrderTime;

    @FXML
    private Label lblTransportCost;

    @FXML
    private Label lblCustomerId;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    private List<ItemCard> productCards = new ArrayList<>();

    ItemCard pc = null;

    MyListener myListener;

    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PLACEORDERBO);

    private QrReader qr;

    private QrResult qrResultModel;

    public PlaceOrderFormController(){
        qrResultModel = new QrResult();
    }

    public void initialize() {
        setDateAndTime();
        getCurrentOrderId();
        getCustomerTel();
        getItemName();
        setCellValueFactory();
        getTransportLocation();
        getPMethod();
        addAl();
    }

    private void setCellValueFactory() { // Set Columns Cart Data In CartTm
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    void btnAddToCartOnAction() {
        String code = lblItemName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remo");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrder.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblOrder.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            if(code.equals(colItemName.getCellData(i))) {

                CartTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotal(total);

                tblOrder.refresh();

                calculateNetTotal();
                return;
            }
        }

        CartTm tm = new CartTm(code, qty, unitPrice, total, btnRemove);
        obList.add(tm);

        tblOrder.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");

    }

    private void calculateNetTotal() { // Net Total Calculate
        int netTotal = 0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnPlaceOrderOnAction() throws SQLException, ClassNotFoundException {
        String orderId = lblOrderCode.getText();
        String cusId = lblCustomerId.getText();
        String transId = String.valueOf(lblTrId.getText());
        Date date = Date.valueOf(LocalDate.now());
        String payment = String.valueOf(cmbPaymentMethod.getValue());

        var order = new OrderDTO(orderId, cusId, transId, date, payment);

        List<OrderDetailDTO> odList = new ArrayList<>();
        ItemDTO item;

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            CartTm tm = obList.get(i);
            item = placeOrderBO.searchItem(tm.getItemName());

            OrderDetailDTO od = new OrderDetailDTO(
                    orderId,
                    item.getItemId(),
                    tm.getQty(),
                    tm.getUnitPrice()
            );

            odList.add(od);
        }

        try {
            boolean isPlaced = placeOrderBO.purchaseOrder(order,odList);
            if (isPlaced) {
                getLastOrderId();
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                clear();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private List<ItemCard> getData(){ // Load All Item Data in ItemCard
        try {
            List<ItemSupplierDetailDTO> supplierDetail = placeOrderBO.getItemSupplierDetaiAll();
            for(ItemSupplierDetailDTO itemSupplier : supplierDetail) {

                String item_name = null;
                String item_cat = null;
                String path = null;

                List<ItemDTO> itemList = placeOrderBO.getAllItem();

                for (ItemDTO item : itemList){
                    if (item.getItemId().equals(itemSupplier.getItemId())){
                        item_name =   item.getName();
                        item_cat = item.getCategory();
                        path = item.getPath();
                    }
                }
                pc = new ItemCard(

                        itemSupplier.getItemId(),
                        item_name,
                        itemSupplier.getUnitPrice(),
                        itemSupplier.getQty(),
                        item_cat,
                        path

                );
                productCards.add(pc);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return productCards;

    }

    private void clear() {
        txtItemNameSearch.setText("");
        txtCustomerTel.setText("");
        lblCustomerId.setText("");
        lblCustName.setText("");
        txtLocation.setText("");
        cmbPaymentMethod.setValue("");
        lblNetTotal.setText("");
        lblTrId.setText("");
        lblTransportCost.setText("");
        getCurrentOrderId();
        tblOrder.getItems().clear();
    }

    private void addAl(){ // Set Item Card
        productCards.addAll(getData());

        if (productCards.size() > 0){
            setChosenItem(productCards.get(0));

            myListener = new MyListener() {
                @Override
                public void onClickListener(ItemCard productCard) {
                    setChosenItem(productCard);
                }
            };
        }

        int col = 0;
        int row = 1;

        try {
            List<ItemDTO> itemList = placeOrderBO.getAllItem();
            for (int i = 0; i < itemList.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/itemCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                ItemCardController productCardController = fxmlLoader.getController();
                productCardController.setData(productCards.get(i), myListener);

                if (col == 2){
                    col = 0;
                    row++;
                }

                gridPane.add(anchorPane, col++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setChosenItem(ItemCard productCard) {
        lblItemName.setText(productCard.getItemName());
        lblCatagory.setText(productCard.getCategory());

        double profit = productCard.getPrice() * 0.25;
        double netPrice = productCard.getPrice() + profit;

        lblUnitPrice.setText(String.valueOf(netPrice));
        lblItemId.setText(productCard.getItemId());
        lblHandOnQty.setText(String.valueOf(productCard.getHandOnQty()));
        Image image = new Image(productCard.getImage(),281, 178, false, true);
        imageCart.setImage(image);

    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane customerRootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(customerRootNode);

    }

    private void getItemName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = placeOrderBO.getItemName();

            for (String code : codeList) {
                obList.add(code);
            }
            TextFields.bindAutoCompletion(txtItemNameSearch,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction();
    }

    private void setDateAndTime() {
        LocalDate nowDate = LocalDate.now();
        lblOrderDate.setText(String.valueOf(nowDate));

        Time nowTime = Time.valueOf(LocalTime.now());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm");
        String time = simpleDateFormat1.format(nowTime);
        lblOrderTime.setText(time);
    }

    private void getCustomerTel() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = placeOrderBO.getCustomerTel();

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

    private String getCurrentOrderId() {
        String nextOrderId = "";

        try {
            nextOrderId = placeOrderBO.generateOrderID();
            lblOrderCode.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return nextOrderId;
    }


    private void getTransportLocation() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> locationList = placeOrderBO.getlocation();

            for(String location : locationList) {
                obList.add(location);
            }

            TextFields.bindAutoCompletion(txtLocation,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddLocationOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane supRootNode = FXMLLoader.load(this.getClass().getResource("/view/transport_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(supRootNode);
    }

    private void getPMethod() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Cash");
        obList.add("Card");

        cmbPaymentMethod.setItems(obList);

    }

    String email ;

    @FXML
    void txtCustomerIdOnAction(ActionEvent actionEvent) {

        String tel = txtCustomerTel.getText();

        try {
            CustomerDTO customer = placeOrderBO.searchCustomer(tel);

            lblCustName.setText(customer.getCName());
            lblCustomerId.setText(customer.getCustId());
            email = customer.getCEmail();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        txtLocation.requestFocus();
    }

    public void txtItemNameSearchOnAction(ActionEvent actionEvent) {
        btnItemNameSearchOnAction();
    }

    public void btnItemNameSearchOnAction() {
        String name = txtItemNameSearch.getText();

        try {
            ItemDTO item = placeOrderBO.searchItem(name);
            ItemSupplierDetailDTO itemDetail = placeOrderBO.searchItemSuppliers(item.getItemId());
            if(item != null) {
                lblItemName.setText(item.getName());
                lblItemId.setText(item.getItemId());
                Image image = new Image(item.getPath(),281, 178, false, true);
                imageCart.setImage(image);
                lblCatagory.setText(item.getCategory());
                lblUnitPrice.setText(String.valueOf(itemDetail.getUnitPrice()));
                lblHandOnQty.setText(String.valueOf(itemDetail.getQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtLocationOnAction(ActionEvent actionEvent) {
        String location = txtLocation.getText();

        try {
            Transport tr = placeOrderBO.searchByLocation(location);
            if(tr != null) {
                lblTrId.setText(tr.getTrId());
                lblTransportCost.setText(String.valueOf(tr.getCost()));

            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        txtItemNameSearch.requestFocus();
    }

    //Get Order Receipt --------------------------------------------------------------------------------------------------------
    public void btnOrderReceiptOnAction(ActionEvent actionEvent) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/OrderBill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("oId",getLastOrderId());
        data.put("netAm", String.valueOf(gettNetTotal()));

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

        JasperExportManager.exportReportToPdfFile(jasperPrint,"src/main/resources/report/OrderBill.pdf");


        SendMail.sendEmail(email, "src/main/resources/report/OrderBill.pdf",2);
    }

    private double gettNetTotal() {
        double netTotal = 0.0;
        try {
            System.out.println(getLastOrderId());
            netTotal = placeOrderBO.getNetTot(getLastOrderId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return netTotal;
    }

    private String getLastOrderId(){
        String orderId;
        try {
            orderId = placeOrderBO.getLastOrderId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return orderId;
    }

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
            txtCustomerTel.setText(qrResultModel.getResult());
        }).start();

        txtCustomerTel.requestFocus();
    }
}
