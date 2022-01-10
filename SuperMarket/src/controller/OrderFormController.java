package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.ItemDetails;
import model.Order;
import view.tm.CartTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderFormController {

    public AnchorPane orderFormContext;
    public Label lblDate;
    public Label lblTime;
    public TextField txtPacketSize;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public TextField txtDiscount;
    public ComboBox<String> cmbItemCode;
    public TableView<CartTm> tblItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQTY;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblTotal;

   public static ObservableList<CartTm> obList = FXCollections.observableArrayList();


    int cartSelectedRowForRemove = -1;
    public static ObservableList getCart()
    {
        return obList;
    }

    public void initialize() {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();

        try {
            loadItemIds();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        tblItem.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        Item i1 = new ItemController().getItem(itemCode);

        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(i1.getDescription());
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtPacketSize.setText(String.valueOf(i1.getPacketSize()));
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtDiscount.setText(String.valueOf(i1.getDiscount()));

        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = new ItemController().getAllItemIds();
        cmbItemCode.getItems().addAll(itemIds);
    }

    public void addToCartOnAction(ActionEvent actionEvent) {

        String description = txtDescription.getText();
        int packetSize = Integer.parseInt(txtPacketSize.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        int discount = Integer.parseInt(txtDiscount.getText());
        double total = qtyForCustomer * unitPrice - qtyForCustomer * unitPrice * discount / 100;

        if (qtyOnHand < qtyForCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }


        CartTm tm = new CartTm(
                cmbItemCode.getValue(),
                description,
                qtyForCustomer,
                unitPrice,
                discount,
                total

        );

        int rowNumber = isExists(tm);

        if (rowNumber == -1) {
            obList.add(tm);

        } else {

            CartTm temp = obList.get(rowNumber);

            CartTm newTm = new CartTm(
                    temp.getCode(),
                    temp.getDescription(),
                    temp.getQty() + qtyForCustomer,
                    unitPrice,
                    temp.getDiscount(),
                    total + temp.getTotal()

            );
            if (qtyOnHand < temp.getQty() + qtyForCustomer) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }

            obList.remove(rowNumber);
            obList.add(newTm);
        }

        tblItem.setItems(obList);
        calculateCost();
        clearFeild();
    }

    private int isExists(CartTm tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getCode().equals(obList.get(i).getCode())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        double ttl = 0;
        for (CartTm tm : obList
        ) {
            ttl += tm.getTotal();
        }
        String formatTotal = new DecimalFormat("0.00").format(ttl);
        lblTotal.setText(formatTotal + " /=");
    }

    public void clearOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblItem.refresh();
        }
        clearFeild();
    }

    public void clearFeild() {
        txtDescription.clear();
        txtPacketSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtQty.clear();
        txtDiscount.clear();
        cmbItemCode.setValue("");
        cmbItemCode.requestFocus();
        tblItem.getSelectionModel().clearSelection();
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) {
        clearFeild();
        lblTotal.setText("");
        obList.clear();
    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws  IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void openManageCustomerOrder(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageCustomerOrdersForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Manage Customer Order");
        stage.show();

    }

    public void openOrderEditForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/OrderEditForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Order Edit Form");
        stage.show();

    }

    public void backLogInForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) orderFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
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

    public void moveQTY(ActionEvent actionEvent) {
        txtQty.requestFocus();
    }

    public void moveDiscount(ActionEvent actionEvent) {
        txtDiscount.requestFocus();
    }
}


