package model;

public class ItemDetails {
    private String itemCode;
    private String orderId;
    private int qtyOnSell;
    private double unitPrice;


    public ItemDetails() {
    }

    public ItemDetails(String itemCode, String orderId, int qtyOnSell, double unitPrice) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setQtyOnSell(qtyOnSell);
        this.setUnitPrice(unitPrice);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQtyOnSell() {
        return qtyOnSell;
    }

    public void setQtyOnSell(int qtyOnSell) {
        this.qtyOnSell = qtyOnSell;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", qtyOnSell=" + qtyOnSell +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
