package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.sql.SQLException;

public class AddCustomerFormController {

    public TextField txtCustomerId;
    public TextField txtCustomerTitle;
    public TextField txtCustomerName;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public Button addCustomer;


    public void addCustomerFormOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
                Customer c1 = new Customer(
                        txtCustomerId.getText(),
                        txtCustomerTitle.getText(),
                        txtCustomerName.getText(),
                        txtAddress.getText(),
                        txtCity.getText(),
                        txtProvince.getText(),
                        txtPostalCode.getText()
        );
        if(new CustomerController().getCustomers(c1))
            new Alert(Alert.AlertType.CONFIRMATION,"Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();

        Stage stage = (Stage) addCustomer.getScene().getWindow(); stage.close();

    }

    public void moveCustomerTitle(ActionEvent actionEvent) {
        txtCustomerTitle.requestFocus();
    }

    public void moveCustomerName(ActionEvent actionEvent) {
        txtCustomerName.requestFocus();
    }

    public void moveAddress(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void moveCity(ActionEvent actionEvent) {
        txtCity.requestFocus();
    }

    public void moveProvince(ActionEvent actionEvent) {
        txtProvince.requestFocus();
    }

    public void movePostalCode(ActionEvent actionEvent) {
        txtPostalCode.requestFocus();
    }
}
