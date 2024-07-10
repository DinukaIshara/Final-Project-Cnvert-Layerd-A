package lk.ijse.chama.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.bo.BOFactory;
import lk.ijse.chama.bo.custom.DashboardBO;
import lk.ijse.chama.dto.CustomDTO;
import lk.ijse.chama.dto.CustomerDTO;
import lk.ijse.chama.dto.ItemDTO;
import lk.ijse.chama.dto.OrderDTO;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DashboardFormController {

    public TableView tblMostSellItems;

    public Label txtMonthlyProfit;

    @FXML
    private Label labCutCount;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblOrderCount;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colOrderCount;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private TextField txtOrderDate;

    @FXML
    private Label lblItemQty;

    @FXML
    private Label lblOrderCountlab;

    DashboardBO dashboardBO = (DashboardBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.DASHBOARDBO);

    public void initialize() {
        loadCustomerCount();
        loadOrderCount();
        loadMostSellItemTable();
        loadAll();
        getOrderDate();
        setMonthlyProfit();

        try {
            barChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pieChartConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMostSellItemTable() {
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colOrderCount.setCellValueFactory(new PropertyValueFactory<>("orderCount"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("sumQty"));
    }

    private void loadAll() {
        ObservableList<CustomDTO> obList = FXCollections.observableArrayList();

        try {
            List<CustomDTO> itemList = dashboardBO.getMostSellItem();
            ItemDTO item;
            for (CustomDTO sellItem : itemList) {
                item = dashboardBO.searchItemById(sellItem.getItemId());
                CustomDTO tm = new CustomDTO(
                        item.getName(),
                        sellItem.getOrderCount(),
                        sellItem.getSumQty()
                );

                obList.add(tm);
            }

            tblMostSellItems.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerCount() { // Check Customer Count
        int count = 0;
        try {
            List<CustomerDTO> customerList = dashboardBO.getAllCustomers();
            for (CustomerDTO cust : customerList) {

                count ++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        labCutCount.setText(String.valueOf(count));
    }

    private void loadOrderCount() {
        int count = 0;
        try {
            List<OrderDTO> orderList = dashboardBO.getAllOrders();
            for (OrderDTO order : orderList) {

                count ++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        lblOrderCount.setText(String.valueOf(count));
    }

    // Pie Chart --------------------------------------------------------------------------------------------------------------------------------------------------
    public void pieChartConnect() throws SQLException,ClassNotFoundException {
        List<CustomDTO> itemList = dashboardBO.getMostSellItem();
        ItemDTO item;
        for (CustomDTO sellItem : itemList) {
            item = dashboardBO.searchItemById(sellItem.getItemId());

            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data(item.getName(), sellItem.getSumQty())
                    );
            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), "  amount: ", data.pieValueProperty()
                            )
                    )
            );

            pieChart.getData().addAll(pieChartData);
        }
    }

    // Bar Chart -----------------------------------------------------------------------------------------------------------------------------------------------------
    public void barChart() throws SQLException {
        try {
            XYChart.Series series1 = dashboardBO.getBarChart();
            barChart.getData().addAll(series1);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void getOrderDate() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> dateList = dashboardBO.getAllDate();

            for (String date : dateList) {
                obList.add(date);
            }
            TextFields.bindAutoCompletion(txtOrderDate, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOrderDateOnAction() throws SQLException,ClassNotFoundException {
        Date date = java.sql.Date.valueOf(txtOrderDate.getText());

        CustomDTO dailyOrders = dashboardBO.orderDaily(date);

        lblOrderCountlab.setText(String.valueOf(dailyOrders.getOrderCount()));
        lblItemQty.setText(String.valueOf(dailyOrders.getSumQty()));
    }

    public void txtOrderDateOnAction(ActionEvent actionEvent) throws SQLException,ClassNotFoundException {
        btnSearchOrderDateOnAction();
    }

    public void setMonthlyProfit(){
        double monthlyProfit;
        try {
            monthlyProfit = dashboardBO.getLastMonthIncome();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }  catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtMonthlyProfit.setText(String.valueOf(monthlyProfit));
    }
}
