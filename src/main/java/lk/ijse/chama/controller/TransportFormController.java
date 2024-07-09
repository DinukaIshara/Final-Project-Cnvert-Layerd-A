package lk.ijse.chama.controller;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.bo.BOFactory;
import lk.ijse.chama.bo.custom.TransportBO;
import lk.ijse.chama.dto.TransportDTO;
import lk.ijse.chama.entity.Location;
import lk.ijse.chama.entity.Transport;
import lk.ijse.chama.entity.tm.TransportTm;
import lk.ijse.chama.repository.LocationRepo;
import lk.ijse.chama.repository.TransportRepo;
import lk.ijse.chama.util.validation.Regex;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TransportFormController {

    public TextField txtSearchLocation;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colDriverName;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colTransportId;

    @FXML
    private TableColumn<?, ?> colVehicalNo;

    @FXML
    private AnchorPane mapRootNode;

    @FXML
    private TableView<TransportTm> tblTransport;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtDriverName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtVehicalNo;

    private MapPoint eiffelPoint = new MapPoint(6.711053811499971, 79.9097716129893);

    TransportBO transportBO = (TransportBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRANSPORTBO);

    public void initialize() {
        setCellValueFactory();
        loadAllTransport();
        getPlaces();
        getLoaction();
        getCurrentId();

        try {
            MapView mapView = crateMapView();
            mapRootNode.getChildren().add(mapView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colTransportId.setCellValueFactory(new PropertyValueFactory<>("trId"));
        colVehicalNo.setCellValueFactory(new PropertyValueFactory<>("vehicalNo"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private void loadAllTransport() {
        ObservableList<TransportTm> obList = FXCollections.observableArrayList();

        try {
            List<TransportDTO> transportList = transportBO.getAllTransport();//TransportRepo.getAll();
            for (TransportDTO transport : transportList) {
                TransportTm tm = new TransportTm(
                        transport.getTrId(),
                        transport.getVehicalNo(),
                        transport.getDriverName(),
                        transport.getLocation(),
                        transport.getCost()
                );

                obList.add(tm);
            }

            tblTransport.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String trId = txtId.getText();
        String vehicalNo = txtVehicalNo.getText();
        String driverName = txtDriverName.getText();
        String location = txtLocation.getText();
        double cost = Double.parseDouble(txtCost.getText());

        TransportDTO transport = new TransportDTO(trId,vehicalNo,driverName,location,cost);

        //if(isValidate()) {
        try {

                boolean isSaved = transportBO.saveTransport(transport);//TransportRepo.save(transport);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Transport saved!").show();
                    clearFields();
                    initialize();
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        /*}else{
            new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
        }*/
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update Transport?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String trId = txtId.getText();
            String vehicalNo = txtVehicalNo.getText();
            String driverName = txtDriverName.getText();
            String location = txtLocation.getText();
            double cost = Double.parseDouble(txtCost.getText());

            TransportDTO transport = new TransportDTO(trId, vehicalNo, driverName, location, cost);

            try {
                //if(isValidate()) {
                boolean isSaved = transportBO.updateTransport(transport);//TransportRepo.update(transport);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Transport updated!").show();
                    clearFields();
                    initialize();
                }
            /*}else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }*/
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete Supplier?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String id = txtId.getText();

            try {
                boolean isDeleted = transportBO.deleteTransport(id);//TransportRepo.delete(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Transport deleted!").show();
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

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtDriverName.setText("");
        txtVehicalNo.setText("");
        txtLocation.setText("");
        txtCost.setText("");
        txtSearchLocation.setText("");
    }

    private String getCurrentId() {
        String nextId = "";

        try {
            String currentId = "";TransportRepo.getLastId();

            nextId = transportBO.generateNewID(currentId);//generateNextId(currentId);
            txtId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return nextId;
    }

    private String generateNextId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("T");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);

            if(idNum >= 1){
                return "T" + 0 + 0 + ++idNum;
            }else if(idNum >= 9){
                return "T" + 0 + ++idNum;
            } else if(idNum >= 99){
                return "T" + ++idNum;
            }
        }
        return "T001";
    }

    public void getPlaces(){  // Location Table place get
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> placeList = transportBO.getPlace();//LocationRepo.getPlace();

            for(String place : placeList) {
                obList.add(place);
            }

            TextFields.bindAutoCompletion(txtLocation, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public MapView crateMapView() throws SQLException, ClassNotFoundException {
        MapView mapView = new MapView();
        List<String> validLocations = transportBO.getPlace();//LocationRepo.getPlace();
        if (validLocations.contains(txtLocation.getText())) {
            Location location = LocationRepo.searchByPath(txtLocation.getText());
            System.out.println(location.getLatitude());
            System.out.println(location.getLongitude());
            eiffelPoint = new MapPoint(location.getLatitude(), location.getLongitude());

            mapView.setPrefSize(446, 487);
            mapView.setZoom(15);
            mapView.flyTo(0, eiffelPoint, 0.1);

            return mapView;

        }else if (!(txtLocation.getText().equals(null))) {

            mapView.setPrefSize(446, 487);
            mapView.setZoom(15);
            mapView.flyTo(0, eiffelPoint, 0.1);

            System.out.println("!null");
            return mapView;
        }
        return mapView;
    }

    public void txtSearchLocationOnAction(ActionEvent actionEvent) {
        try {
            btnSearchLocationOnAction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getLoaction() {  // Transport Table Location Loads
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> locationList = transportBO.getlocation();//TransportRepo.getlocation();

            for (String location : locationList) {
                obList.add(location);
            }
            TextFields.bindAutoCompletion(txtSearchLocation, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchLocationOnAction() throws SQLException, ClassNotFoundException {
        String location = txtSearchLocation.getText();

        Transport tr = transportBO.searchByLocation(location);//TransportRepo.searchByLocation(location);
        if (tr != null) {
            txtId.setText(tr.getTrId());
            txtVehicalNo.setText(tr.getVehicalNo());
            txtDriverName.setText(tr.getDriverName());
            txtLocation.setText(tr.getLocation());
            txtCost.setText(String.valueOf(tr.getCost()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Location not found!").show();
        }
    }

    public void txtTrIdOnAction(ActionEvent actionEvent) {
        txtVehicalNo.requestFocus();
    }

    public void txtVehicalNoOnAction(ActionEvent actionEvent) {
        txtDriverName.requestFocus();
    }

    public void txtDriverNameOnAction(ActionEvent actionEvent) {
        txtLocation.requestFocus();
    }

    public void txtLocationOnAction(ActionEvent actionEvent) {

        try {
            MapView mapView = crateMapView();
            mapRootNode.getChildren().add(mapView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        txtCost.requestFocus();
    }

    public void txtTrIdOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.TID,txtId);
    }

    public void txtVehicalNoOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.VEHICALNO,txtVehicalNo);
    }

    public void txtcostOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PRICE,txtCost);
    }

    public boolean isValidate(){
        if (!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.TID,txtId))return false;
        if (!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.VEHICALNO,txtVehicalNo))return false;
        if (!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PRICE,txtCost))return false;

        return false;
    }
}