package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ManageAndReportLoadFormController {
    public AnchorPane manageAndReportsContext;

    public void openManageItemsForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItemsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) manageAndReportsContext.getScene().getWindow();
        window.setScene(new Scene(load));

    }

    public void openSystemReports(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SystemReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) manageAndReportsContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
