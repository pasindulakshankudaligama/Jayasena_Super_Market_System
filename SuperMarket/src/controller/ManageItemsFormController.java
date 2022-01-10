package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import view.tm.CartTm;
import view.tm.ItemTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageItemsFormController {
    public AnchorPane manageItemsContext;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPacketSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TableView<ItemTm> tblAddItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPacketSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colDiscount;
    public TextField txtDiscount;
    public Button btnDeleteItem;
    public Button btnAddItem;
    public Button btnClearFeild;
    public Button btnEdit;

    int SelectedRow=-1;

    public void initialize() {

        try {
            showItemInTable();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnEdit.setDisable(true);
        btnDeleteItem.setDisable(true);

        tblAddItem.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> { SelectedRow = (int) newValue;

            if (SelectedRow == -1) {
                btnAddItem.setDisable(false);
                btnEdit.setDisable(true);
                btnEdit.setDisable(true);
                txtItemCode.setEditable(true);

            }else{
                btnAddItem.setDisable(true);
                btnEdit.setDisable(false);
                btnDeleteItem.setDisable(false);
                txtItemCode.setEditable(false); }
        });

    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        int packetSize = Integer.parseInt(txtPacketSize.getText());
        double unitPrize = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int discount = Integer.parseInt(txtDiscount.getText());

        ItemTm i1 = new ItemTm(
                itemCode,
                description,
                packetSize,
                unitPrize,
                qtyOnHand,
                discount


        );
        getItemList().add(i1);

        tblAddItem.setItems(getItemList());

        Item i = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                Integer.parseInt(txtPacketSize.getText()),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText()),
                Integer.parseInt(txtDiscount.getText())

        );

        clearFeild();

        if (new ItemController().getItems(i))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();

        showItemInTable();
    }

    private void clearFeild() {
        txtItemCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtPacketSize.clear();
        txtQtyOnHand.clear();
        txtDiscount.clear();
        txtItemCode.requestFocus();

        btnAddItem.setDisable(false);
        btnEdit.setDisable(true);
        btnEdit.setDisable(true);
        txtItemCode.setEditable(true);
    }

    public void clearFeildOnAction(ActionEvent actionEvent) {
        clearFeild();

    }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemTm item = tblAddItem.getSelectionModel().getSelectedItem();
        String itemCode = item.getCode();

        if (new ItemController().deleteItem(itemCode)) {

            new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                showItemInTable();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
        clearFeild();
    }

    public ObservableList<ItemTm> getItemList() throws SQLException, ClassNotFoundException {

        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM Item";
        PreparedStatement stm = con.prepareStatement(query);
        ResultSet rst = stm.executeQuery();

        while (rst.next()) {
            obList.add(new ItemTm(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6)
            ));

        }
        return obList;
    }
    public void showItemInTable() throws SQLException, ClassNotFoundException {

        ObservableList<ItemTm> list = getItemList();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPacketSize.setCellValueFactory(new PropertyValueFactory<>("packetSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tblAddItem.setItems(list);
    }

    public void handleMouseOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ItemTm item = null;
         try{
             item  = tblAddItem.getSelectionModel().getSelectedItem();
             txtItemCode.setText(item.getCode());
             txtUnitPrice.setText(""+item.getUnitPrice());
             txtDescription.setText(item.getDescription());
             txtQtyOnHand.setText(""+item.getQtyOnHand());
             txtDiscount.setText(""+item.getDiscount());
             txtPacketSize.setText(""+item.getPacketSize());
         } catch (Exception e) {

         }
    }
    public void btnEditAndUpdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        ItemTm i1 = new ItemTm(
                txtItemCode.getText(),
                txtDescription.getText(),
                Integer.parseInt(txtPacketSize.getText()),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText()),
                Integer.parseInt(txtDiscount.getText()));

        if (new ItemController().updateItem(i1)) {

            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            showItemInTable();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();

        }
    }
    public void backReportsAndManageItemsForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageAndReportLoadForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) manageItemsContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void backLogInForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) manageItemsContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void moveDescription(ActionEvent actionEvent) {
        txtDescription.requestFocus();
    }

    public void movePacketSize(ActionEvent actionEvent) {
        txtPacketSize.requestFocus();
    }

    public void moveUnitPrice(ActionEvent actionEvent) {
        txtUnitPrice.requestFocus();
    }

    public void moveQtyOnHand(ActionEvent actionEvent) {
        txtQtyOnHand.requestFocus();
    }

    public void moveDiscount(ActionEvent actionEvent) {
        txtDiscount.requestFocus();
    }
}


