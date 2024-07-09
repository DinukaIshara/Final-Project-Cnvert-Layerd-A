package lk.ijse.chama.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.chama.bo.BOFactory;
import lk.ijse.chama.bo.custom.UserBO;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.util.validation.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationFormController {

    public AnchorPane rootNode;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USERBO);

    @FXML
    void logInOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage)this.rootNode.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Log In");

        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void registrationOnAction() {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            if(isValied()){ // Add Validation
                boolean isSaved = userBO.saveUser(userName,password);//saveUser(userName, password);
                if(isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
                    callLogIn();
                }
            }else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void userNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    public void callLogIn() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage)this.rootNode.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Log In");

        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void txtPasswordOnAction() {
        registrationOnAction();
    }


    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.NAME,txtUserName)) return false;
        if (!Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PASSWORD,txtPassword)) return false;
        return true;
    }

    public void txtUserNameOnKeyAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.NAME,txtUserName);
    }

    public void txtPasswordOnKeyAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.validation.TextField.PASSWORD,txtPassword);
    }
}