package controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Item;
import model.ItemDetails;
import view.tm.CartTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageCustomerOrdersFormController {

    public TextField txtSearch;
    public TableView tblGetDetails;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colNetTotal;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtQty;
    public TextField txtDiscount;
    public TextField txtNetTotal;
    public Button btnCancelContext;
    public Label lblCD;
    public ComboBox cmbOrderId;

    static ObservableList<CartTm> observableList = FXCollections.observableArrayList();
    public TableColumn colOrderId;
    public TableColumn colUnitPrice;
    public TextField txtUnitPrice;
    public TextField txtOrderId;

    public void initialize() {
       /* colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colNetTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        Bindings.bindContentBidirectional(observableList, OrderEditFormController.getCart());

        tblGetDetails.setItems(observableList);*/

        try {
            loadItemIds();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = new OrderController().getAllOrderIds();
        cmbOrderId.getItems().addAll(itemIds);
    }

    public void btnConfimEditOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       /* ItemDetails item = new ItemDetails(
                txtItemCode.getText(),
                txtDescription.getText(),
                "",
                ""
        );
        CartTm c1 = new CartTm(
                txtItemCode.getText(),
                txtDescription.getText(),
                Integer.parseInt(txtQty.getText()),
                "",
                "",
                ""
          txtItemCode.getText(),
                txtDescription.getText(),
                Integer.parseInt(txtQty.getText()),
               Double.parseDouble(txtNetTotal.getText()),
                Integer.parseInt(txtDiscount.getText()),
                Double.parseDouble(txtNetTotal.getText())

        );
        if(new OrderController().updateOrder(c1)){
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "TryAgain..").show();
        }
    }*/

    }

    public void cancelOrderOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelContext.getScene().getWindow();
        stage.close();
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }

    public void addDataOnAction(MouseEvent mouseEvent) {
        CartTm cart = null;
        try {
            cart = (CartTm) tblGetDetails.getSelectionModel().getSelectedItem();
            txtItemCode.setText(cart.getCode());
            txtDescription.setText(cart.getDescription());
            txtQty.setText("" + cart.getQty());
            txtDiscount.setText("" + cart.getDiscount());
            txtNetTotal.setText("" + cart.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnConfrimEditOnAction(ActionEvent actionEvent) {
    }
}
