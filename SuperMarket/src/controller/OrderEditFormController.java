package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
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
import view.tm.ItemTm;

import javax.naming.Binding;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderEditFormController {
    public ComboBox<String> cmbCustomerId;
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtAddress;
    public TableView<CartTm> tblOrder;
    public TableColumn colOrderItemCode;
    public TableColumn colOrderDescription;
    public TableColumn colOrderQTY;
    public TableColumn colOrderTotal;
    public TableColumn colOrderDiscountPrice;
    public TextField txtTotalAmount;
    public TextField txtSaveAmount;
    public TextField txtAmountPaid;
    public TextField txtRemainingBalance;
    public AnchorPane orderEditContext;
    public Button cancelBtn;
    public Button confirmOrder;
    public Label lblDate;
    public Label lblTime;
    public Label lblOrderId;
    ArrayList<CartTm> arrayList;
    static ObservableList<CartTm> parentObList =FXCollections.observableArrayList();
    double netPrice;

    public static ObservableList getCart(){
        return parentObList;
    }
    public void initialize(){
        colOrderItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colOrderDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colOrderQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOrderTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOrderDiscountPrice.setCellValueFactory(new PropertyValueFactory<>("discount"));


        loadDateAndTime();
        setOrderId();
        try {
            loadCustomerIds();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Bindings.bindContentBidirectional(parentObList,OrderFormController.getCart());
        arrayList= new ArrayList<>(parentObList);


        tblOrder.setItems(parentObList);

        for (CartTm tm:tblOrder.getItems()){
            netPrice+=tm.getTotal();
        }
        txtTotalAmount.setText(new DecimalFormat("0.00").format(netPrice)+" /=");

        int qty = 0;
        double main = 0.00;
        double unitPrice=0.00;

        for (CartTm tm:tblOrder.getItems()) {
            qty+=tm.getQty();
            unitPrice+=tm.getUnitPrice();
            main+=qty * unitPrice;

        }
        txtSaveAmount.setText(new DecimalFormat("0.00").format(main-netPrice)+" /=");
    }

    private void setCustomerData(String id) throws SQLException, ClassNotFoundException {
        Customer c1 =new CustomerController().passCustomerDetails(id);
        if(c1 == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();

        }else{
            txtCustomerId.setText(c1.getCustomerTitle());
            txtCustomerName.setText(c1.getName());
            txtAddress.setText(c1.getAddress());
        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> id = new CustomerController().getAllCustomerIds();
        cmbCustomerId.getItems().addAll(id);
    }

    public void confirmOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Stage stage = (Stage) confirmOrder.getScene().getWindow(); stage.close();
          ArrayList<ItemDetails> items = new ArrayList<>();
        for (CartTm tempTm : parentObList
        ) {
            items.add(
                    new ItemDetails(
                            tempTm.getCode(),
                            lblOrderId.getText(),
                            tempTm.getQty(),
                            tempTm.getUnitPrice()
                    ));
        }
        Order order = new Order(
                lblOrderId.getText(),
                lblDate.getText(),
                cmbCustomerId.getValue(),
                items

        );
        if (new OrderController().
                placeOrder(order)){
            new Alert(Alert.AlertType.CONFIRMATION,"Success").show();
            setOrderId();

        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }

    }

    public void moveReamainingBalance(ActionEvent actionEvent) {
        int amountPaidText = Integer.parseInt(txtAmountPaid.getText());
        txtRemainingBalance.setText(new DecimalFormat("0.00").format(amountPaidText-netPrice)+" /=");

        txtRemainingBalance.requestFocus();
    }

    private void setOrderId() {
        try {
            lblOrderId.setText(new OrderController().getOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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

    public void moveSaveAmount(ActionEvent actionEvent) {
        txtSaveAmount.requestFocus();
    }

    public void moveAmountPaid(ActionEvent actionEvent) {
        txtAmountPaid.requestFocus();
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cancelBtn.getScene().getWindow(); stage.close();
    }


}
