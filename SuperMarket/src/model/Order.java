package model;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String orderDate;
    private String customerId;
    private ArrayList<ItemDetails> items;

    public Order() {
    }

    public Order(String orderId, String orderDate, String customerId, ArrayList<ItemDetails> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.items = items;
    }



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<ItemDetails> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetails> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", customerId='" + customerId + '\'' +
                ", items=" + items +
                '}';
    }
}
