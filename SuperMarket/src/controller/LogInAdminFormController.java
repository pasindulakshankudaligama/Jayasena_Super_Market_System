package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LogInAdminFormController {
    public AnchorPane LogInFormContext;
    public TextField userNameContext;
    public PasswordField passwordContext;
    public Button cancelBtnContext;
    public Label lblAttempt;

    public void openOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        if (userNameContext.getText().equals("") & passwordContext.getText().equals("")){

            URL resource = getClass().getResource("../view/ManageAndReportLoadForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window   = (Stage) LogInFormContext.getScene().getWindow();
            window.setScene(new Scene(load));



        }else{
            lblAttempt.setVisible(true);
        }
    }

    public void backLogInForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) LogInFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void movePassword(ActionEvent actionEvent) {
        passwordContext.requestFocus();
    }
}
